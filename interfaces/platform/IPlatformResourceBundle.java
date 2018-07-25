package com.github.a2g.core.interfaces.platform;

import com.github.a2g.core.interfaces.internal.IMasterPresenterFromBundle;
import com.github.a2g.core.primitive.PointI;
import com.google.gwt.event.dom.client.LoadHandler;

public interface IPlatformResourceBundle {
	
	String toString();
	int getSize();
	int loadImageBundle(LoadHandler lh, IMasterPresenterFromBundle api);
	int getTypeOfLoader();
	PointI getImageResolution();
}
