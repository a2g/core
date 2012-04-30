package com.github.a2g.core.authoredroom;

import com.github.a2g.core.loader.ILoadImageBundle;

public interface IAmTheLoaderAPI 
{
	//void setDoDownloadsViaProgressBarBefore(boolean doDownloads);
	void addEssential(ILoadImageBundle blah);
	void addNonEssential(ILoadImageBundle blah);
	void doIt();
	void setWorldViewSize(int width, int height);
}
