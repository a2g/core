package com.github.a2g.core.authoredroom;

import com.github.a2g.core.loader.ILoadImageBundle;

public interface OnFillLoadListAPI 
{
	void addEssential(ILoadImageBundle blah);
	void addNonEssential(ILoadImageBundle blah);
	void setWorldViewSize(int width, int height);
	void kickStartLoading();
}
