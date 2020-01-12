package com.artemisgamesstudio.engine.util.interfaces;

import com.artemisgamesstudio.engine.render.Window;
import com.artemisgamesstudio.engine.util.MouseInput;

public interface IGameLogic
{
    void init(Window window) throws Exception;

    void input(Window window, MouseInput mouseInput);

    void update(float interval, MouseInput mouseInput);

    void render(Window window);

    void cleanup();
}
