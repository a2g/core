/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.loader;

import com.github.a2g.core.authoredroom.IAmTheMainAPI;
import com.google.gwt.event.dom.client.LoadHandler;


public interface ILoadImageBundle
{
	public int getNumberOfBundles();
	public int getNumberOfImagesInBundle(int bundleNumber);
	public int loadImageBundle(final LoadHandler individualImageCallback, final IAmTheMainAPI api, final int bundleNumber, final int CHUNK, final int milliseconds);
	
}