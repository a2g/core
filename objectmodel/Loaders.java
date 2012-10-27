package com.github.a2g.core.objectmodel;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.MergeSceneAndStartAPI;
import com.github.a2g.core.loader.ImageBundleLoaderAPI;
import com.github.a2g.core.loader.ImageBundleLoaderCallbackAPI;

public class Loaders 
implements ImageBundleLoaderCallbackAPI
{
	private Loader theCurrentLoader;
	private LoaderCollection listOfEssentialLoaders;
	private Set<String> setOfCompletedLoaders;
	private Map<String, SceneObjectCache>  objectCache;
	private MergeSceneAndStartAPI master;
	private String inventoryResourceAsString;
	private boolean isSameInventoryAsLastTime;
	private int imagesToLoad;	
	
	public Loaders(MergeSceneAndStartAPI callbacks)
	{
		this.theCurrentLoader = null;
		this.listOfEssentialLoaders = new LoaderCollection();
		this.setOfCompletedLoaders = new TreeSet<String>();
		this.objectCache = new TreeMap<String,SceneObjectCache>();
		this.master = callbacks; 
	}
	
	
	@Override
	public void onLoaderComplete(Loader loader) 
	{
		String loaderName = loader.toString();
		setOfCompletedLoaders.add(loaderName);
		SceneObjectCache cachedCollection = loader.getSceneObjectCollection();
		String combinedName = loader.getCombinedClassAndNumber();
		objectCache.put(combinedName, cachedCollection);
		master.mergeWithScene(cachedCollection);
		// now we need to remove the loader from the list.
		// and since we only load non-ess after ess
		// then we try ess first.
		if(!listOfEssentialLoaders.isEmpty())
		{
			this.listOfEssentialLoaders.remove(0);
		}
		
		loadNext();
	}


	void loadNext()
	{
		if(!listOfEssentialLoaders.isEmpty())
		{
			theCurrentLoader = this.listOfEssentialLoaders.get(0);
		}
		else
		{
			master.startGame();
			return;
		}
		
		String nameAndNum = theCurrentLoader.getCombinedClassAndNumber();
		if(objectCache.containsKey(nameAndNum))
		{
			master.mergeWithScene(objectCache.get(nameAndNum));
			
			//remove from processing straight away.
			this.listOfEssentialLoaders.remove(0);
			loadNext();
		}
		else
		{
			theCurrentLoader.setCallbacks(this);
			String s = theCurrentLoader.getCombinedClassAndNumber();
			if(this.setOfCompletedLoaders.contains(s))
			{
				theCurrentLoader.runLoaderAfterItsBeenLoaded();
			}else
			{
				theCurrentLoader.runLoader();
			}
			// only when it completes will it
			// a) be removed from the list
			// b) loadNext
		}
		
	}
	
	@Override
	public void onImageLoaded() 
	{
		master.incrementProgress();
	}

	public void addToAppropriateAnimation(int numberPrefix, Image imageAndPos,
			String objectTextualId, String animationTextualId,
			short objectCode, int objPlusAnimCode, int width, int height) {
		theCurrentLoader.addToAppropriateAnimation(numberPrefix, imageAndPos, objectTextualId, animationTextualId, objectCode, objPlusAnimCode,width, height);
		
		
	}

	public void addEssential(ImageBundleLoaderAPI blah, InternalAPI api)
	{
		
		for(int i=0;i<blah.getNumberOfBundles();i++)
		{
			listOfEssentialLoaders.add( new Loader(api,blah,i));
		}
	}


	public boolean isSameInventoryAsLastTime()
	{
		return isSameInventoryAsLastTime;
	}
	public int imagesToLoad()
	{
		return imagesToLoad;
	}
	public void calculateImagesToLoadAndIsSameInventory()
	{
		imagesToLoad =0;
		// get totals
		Collections.sort(listOfEssentialLoaders);
		
		Iterator<Loader> iter = listOfEssentialLoaders.iterator();
		while(iter.hasNext())
		{
			Loader loader = iter.next();
			String loaderName = loader.getCombinedClassAndNumber();
			
			if(loader.isInventory())
			{
				
				if(loaderName.equalsIgnoreCase(this.inventoryResourceAsString))
				{
					iter.remove();
					this.isSameInventoryAsLastTime = true;
					continue;
				}
				else
				{
					this.inventoryResourceAsString = loaderName;
				}
			}
				 
			if(!setOfCompletedLoaders.contains(loaderName))
			{
				imagesToLoad+=loader.getNumberOfImages();
			}
		}
	}

	public void clearLoaders() 
	{
		listOfEssentialLoaders.clear();
	}
}
