package com.artemisgamesstudio.engine.core.kernel;

import com.artemisgamesstudio.engine.core.kernel.Window;
import com.artemisgamesstudio.engine.core.kernel.Camera;

/**
 * 
 * @author oreon3D
 * The RenderingEngine manages the render calls of all 3D entities
 * with shadow rendering and post processing effects
 *
 */
public class RenderingEngine {
	
	private Window window;
	
	public RenderingEngine()
	{
		window = Window.getInstance();
	}
	
	public void init()
	{
		window.init();
	}

	public void render()
	{	
		Camera.getInstance().update();
		
		// draw into OpenGL window
		window.render();
	}
	
	public void update(){}
	
	public void shutdown(){}
}
