package com.github.a2g.core.authoredscene;

import com.github.a2g.core.loader.ImageBundleLoaderAPI;

public class OnFillLoadListAPIImpl 
{
	OnFillLoadListAPI implementation;
	public OnFillLoadListAPIImpl(OnFillLoadListAPI impl)
	{
		this.implementation = impl;
	}
			
	public void addEssential(ImageBundleLoaderAPI imageBundle)
	{
		this.implementation.addEssential(imageBundle);
	}
	
	public void addNonEssential(ImageBundleLoaderAPI imageBundle)
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


