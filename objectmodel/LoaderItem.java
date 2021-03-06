/*
 * Copyright 2012 Anthony Cassidy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.a2g.core.objectmodel;

import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformResourceBundle;
import com.github.a2g.core.interfaces.nongame.presenter.ILoaderPresenterFromLoaderItem;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromBundle;
import com.github.a2g.core.primitive.PointI;
import com.google.gwt.event.dom.client.LoadEvent;

public class LoaderItem implements LoadHandler, Comparable<LoaderItem> {
	private ILoaderPresenterFromLoaderItem callbacks;
	IPlatformResourceBundle singleBundle; 
	int numberOfImagesLeftToLoad;
	int origNumberOfImagesLeftToLoad;
	IMasterPresenterFromBundle api;
	private LoadedLoad theCurrentCacheObject;

	public LoaderItem(IMasterPresenterFromBundle api2,
			IPlatformResourceBundle bundleToCallLoadOn) {
		assert(bundleToCallLoadOn!=null);
		this.api = api2;
		this.singleBundle = bundleToCallLoadOn;
		 
		numberOfImagesLeftToLoad = 0;
		origNumberOfImagesLeftToLoad = 0;
		theCurrentCacheObject = new LoadedLoad(this.getCombinedClassAndNumber());
	}
 

	String getName() {
		return singleBundle.toString();
	}

	public void setCallbacks(ILoaderPresenterFromLoaderItem callbacks) {
		this.callbacks = callbacks;
	}

	LoadHandler getLoadHandler() {
		return this;
	}

	public void runLoader() {
		numberOfImagesLeftToLoad = origNumberOfImagesLeftToLoad = singleBundle.getSize();
		singleBundle.loadImageBundle(getLoadHandler(), api);
	}

	public void runLoaderAfterItsBeenLoaded() {
		numberOfImagesLeftToLoad = origNumberOfImagesLeftToLoad = singleBundle.getSize();
		//bundle.loadImageBundle(getLoadHandler(), api, bundleNumber, origNumberOfImagesLeftToLoad);
		singleBundle.loadImageBundle(getLoadHandler(), api);
	}

	public int getNumberOfImages() {
		int numberOfImages = singleBundle.getSize();
		return numberOfImages;
	}

	@Override
	public void onLoad(LoadEvent event) {
		numberOfImagesLeftToLoad--;
		// String name = this.theCurrentCacheObject.getName();
		if (this.callbacks != null) {
			this.callbacks.onImageLoaded();

			if (numberOfImagesLeftToLoad == 0) {
				// then call the object that ran the action
				this.callbacks.onLoaderComplete(this);
			}
		}
	}

	public void fireProgress() {

	}

	// controls the order they are loaded.
	@Override
	public int compareTo(LoaderItem o) {
		int thisMain = !this.getName().contains("shared") ? 1 : 0;
		int thatMain = !o.getName().contains("shared") ? 1 : 0;
		if (thisMain != thatMain)
			return thatMain - thisMain;
		int result = this.getLoaderEnum() - o.getLoaderEnum();
		return result;
	}

	public int getLoaderEnum() {
		int loaderEnum = singleBundle.getTypeOfLoader();
		return loaderEnum;
	}

	public PointI getResolution() {
		PointI p = singleBundle.getImageResolution();
		return p;
	}
 

	public LoadedLoad getSceneObjectCollection() {
		return theCurrentCacheObject;
	}

	@Override
	public String toString() {
		return getCombinedClassAndNumber();
	}

	public String getCombinedClassAndNumber() {
		String name = singleBundle.toString();
		int len = name.indexOf("@");
		String loaderName = name.substring(0, len>0? len : (name.length()));
		return loaderName;
	}

	public void addToAppropriateAnimation(int prefix, Image imageAndPos,
			String objectTextualId, String animationTextualId,
			short objectCode, String objPlusAnimCode, int width, int height) {
		this.theCurrentCacheObject.addToAppropriateAnimation(prefix,
				imageAndPos, objectTextualId, animationTextualId, objectCode,
				objPlusAnimCode, width, height);
	}

}
