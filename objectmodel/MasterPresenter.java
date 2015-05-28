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

//import java.util.Iterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.action.ActionRunner;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.action.ChainableAction;
import com.github.a2g.core.action.ActivateDialogTreeModeAction;
import com.github.a2g.core.action.DialogChainRootAction;
import com.github.a2g.core.action.DialogTreeChainToAction;
import com.github.a2g.core.action.DialogTreeEndAction;
import com.github.a2g.core.action.DialogTreeTalkAction;
import com.github.a2g.core.action.DoNothingAction;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointF;
import com.github.a2g.core.action.DecoratedForDialogBaseAction;
import com.github.a2g.core.event.PropertyChangeEvent;
import com.github.a2g.core.event.PropertyChangeEventHandlerAPI;
import com.github.a2g.core.event.SetRolloverEvent;
import com.github.a2g.core.interfaces.ConstantsForAPI;
import com.github.a2g.core.interfaces.ISound;
import com.github.a2g.core.interfaces.IDialogTreePanelFromDialogTreePresenter;
import com.github.a2g.core.interfaces.IMasterPresenterFromActionRunner;
import com.github.a2g.core.interfaces.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.IMasterPresenterFromCommandLine;
import com.github.a2g.core.interfaces.IFactory;
import com.github.a2g.core.interfaces.IHostingPanel;
import com.github.a2g.core.interfaces.IMasterPresenterFromDialogTree;
import com.github.a2g.core.interfaces.IMasterPresenterFromScene;
import com.github.a2g.core.interfaces.IMasterPresenterFromTitleCard;
import com.github.a2g.core.interfaces.IMasterPresenterFromBundle;
import com.github.a2g.core.interfaces.IMasterPresenterFromInventory;
import com.github.a2g.core.interfaces.ILoad;
import com.github.a2g.core.interfaces.IMasterPanelFromMasterPresenter;
import com.github.a2g.core.interfaces.IMasterPanelFromMasterPresenter.GuiStateEnum;
import com.github.a2g.core.interfaces.IHostFromMasterPresenter;
import com.github.a2g.core.interfaces.IMasterPresenterFromLoader;
import com.github.a2g.core.interfaces.IOnFillLoadListImpl;
import com.github.a2g.core.interfaces.IPackagedImage;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.interfaces.ITimer;
import com.github.a2g.core.interfaces.IMasterPresenterFromTimer;
import com.github.a2g.core.interfaces.IMasterPresenterFromVerbs;
import com.google.gwt.event.shared.EventBus;

public class MasterPresenter implements
IMasterPresenterFromActions, IMasterPresenterFromScene,
IMasterPresenterFromDialogTree, IMasterPresenterFromTimer,
IMasterPresenterFromBundle, IMasterPresenterFromLoader,
IMasterPresenterFromCommandLine, IMasterPresenterFromActionRunner,
IMasterPresenterFromInventory, IMasterPresenterFromVerbs,
IMasterPresenterFromTitleCard, 
PropertyChangeEventHandlerAPI
{
	private static final Logger LOADING = Logger.getLogger("LOADING");
	private static final Logger LOADING_ANIM = Logger.getLogger("LOADING.ANIM");
	private static final Logger COMMAND_AUTOPLAY = Logger.getLogger("COMMAND.AUTOPLAY");
	
	MasterProxyForGameScene proxyForGameScene;
	private CommandLinePresenter commandLinePresenter;
	private InventoryPresenter inventoryPresenter;
	private VerbsPresenter verbsPresenter;
	private ScenePresenter scenePresenter;
	private DialogTreePresenter dialogTreePresenter;
	private LoaderPresenter loaderPresenter;
	private TitleCardPresenter titleCardPresenter;

	private IGameScene sceneHandlers;

	private EventBus bus;
	private IHostFromMasterPresenter host;
	private ITimer timer;
	private ITimer switchTimer;
	private IMasterPanelFromMasterPresenter masterPanel;
	private ActionRunner dialogActionRunner;
	private ActionRunner doCommandActionRunner;
	private ActionRunner onEveryFrameActionRunner;

	private Integer[] theListOfIndexesToInsertAt;
	private ArrayList<PointF> gatePoints;
	private ArrayList<Integer> gateIds;

	private String lastSceneAsString;
	private String switchDestination;
	private boolean isSayNonIncremementing;
	private short boundaryCrossObject;
	private MasterProxyForActions proxyForActions;
	private Map<String, ISound> mapOfSounds;
	private boolean isAutoplayCancelled;

	public MasterPresenter(final IHostingPanel panel, EventBus bus,
			IHostFromMasterPresenter host) {
		this.bus = bus;
		isAutoplayCancelled = false;
		this.timer = null;
		this.switchTimer = null;
		this.host = host;
		this.proxyForGameScene = new MasterProxyForGameScene(this);
		this.proxyForActions = new MasterProxyForActions(this);
		mapOfSounds = new TreeMap<String,ISound>();

		IFactory factory = host.getFactory(bus, this);
		this.doCommandActionRunner = new ActionRunner(factory, proxyForActions, proxyForActions,
				proxyForActions, proxyForActions, proxyForActions, this, 1);
		this.dialogActionRunner = new ActionRunner(factory, proxyForActions, proxyForActions,
				proxyForActions, proxyForActions, proxyForActions, this, 2);
		this.onEveryFrameActionRunner = new ActionRunner(factory, proxyForActions, proxyForActions,
				proxyForActions, proxyForActions, proxyForActions, this, 3);
		this.gatePoints = new ArrayList<PointF>();
		this.gateIds = new ArrayList<Integer>();
		clearListOfIntegersToInsertAt();

		bus.addHandler(PropertyChangeEvent.TYPE, this);

		this.masterPanel = getFactory().createMasterPanel(320, 240,
				ColorEnum.Black);
		panel.setThing(this.masterPanel);

		this.dialogTreePresenter = new DialogTreePresenter(
				masterPanel.getHostForDialogTree(), bus, this);
		this.commandLinePresenter = new CommandLinePresenter(
				masterPanel.getHostForCommandLine(), bus, this);

		this.inventoryPresenter = new InventoryPresenter(
				masterPanel.getHostForInventory(), bus, this);
		this.scenePresenter = new ScenePresenter(masterPanel.getHostForScene(),
				this);
		this.verbsPresenter = new VerbsPresenter(masterPanel.getHostForVerbs(),
				bus, this);
		this.loaderPresenter = new LoaderPresenter(
				masterPanel.getHostForLoading(), bus, this, host, factory);
		this.titleCardPresenter = new TitleCardPresenter(
				masterPanel.getHostForTitleCard(), bus, this, factory);

		this.masterPanel
		.setActiveState(IMasterPanelFromMasterPresenter.GuiStateEnum.Loading);
	}

	private void clearListOfIntegersToInsertAt() {
		this.theListOfIndexesToInsertAt = new Integer[100];
		for (int i = 0; i < 100; i++)
			theListOfIndexesToInsertAt[i] = new Integer(0);
		
	}

	public void setCallbacks(IGameScene callbacks) {
		if (this.sceneHandlers != null) {
			lastSceneAsString = this.sceneHandlers.toString();
		}
		this.loaderPresenter.setName(callbacks.toString());
		this.sceneHandlers = callbacks;
	}

	public MasterPresenter getHeaderPanel() {
		return this;
	}

	@Override
	public boolean addImageForAnInventoryItem(LoadHandler lh, String itid,
			int icode, IPackagedImage imageResource) {
		if (this.sceneHandlers == null) {
			return true;
		}
		InventoryItem item = this.getInventoryPresenter().getInventory()
				.items().getByItid(itid);
		boolean result = true;

		if (item == null) {

			Image imageAndPos = getInventoryPresenter().getView()
					.createNewImageAndAdddHandlers(imageResource, lh, bus,
							itid, icode, 0, 0);

			imageAndPos.addImageToPanel(0);

			boolean initiallyVisible = false;
			result = getInventoryPresenter().addInventory(itid, icode,
					initiallyVisible, imageAndPos);

		}

		return result;
	}

	@Override
	public boolean addImageForASceneObject(LoadHandler lh, int numberPrefix,
			int x, int y, int w, int h, String otid, String atid, short ocode,
			String objPlusAnimCode, IPackagedImage imageResource) {
		if (this.sceneHandlers == null) {
			return true;
		}

		Image imageAndPos = this.scenePresenter.getView()
				.createNewImageAndAddHandlers(lh, imageResource,
						scenePresenter, bus, x, y, otid, ocode);

		loaderPresenter.getLoaders().addToAppropriateAnimation(numberPrefix,
				imageAndPos, otid, atid, ocode, objPlusAnimCode,
				scenePresenter.getSceneGuiWidth(),
				scenePresenter.getSceneGuiHeight());

		int before = getIndexToInsertAt(numberPrefix);
		updateTheListOfIndexesToInsertAt(numberPrefix);

		// this triggers the loading
		imageAndPos.addImageToPanel(before);

		return true;
	}

	public int getIndexToInsertAt(int numberPrefix) {
		int i = theListOfIndexesToInsertAt[numberPrefix];
		return i;
	}

	void updateTheListOfIndexesToInsertAt(int numberPrefix) {
		for (int i = numberPrefix; i <= 99; i++) {
			theListOfIndexesToInsertAt[i]++;
		}
	}

	public void executeActionWithDialogActionRunner(BaseAction a) {
		if (a == null) {
			// null must get turned in to 
			// a DialogTreeEndAction
			// since
			// a) when it falls thru the switch
			// we want it to exit the dialog..
			// not to just sit there and hang.
			// b) if it falls thru the switch
			// it will usually fall to what
			// was auto-generated as the default
			// return value when the IGameScene
			// interface was implemented.
			// c) the default return value, when
			// IGameScene is implemented
			// is null..
			// ... thus null must be interpreted
			// as DialogTreeEndAction
			a = new DialogTreeEndAction(createChainRootAction());
		}

		dialogActionRunner.runAction(a);
	}

	public void executeActionWithDoCommandActionRunner(BaseAction a) {
		if (a == null) {
			a = new DoNothingAction(createChainRootAction());
		}

		doCommandActionRunner.runAction(a);
	}
	
	public void executeActionWithOnEveryFrameActionRunner(BaseAction a) {
		if (a == null) {
			a = new DoNothingAction(createChainRootAction());
		}

		onEveryFrameActionRunner.runAction(a);
	}

	public void skip() {
		dialogActionRunner.skip();
	}

	public void setInitialAnimationsAsCurrent() {
		int count = this.scenePresenter.getModel().objectCollection().count();
		for (int i = 0; i < count; i++) {
			SceneObject sceneObject = this.scenePresenter.getModel()
					.objectCollection().getByIndex(i);

			if (sceneObject != null) {
				sceneObject.setToInitialAnimationWithoutChangingFrame(); // to
				// the
				// positions
				// they
				// were
				// in
				// when
				// all
				// objects
				// were
				// rendered
				// out.
				sceneObject.setX(0);
				sceneObject.setY(0);
			}
		}

	}

	public void callOnPreEntry() {
		this.sceneHandlers.onPreEntry(proxyForGameScene);
	}

	@Override
	public void onTimer() {

		if (timer != null) {
			this.sceneHandlers.onEveryFrame(proxyForGameScene);
			// this.checkForBoundaryCross();
		}
		if (switchTimer != null) {
			switchTimer.cancel();
			switchTimer = null;
			setCameraToZero();// no scene is meant to keep camera position
			this.host
			.instantiateSceneAndCallSetSceneBackOnTheMasterPresenter(switchDestination);
			switchDestination = "";
		}
	}

	public void loadInventoryFromAPI() {

		getInventoryPresenter().updateInventory();
	}

	public void saveInventoryToAPI() {
		InventoryItemCollection items = this.getInventoryPresenter()
				.getInventory().items();

		for (int i = 0; i < items.getCount(); i++) {
			String name = items.getByIndex(i).getItid();

			int isCarrying = items.getByIndex(i).isVisible() ? 1 : 0;

			setValue("CARRYING_" + name.toUpperCase(), isCarrying);
		}
	}

	public void setValue(Object key, int value) {
		String keyAsString = key.toString();
		host.setValue(keyAsString, value);
	}

	@Override
	public int getValue(Object key) {
		String keyAsString = key.toString();

		int i = host.getValue(keyAsString);

		return i;
	}

	public boolean isTrue(Object key) {
		String keyAsString = key.toString();

		int property = getValue(keyAsString);

		return property != 0;
	}

	@Override
	public void switchToSceneFromAction(String scene) {
		cancelOnEveryFrameTimer();
		this.dialogActionRunner.cancel();

		// now wait for the last onEveryFrame to execute
		// .. which is about 40 milliseconds
		// (an onEveryFrame can go more than
		// this, but usually not).
		switchTimer = getFactory().createSystemTimer(this);
		switchDestination = scene;
		switchTimer.scheduleRepeating(40);
	}

	@Override
	public void switchToScene(String scene) {
		
			// since instantiateScene..ToIt does some asynchronous stuff,
			// I thought maybe I could do it, then cancel the timers.
			// but I've put it off til I need the microseconds.
			cancelOnEveryFrameTimer();
			this.dialogActionRunner.cancel();
			setCameraToZero();// no scene is meant to keep camera position
			this.host.instantiateSceneAndCallSetSceneBackOnTheMasterPresenter(scene);
		
	}

	public String getLastScene() {
		if (lastSceneAsString != null)
			return lastSceneAsString.toUpperCase();
		return "";
	}

	public boolean isInDebugMode() {
		return true;
	}

	public void startCallingOnEveryFrame() {
		if(timer!=null)
			timer.cancel();
		timer = getFactory().createSystemTimer(this);
		timer.scheduleRepeating(40);
	}

	public void cancelOnEveryFrameTimer() {
		if (this.timer != null) {
			this.timer.cancel();
			timer = null;
		}
	}

	public IGameScene getCurrentScene() {
		return this.sceneHandlers;
	}

	@Override
	public void saySpeechAndThenExecuteBranchWithBranchId(int branchId) {
		// get speech before clearing
		String speech = this.getDialogTreePresenter().getLineOfDialogForId(branchId);
		
		// clear the branches
		this.dialogTreePresenter.clearBranches();
		
		// mark speech as said, but not the escape phrase, that is golden.
		if(branchId!=-1)
		{
			this.dialogTreePresenter.markSpeechAsSaid(speech);
		}
		String animId = this.dialogTreePresenter.getDialogTreeTalkAnimation();
		// This is a bit sneaky:
		// 1. we create a TalkAction as the root of the chain.
		// 2. we pass this to onDialogTree..
		// 3. ..where the user appends other actions to it
		// 4. Then we execute it
		// Thus it will talk the text, and do what the user prescribes.

		// String animId = getDialogTreeGui().setBranchVisited(branchId);
		DialogTreeTalkAction talk = new DialogTreeTalkAction(createDialogChainRootAction(), animId, speech);
		BaseAction actionChain = sceneHandlers.onDialogTree(
				proxyForGameScene, talk, branchId);
		BaseAction  actionChain2 = replaceDialogChainToActionWithOnDialogTreeChain(actionChain);
		
		executeActionWithDialogActionRunner(actionChain2);
	}

	public void callOnEnterScene() {
		BaseAction a = this.sceneHandlers.onEntry(proxyForGameScene,
				createChainRootAction());

		// .. then executeBaseAction->actionRunner::runAction will add an
		// TitleCardAction
		// the title card
		executeActionWithDoCommandActionRunner(a);
	}

	

	public IMasterPanelFromMasterPresenter getMasterPanel() {
		return masterPanel;
	}

	void setCameraToZero() {
		scenePresenter.setCameraX(0);
		scenePresenter.setCameraY(0);
	}

	void clearBoundaries() {
		this.gatePoints.clear();
		this.gateIds.clear();
	}

	@Override
	public void startScene() {
		masterPanel
		.setActiveState(IMasterPanelFromMasterPresenter.GuiStateEnum.Loading);
		loadInventoryFromAPI();
		setInitialAnimationsAsCurrent();
		clearBoundaries();

		// setAllObjectsToVisible();
		// it is reasonable for a person to set current animations in pre-entry
		// and expect them to stay current, so we set cuurentAnimations before
		// pre-entry.

		callOnPreEntry();

		startCallingOnEveryFrame();
		this.masterPanel
		.setActiveState(IMasterPanelFromMasterPresenter.GuiStateEnum.TitleCardOverOnEnterScene);
		callOnEnterScene();

	}

	public void addEssential(ILoad blah) {
		loaderPresenter.getLoaders().addEssential(blah, this);
	}

	public void kickStartLoading() {
		loaderPresenter.getLoaders()
		.calculateImagesToLoadAndOmitInventoryIfSame();

		int total = loaderPresenter.getLoaders().imagesToLoad();
		boolean isSameInventory = loaderPresenter.getLoaders()
				.isSameInventoryAsLastTime();
		
	
		// hide all visible images.
		// (using scene's data is quicker than using scenePanel data)
		int count = scenePresenter.getModel().objectCollection().count();
		for (int i = 0; i < count; i++) {
			scenePresenter.getModel().objectCollection().getByIndex(i)
			.setVisible(false);
		}

		scenePresenter.reset();

		// set gui to blank
		masterPanel
		.setActiveState(IMasterPanelFromMasterPresenter.GuiStateEnum.Loading);
		scenePresenter.clearEverythingExceptView(); // something like caching doesn't work if this is on.
		loaderPresenter.clear();
		// commandLinePresenter.clear();
		verbsPresenter.clear();

		if (!isSameInventory) {
			getInventoryPresenter().clear();
		}

		loaderPresenter.setTotal(total);
		loaderPresenter.getLoaders().loadNext();
	}

	@Override
	public void setScenePixelSize(int width, int height) {
		this.scenePresenter.setScenePixelSize(width, height);
		this.titleCardPresenter.setScenePixelSize(width, height);
		this.loaderPresenter.setScenePixelSize(width, height);
		this.dialogTreePresenter.setScenePixelSize(width, height >> 1);
		this.verbsPresenter.setWidthOfScene(width);
	}
	
	void clearMapOfSounds()
	{
		for (Map.Entry<String, ISound> entry : mapOfSounds.entrySet())
		{
			entry.getValue().stop();
		}
		mapOfSounds.clear();
	}

	public void setScene(IGameScene scene) {

		setCallbacks(scene);

		// give the setContinueAfterLoad a default
		this.loaderPresenter.setContinueAfterLoad(host.isAutoplay());

		// clear sounds before onFillLoadList
		clearMapOfSounds();
		
		// then in the scene the user can overwrite this.
		this.sceneHandlers
		.onFillLoadList(new IOnFillLoadListImpl(proxyForGameScene));
	}

	@Override
	public void restartReloading() {
		loaderPresenter.getLoaders().clearLoaders();

		this.sceneHandlers
		.onFillLoadList(new IOnFillLoadListImpl(proxyForGameScene));
	}

	@Override
	public void mergeWithScene(LoadedLoad s) {
		String name = s.getName();
		LOADING.log(Level.FINE, "merge with scene {0}",new Object[]{name});

		SceneObjectCollection theirs = s.getSceneObjectCollection();
		SceneObjectCollection ours = this.scenePresenter.getModel()
				.objectCollection();

		for (int i = 0; i < theirs.count(); i++) {
			SceneObject srcObject = theirs.getByIndex(i);
			String otid = srcObject.getOtid();
			int prefix = srcObject.getNumberPrefix();
			short ocode = srcObject.getOCode();
			SceneObject destObject = ours.getByOtid(otid);
			if (destObject == null) {
				destObject = new SceneObject(otid,
						scenePresenter.getSceneGuiWidth(),
						scenePresenter.getSceneGuiHeight());
				destObject.setNumberPrefix(prefix);
				destObject.setOCode(ocode);

				if (ocode == -1) {
					host.alert("Missing initial image for "
							+ otid
							+ "\n At the least it will need an image in a placeholder folder, so it shows up in list of objects.");
					return;
				}

				ours.add(destObject);
				scenePresenter.addSceneObject(destObject);
				LOADING.log(Level.FINE, "new object {0} {1}",new Object[]{otid,ocode});


			}

			for (int j = 0; j < srcObject.getAnimations().getCount(); j++) {
				Animation srcAnimation = srcObject.getAnimations()
						.getByIndex(j);
				String atid = srcAnimation.getAtid();

				Animation destAnimation = destObject.getAnimations().getByAtid(
						atid);
				if (destAnimation == null) {
					destAnimation = new Animation(atid, destObject);
					destObject.getAnimations().add(destAnimation);
					scenePresenter.addAnimation(atid, destAnimation);
				}

				LOADING.log(Level.FINE, "new anim {0} {1}",new Object[]{otid,atid});


				for (int k = 0; k < srcAnimation.getFrames().getCount(); k++) {
					Image srcImage = srcAnimation.getFrames().getByIndex(k);
					destAnimation.getFrames().add(srcImage);
				}

			}

		}
	}

	IMasterPanelFromMasterPresenter.GuiStateEnum getStateIfEntering(
			IMasterPanelFromMasterPresenter.GuiStateEnum state) {
		switch (state) {
		case OnEnterScene:
			return IMasterPanelFromMasterPresenter.GuiStateEnum.TitleCardOverOnEnterScene;
		case DialogTree:
			return IMasterPanelFromMasterPresenter.GuiStateEnum.TitleCardOverDialogTree;
		case CutScene:
			return IMasterPanelFromMasterPresenter.GuiStateEnum.TitleCardOverCutScene;
		case ActiveScene:
			return IMasterPanelFromMasterPresenter.GuiStateEnum.TitleCardOverActiveScene;
		case Loading:
			return IMasterPanelFromMasterPresenter.GuiStateEnum.TitleCardOverLoading;
		default:
			return state;
		}
	}

	IMasterPanelFromMasterPresenter.GuiStateEnum getStateIfExiting(
			IMasterPanelFromMasterPresenter.GuiStateEnum state) {
		switch (state) {
		case TitleCardOverOnEnterScene:
			return IMasterPanelFromMasterPresenter.GuiStateEnum.OnEnterScene;
		case TitleCardOverDialogTree:
			return IMasterPanelFromMasterPresenter.GuiStateEnum.DialogTree;
		case TitleCardOverCutScene:
			return IMasterPanelFromMasterPresenter.GuiStateEnum.CutScene;
		case TitleCardOverActiveScene:
			return IMasterPanelFromMasterPresenter.GuiStateEnum.ActiveScene;
		case TitleCardOverLoading:
			return IMasterPanelFromMasterPresenter.GuiStateEnum.Loading;
		default:
			return state;
		}
	}

	public void displayTitleCard(String text) {
		boolean isEntering = text.length() > 0;
		if (isEntering) {
			titleCardPresenter.setText(text);
		}
		IMasterPanelFromMasterPresenter.GuiStateEnum state = masterPanel
				.getActiveState();
		state = isEntering ? getStateIfEntering(state)
				: getStateIfExiting(state);
		masterPanel.setActiveState(state);
	}

	@Override
	public void incrementProgress() {
		loaderPresenter.incrementProgress();
	}

	@Override
	public IFactory getFactory() {
		return host.getFactory(bus, this);
	}

	SentenceItem getSIOfObject(int code)
	{
		if(SentenceItem.isInventory(code))
		{
			InventoryItem i = this.getInventoryPresenter().getInventoryItemByICode(code);
			return new SentenceItem(i.getDisplayName(),i.getItid(),code);
		}
		String otid = this.getScenePresenter().getOtidByCode((short)code);
		SceneObject o = this.getScenePresenter().getObjectByOtid(otid);
		if(o==null)
			return new SentenceItem();
		
		return new SentenceItem(o.getDisplayName(),o.getOtid(),code);
	}
	
	SentenceItem getSIOfVerb(int vcode)
	{
		Verb v = this.verbsPresenter.getVerbsModel().items().getVerbByCode(vcode);
		if(v==null)
			return new SentenceItem();
		
		return new SentenceItem(v.getdisplayText(), v.getVtid(), vcode);
	}
	
	void linkUpperMostActionOfAToB(BaseAction a, BaseAction b)
	{
		for(;;)
		{
			if(a.getParent()==null)
				break;
			a = a.getParent();
		}
		a.setParent(b);
	}
	
	BaseAction replaceDialogChainToActionWithOnDialogTreeChain(BaseAction b)
	{
		if (b instanceof DialogTreeChainToAction) {
			int branchId = ((DialogTreeChainToAction) b).getBranchId();
			DecoratedForDialogBaseAction d = createDialogChainRootAction();
			BaseAction a = this.sceneHandlers.onDialogTree(proxyForGameScene, d, branchId);
			linkUpperMostActionOfAToB(a,b);
			return a;
		}
		return b;
	}
	BaseAction replaceDoDialogActionWithOnDialogTreeChain(BaseAction a)
	{
		if (a instanceof ActivateDialogTreeModeAction) {
			int branchId = ((ActivateDialogTreeModeAction) a).getBranchId();
			DecoratedForDialogBaseAction d = createDialogChainRootAction();
			BaseAction b = this.sceneHandlers.onDialogTree(proxyForGameScene, d, branchId);
			return b;
		}
		return a;
	}
	
	
	@Override
	public void doCommand(int verbAsCode, int verbAsVerbEnumeration,
			SentenceItem sentenceA, SentenceItem sentenceB, double x, double y) {

		BaseAction a = this.sceneHandlers.onDoCommand(proxyForGameScene,
				createChainRootAction(), verbAsCode, sentenceA, sentenceB, x
				+ scenePresenter.getCameraX(),
				y + scenePresenter.getCameraY());

		this.commandLinePresenter.setMouseable(false);
		
		a = replaceDoDialogActionWithOnDialogTreeChain(a);
				
		executeActionWithDoCommandActionRunner(a);

		host.setLastCommand(x, y, verbAsVerbEnumeration,
				sentenceA.getTextualId(), sentenceB.getTextualId());

	}
 
	void ProcessAutoplayCommand(int id)
	{
		//ignore id==3, which is the oneveryframe action runner
		if(id==3)
			return;
		
		AutoplayCommand cmd  = this.host.getNextAutoplayAction();
		if(cmd!=null)
		{
			
			BaseAction a = null;
			if(cmd.getVerb()==ConstantsForAPI.DIALOG)
			{
				int branchId  = cmd.getBranch();
				String text = dialogTreePresenter.getLineOfDialogForId(branchId);
				if(text=="")
				{
					isAutoplayCancelled = true;
					titleCardPresenter.setText("can't say that id currently");
					return;
				}
				COMMAND_AUTOPLAY.log(Level.FINE, "DIALOG {0} {1}", new Object[]{branchId,text});
				saySpeechAndThenExecuteBranchWithBranchId(branchId);
			}
			else
			{
				if(cmd.getVerb()==ConstantsForAPI.SLEEP)
				{
					// SLEEP = sleep for 100ms
					a = createChainRootAction().sleep(cmd.getObj1());
					COMMAND_AUTOPLAY.log(Level.FINE, "SLEEP {0}", new Object[]{cmd.getObj1()});
					
				}
				else if(cmd.getVerb()==ConstantsForAPI.SWITCH)
				{
					a = createChainRootAction().switchTo(cmd.getString());
					COMMAND_AUTOPLAY.log(Level.FINE, "SWITCH {0}", new Object[]{cmd.getString()});
					
				}
				else 
				{
					COMMAND_AUTOPLAY.log(Level.FINE, "{0} {1} {2}", 
							new Object[]{cmd.getVerbAsString(), getSIOfObject(cmd.getObj1()).getDisplayName(), getSIOfObject(cmd.getObj2()).getDisplayName()});
					
					this.commandLinePresenter.setVerbItemItem(getSIOfVerb(cmd.getVerb()), getSIOfObject(cmd.getObj1()), getSIOfObject(cmd.getObj2()));

					//otherwise ask the sceneHanders what the outcome is.
					SentenceItem o1 = new SentenceItem("","",cmd.getObj1());
					SentenceItem o2 = new SentenceItem("","",cmd.getObj2());
					a = this.sceneHandlers.onDoCommand(proxyForGameScene,
							createChainRootAction(), cmd.getVerb(),o1,o2,cmd.getDouble1(),cmd.getDouble2());
					if (a instanceof DoNothingAction) {
						isAutoplayCancelled = true;
						titleCardPresenter.setText("action returned do nothing");
						return;
				
					}

					a = replaceDoDialogActionWithOnDialogTreeChain(a);
				}
				this.commandLinePresenter.setMouseable(false);
				executeActionWithDoCommandActionRunner(a);
			}
		}
	}

	@Override
	public void actionFinished(int id) {
		// must be in this order, because clear does't work unless mousable
		this.commandLinePresenter.setMouseable(true);
		this.commandLinePresenter.clear();

		if (masterPanel.getActiveState() == IMasterPanelFromMasterPresenter.GuiStateEnum.OnEnterScene) 
		{
			this.masterPanel
			.setActiveState(IMasterPanelFromMasterPresenter.GuiStateEnum.ActiveScene);
		}
		IMasterPanelFromMasterPresenter.GuiStateEnum state = masterPanel.getActiveState();
		if (state == IMasterPanelFromMasterPresenter.GuiStateEnum.ActiveScene||
			state == IMasterPanelFromMasterPresenter.GuiStateEnum.DialogTree) 
		{
			if(this.host.isAutoplay() && !isAutoplayCancelled)
			{
				ProcessAutoplayCommand(id);
			}
		}
	}

	@Override
	public void setInventoryImageSize(int width, int height) {
		this.getInventoryPresenter().setSizeOfSingleInventoryImage(width, height);
		this.verbsPresenter.setWidthOfInventory(getInventoryPresenter().getWidth());
	}

	@Override
	public void onClickVerbsOrInventory() {
		// a click on the inventory results in negative coords.
		commandLinePresenter.onClick(-1, -1);

	}

	@Override
	public void onMouseOverVerbsOrInventory(String displayName,
			String textualId, int icode) {
		commandLinePresenter.setCommandLineMouseOver(displayName, textualId,
				icode);
		bus.fireEvent(new SetRolloverEvent(displayName, textualId, icode));
	}

	@Override
	public void onPropertyChange(PropertyChangeEvent inventoryEvent) {
		this.getInventoryPresenter().updateInventory();
	}

	public IGameScene getSceneByName(String string) {
		return this.host.getSceneViaCache(string);
	}


	@Override
	public boolean isCommandLineActive() {
		boolean isCommandLineActive = masterPanel.getActiveState() == IMasterPanelFromMasterPresenter.GuiStateEnum.ActiveScene;
		return isCommandLineActive;
	}

	public void clearAllLoadedLoads() {
		this.loaderPresenter.clearAllLoadedLoads();
		// if we're clearing all loaded loads then
		// we want to also get rid of currently loaded
		// inventory images. So we clear the inventory too.
		// actually this should probably go under "loseEverything"
		this.inventoryPresenter.clear();
		this.scenePresenter.clearEverythingExceptView();
		this.scenePresenter.clearView();
		this.clearListOfIntegersToInsertAt();
	}

	public void setActiveState(GuiStateEnum state) {
		this.masterPanel.setActiveState(state);

	}

	public ChainRootAction createChainRootAction() {
		ChainRootAction npa = new ChainRootAction();
		return npa;
	}
	
	public DialogChainRootAction createDialogChainRootAction() {
		DialogChainRootAction npa = new DialogChainRootAction();
		return npa;
	}

	public void executeChainedAction(ChainableAction ba) {
		executeActionWithOnEveryFrameActionRunner(ba);
	}

	@Override
	public void enableClickToContinue() {
		this.loaderPresenter.onLoadingComplete();
	}

	public void setIsSayAlwaysWithoutIncremementing(
			boolean isSayWithoutIncremementing) {
		this.isSayNonIncremementing = isSayWithoutIncremementing;
	}

	public boolean getIsSayAlwaysWithoutIncremementing() {
		return isSayNonIncremementing;
	}

	public void setContinueAfterLoad(boolean isIgnore) {
		this.loaderPresenter.setContinueAfterLoad(isIgnore);

	}

	public void addBoundaryGate(int id, PointF a, PointF b) {
		gateIds.add(new Integer(id));
		gatePoints.add(a);
		gatePoints.add(b);
	}

	public void addBoundaryPoint(PointF a) {
		gateIds.add(new Integer(-1));
		gatePoints.add(new PointF(-1, -1));
		// important for the valid point to be the last one,
		// due to how the span calculations use the last one.
		gatePoints.add(a);

	}

	boolean arePointsSameSide(PointF A, PointF B, PointF tp, PointF c) {
		double result1 = (B.getX() - A.getX()) * (tp.getY() - A.getY())
				- (B.getY() - A.getY()) * (tp.getX() - A.getX());
		double result2 = (B.getX() - A.getX()) * (c.getY() - A.getY())
				- (B.getY() - A.getY()) * (c.getX() - A.getX());
		return result1 * result2 > 0;
	}

	public void setBoundaryCrossObject(short boundaryCrossObject) {
		this.boundaryCrossObject = boundaryCrossObject;
	}

	short getBoundaryCrossObject() {
		return boundaryCrossObject;
	}

	PointF getGatePointsCentre() {
		double totalX = 0;
		double totalY = 0;
		int numberOfExtras = 0;
		double size = gateIds.size();
		for (int i = 0; i < size; i++) {
			if (gateIds.get(i) != -1) {
				PointF bp = gatePoints.get(i * 2);
				totalX += bp.getX();
				totalY += bp.getY();
				numberOfExtras++;
			}

			PointF bp = gatePoints.get(i * 2 + 1);
			totalX += bp.getX();
			totalY += bp.getY();
		}
		double numberOfPoints = size + numberOfExtras;
		return new PointF(totalX / numberOfPoints, totalY / numberOfPoints);
	}

	PointF getMidPoint(PointF a, PointF b) {
		return new PointF(a.getX() / 2 + b.getX() / 2, a.getY() / 2 + b.getY()
				/ 2);
	}

	boolean isBetweenSpokesAndOnWrongSide(PointF p1, PointF p2, PointF tp) {
		PointF c = getGatePointsCentre();
		PointF mp = getMidPoint(p1, p2);
		boolean isBetweenSpokes = arePointsSameSide(c, p1, mp, tp)
				&& arePointsSameSide(c, p2, mp, tp);
		if (!isBetweenSpokes)
			return false;
		boolean isOnSameSideAsCentre = arePointsSameSide(p1, p2, c, tp);
		if (isOnSameSideAsCentre)
			return false;
		return true;
	}

	@Override
	public boolean isInANoGoZone(PointF tp) {
		if (gatePoints.size() < 2)
			return false;

		// this following line relies craftily on addPoint to
		// put a dummy value in the first slot, and
		// the valid value in the last slot
		PointF previousPoint = gatePoints.get(gatePoints.size() - 1);

		int size = gateIds.size();
		for (int i = 0; i < size; i++) {
			// only gates (designated by ids >=0) get their first point
			// processed
			if (gateIds.get(i) != -1) {
				PointF firstPoint = gatePoints.get(i * 2 + 0);
				if (isBetweenSpokesAndOnWrongSide(previousPoint, firstPoint, tp))
					return true;
				previousPoint = firstPoint;
			}

			// every gate/point has their second point processed.
			PointF secondPoint = gatePoints.get(i * 2 + 1);
			if (isBetweenSpokesAndOnWrongSide(previousPoint, secondPoint, tp))
				return true;
			previousPoint = secondPoint;
		}
		return false;
	}

	@Override
	public boolean fireOnMovementBeyondAGateIfRelevant(PointF tp) 
	{
		int foundId = -1;
		if (gatePoints.size() > 2) {
			int size = gateIds.size();
			for (int i = 0; i < size; i++) {
				if (gateIds.get(i) == -1)
					continue;

				PointF a = gatePoints.get(i * 2);
				PointF b = gatePoints.get(i * 2 + 1);

				if (isBetweenSpokesAndOnWrongSide(a, b, tp)) {
					foundId = gateIds.get(i);
					this.sceneHandlers.onMovementBeyondAGate(proxyForGameScene, a,b,tp, foundId);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void shareWinning(String token) {
		this.host.shareWinning(token);

	}

	public void log(String type, String content) {
		this.host.log(type);

	}

	@Override
	public void setValue(String name, int value) {
		host.setValue(name, value);

	}

	@Override
	public IDialogTreePanelFromDialogTreePresenter createDialogTreePanel(
			EventBus bus, ColorEnum fore, ColorEnum back, ColorEnum rollover) {
		return host.getFactory(bus, this).createDialogTreePanel(this, fore,
				back, rollover);
	}

	public ScenePresenter getScenePresenter() {
		return scenePresenter;
	}

	public CommandLinePresenter getCommandLinePresenter() {
		return commandLinePresenter;
	}

	public VerbsPresenter getVerbsPresenter() {
		return verbsPresenter;
	}

	public InventoryPresenter getInventoryPresenter() {
		return inventoryPresenter;
	}

	DialogTreePresenter getDialogTreePresenter() {
		return dialogTreePresenter;
	}

	TitleCardPresenter getTitleCardPresenter() {
		return titleCardPresenter;
	}

	LoaderPresenter getLoaderPresenter() {
		return loaderPresenter;
	}

	@Override
	public double getPopupDisplayDuration() {
		return titleCardPresenter.getPopupDisplayDuration();
	}

	@Override
	public boolean isSayNonIncrementing() {
		return this.isSayNonIncremementing;
	}

	public void quit() {
		host.quit();
		
	}

	

	@Override
	public void playSoundByStid(String stid) {
		mapOfSounds.get(stid).play();
		
	}

	@Override
	public double getSoundDurationByStid(String stid) {
		double dur = mapOfSounds.get(stid).getDuration();
		return dur;
	}

	@Override
	public boolean addMP3ForASoundObject(String name, String location) 
	{
		ISound sound = this.getFactory().createSound(location);
		this.mapOfSounds.put(name, sound);
		
		return false;
	}

	@Override
	public void stopSoundByStid(String stid) {
		mapOfSounds.get(stid).stop();
	}

}
