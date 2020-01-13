package com.artemisgamesstudio.engine;

import com.artemisgamesstudio.engine.core.Engine;
import com.artemisgamesstudio.engine.core.util.interfaces.IGameLogic;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            boolean vSync = true;
            IGameLogic gameLogic = new DummyGame();
            Engine engine = new Engine("ArtemisEngine", 1280, 720, vSync, gameLogic);
            engine.run();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
