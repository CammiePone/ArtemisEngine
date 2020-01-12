package com.artemisgamesstudio.engine;

import com.artemisgamesstudio.engine.render.Window;
import com.artemisgamesstudio.engine.util.MouseInput;
import com.artemisgamesstudio.engine.util.interfaces.IGameLogic;
import com.artemisgamesstudio.engine.util.Timer;
import org.lwjgl.glfw.GLFW;

public class Engine implements Runnable
{
    public static final int TARGET_FPS = 120;
    public static final int TARGET_UPS = 25;
    public static double ups;
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
        float elapsedTime;
        float accumulator = 0F;
        float interval = 1F / TARGET_UPS;
        boolean running = true;

        while(running && !window.windowShouldClose())
        {
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
        float loopSlot = 1F / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;

        while(timer.getTime() < endTime)
        {
            try
            {
                Thread.sleep(1);
            }
            catch (InterruptedException e)
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
