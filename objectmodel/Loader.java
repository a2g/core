package com.github.a2g.core.objectmodel;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.a2g.core.interfaces.internal.IBundleLoader;
import com.github.a2g.core.interfaces.internal.ILoaderPresenter;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromBundle;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromLoader;
import com.github.a2g.core.interfaces.internal.ISingleBundle;
import com.github.a2g.core.primitive.LoaderEnum;
import com.github.a2g.core.primitive.LogNames;
import com.github.a2g.core.primitive.PointI;
import com.google.gwt.dev.util.collect.HashMap; 

public class Loader implements ILoaderPresenter {
	private LoaderItem theCurrentLoader;
	private LoaderItemCollection listOfEssentialLoaders;
	private Set<String> setOfCompletedLoaders;
	private Map<String, LoadedLoad> objectCache;
	private IMasterPresenterFromLoader master;
	private String nameOfInventoryResourceUsedLastTime;
	private boolean isSameInventoryAsLastTime;
	private int numberOfImagesToLoad;
	private static final Logger LOADNEXT = Logger.getLogger(LogNames.LOADNEXT.toString());

	public Loader(IMasterPresenterFromLoader callbacks) {
		this.theCurrentLoader = null;
		this.listOfEssentialLoaders = new LoaderItemCollection();
		this.setOfCompletedLoaders = new TreeSet<String>();
		this.objectCache = new TreeMap<String, LoadedLoad>();
		this.master = callbacks;
		this.nameOfInventoryResourceUsedLastTime  ="";
	}

	@Override
	public void onLoaderComplete(LoaderItem loader) {
		String loaderName = loader.toString();
		setOfCompletedLoaders.add(loaderName);
		LoadedLoad cachedCollection = loader.getSceneObjectCollection();

		String combinedName = loader.getCombinedClassAndNumber();
		assert(combinedName!=null);
		assert(cachedCollection!=null);
		objectCache.put(combinedName, cachedCollection);
		master.mergeWithScene(cachedCollection);
		// now we need to remove the loader from the list.
		// and since we only load non-ess after ess
		// then we try ess first.
		if (!listOfEssentialLoaders.isEmpty()) {
			this.listOfEssentialLoaders.remove(0);
		}

		loadNext();
	}

	void loadNext() {
		
		if (!listOfEssentialLoaders.isEmpty()) {
			theCurrentLoader = this.listOfEssentialLoaders.get(0);
		} else {
			master.enableClickToContinue();
			return;
		}

		String nameAndNum = theCurrentLoader.getCombinedClassAndNumber();
		LOADNEXT.log( Level.FINE, "LOADNEXT "+nameAndNum );  
		if (objectCache.containsKey(nameAndNum)) {
			master.mergeWithScene(objectCache.get(nameAndNum));

			// remove from processing straight away.
			this.listOfEssentialLoaders.remove(0);
			loadNext();
		} else {
			theCurrentLoader.setCallbacks(this);
			String s = theCurrentLoader.getCombinedClassAndNumber();
			if (this.setOfCompletedLoaders.contains(s)) {
				theCurrentLoader.runLoaderAfterItsBeenLoaded();
			} else {
				theCurrentLoader.runLoader();
			}
			// only when it completes will it
			// a) be removed from the list
			// b) loadNext
		}
		return;
	}

	@Override
	public void onImageLoaded() {
		master.incrementProgress();
	}

	public void addToAppropriateAnimation(int drawingOrder, Image imageAndPos,
			String objectTextualId, String animationTextualId,
			short objectCode, String objPlusAnimCode, int width, int height) {
		theCurrentLoader.addToAppropriateAnimation(drawingOrder, imageAndPos,
				objectTextualId, animationTextualId, objectCode,
				objPlusAnimCode, width, height);
	}

	public boolean isSameInventoryAsLastTime() {
		return isSameInventoryAsLastTime;
	}

	public int getNumberOfImagesToLoad() {
		return numberOfImagesToLoad;
	}

	public void calculateImagesToLoadAndOmitInventoryIfSame() {
		numberOfImagesToLoad = 0;
		// get totals
		//Collections.sort(listOfEssentialLoaders);

		Iterator<LoaderItem> iter = listOfEssentialLoaders.iterator();
		while (iter.hasNext()) {
			LoaderItem loader = iter.next();
			String loaderName = loader.getCombinedClassAndNumber();

			if (loader.getLoaderEnum()==LoaderEnum.Inventory.r) {

				if (loaderName.equalsIgnoreCase(this.nameOfInventoryResourceUsedLastTime)) {
					iter.remove();
					this.isSameInventoryAsLastTime = true;
					continue;
				} else {
					this.isSameInventoryAsLastTime = false;
					this.nameOfInventoryResourceUsedLastTime = loaderName;
				}
			}

			if (!setOfCompletedLoaders.contains(loaderName)) {
				numberOfImagesToLoad += loader.getNumberOfImages();
			}
		}
	}

	public void clearLoaders() {
		listOfEssentialLoaders.clear();
		// and since we've cleared the loaders,
		// can we have the same inventory as last time?
		// A: yes
		// so we can't set it to false here.

	}

	public void clearAllLoadedLoads() {
		objectCache.clear();
		setOfCompletedLoaders.clear();

		// and since we've cleared the loaders,
		// can we have the same inventory as last time?
		// A: yes
		// but we should add a "lose all objects" command
		//// if we wipe all memory of last loaded inventory resource bundle
		// then we can't tell whether it should be same inv as last time.
		nameOfInventoryResourceUsedLastTime = "";
		// i don't think you need this line.
		this.isSameInventoryAsLastTime = false;
	}

	public void setSceneAndInventoryResolution() {
		
		Iterator<LoaderItem> iter = listOfEssentialLoaders.iterator();
		while (iter.hasNext()) {
			LoaderItem loader = iter.next();
			PointI res = loader.getResolution();
			int i = loader.getLoaderEnum();
			if(i==LoaderEnum.Main.r)
				master.setScenePixelSize(res.getX(), res.getY());
			else  if(i==LoaderEnum.Inventory.r)
				master.setInventoryImageSize(res.getX(), res.getY());
			
		}
	}

	public void queueEntireBundleLoader(IBundleLoader bundleLoader, IMasterPresenterFromBundle api) {
		
		for (int i = 0; i < bundleLoader.getNumberOfBundles(); i++) {
			listOfEssentialLoaders.add(new LoaderItem(api, bundleLoader.getSingleBundle(i)));
		}
	}

	public void queueSingleBundle(ISingleBundle loader, IMasterPresenterFromBundle api) {
	   
		listOfEssentialLoaders.add(new LoaderItem(api, loader));
	}

}
