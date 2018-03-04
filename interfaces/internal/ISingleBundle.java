package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.primitive.PointI;
import com.google.gwt.event.dom.client.LoadHandler;

public interface ISingleBundle {
	
	String toString();
	int getSize();
	int loadImageBundle(LoadHandler lh, IMasterPresenterFromBundle api);
	int getLoaderEnum();
	PointI getImageResolution();
}
