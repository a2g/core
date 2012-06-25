/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.loader;


import com.github.a2g.core.authoredroom.InternalAPI;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;


public class ImageBundleLoader implements LoadHandler, Comparable<ImageBundleLoader>{
	private ImageBundleLoaderCallbackAPI callbacks;
    ImageBundleLoaderAPI  bundle;
    int bundleNumber;
    int numberOfImagesLeftToLoad;
    int origNumberOfImagesLeftToLoad;
    InternalAPI api;
    
    //private Logger logger = Logger.getLogger("com.mycompany.level");
    
    public ImageBundleLoader(InternalAPI api, ImageBundleLoaderAPI bundleToCallLoadOn, int bundleNumber) 
    {
    	this.api = api;
        this.bundle = bundleToCallLoadOn;
        this.bundleNumber = bundleNumber;
        numberOfImagesLeftToLoad = 0;
        origNumberOfImagesLeftToLoad = 0;
    }

    public String getName()
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
	public int compareTo(ImageBundleLoader o) {
		int result = this.bundleNumber - o.bundleNumber;
		return result;
	}

	public boolean isInventory() {
		boolean isInventory = bundle.isInventory();
		return isInventory;
		
	}

}
