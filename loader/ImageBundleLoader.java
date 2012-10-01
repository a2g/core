/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.loader;


import com.github.a2g.bridge.image.Image;
import com.github.a2g.bridge.image.LoadHandler;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.github.a2g.core.sceneobject.SceneObjectCache;
import com.google.gwt.event.dom.client.LoadEvent;


public class ImageBundleLoader implements LoadHandler, Comparable<ImageBundleLoader>{
	private ImageBundleLoaderCallbackAPI callbacks;
    ImageBundleLoaderAPI  bundle;
    int bundleNumber;
    int numberOfImagesLeftToLoad;
    int origNumberOfImagesLeftToLoad;
    InternalAPI api;
    private SceneObjectCache theCurrentCacheObject;
    
    //private Logger logger = Logger.getLogger("com.mycompany.level");
    
    public ImageBundleLoader(InternalAPI api, ImageBundleLoaderAPI bundleToCallLoadOn, int bundleNumber) 
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
	public int compareTo(ImageBundleLoader o) {
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

	public String getCombinedClassAndNumber() {
		// TODO Auto-generated method stub
		return bundle.toString() + bundleNumber;
	}
	
	public void addToAppropriateAnimation(Image imageAndPos, String objectTextualId, String animationTextualId, short objectCode, int objPlusAnimCode, int width, int height)
	{
		this.theCurrentCacheObject.addToAppropriateAnimation(imageAndPos, objectTextualId, animationTextualId, objectCode, objPlusAnimCode, width, height);
	}
	

}
