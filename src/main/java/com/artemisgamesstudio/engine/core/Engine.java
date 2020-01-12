package com.artemisgamesstudio.engine.core;

import com.artemisgamesstudio.engine.core.io.Window;
import com.artemisgamesstudio.engine.core.io.MouseInput;
import com.artemisgamesstudio.engine.core.util.interfaces.IGameLogic;
import com.artemisgamesstudio.engine.core.util.Timer;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Engine implements Runnable
{
    public static final int TARGET_FPS = Integer.MAX_VALUE;
    public static final int TARGET_UPS = 25;
    private final Window window;
    private final Timer timer;
    private final IGameLogic gameLogic;
    private final MouseInput mouseInput;

    public Engine(String windowTitle, int width, int height, boolean vSync, IGameLogic gameLogic) throws Exception
    {
        window = new Window(windowTitle, width, height, vSync);
        mouseInput = new MouseInput();
        this.gameLogic = gameLogic;
        timer = new Timer();
    }

    @Override
    public void run()
    {
        try
        {
            init();
            gameLoop();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            gameLogic.cleanup();
        }
    }

    protected void init() throws Exception
    {
        window.init();
        timer.init();
        mouseInput.init(window);
        gameLogic.init(window);
    }

    protected void gameLoop()
    {
        double previousTime = glfwGetTime();
        int frameCount = 0;

        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;
        boolean running = true;

        while(running && !window.windowShouldClose())
        {
            double currentTime = glfwGetTime();
            frameCount++;

            // If a second has passed.
            if(currentTime - previousTime >= 1.0f)
            {
                // Display the frame count here any way you want.
                System.out.println(frameCount);

                frameCount = 0;
                previousTime = currentTime;
            }

            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while(accumulator >= interval)
            {
                update(interval);
                accumulator -= interval;
            }

            render();

            if(!window.isvSync())
            {
                sync();
            }
        }
    }

    private void sync()
    {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;

        while(timer.getTime() < endTime)
        {
            try
            {
                Thread.sleep(1);
            }
            catch(InterruptedException e)
            {
            }
        }
    }

    protected void input()
    {
        mouseInput.input(window);
        gameLogic.input(window, mouseInput);
    }

    protected void update(float interval)
    {
        gameLogic.update(interval, mouseInput);
    }

    protected void render()
    {
        gameLogic.render(window);
        window.update();
    }
}
