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
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.loader.ImageBundleLoaderAPI;
import com.github.a2g.core.loader.ImageBundleLoaderCallbackAPI;
import com.google.gwt.event.dom.client.LoadEvent;


public class Loader implements LoadHandler, Comparable<Loader>{
	private ImageBundleLoaderCallbackAPI callbacks;
    ImageBundleLoaderAPI  bundle;
    int bundleNumber;
    int numberOfImagesLeftToLoad;
    int origNumberOfImagesLeftToLoad;
    InternalAPI api;
    private SceneObjectCache theCurrentCacheObject;
    
    //private Logger logger = Logger.getLogger("com.mycompany.level");
    
    public Loader(InternalAPI api, ImageBundleLoaderAPI bundleToCallLoadOn, int bundleNumber) 
    {
    	this.api = api;
        this.bundle = bundleToCallLoadOn;
        this.bundleNumber = bundleNumber;
        numberOfImagesLeftToLoad = 0;
        origNumberOfImagesLeftToLoad = 0;
        theCurrentCacheObject = new SceneObjectCache( this.getCombinedClassAndNumber());
    }

    String getName()
    {
    	return bundle.toString();
    }
    
	public void setCallbacks(ImageBundleLoaderCallbackAPI callbacks)
	{
		this.callbacks = callbacks;
	}

    
    LoadHandler getLoadHandler(){ return this; }

    public void runLoader() {
    	numberOfImagesLeftToLoad = origNumberOfImagesLeftToLoad = bundle.getNumberOfImagesInBundle(bundleNumber);
    	bundle.loadImageBundle(getLoadHandler(), api, bundleNumber, 1,1);
    }
    
    public void runLoaderAfterItsBeenLoaded() {
    	numberOfImagesLeftToLoad = origNumberOfImagesLeftToLoad = bundle.getNumberOfImagesInBundle(bundleNumber);
    	bundle.loadImageBundle(getLoadHandler(), api, bundleNumber, origNumberOfImagesLeftToLoad,1);
    }

	
	private void fireCompletedIfZeroImagesRemaining() 
	{
		if(numberOfImagesLeftToLoad == 0)
		{
	        // then call the object that ran the action
	       fireCompleted();
		}
		
		
	}

	public int getNumberOfImages() {
		int numberOfImages = bundle.getNumberOfImagesInBundle( bundleNumber);
		return numberOfImages;
	}
	
	@Override
	public void onLoad(LoadEvent event) 
	{
		numberOfImagesLeftToLoad --;
		fireProgress();
		fireCompletedIfZeroImagesRemaining();
	}
	
	public void fireCompleted()
    {
    	if (this.callbacks != null)
    	{
    		this.callbacks.onLoaderComplete(this);
    	}
    }
    
    public void fireProgress()
    {
    	if (this.callbacks != null)
    	{
    		this.callbacks.onImageLoaded();
    	}
    }


	@Override
	public int compareTo(Loader o) {
		int result = this.bundleNumber - o.bundleNumber;
		return result;
	}

	public boolean isInventory() {
		boolean isInventory = bundle.isInventory();
		return isInventory;
		
	}

	public int getBundleNumber()
	{
		return bundleNumber;
	}
	public SceneObjectCache getSceneObjectCollection() {
		return theCurrentCacheObject;//.getSceneObjectCollection();
	}

	public String toString()
	{
		return getCombinedClassAndNumber();
	}
	public String getCombinedClassAndNumber() 
	{
		String name = bundle.toString();
		int len = name.indexOf("@");
		String loaderName = name.substring(0,len);
		return  loaderName + bundleNumber;
	}
	
	public void addToAppropriateAnimation(int prefix, Image imageAndPos, String objectTextualId, String animationTextualId, short objectCode, int objPlusAnimCode, int width, int height)
	{
		this.theCurrentCacheObject.addToAppropriateAnimation(prefix, imageAndPos, objectTextualId, animationTextualId, objectCode, objPlusAnimCode, width, height);
	}
	

}
