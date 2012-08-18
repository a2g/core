/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.objectmodel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;

import com.github.a2g.core.action.ActionRunner;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.NullParentAction;
import com.github.a2g.core.loader.ImageBundleLoaderCallbackAPI;
import com.github.a2g.core.loader.ImageBundleLoaderAPI;
import com.github.a2g.core.loader.ImageBundleLoader;
import com.github.a2g.core.action.BaseDialogTreeAction;
import com.github.a2g.core.authoredscene.ImageAddAPI;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.github.a2g.core.authoredscene.OnDialogTreeAPI;
import com.github.a2g.core.authoredscene.OnDoCommandAPI;
import com.github.a2g.core.authoredscene.OnEntryAPI;
import com.github.a2g.core.authoredscene.OnEveryFrameAPI;
import com.github.a2g.core.authoredscene.OnFillLoadListAPI;
import com.github.a2g.core.authoredscene.OnFillLoadListAPIImpl;
import com.github.a2g.core.authoredscene.OnPreEntryAPI;
import com.github.a2g.core.authoredscene.Point;
import com.github.a2g.core.authoredscene.SceneAPI;

import com.github.a2g.core.event.SaySpeechCallDialogTreeEvent;
import com.github.a2g.core.event.SaySpeechCallDialogTreeEventHandlerAPI;
import com.github.a2g.core.mouse.ImageMouseClickHandler;
import com.github.a2g.core.mouse.InventoryItemMouseOverHandler;
import com.github.a2g.core.mouse.SceneObjectMouseOverHandler;
import com.github.a2g.core.bridge.ImageResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.github.a2g.core.bridge.LoadHandler;
import com.google.web.bindery.event.shared.EventBus;


public class MasterPresenter  
implements InternalAPI, 
SaySpeechCallDialogTreeEventHandlerAPI
, ImageBundleLoaderCallbackAPI
, ImageAddAPI
, OnFillLoadListAPI
, OnEntryAPI
, OnPreEntryAPI
, OnEveryFrameAPI
, OnDoCommandAPI
, OnDialogTreeAPI

{

	private CommandLinePresenter commandLinePresenter;
	private InventoryPresenter inventoryPresenter;
	private VerbsPresenter verbsPresenter;
	private ScenePresenter scenePresenter;
	private DialogTreePresenter dialogTreePresenter;
	private LoadingPresenter loadingPresenter;

	private SceneAPI callbacks;
	private TreeMap<Short, SceneObject> theObjectMap;
	private TreeMap<Integer, Animation> theAnimationMap;
	private TreeMap<String, com.google.gwt.user.client.ui.Image> theLoadedImageMap;
	private Set<String> setOfCompletedLoaders;

	private EventBus bus;
	private MasterPresenterHostAPI parent;
	private int noImagesAreGreaterThanThis;
	private int numberOfImagesToLoad;
	private Timer timer;
	private Scene scene;
	private MasterPanel masterPanel;
	private List<ImageBundleLoader> mapOfEssentialLoaders;
	private List<ImageBundleLoader> mapOfNonEssentialLoaders;
	private int m_imagesYetToLoad;
	private boolean isOkToWaitForImages;
	private ActionRunner actionRunner;
	private int textSpeedDelay;

	private Logger logger = Logger.getLogger("com.mycompany.level");
	private String inventoryResourceAsString;

	public MasterPresenter(final AcceptsOneWidget panel, EventBus bus, MasterPresenterHostAPI parent) {
		this.bus = bus;
		this.timer = null;
		this.parent = parent;
		this.isOkToWaitForImages = true;
		this.noImagesAreGreaterThanThis = 0;
		this.textSpeedDelay = 20;
		this.theObjectMap = new TreeMap<Short, SceneObject>();
		this.theAnimationMap = new TreeMap<Integer, Animation>();
		this.mapOfEssentialLoaders = new LinkedList<ImageBundleLoader>();
		this.mapOfNonEssentialLoaders = new LinkedList <ImageBundleLoader>();
		this.theLoadedImageMap = new TreeMap<String, com.google.gwt.user.client.ui.Image>();
		this.setOfCompletedLoaders = new TreeSet<String>();
		this.m_imagesYetToLoad = 0;
		this.actionRunner = new ActionRunner();

		bus.addHandler(
				SaySpeechCallDialogTreeEvent.TYPE,
				this);



		this.masterPanel = new MasterPanel();
		panel.setWidget(this.masterPanel);



		this.dialogTreePresenter = new DialogTreePresenter(
				masterPanel.getHostForDialogTree(), bus, this);
		this.commandLinePresenter = new CommandLinePresenter(
				masterPanel.getHostForCommandLine(), bus, this);
		this.inventoryPresenter = new InventoryPresenter(
				masterPanel.getHostForInventory(), bus, parent);
		this.verbsPresenter = new VerbsPresenter(
				masterPanel.getHostForVerbs(), bus, parent);
		this.scenePresenter = new ScenePresenter(
				masterPanel.getHostForScene(), bus, parent);
		this.loadingPresenter =  new LoadingPresenter(
				masterPanel.getHostForLoading(), bus, this);


		this.scenePresenter.setWorldViewSize(
				scenePresenter.getWidth(),
				scenePresenter.getHeight());

		masterPanel.setLoadingActive();

	}

	public void setCallbacks(SceneAPI callbacks) {
		this.loadingPresenter.setName(callbacks.toString());
		this.callbacks = callbacks;
		this.getCommandLineGui().setCallbacks(
				callbacks);
	}

	public MasterPresenter getHeaderPanel() {
		return this;
	}

	
	final com.google.gwt.user.client.ui.Image getImageFromResource(ImageResource imageResource, LoadHandler lh)
	{
		this.numberOfImagesToLoad++;
		assert (imageResource != null);
		if(theLoadedImageMap.containsKey(imageResource.toString()))
		{
			final com.google.gwt.user.client.ui.Image image = theLoadedImageMap.get(imageResource.toString());
			lh.onLoad(null);
			return image;
		}
		else
		{
			final com.google.gwt.user.client.ui.Image image = new com.google.gwt.user.client.ui.Image(
				imageResource);
			theLoadedImageMap.put(imageResource.toString(), image);
			if(lh!=null)
			{
				image.addLoadHandler(lh);
			}
			return image;
		}
	}
	@Override
	public boolean addImageForAnInventoryItem(LoadHandler lh, String objectTextualId, int objectCode, ImageResource imageResource)
	{
		if (this.callbacks == null) {
			return true;
		}
		InventoryItem item = this.inventoryPresenter.getInventory().items().at(
				objectTextualId);
		boolean result = true;

		com.google.gwt.user.client.ui.Image image = getImageFromResource(imageResource,lh);
		//Image imageAndPos = new Image(image,this.scenePresenter.getView(),new Point(x, y));

		if (item == null) {

			image.addMouseMoveHandler(
					new InventoryItemMouseOverHandler(
							bus, this,
							objectTextualId,
							objectCode));
			image.addClickHandler(
					new ImageMouseClickHandler(
							bus,
							this.scenePresenter.getView()));

			result = inventoryPresenter.addInventory(
					objectTextualId, objectCode,
					image);
			
			// if we don't start the image loading, the series of events leading
			// to the progress bar increasing will fail, and we'll come to a halt.
			this.scenePresenter.inititateLoadingOfImage(image);
		}

		return result;
	} 

	@Override
	public boolean addImageForASceneObject(LoadHandler lh, int numberPrefix, int x, int y, String objectTextualId, String animationTextualId, short objectCode, int objPlusAnimCode, ImageResource imageResource) {
		if (this.callbacks == null) {
			return true;
		}

		// objects and animations
		SceneObject sceneObject = this.scene.objectCollection().at(
				objectTextualId);

		if (sceneObject == null) {
			sceneObject = new SceneObject(
					objectTextualId,
					scenePresenter.getWidth(),
					scenePresenter.getHeight());
			sceneObject.setNumberPrefix(
					numberPrefix);
			sceneObject.setObjectCode(
					objectCode);
			short code = objectCode;

			if (code == -1) {
				Window.alert(
						"Missing initial image for "
						+ objectTextualId
						+ " "
						+ animationTextualId);
				return true;
			}
			;

			this.theObjectMap.put(code,
					sceneObject);
			this.scene.objectCollection().add(
					sceneObject);
		}

		// if its in the animation map already then we need to be careful 
		// because we might have run in to a prematurely added animation.
		Animation animation = this.theAnimationMap.get(
				objPlusAnimCode);

		if (animation == null) {
			// much simpler if not in the animation map. 
			animation = new Animation(
					animationTextualId,
					sceneObject);
			this.theAnimationMap.put(
					objPlusAnimCode, animation);
			sceneObject.animations().add(
					animation);
		} else // ... it already exists but isn't in the sceneObjectList...
		{
			Animation animation2 = sceneObject.animations().at(
					animationTextualId);

			if (animation2 == null) {

				animation.setSceneObject(
						sceneObject);
				animation.setTextualId(
						animationTextualId);
				sceneObject.animations().add(
						animation);
				// then update all properties that could have already been set on the anim
				if (animation.getWasSetAsHomeAnimation()) {
					animation.setAsHomeAnimation();
				}
				if (animation.getWasSetAsTalkingAnimation()) {
					animation.setAsTalkingAnimation();
				}
				if (animation.getWasSetAsSpecialAnimation()) {
					animation.setAsSpecialAnimation(
							animation.getDesignatedSpecialAnimation());
				}
				if (animation.getWasSetAsCurrentAnimation()) {
					animation.setAsCurrentAnimation();
				}

			}
		}

		com.google.gwt.user.client.ui.Image image = getImageFromResource(imageResource,lh);


		Image imageAndPos = new Image(image,
				this.scenePresenter.getView(),
				new Point(x, y));


		animation.getImageAndPosCollection().add(
				imageAndPos);
		int before = getIndexOfFirstElementHigherThan(
				numberPrefix);

		imageAndPos.addImageToPanel( before );

		image.addMouseMoveHandler(
				new SceneObjectMouseOverHandler(
						bus, this,
						objectTextualId,
						sceneObject.getObjectCode()));
		image.addClickHandler(
				new ImageMouseClickHandler(bus,
						this.scenePresenter.getView()));

		if (numberPrefix
				> noImagesAreGreaterThanThis) {
			noImagesAreGreaterThanThis = numberPrefix;
		}		

		return true;
	}

	public SceneObject getObject(short code) {
		SceneObject ob = this.theObjectMap.get(
				code);

		if (ob == null) {
			int placeBreakpointHere = 0;

			placeBreakpointHere++;
		}
		return ob;
	}

	public Animation getAnimation(int code) {
		Animation anim = this.theAnimationMap.get(
				code);

		if (anim == null) {
			// first param is name, second is parent;
			anim = new Animation("", null);
			this.theAnimationMap.put(code,
					anim);
		}
		return anim;
	}

	public InventoryItem getInventoryItem(int i) {
		InventoryItem inv = inventoryPresenter.getInventoryItem(
				i);

		return inv;
	}

	public int getIndexOfFirstElementHigherThan(int numberPrefix) {
		int numberOfImages = -1;
		ArrayList<SceneObject> list = this.scene.objectCollection().getSortedList();

		Iterator<SceneObject> it = list.iterator();

		while (it.hasNext()) {
			SceneObject o = it.next();

			if (o.getCodePrefix()
					> numberPrefix) {
				return numberOfImages;
			}

			for (int i = 0; i
			< o.animations().getCount(); i++) {
				Animation a = o.animations().at(
						i);
				ImageCollection frames = a.getFrames();

				for (int j = 0; j
				< frames.count(); j++) {
					Image image = frames.at(j);

					assert(image != null);
					numberOfImages++;
				}
			}
		}
		return numberOfImages;
	}

	public void onEnterScene() {
		NullParentAction npa = new NullParentAction();
		npa.setApi(this);
		BaseAction a = this.callbacks.onEntry(this,npa);

		executeBaseActionAndProcessReturnedInteger(a);
	}

	public void executeBaseActionAndProcessReturnedInteger(BaseAction a) {
		int result = actionRunner.runAction(a);
		
		result++;
	}
	
	public void skip()
	{
		actionRunner.skip();
	}
	
	public void decrementTextSpeed()
	{
		textSpeedDelay++;
	}
	
	public void incrementTextSpeed()
	{
		textSpeedDelay--;
	}
	

	public void showEverything() {
		int count = this.scene.objectCollection().count();
		for (int i = 0; i<count; i++) 
		{
			SceneObject sceneObject = this.scene.objectCollection().at(i);

			if (sceneObject != null) {
				if (sceneObject.animations().at(SceneAPI.INITIAL)!= null) 
				{
					sceneObject.animations().at(SceneAPI.INITIAL).setAsCurrentAnimation();
				} 
				else 
				{
					boolean b = true;

					b = (b) ? true : false;
				}

			}
		}

	}



	public void prepareSceneForFocus() {
		this.callbacks.onPreEntry(this);
	}	

	public void doEveryFrame() {
		if(timer!=null)
		{
			this.callbacks.onEveryFrame(this);
		}
	}



	public void showEverythingThenEnterScene()
	{

		prepareSceneForFocus();
		showEverything();
		loadInventoryFromAPI();
		this.dialogTreePresenter.setInDialogTreeMode(
				false);
		startEveryFrameCallbacks();
		onEnterScene();
	}

	public void loadInventoryFromAPI() {
		
		inventoryPresenter.updateInventory();
	}

	public void saveInventoryToAPI() {
		InventoryItemCollection items = this.inventoryPresenter.getInventory().items();

		for (int i = 0; i < items.getCount(); i++) {
			String name = items.at(i).getTextualId();

			int isCarrying = items.at(i).isVisible()
			? 1
					: 0;

			setValue(
					"CARRYING_"
					+ name.toUpperCase(),
					isCarrying);
		}
	}

	@Override
	public void setValue(String name, int value) {
		parent.setValue(name, value);

	}

	@Override
	public int getValue(String name) {
		int i = parent.getValue(name);

		return i;
	}

	@Override
	public boolean isTrue(String name) {
		int property = getValue(name);

		return property != 0;
	}

	public void switchToScene(String scene) {
		// since instantiateScene..ToIt does some assynchronous stuff,
		// I thought maybe I could do it, then cancel the timers.
		// but I've put it off til I need the microseconds.
		cancelTimer();
		this.actionRunner.cancel();
		this.parent.instantiateSceneAndCallSetSceneBackOnTheMasterPresenter(	scene);
	}

	@Override
	public String getLastScene() {

		return null;
	}

	@Override
	public boolean isInDebugMode() {
		return true;
	}

	public void startEveryFrameCallbacks() {
		this.timer = new Timer() {
			@Override
			public void run() {
				doEveryFrame();
			}
		};

		// Schedule the timer to run once in 5 seconds.
		this.timer.scheduleRepeating(40);
	}

	public void cancelTimer() 
	{
		if(this.timer!=null)
		{
			this.timer.cancel();
			timer = null;
		}

	}

	@Override
	public void setLastCommand(double x, double y, int v,
			String a, String b) {
		parent.setLastCommand(x, y, v, a, b);

	}

	public void setCommandLineGui(CommandLinePresenter commandLinePanel) {
		this.commandLinePresenter = commandLinePanel;
	}

	@Override
	public CommandLinePresenter getCommandLineGui() {
		return commandLinePresenter;
	}

	@Override
	public InventoryPresenter getInventoryGui() {
		return inventoryPresenter;
	}

	public Inventory getInventory() {
		return inventoryPresenter.getInventory();
	}

	@Override
	public SceneAPI getCurrentScene() {
		return this.callbacks;
	}

	@Override
	public void executeBranchOnCurrentScene(int branchId) {
		NullParentAction npa = new NullParentAction();
		npa.setApi(this);
		BaseDialogTreeAction actionChain = this.callbacks.onDialogTree(this, npa, branchId);

		executeBaseActionAndProcessReturnedInteger(
				actionChain);
	}


	public void saySpeechAndThenExecuteBranchWithBranchId(String speech, int branchId) {
		this.dialogTreePresenter.clear();

		// this code is dodgey because it doesn't use the current scene to execute..
		// it uses a dynamically created scene to execute it. This seems ok, if it has the same api pointer.
		short objId = getDialogTreeGui().getDialogTreeTalker();
		
		NullParentAction npa = new NullParentAction();
		npa.setApi(this);
		BaseAction say = npa.say(objId, speech);
		BaseDialogTreeAction actionChain = callbacks.onDialogTree(this, say, branchId);

		executeBaseActionAndProcessReturnedInteger(actionChain);
	}

	@Override
	public VerbsPresenter getVerbsGui() {
		return this.verbsPresenter;
	}

	@Override
	public DialogTreePresenter getDialogTreeGui() {
		return this.dialogTreePresenter;
	}

	@Override
	public ScenePresenter getSceneGui() {
		return this.scenePresenter;
	}

	@Override
	public void onSaySpeechCallBranch(String speech, int branchId) {
		saySpeechAndThenExecuteBranchWithBranchId(
				speech, branchId);
	}

	public Widget getPanel() {
		return masterPanel;
	}


	void setDialogTreeActive()
	{
		masterPanel.setDialogTreeActive();
	}

	void setLoadingActive()
	{
		masterPanel.setLoadingActive();
	}

	void setGameActive()
	{
		masterPanel.setGameActive();
	}


	@Override
	public void onImageLoaded() {
		m_imagesYetToLoad--;
		if(m_imagesYetToLoad==0)
		{
			startGame();
		}

		loadingPresenter.incrementProgress();

	}
	
	void startGame()
	{
		setGameActive();
		showEverythingThenEnterScene();
	}


	@Override
	public void addEssential(ImageBundleLoaderAPI blah)
	{

		for(int i=0;i<blah.getNumberOfBundles();i++)
		{
			mapOfEssentialLoaders.add( new ImageBundleLoader(this,blah,i));
		}


	}

	@Override
	public void addNonEssential(ImageBundleLoaderAPI blah) {

		for(int i=0;i<blah.getNumberOfBundles();i++)
		{

			mapOfNonEssentialLoaders.add( new ImageBundleLoader(this,blah,i));
			
		}

	}


	@Override
	public void kickStartLoading() 
	{
		Collections.sort(mapOfNonEssentialLoaders);
		Collections.sort(mapOfEssentialLoaders);
		int total = 0;
		boolean isSameInventory = false;
		// get totals
		Iterator<ImageBundleLoader> iter = mapOfEssentialLoaders.iterator();
		while(iter.hasNext())
		{
			ImageBundleLoader loader = iter.next();
		
			//ImageBundleLoader loader = mapOfEssentialLoaders.get(i);
			if(loader.isInventory())
			{
				String name = loader.getName();
				int len = name.indexOf("@");
				String loaderName = loader.getName().substring(0,len);
				if(loaderName.equalsIgnoreCase(this.inventoryResourceAsString))
				{
					iter.remove();
					isSameInventory = true;
					continue;
				}
				else
				{
					this.inventoryResourceAsString = loaderName;
				}
			}
				 
			total+=loader.getNumberOfImages();
		}
		for(int i=0;i<mapOfNonEssentialLoaders.size();i++)
		{
			total+=mapOfNonEssentialLoaders.get(i).getNumberOfImages();
		}
		
		setLoadingActive();
		theObjectMap.clear();
		theAnimationMap.clear();
		this.theObjectMap.clear();
		this.theAnimationMap.clear();
		this.noImagesAreGreaterThanThis = 0;
		this.scene = new Scene();

		scenePresenter.clear();
		loadingPresenter.clear();
		commandLinePresenter.clear();
		verbsPresenter.clear();
		scenePresenter.clear();

		if(!isSameInventory)
		{
			inventoryPresenter.clear();
			
		}
		
		loadingPresenter.setTotal(total);
		m_imagesYetToLoad = total;
		
		loadNextEssential();
	}

	void loadNextEssential()
	{
		if(!mapOfEssentialLoaders.isEmpty())
		{
			ImageBundleLoader l = this.mapOfEssentialLoaders.get(0);
			l.setCallbacks(this);
			String s = l.toString();
			if(this.setOfCompletedLoaders.contains(s))
			{
				l.runLoaderAfterItsBeenLoaded();
			}else
			{
				l.runLoader();
			}
			
			// we remove it from the list of essentials when it completes
		}
	}

	@Override
	public void onLoaderComplete(ImageBundleLoader a) 
	{
		String loaderName = a.toString();
		setOfCompletedLoaders.add(loaderName);
		
		// both essential and non essential trigger a this event.
		// but we only care for when essential calls it.
		if(!mapOfEssentialLoaders.isEmpty())
		{
			this.mapOfEssentialLoaders.remove(0);
			if(mapOfEssentialLoaders.isEmpty())
			{
				startNonEssential();
			}else
			{
				loadNextEssential();
			}
		}
		else
		{
			loadNextNonEssential();
		}
	}



	void startNonEssential() 
	{
		if(isOkToWaitForImages)
		{
			int total = 0;
			for(int i=0;i<mapOfNonEssentialLoaders.size();i++)
			{
				total+=mapOfNonEssentialLoaders.get(i).getNumberOfImages();
			}
			loadingPresenter.setTotal(total);
			m_imagesYetToLoad = total;
			loadNextNonEssential();	
			
			if(total==0)
			{
				//Window.alert("startGame();");	
			}
		}
		else
		{
			// kick off delay loads
			//Window.alert("startGame();");
		}
	}

	void loadNextNonEssential()
	{
		if(!mapOfNonEssentialLoaders.isEmpty())
		{
			ImageBundleLoader l = this.mapOfNonEssentialLoaders.get(0);
			this.mapOfNonEssentialLoaders.remove(0);
			l.setCallbacks(this);
			l.runLoader();
			// we remove it from the list of essentials when it completes
		}
	}

	@Override
	public void setWorldViewSize(int width, int height) {
		this.scenePresenter.setWorldViewSize(width, height);
		
	}

	@Override
	public int getPopupDelay() {
		return textSpeedDelay;
	}


	public void setScene(SceneAPI sceneCallbacks) {
		
		setCallbacks(sceneCallbacks);

		this.callbacks.onFillLoadList(new OnFillLoadListAPIImpl(this));
	}
	
	@Override
	public void restartReloading() 
	{
		Iterator<ImageBundleLoader> iter = mapOfEssentialLoaders.iterator();
		while(iter.hasNext())
		{
			iter.next();
		}
		for(int i=0;i<mapOfNonEssentialLoaders.size();i++)
		{
			//mapOfNonEssentialLoaders.get(i).fireCompleted();	
		}
		
		mapOfNonEssentialLoaders.clear();
		mapOfEssentialLoaders.clear();
		
		this.callbacks.onFillLoadList(new OnFillLoadListAPIImpl(this));
		
	}
};

