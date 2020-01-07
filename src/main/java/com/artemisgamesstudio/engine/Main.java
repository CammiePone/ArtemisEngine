package com.artemisgamesstudio.engine;

import com.artemisgamesstudio.engine.core.kernel.CoreEngine;

public class Main
{
	protected CoreEngine engine;
	
	public Main()
	{
		engine = new CoreEngine();
	}
	
	public void launch()
	{
		engine.start();
	}
	
	public void init()
	{
		engine.init();
	}
	
	public CoreEngine getEngine()
	{
		return engine;
	}
	public void setEngine(CoreEngine engine)
	{
		this.engine = engine;
	}
}
