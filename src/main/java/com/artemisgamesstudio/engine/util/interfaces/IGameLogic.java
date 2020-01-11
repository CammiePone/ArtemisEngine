package com.artemisgamesstudio.engine.util.interfaces;

import com.artemisgamesstudio.engine.render.Window;

public interface IGameLogic
{
    void init(Window window) throws Exception;

    void input(Window window);

    void update(float interval);

    void render(Window window);

    void cleanup();
}
