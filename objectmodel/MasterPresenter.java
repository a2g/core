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


import java.util.TreeMap;
import java.util.logging.Logger;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.action.ActionRunner;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.DoNothingAction;
import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.action.ChainedAction;
import com.github.a2g.core.action.SayAction;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.STARTING_ODD_OBJECTS_CODE;
import com.github.a2g.core.action.BaseDialogTreeAction;

import com.github.a2g.core.event.PropertyChangeEvent;
import com.github.a2g.core.event.PropertyChangeEventHandlerAPI;
import com.github.a2g.core.event.SaySpeechCallDialogTreeEvent;
import com.github.a2g.core.event.SaySpeechCallDialogTreeEventHandlerAPI;
import com.github.a2g.core.event.SetRolloverEvent;
import com.github.a2g.core.interfaces.ActionRunnerCallbackAPI;
import com.github.a2g.core.interfaces.CommandLineCallbackAPI;
import com.github.a2g.core.interfaces.FactoryAPI;
import com.github.a2g.core.interfaces.HostingPanelAPI;
import com.github.a2g.core.interfaces.ImageAddAPI;
import com.github.a2g.core.interfaces.InventoryPresenterCallbackAPI;
import com.github.a2g.core.interfaces.LoadAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.MasterPanelAPI;
import com.github.a2g.core.interfaces.MasterPanelAPI.GuiStateEnum;
import com.github.a2g.core.interfaces.MasterPresenterHostAPI;
import com.github.a2g.core.interfaces.MergeSceneAndStartAPI;
import com.github.a2g.core.interfaces.OnDialogTreeAPI;
import com.github.a2g.core.interfaces.OnDoCommandAPI;
import com.github.a2g.core.interfaces.OnEntryAPI;
import com.github.a2g.core.interfaces.OnEveryFrameAPI;
import com.github.a2g.core.interfaces.OnFillLoadListAPI;
import com.github.a2g.core.interfaces.OnFillLoadListAPIImpl;
import com.github.a2g.core.interfaces.OnPreEntryAPI;
import com.github.a2g.core.interfaces.PackagedImageAPI;
import com.github.a2g.core.interfaces.SceneAPI;
import com.github.a2g.core.interfaces.TimerAPI;
import com.github.a2g.core.interfaces.TimerCallbackAPI;
import com.github.a2g.core.interfaces.VerbsPresenterCallbackAPI;
import com.google.gwt.event.shared.EventBus;

public class MasterPresenter
implements InternalAPI
, SaySpeechCallDialogTreeEventHandlerAPI
, PropertyChangeEventHandlerAPI
, TimerCallbackAPI
, ImageAddAPI
, MergeSceneAndStartAPI
, OnFillLoadListAPI
, OnEntryAPI
, OnPreEntryAPI
, OnEveryFrameAPI
, OnDoCommandAPI
, OnDialogTreeAPI
, CommandLineCallbackAPI
, ActionRunnerCallbackAPI
, InventoryPresenterCallbackAPI
, VerbsPresenterCallbackAPI
{

	private CommandLinePresenter commandLinePresenter;
	private InventoryPresenter inventoryPresenter;
	private VerbsPresenter verbsPresenter;
	private ScenePresenter scenePresenter;
	private DialogTreePresenter dialogTreePresenter;
	private LoaderPresenter loadingPresenter;
	private TitleCardPresenter titleCardPresenter;

	private SceneAPI callbacks;
	private TreeMap<Short, SceneObject> theObjectMap;
	private TreeMap<String, Animation> theAnimationMap;


	private EventBus bus;
	private MasterPresenterHostAPI parent;

	private TimerAPI timer;
	private MasterPanelAPI masterPanel;
	private ActionRunner actionRunner;
	private int textSpeedDelay;
	private Integer[] theListOfIndexesToInsertAt;


	private Logger logger = Logger.getLogger("com.mycompany.level");


	private String lastSceneAsString;
	private short defaultSayer;
	private short defaultWalker;

	public MasterPresenter(final HostingPanelAPI panel, EventBus bus, MasterPresenterHostAPI parent)
	{
		this.bus = bus;
		this.timer = null;
		this.parent = parent;
		this.textSpeedDelay = 20;

		this.theObjectMap = new TreeMap<Short, SceneObject>();
		this.theAnimationMap = new TreeMap<String, Animation>();

		this.actionRunner = new ActionRunner(this);
		this.theListOfIndexesToInsertAt= new Integer[100];
		for(int i=0;i<100;i++)
			theListOfIndexesToInsertAt[i] = new Integer(0);

		bus.addHandler(
				SaySpeechCallDialogTreeEvent.TYPE,
				this);

		bus.addHandler(
				PropertyChangeEvent.TYPE,
				this);

		this.masterPanel = getFactory().createMasterPanel(320,240, ColorEnum.Black);
		panel.setThing(this.masterPanel);



		this.dialogTreePresenter = new DialogTreePresenter(
				masterPanel.getHostForDialogTree(), bus, this);
		this.commandLinePresenter = new CommandLinePresenter(
				masterPanel.getHostForCommandLine(), bus, this);

		this.inventoryPresenter = new InventoryPresenter(
				masterPanel.getHostForInventory(), bus, this);
		this.scenePresenter = new ScenePresenter(
				masterPanel.getHostForScene(), bus, this);
		this.verbsPresenter = new VerbsPresenter(
				masterPanel.getHostForVerbs(), bus, this);
		this.loadingPresenter =  new LoaderPresenter(
				masterPanel.getHostForLoading(), bus, this, this, parent);
		this.titleCardPresenter =  new TitleCardPresenter(
				masterPanel.getHostForTitleCard(), bus, this, parent);


		this.masterPanel.setActiveState(MasterPanelAPI.GuiStateEnum.Loading);
		this.defaultSayer = STARTING_ODD_OBJECTS_CODE.STARTING_ODD_OBJECTS_CODE;
		this.defaultWalker = STARTING_ODD_OBJECTS_CODE.STARTING_ODD_OBJECTS_CODE;
	}

	public void setCallbacks(SceneAPI callbacks) {
		if(this.callbacks!=null)
		{
			lastSceneAsString = this.callbacks.toString();
		}
		this.loadingPresenter.setName(callbacks.toString());
		this.callbacks = callbacks;
	}

	public MasterPresenter getHeaderPanel() {
		return this;
	}



	@Override
	public boolean addImageForAnInventoryItem(LoadHandler lh, String objectTextualId, int objectCode, PackagedImageAPI imageResource)
	{
		if (this.callbacks == null) {
			return true;
		}
		InventoryItem item = this.inventoryPresenter.getInventory().items().at(
				objectTextualId);
		boolean result = true;


		if (item == null)
		{

			Image imageAndPos = inventoryPresenter.getView().createNewImageAndAdddHandlers(
					imageResource,lh, bus, objectTextualId, objectCode, 0,0);

			imageAndPos.addImageToPanel( 0 );

			boolean initiallyVisible = false;
			result = inventoryPresenter.addInventory(
					objectTextualId
					, objectCode
					, initiallyVisible
					, imageAndPos
					);



		}

		return result;
	}

	@Override
	public boolean addImageForASceneObject(LoadHandler lh, int numberPrefix, int x, int y, int w, int h, String objectTextualId, String animationTextualId, short objectCode, String objPlusAnimCode, PackagedImageAPI imageResource) {
		if (this.callbacks == null) {
			return true;
		}


		Image imageAndPos = this.scenePresenter.getView().createNewImageAndAddHandlers(lh, imageResource, this, bus, x,y, objectTextualId, objectCode);

		loadingPresenter.getLoaders().addToAppropriateAnimation(numberPrefix, imageAndPos, objectTextualId, animationTextualId, objectCode, objPlusAnimCode, scenePresenter.getWidth(), scenePresenter.getHeight());


		int before = getIndexToInsertAt(numberPrefix);
		updateTheListOfIndexesToInsertAt(numberPrefix);

		// this triggers the loading
		imageAndPos.addImageToPanel( before );


		return true;
	}


	@Override
	public SceneObject getObject(short code) {
		theObjectMap.size();
		SceneObject ob = this.theObjectMap.get(
				code);

		if (ob == null)
		{
			ob = null;
		}
		return ob;
	}

	@Override
	public Animation getAnimation(String code) {
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

	@Override
	public InventoryItem getInventoryItem(int i) {
		InventoryItem inv = inventoryPresenter.getInventoryItem(
				i);

		return inv;
	}

	public int getIndexToInsertAt(int numberPrefix) {
		int i = theListOfIndexesToInsertAt[numberPrefix];
		return i;
	}

	void updateTheListOfIndexesToInsertAt(int numberPrefix)
	{
		for(int i=numberPrefix;i<=99;i++)
		{
			theListOfIndexesToInsertAt[i]++;
		}
	}



	@Override
	public void executeBaseAction(BaseAction a)
	{
		if(a==null)
		{
			a = new DoNothingAction(createChainRootAction());
		}

		actionRunner.runAction(a);
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


	public void setInitialAnimationsAsCurrent() {
		int count = this.scenePresenter.getModel().objectCollection().count();
		for (int i = 0; i<count; i++)
		{
			SceneObject sceneObject = this.scenePresenter.getModel().objectCollection().at(i);

			if (sceneObject != null) {
				String initial = sceneObject.getInitialAnimation();
				if (sceneObject.getAnimations().at(initial)!=null)
				{
					sceneObject.getAnimations().at(initial).setAsCurrentAnimation();
					// set x & y to zero sets the base middles 
					// to the positions they were in when all objects were rendered out. 
					sceneObject.setX(0);
					sceneObject.setY(0);
				}
				else
				{
					boolean b = true;

					b = (b) ? true : false;
				}

			}
		}

	}



	public void callOnPreEntry() {
		this.callbacks.onPreEntry(this);
	}

	@Override
	public void doEveryFrame() {
		int size = this.theObjectMap.size();
		if(size==0)
		{
			System.out.println("sIZE WAS ZERO");
		}
		if(timer!=null)
		{
			this.callbacks.onEveryFrame(this);
		}
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
	public void setValue(Object key, int value) {
		String keyAsString = key.toString();
		parent.setValue(keyAsString, value);

	}

	@Override
	public int getValue(Object key) {
		String keyAsString = key.toString();

		int i = parent.getValue(keyAsString);

		return i;
	}

	@Override
	public boolean isTrue(Object key) {
		String keyAsString = key.toString();

		int property = getValue(keyAsString);

		return property != 0;
	}

	@Override
	public void switchToScene(String scene)
	{
		// since instantiateScene..ToIt does some asynchronous stuff,
		// I thought maybe I could do it, then cancel the timers.
		// but I've put it off til I need the microseconds.
		cancelTimer();
		this.actionRunner.cancel();

		this.parent.instantiateSceneAndCallSetSceneBackOnTheMasterPresenter(	scene);
	}

	@Override
	public String getLastScene() {

		return lastSceneAsString;
	}

	@Override
	public boolean isInDebugMode() {
		return true;
	}

	public void startCallingOnEveryFrame()
	{
		timer = getFactory().createSystemTimer(this);
		timer.scheduleRepeating(40);
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
	public void executeBranchOnCurrentScene(int branchId)
	{	this.actionRunner.cancel();
	// clear it so any old branches don't show up
	this.dialogTreePresenter.clear();

	// make dialogtreepanel active if not already
	this.setDialogTreeActive(true);

	// get the chain from the client code
	BaseDialogTreeAction actionChain = this.callbacks.onDialogTree(this, createChainRootAction(), branchId);

	// execute it
	executeBaseAction(	actionChain );
	}


	public void saySpeechAndThenExecuteBranchWithBranchId(String speech, int branchId) {
		this.dialogTreePresenter.clear();

		short objId = getDialogTreeGui().getDialogTreeTalker();
		// This is a bit sneaky:
		// 1. we construct a BaseAction that sas the speech
		// 2. we pass this to onDialogTree
		// 3. where the user appends other actions to it
		// 4. Then we execute it
		// Thus it will say the text, and do what the user prescribes.


		SayAction say = new SayAction(createChainRootAction(), objId, speech);
		BaseDialogTreeAction actionChain = callbacks.onDialogTree(this, say, branchId);
		executeBaseAction(actionChain);
	}

	public void callOnEnterScene()
	{
		BaseAction a = this.callbacks.onEntry(this,createChainRootAction());

		//.. then executeBaseAction->actionRunner::runAction will add an TitleCardAction
		// the title card
		executeBaseAction(a);
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
	public void onSaySpeechCallBranch(String speech, int branchId)
	{
		saySpeechAndThenExecuteBranchWithBranchId(speech, branchId);
	}

	public MasterPanelAPI getMasterPanel() {
		return masterPanel;
	}




	@Override
	public void startScene()
	{
		masterPanel.setActiveState(MasterPanelAPI.GuiStateEnum.Loading);
		loadInventoryFromAPI();
		setInitialAnimationsAsCurrent();

		// it is reasonable for a person to set current animations in pre-entry
		// and expect them to stay current, so we set cuurentAnimations before pre-entry.

		callOnPreEntry();

		startCallingOnEveryFrame();
		this.masterPanel.setActiveState(MasterPanelAPI.GuiStateEnum.TitleCardOverOnEnterScene);
		callOnEnterScene();

	}


	@Override
	public void addEssential(LoadAPI blah)
	{

		loadingPresenter.getLoaders().addEssential(blah, this);
	}


	@Override
	public void kickStartLoading()
	{
		loadingPresenter.getLoaders().calculateImagesToLoadAndOmitInventoryIfSame();

		int total = loadingPresenter.getLoaders().imagesToLoad();
		boolean isSameInventory = loadingPresenter.getLoaders().isSameInventoryAsLastTime();

		// hide all visible images.
		// (using scene's data is quicker than using scenePanel data)
		for(int i=0;i<scenePresenter.getModel().objectCollection().count();i++)
		{
			scenePresenter.getModel().objectCollection().at(i).setVisible(false);
		}

		theObjectMap.clear();
		theAnimationMap.clear();
		this.theObjectMap.clear();
		this.theAnimationMap.clear();
		scenePresenter.reset();


		// set gui to blank
		masterPanel.setActiveState(MasterPanelAPI.GuiStateEnum.Loading);
		//scenePresenter.clear(); don't clear, all its images are switched off anyhow.
		loadingPresenter.clear();
		//commandLinePresenter.clear();
		verbsPresenter.clear();

		if(!isSameInventory)
		{
			inventoryPresenter.clear();
		}

		loadingPresenter.setTotal(total);
		loadingPresenter.getLoaders().loadNext();
	}





	@Override
	public void setScenePixelSize(int width, int height) {
		this.scenePresenter.setPixelSize(width, height);
		this.titleCardPresenter.setPixelSize(width, height);
		this.loadingPresenter.setPixelSize(width, height);
		this.dialogTreePresenter.setPixelSize(width, height>>1);
		this.verbsPresenter.setWidthOfScene(width);
	}

	@Override
	public int getPopupDelay() {
		return textSpeedDelay;
	}


	public void setScene(SceneAPI scene) {


		setCallbacks(scene);

		this.callbacks.onFillLoadList(new OnFillLoadListAPIImpl(this));
	}

	@Override
	public void restartReloading()
	{
		loadingPresenter.getLoaders().clearLoaders();

		this.callbacks.onFillLoadList(new OnFillLoadListAPIImpl(this));
	}





	@Override
	public void mergeWithScene(LoadedLoad s)
	{


		String name = s.getName();
		logger.fine(name);
		System.out.println("dumping " +  name);
		SceneObjectCollection theirs = s.getSceneObjectCollection();
		SceneObjectCollection ours = this.scenePresenter.getModel().objectCollection();

		for(int i=0;i<theirs.count();i++)
		{
			SceneObject srcObject = theirs.at(i);
			String objTextualId = srcObject.getTextualId();
			int prefix = srcObject.getNumberPrefix();
			short objectCode = srcObject.getCode();
			SceneObject destObject = ours.at(objTextualId);
			if(destObject==null)
			{
				destObject = new SceneObject(objTextualId, scenePresenter.getWidth(), scenePresenter.getHeight());
				destObject.setNumberPrefix(prefix);
				destObject.setCode(objectCode);

				if (objectCode == -1) {
					parent.alert(
							"Missing initial image for "
									+ objTextualId
									+ " ");
					return;
				}

				ours.add(destObject);
				this.theObjectMap.put(objectCode,destObject);
				System.out.println("object " + objTextualId + " " + objectCode);

			}

			for(int j=0;j<srcObject.getAnimations().getCount();j++)
			{
				Animation srcAnimation = srcObject.getAnimations().at(j);
				String animTextualId = srcAnimation.getTextualId();

				Animation destAnimation = destObject.getAnimations().at(animTextualId);
				if(destAnimation==null)
				{
					destAnimation = new Animation(animTextualId, destObject);
					destObject.getAnimations().add(destAnimation);
					this.theAnimationMap.put(animTextualId, destAnimation);
				}

				//System.out.println("new anim " + objTextualId + " " + animTextualId+" = "+animationCode);

				for(int k=0;k<srcAnimation.getFrames().getCount();k++)
				{
					Image srcImage = srcAnimation.getFrames().at(k);
					destAnimation.getFrames().add(srcImage);
				}

			}


		}
	}

	MasterPanelAPI.GuiStateEnum getStateIfEntering(MasterPanelAPI.GuiStateEnum state)
	{
		switch(state)
		{
		case OnEnterScene: return MasterPanelAPI.GuiStateEnum.TitleCardOverOnEnterScene;
		case DialogTree:return MasterPanelAPI.GuiStateEnum.TitleCardOverDialogTree;
		case CutScene:return MasterPanelAPI.GuiStateEnum.TitleCardOverCutScene;
		case ActiveScene:return MasterPanelAPI.GuiStateEnum.TitleCardOverActiveScene;
		case Loading:return MasterPanelAPI.GuiStateEnum.TitleCardOverLoading;
		default:
			return state;
		}
	}

	MasterPanelAPI.GuiStateEnum getStateIfExiting(MasterPanelAPI.GuiStateEnum state)
	{
		switch(state)
		{
		case TitleCardOverOnEnterScene:return MasterPanelAPI.GuiStateEnum.OnEnterScene;
		case TitleCardOverDialogTree: return MasterPanelAPI.GuiStateEnum.DialogTree;
		case TitleCardOverCutScene: return MasterPanelAPI.GuiStateEnum.CutScene;
		case TitleCardOverActiveScene: return MasterPanelAPI.GuiStateEnum.ActiveScene;
		case TitleCardOverLoading: return MasterPanelAPI.GuiStateEnum.Loading;
		default:
			return state;
		}
	}

	@Override
	public void displayTitleCard(String text)
	{
		boolean isEntering = text.length()>0;
		if(isEntering)
		{
			titleCardPresenter.setText(text);
		}
		MasterPanelAPI.GuiStateEnum state = masterPanel.getActiveState();
		state = isEntering? getStateIfEntering(state) : getStateIfExiting(state);
		masterPanel.setActiveState(state);
	}

	@Override
	public void incrementProgress() {
		loadingPresenter.incrementProgress();
	}

	@Override
	public MasterPresenterHostAPI getMasterHostAPI() {
		return parent;
	}

	@Override
	public FactoryAPI getFactory() {
		return parent.getFactory(bus, this);
	}


	@Override
	public void doCommand(int verbAsCode, int verbAsVerbEnumeration,
			SentenceItem sentenceA, SentenceItem sentenceB, double x, double y) {

		BaseAction a = this.callbacks.onDoCommand(
				this, createChainRootAction(),
				verbAsCode,
				sentenceA,
				sentenceB,
				x,
				y);


		this.commandLinePresenter.setMouseable(false);
		executeBaseAction(a);

		setLastCommand(x, y,
				verbAsVerbEnumeration,
				sentenceA.getTextualId(),
				sentenceB.getTextualId());

	}

	@Override
	public void actionFinished()
	{
		this.commandLinePresenter.clear();
		this.commandLinePresenter.setMouseable(true);

		if(masterPanel.getActiveState() == MasterPanelAPI.GuiStateEnum.OnEnterScene)
		{
			this.masterPanel.setActiveState(MasterPanelAPI.GuiStateEnum.ActiveScene);
		}
	}

	@Override
	public void setInventoryPixelSize(int width, int height) {
		this.inventoryPresenter.setSizeOfSingleInventoryImage(width,height);
		this.verbsPresenter.setWidthOfInventory(inventoryPresenter.getWidth());
	}

	@Override
	public void onClickVerbsOrInventory()
	{
		// a click on the inventory results in negative coords.
		commandLinePresenter.onClick(-1, -1);

	}

	@Override
	public void onMouseOverVerbsOrInventory
	(String displayName, String textualId, int code)
	{
		getCommandLineGui().onSetMouseOver(displayName, textualId, code);
		bus.fireEvent(
				new SetRolloverEvent(
						displayName,
						textualId,
						code));
	}

	@Override
	public void onPropertyChange(PropertyChangeEvent inventoryEvent)
	{
		this.inventoryPresenter.updateInventory();
	}

	@Override
	public SceneAPI getSceneByName(String string)
	{
		return this.parent.getSceneViaCache(string);
	}

	@Override
	public void setDialogTreeActive(boolean isInDialogTreeMode)
	{
		if(isInDialogTreeMode)
		{
			this.masterPanel.setActiveState(MasterPanelAPI.GuiStateEnum.DialogTree);
		}
		else
		{
			this.masterPanel.setActiveState(MasterPanelAPI.GuiStateEnum.ActiveScene);
		}
	}

	@Override
	public boolean isCommandLineActive()
	{
		boolean isCommandLineActive = masterPanel.getActiveState()==MasterPanelAPI.GuiStateEnum.ActiveScene;
		return isCommandLineActive;
	}

	@Override
	public void clearAllLoadedLoads() {
		this.loadingPresenter.clearAllLoadedLoads();
	}

	@Override
	public void setActiveState(GuiStateEnum state) {
		this.masterPanel.setActiveState(state);

	}

	@Override
	public ChainRootAction createChainRootAction()
	{
		ChainRootAction npa = new ChainRootAction(this);
		return npa;
	}

	@Override
	public void executeChainedAction(ChainedAction ba)
	{
		executeBaseAction(ba);
	}
	
	@Override
	public void setDefaultSayer(short object)
	{
		this.defaultSayer = object;
	}
	
	@Override
	public void setDefaultWalker(short object)
	{
		this.defaultWalker = object;
	}

	@Override
	public short getDefaultSayer() {
		return this.defaultSayer;
	}

	@Override
	public short getDefaultWalker() {
		return this.defaultWalker;
	}

}

