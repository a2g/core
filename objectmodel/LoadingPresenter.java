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


import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.github.a2g.bridge.panel.LoadingPanel;
import com.github.a2g.bridge.thing.AcceptsOneThing;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.github.a2g.core.authoredscene.MergeSceneAndStartAPI;
import com.github.a2g.core.loader.ImageBundleLoaderAPI;
import com.github.a2g.core.loader.ImageBundleLoaderCallbackAPI;
import com.google.gwt.event.shared.EventBus;


public class LoadingPresenter 
implements ImageBundleLoaderCallbackAPI
{
	private Loader theCurrentLoader;
	private List<Loader> listOfEssentialLoaders;
	private Set<String> setOfCompletedLoaders;
	private Map<String, SceneObjectCache>  objectCache;
	private String inventoryResourceAsString;
	private LoadingPanel view;
	int current;
	int total;
	private String name;
	private boolean isSameInventoryAsLastTime;
	private int imagesToLoad;	
	private MergeSceneAndStartAPI master;
	public LoadingPresenter(final AcceptsOneThing panel, EventBus bus, InternalAPI api, MergeSceneAndStartAPI master) 
	{
		this.listOfEssentialLoaders = new LinkedList<Loader>();
		this.setOfCompletedLoaders = new TreeSet<String>();
		this.objectCache = new TreeMap<String,SceneObjectCache>();
	
		this.name = "";
		this.view = new LoadingPanel(api);
		panel.setThing(view);
		this.current = 0;
		this.total = 0;
	}

	void incrementProgress()
	{
		current = current +1;
		view.update(current, total, name);
	}

	void setTotal(int total)
	{
		this.total = total;
		this.current = 0;
		view.update(current, total, name);
	}

	public void setName(String string) {
		name = string;
	}

	public void clear() {
		name = "";
		total = 1;
		current =1;

	}

	public void setPixelSize(int width, int height) 
	{
		this.view.setSize(width , height);

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
		incrementProgress();
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
		Iterator<Loader> iter = listOfEssentialLoaders.iterator();
		while(iter.hasNext())
		{
			iter.next();
		}

		listOfEssentialLoaders.clear();
	}
	
}
