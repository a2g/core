package com.github.a2g.core.authoredroom;

import com.github.a2g.core.loader.ILoadImageBundle;

public class LoaderAPI 
{
	IAmTheLoaderAPI implementation;
	public LoaderAPI(IAmTheLoaderAPI impl)
	{
		this.implementation = impl;
	}
			
	public void addEssential(ILoadImageBundle imageBundle)
	{
		this.implementation.addEssential(imageBundle);
	}
	
	public void addNonEssential(ILoadImageBundle imageBundle)
	{
		this.implementation.addNonEssential(imageBundle);
	}
	
	public void setWorldViewSize(int width, int height)
	{
		this.implementation.setWorldViewSize(width,height);
	}
	public LoadKickStarter createLoadKickStarter()
	{
		this.implementation.kickStartLoading();
		return new LoadKickStarter();
	}
	
	public class LoadKickStarter
	{
		private LoadKickStarter(){};
	}
}


