package com.artemisgamesstudio.engine.core.util.interfaces;

import com.artemisgamesstudio.engine.core.io.Window;
import com.artemisgamesstudio.engine.core.io.MouseInput;

public interface IGameLogic
{
    void init(Window window) throws Exception;

    void input(Window window, MouseInput mouseInput);

    void update(float interval, MouseInput mouseInput);

    void render(Window window);

    void cleanup();
}
