package com.github.a2g.core.authoredscene;

import com.github.a2g.core.loader.ImageBundleLoaderAPI;

public interface OnFillLoadListAPI 
{
	void addEssential(ImageBundleLoaderAPI blah);
	void addNonEssential(ImageBundleLoaderAPI blah);
	void setWorldViewSize(int width, int height);
	void kickStartLoading();
}
