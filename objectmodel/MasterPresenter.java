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

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.a2g.core.primitive.A2gException;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.action.ActionRunner;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.ChainableAction;
import com.github.a2g.core.action.ChainToDialogAction;
import com.github.a2g.core.action.DialogChainToDialogAction;
import com.github.a2g.core.action.DialogEndAction;
import com.github.a2g.core.action.DialogTalkAction;
import com.github.a2g.core.action.DoNothingAction;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.LogNames;
import com.github.a2g.core.action.DialogChainableAction;
import com.github.a2g.core.event.PropertyChangeEvent;
import com.github.a2g.core.event.PropertyChangeEventHandlerAPI;
import com.github.a2g.core.event.SetRolloverEvent;
import com.github.a2g.core.interfaces.ConstantsForAPI;
import com.github.a2g.core.interfaces.IAuxGameScene;
import com.github.a2g.core.interfaces.internal.IDialogTreePanelFromDialogTreePresenter;
import com.github.a2g.core.interfaces.internal.IFactory;
import com.github.a2g.core.interfaces.internal.IHostFromMasterPresenter;
import com.github.a2g.core.interfaces.internal.IHostingPanel;
import com.github.a2g.core.interfaces.internal.IBundleLoader;
import com.github.a2g.core.interfaces.internal.IMasterPanelFromMasterPresenter;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromActionRunner;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromBundle;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromCommandLine;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromDialogTree;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromInventory;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromLoader;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromScenePresenter;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromTimer;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromTitleCard;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromVerbs;
import com.github.a2g.core.interfaces.internal.IPackagedImage;
import com.github.a2g.core.interfaces.internal.ISound;
import com.github.a2g.core.interfaces.internal.ITimer;
import com.github.a2g.core.interfaces.internal.IMasterPanelFromMasterPresenter.GuiStateEnum;
import com.github.a2g.core.interfaces.IOnQueueResourcesImpl;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.interfaces.IMixin;
import com.google.gwt.event.shared.EventBus;

public class MasterPresenter
		implements IMasterPresenterFromActions, IMasterPresenterFromScenePresenter, IMasterPresenterFromDialogTree,
		IMasterPresenterFromTimer, IMasterPresenterFromBundle, IMasterPresenterFromLoader,
		IMasterPresenterFromCommandLine, IMasterPresenterFromActionRunner, IMasterPresenterFromInventory,
		IMasterPresenterFromVerbs, IMasterPresenterFromTitleCard, PropertyChangeEventHandlerAPI {
	private static final Logger LOADING = Logger.getLogger(LogNames.LOADING.toString());
	private static final Logger COMMANDS_AUTOPLAY = Logger.getLogger(LogNames.COMMANDS_AUTOPLAY.toString());
	private static final Logger ACTIONS_AS_THEY_ARE_EXECUTED = Logger.getLogger(LogNames.ACTIONS_AS_THEY_ARE_EXECUTED.toString());

	AllGameMethods proxyForGameScene;
	private CommandLinePresenter commandLinePresenter;
	private InventoryPresenter inventoryPresenter;
	private VerbsPresenter verbsPresenter;
	private ScenePresenter scenePresenter;
	private DialogTreePresenter dialogTreePresenter;
	private LoaderPresenter loaderPresenter;
	private TitleCardPresenter titleCardPresenter;

	private IGameScene sceneHandlers;
	private IAuxGameScene sceneHandlers2;

	private EventBus bus;
	private IHostFromMasterPresenter host;
	private ITimer timer;
	private ITimer switchTimer;
	private IMasterPanelFromMasterPresenter masterPanel;
	private ActionRunner dialogActionRunner;
	private ActionRunner doCommandActionRunner;
	private ActionRunner onEveryFrameActionRunner;

	private InsertionPointCalculator insertionPointCalculator;

	private String lastSceneAsString;
	private String switchDestination;
	private boolean isSayNonIncremementing;
	private AllActionMethods proxyForActions;
	private Map<String, ISound> mapOfSounds;
	private boolean isAutoplayCancelled;
	ISound soundtrack;

	public MasterPresenter(final IHostingPanel panel, EventBus bus, IHostFromMasterPresenter host) {
		this.bus = bus;
		isAutoplayCancelled = false;
		this.timer = null;
		this.switchTimer = null;
		this.host = host;
		this.proxyForGameScene = new AllGameMethods(this);
		this.proxyForActions = new AllActionMethods(this);
		this.insertionPointCalculator = new InsertionPointCalculator();
		mapOfSounds = new TreeMap<String, ISound>();

		IFactory factory = host.getFactory(bus, this);
		this.doCommandActionRunner = new ActionRunner(factory, proxyForActions, proxyForActions, proxyForActions,
				proxyForActions, proxyForActions, this, 1);
		this.dialogActionRunner = new ActionRunner(factory, proxyForActions, proxyForActions, proxyForActions,
				proxyForActions, proxyForActions, this, 2);
		this.onEveryFrameActionRunner = new ActionRunner(factory, proxyForActions, proxyForActions, proxyForActions,
				proxyForActions, proxyForActions, this, 3);
		insertionPointCalculator.clear();

		bus.addHandler(PropertyChangeEvent.TYPE, this);

		this.masterPanel = getFactory().createMasterPanel(320, 240, ColorEnum.Black);
		panel.setThing(this.masterPanel);

		this.dialogTreePresenter = new DialogTreePresenter(masterPanel.getHostForDialogTree(), bus, this);
		this.commandLinePresenter = new CommandLinePresenter(masterPanel.getHostForCommandLine(), bus, this);

		this.inventoryPresenter = new InventoryPresenter(masterPanel.getHostForInventory(), bus, this);
		this.scenePresenter = new ScenePresenter(masterPanel.getHostForScene(), this, factory);
		this.verbsPresenter = new VerbsPresenter(masterPanel.getHostForVerbs(), bus, this);
		this.loaderPresenter = new LoaderPresenter(masterPanel.getHostForLoading(), bus, this, host, factory);
		this.titleCardPresenter = new TitleCardPresenter(masterPanel.getHostForTitleCard(), bus, this, factory);

		this.masterPanel.setActiveState(IMasterPanelFromMasterPresenter.GuiStateEnum.Loading);
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
	public boolean addImageForAnInventoryItem(LoadHandler lh, String itid, int icode, IPackagedImage imageResource) {
		if (this.sceneHandlers == null) {
			return true;
		}
		InventoryItem item = this.getInventoryPresenter().getInventory().items().getByItid(itid);
		boolean result = true;

		if (item == null) {

			Image imageAndPos = getInventoryPresenter().getView().createNewImageAndAdddHandlers(imageResource, lh, bus,
					itid, icode, 0, 0);

			imageAndPos.addImageToPanel(0);

			boolean initiallyVisible = false;
			result = getInventoryPresenter().addInventory(itid, icode, initiallyVisible, imageAndPos);

		}

		return result;
	}

	@Override
	public boolean addImageForASceneObject(LoadHandler lh, int drawingOrder, int x, int y, int w, int h, String otid,
			String atid, short ocode, String objPlusAnimCode, IPackagedImage imageResource) {
		if (this.sceneHandlers == null) {
			return true;
		}

		Image imageAndPos = this.scenePresenter.getView().createNewImageAndAddHandlers(lh, imageResource,
				scenePresenter, bus, x, y, otid, ocode);

		loaderPresenter.getLoaders().addToAppropriateAnimation(drawingOrder, imageAndPos, otid, atid, ocode,
				objPlusAnimCode, scenePresenter.getSceneGuiWidth(), scenePresenter.getSceneGuiHeight());

		// if its adding an animation to an existing object then use preceding.
		drawingOrder = scenePresenter.getExistingPrefixIfAvailable(ocode, drawingOrder);

		int before = insertionPointCalculator.getIndexToInsertAt(drawingOrder);
		insertionPointCalculator.updateTheListOfIndexesToInsertAt(drawingOrder);

		// this triggers the loading
		imageAndPos.addImageToPanel(before);

		return true;
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
			a = new DialogEndAction(MatOps.createDialogChainRootAction());
		}

		dialogActionRunner.runAction(a);
	}

	public void executeActionWithDoCommandActionRunner(BaseAction a) {
		doCommandActionRunner.runAction(a);
	}

	public void executeActionWithOnEveryFrameActionRunner(BaseAction a) {
		if (a == null) {
			a = new DoNothingAction(MatOps.createChainRootAction());
		}

		onEveryFrameActionRunner.runAction(a);
	}

	public void skip() {
		dialogActionRunner.skip();
	}

	public void setInitialAnimationsAsCurrent() {
		int count = this.scenePresenter.getModel().objectCollection().getCount();
		for (int i = 0; i < count; i++) {
			SceneObject sceneObject = this.scenePresenter.getModel().objectCollection().getByIndex(i);

			if (sceneObject != null) {
				sceneObject.setToInitialAnimationWithoutChangingFrame(); // to
				// reset position
				sceneObject.setX(0);
				sceneObject.setY(0);
			}
		}

	}

	public void callOnPreEntry() {
		this.sceneHandlers2.onPreEntry(proxyForGameScene);
	}

	@Override
	public void onTimer() {

		if (timer != null) {
			this.sceneHandlers2.onEveryFrame(proxyForGameScene);
		}
		if (switchTimer != null) {
			switchTimer.cancel();
			switchTimer = null;
			setCameraToZero();// no scene is meant to keep camera position
			this.host.instantiateSceneAndCallSetSceneBackOnTheMasterPresenter(switchDestination);
			switchDestination = "";
		}
	}

	public void loadInventoryFromAPI() {

		getInventoryPresenter().updateInventory();
	}

	public void saveInventoryToAPI() {
		InventoryItemCollection items = this.getInventoryPresenter().getInventory().items();

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

	// @Override
	// public void switchToSceneFromAction(String scene) {
	// cancelOnEveryFrameTimer();
	// this.dialogActionRunner.cancel();
	//
	// // now wait for the last onEveryFrame to execute
	// // .. which is about 40 milliseconds
	// // (an onEveryFrame can go more than
	// // this, but usually not).
	// switchTimer = getFactory().createSystemTimer(this);
	// switchDestination = scene;
	// switchTimer.scheduleRepeating(40);
	// }

	@Override
	public void switchToScene(String scene, int arrivalSegment) {

		// since instantiateScene..ToIt does some asynchronous stuff,
		// I thought maybe I could do it, then cancel the timers.
		// but I've put it off til I need the microseconds.
		cancelOnEveryFrameTimer();
		this.dialogActionRunner.cancel();
		this.onEveryFrameActionRunner.cancel();
		this.doCommandActionRunner.cancel();// do we really need to cancel
		setCameraToZero();// no scene is meant to keep camera position
		this.scenePresenter.setArrivalSegment(arrivalSegment);
		this.host.instantiateSceneAndCallSetSceneBackOnTheMasterPresenter(scene);

	}

	public String getLastScene() {
		if (lastSceneAsString != null)
			return lastSceneAsString;
		return "";
	}

	public boolean isInDebugMode() {
		return true;
	}

	public void startCallingOnEveryFrame() {
		if (timer != null)
			timer.cancel();
		timer = getFactory().createTimer(this);
		timer.scheduleRepeating(40);
	}

	public void cancelOnEveryFrameTimer() {
		if (this.timer != null) {
			this.timer.cancel();
			timer = null;
		}
	}

	public String getCurrentSceneName() {
		return this.sceneHandlers.toString();
	}

	@Override
	public void saySpeechAndThenExecuteBranchWithBranchId(int branchId) {
		// get speech before clearing
		String speech = this.getDialogTreePresenter().getLineOfDialogForId(branchId);
		boolean isAddableToSaidList = this.getDialogTreePresenter().isAddableToSaidList(branchId);
		// clear the branches
		this.dialogTreePresenter.clearBranches();

		// mark speech as said, but not the escape phrase, that is golden.
		if (branchId != ConstantsForAPI.EXIT_DLG && isAddableToSaidList) {
			this.dialogTreePresenter.addSpeechToSaidList(speech);
		}
		String atidOfInterviewer = this.scenePresenter.getSceneDialoggerAtid();
		// This is a bit sneaky:
		// 1. we create a TalkAction as the root of the chain.
		// 2. we pass this to onDialogTree..
		// 3. ..where the user appends other actions to it
		// 4. Then we execute it
		// Thus it will talk the text, and do what the user prescribes.

		DialogTalkAction newTalkAction = new DialogTalkAction(MatOps.createDialogChainRootAction(), atidOfInterviewer,
				speech);

		try {
			BaseAction actionChain = sceneHandlers2.onDialogTree(proxyForGameScene, newTalkAction, branchId);
			BaseAction actionChain2 = replaceChainToDialogActionWithCallToOnDialogTree(actionChain);

			executeActionWithDialogActionRunner(actionChain2);
		} catch (A2gException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void callOnEnterScene() {
		if (scenePresenter.getBoundaryPoints().size() > 0) {
			scenePresenter.repositionDefaultObject();
		}

		BaseAction a;
		try {
			a = this.sceneHandlers2.onEntry(proxyForGameScene, MatOps.createChainRootAction());

			getScenePresenter().getView().onSceneEntry(sceneHandlers.toString());
			BaseAction b = this.replaceChainToDialogActionWithCallToOnDialogTree(a);
			// .. then executeBaseAction->actionRunner::runAction
			// will add an TitleCardAction the title card
			executeActionWithDoCommandActionRunner(b);
		} catch (A2gException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IMasterPanelFromMasterPresenter getMasterPanel() {
		return masterPanel;
	}

	void setCameraToZero() {
		scenePresenter.setCameraX(0);
		scenePresenter.setCameraY(0);
	}

	@Override
	public void startScene() {
		masterPanel.setActiveState(IMasterPanelFromMasterPresenter.GuiStateEnum.Loading);
		loadInventoryFromAPI();
		setInitialAnimationsAsCurrent();
		scenePresenter.clearBoundaries();

		// setAllObjectsToVisible();
		// it is reasonable for a person to set current animations in pre-entry
		// and expect them to stay current, so we set cuurentAnimations before
		// pre-entry.

		callOnPreEntry();

		startCallingOnEveryFrame();
		this.masterPanel.setActiveState(IMasterPanelFromMasterPresenter.GuiStateEnum.TitleCardOverOnEnterScene);
		callOnEnterScene();

	}

	public void addEssential(IBundleLoader blah) {
		loaderPresenter.getLoaders().addEssential(blah, this);
	}

	public void setSceneAsActiveAndKickStartLoading(IAuxGameScene scene) {
		this.sceneHandlers2 = scene;
		loaderPresenter.getLoaders().setSceneAndInventoryResolution();

		loaderPresenter.getLoaders().calculateImagesToLoadAndOmitInventoryIfSame();

		int total = loaderPresenter.getLoaders().getNumberOfImagesToLoad();
		boolean isSameInventory = loaderPresenter.getLoaders().isSameInventoryAsLastTime();

		// hide all visible images.
		// (using scene's data is quicker than using scenePanel data)
		int count = scenePresenter.getModel().objectCollection().getCount();
		for (int i = 0; i < count; i++) {
			scenePresenter.getModel().objectCollection().getByIndex(i).setVisible(false);
		}

		scenePresenter.reset();

		// set gui to blank
		masterPanel.setActiveState(IMasterPanelFromMasterPresenter.GuiStateEnum.Loading);
		scenePresenter.clearEverythingExceptView(); // something like caching
													// doesn't work if this is
													// on.
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

	void clearMapOfSounds() {
		for (Map.Entry<String, ISound> entry : mapOfSounds.entrySet()) {
			if (entry != this.soundtrack)
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
		if (null == this.sceneHandlers.onFillLoadList(new IOnQueueResourcesImpl(proxyForGameScene))) {
			startScene();
		}
	}

	@Override
	public void restartReloading() {
		loaderPresenter.getLoaders().clearLoaders();

		this.sceneHandlers.onFillLoadList(new IOnQueueResourcesImpl(proxyForGameScene));
	}

	@Override
	public void mergeWithScene(LoadedLoad s) {
		String name = s.getName();
		LOADING.log(Level.FINE, "merge with scene " + name);

		SceneObjectCollection theirs = s.getSceneObjectCollection();
		SceneObjectCollection ours = this.scenePresenter.getModel().objectCollection();

		for (int i = 0; i < theirs.getCount(); i++) {
			SceneObject srcObject = theirs.getByIndex(i);
			String otid = srcObject.getOtid();
			int prefix = srcObject.getDrawingOrder();
			short ocode = srcObject.getOCode();
			SceneObject destObject = null;
			int index = ours.getIndexByOtid(otid);
			if (index != -1) {
				destObject = ours.getByIndex(index);
			} else {
				destObject = new SceneObject(otid, scenePresenter.getSceneGuiWidth(),
						scenePresenter.getSceneGuiHeight());
				destObject.setDrawingOrder(prefix);
				destObject.setOCode(ocode);

				if (ocode == -1) {
					host.alert("Missing initial image for " + otid
							+ "\n At the least it will need an image in a placeholder folder, so it shows up in list of objects.");
					return;
				}

				ours.add(destObject);
				scenePresenter.addSceneObject(destObject);
				LOADING.log(Level.FINE, "new object " + otid + " " + ocode);

			}

			for (int j = 0; j < srcObject.getAnimations().getCount(); j++) {
				Animation srcAnimation = srcObject.getAnimations().getByIndex(j);
				String atid = srcAnimation.getAtid();

				Animation destAnimation = destObject.getAnimations().getByAtid(atid);
				if (destAnimation == null) {
					destAnimation = new Animation(atid, destObject);
					destObject.getAnimations().add(destAnimation);
					scenePresenter.addAnimation(atid, destAnimation);
				}

				LOADING.log(Level.FINE, "new anim " + otid + " " + atid);

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

	IMasterPanelFromMasterPresenter.GuiStateEnum getStateIfExiting(IMasterPanelFromMasterPresenter.GuiStateEnum state) {
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
		IMasterPanelFromMasterPresenter.GuiStateEnum state = masterPanel.getActiveState();
		state = isEntering ? getStateIfEntering(state) : getStateIfExiting(state);
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

	SentenceItem getSIOfObject(int code) {
		if (code == 1)
			return new SentenceItem();
		if (SentenceItem.isInventory(code)) {
			InventoryItem i = this.getInventoryPresenter().getInventoryItemByICode(code);
			return new SentenceItem(i.getDisplayName(), i.getItid(), code);
		}
		String otid = this.getScenePresenter().getOtidByCode((short) code);
		SceneObject o = this.getScenePresenter().getObjectByOtid(otid);
		if (o == null)
			return new SentenceItem();

		return new SentenceItem(o.getDisplayName(), o.getOtid(), code);
	}

	SentenceItem getSIOfVerb(int vcode) {
		Verb v = this.verbsPresenter.getVerbsModel().items().getVerbByCode(vcode);
		if (v == null)
			return new SentenceItem();

		return new SentenceItem(v.getdisplayText(), v.getVtid(), vcode);
	}

	void linkUpperMostActionOfAToB(BaseAction a, BaseAction b) {
		// a and b should be valid here.
		for (;;) {
			if (a.getParent() == null)
				break;
			if (a.getParent().getParent() == null)
				break;
			a = a.getParent();
		}
		a.setParent(b);
	}

	BaseAction replaceChainToDialogActionWithCallToOnDialogTree(BaseAction b) {
		if (b instanceof DialogChainToDialogAction || b instanceof ChainToDialogAction) {

			int branchId = ((ChainToDialogAction) b).getBranchId();
			DialogChainableAction d = MatOps.createDialogChainRootAction();
			BaseAction a = null;
			try {
				a = this.sceneHandlers2.onDialogTree(proxyForGameScene, d, branchId);
			} catch (A2gException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (a == null || a instanceof DoNothingAction)
				a = new DialogEndAction(d);
			linkUpperMostActionOfAToB(a, b);
			return a;
		}
		return b;
	}

	@Override
	public void doCommand(int verbAsCode, int verbAsVerbEnumeration, SentenceItem sentenceA, SentenceItem sentenceB,
			double x, double y) {

		BaseAction a = null;
		try {

			a = this.sceneHandlers2.onDoCommand(proxyForGameScene, MatOps.createChainRootAction(), verbAsCode,
					sentenceA, sentenceB, x + scenePresenter.getCameraX(), y + scenePresenter.getCameraY());

		} catch (A2gException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.commandLinePresenter.setMouseable(false);

		a = replaceChainToDialogActionWithCallToOnDialogTree(a);

		executeActionWithDoCommandActionRunner(a);

		host.setLastCommand(x, y, verbAsVerbEnumeration, sentenceA.getTextualId(), sentenceB.getTextualId());

	}

	void ProcessAutoplayCommand(int id) {
		// ignore id==3, which is the oneveryframe action runner
		if (id == 3)
			return;

		AutoplayCommand cmd = this.host.getNextAutoplayAction(proxyForGameScene);
		if (cmd == null) {
			host.onFinishedAutoplay(null, null);
		} else {

			GuiStateEnum mode = masterPanel.getActiveState();
			if (cmd.getVerb() == ConstantsForAPI.DIALOG) {
				if (mode != GuiStateEnum.DialogTree) {
					// verb was dialog, mode wasn't
					cancelAutoplay(cmd, "verb was dialog, mode wasn't");
					return;
				}
				int branchId = cmd.getBranch();
				boolean isValid = dialogTreePresenter.isBranchValid(branchId);

				// getLineOfDialog
				if (!isValid && branchId != ConstantsForAPI.EXIT_DLG) {
					cancelAutoplay(cmd, "Invalid branch number");
					return;
				}
				COMMANDS_AUTOPLAY.log(Level.FINE,
						"DIALOG " + branchId + " " + dialogTreePresenter.getLineOfDialogForId(branchId));
				saySpeechAndThenExecuteBranchWithBranchId(branchId);
			} else if (cmd.getVerb() == ConstantsForAPI.SLEEP) {
				// SLEEP = sleep for 100ms
				BaseAction sleep = MatOps.createChainRootAction().sleep(cmd.getInt1());
				COMMANDS_AUTOPLAY.log(Level.FINE, "SLEEP " + cmd.getInt1());
				executeActionWithDoCommandActionRunner(sleep);
			} else if (cmd.getVerb() == ConstantsForAPI.SWITCH) {
				BaseAction switchTo = MatOps.createChainRootAction().switchTo(cmd.getString(), 0);
				COMMANDS_AUTOPLAY.log(Level.FINE, "SWITCH " + cmd.getString());
				executeActionWithDoCommandActionRunner(switchTo);
			} else if (mode == GuiStateEnum.DialogTree) {
				// mode was dialog verb wasn't dialog or sleep
				cancelAutoplay(cmd, "mode was dialog verb wasn't dialog or sleep");
			} else {
				COMMANDS_AUTOPLAY.log(Level.FINE,
						"autoplay " + cmd.getVerbAsString() + " " + getSIOfObject(cmd.getInt1()).getDisplayName() + " "
								+ getSIOfObject(cmd.getInt2()).getDisplayName());

				this.commandLinePresenter.setVerbItemItem(getSIOfVerb(cmd.getVerb()), getSIOfObject(cmd.getInt1()),
						getSIOfObject(cmd.getInt2()));

				// otherwise ask the sceneHanders what the outcome is.
				SentenceItem o1 = new SentenceItem("", "", cmd.getInt1());
				SentenceItem o2 = new SentenceItem("", "", cmd.getInt2());
				BaseAction a = null;
				try {
					a = this.sceneHandlers2.onDoCommand(proxyForGameScene, MatOps.createChainRootAction(),
							cmd.getVerb(), o1, o2, cmd.getDouble1(), cmd.getDouble2());
				} catch (A2gException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (a == null || a instanceof DoNothingAction) {
					cancelAutoplay(cmd, "onDoCommand returned do nothing");
					return;
				}

				a = replaceChainToDialogActionWithCallToOnDialogTree(a);
				this.commandLinePresenter.setMouseable(false);
				executeActionWithDoCommandActionRunner(a);
			}
		}
	}

	void cancelAutoplay(AutoplayCommand cmd, String message) {
		if (!isAutoplayCancelled) {
			host.onFinishedAutoplay(cmd, message);
			isAutoplayCancelled = true;
		}
	}

	@Override
	public void actionChainFinished(int id) {
		ACTIONS_AS_THEY_ARE_EXECUTED.fine("-------------------------------------actionChainFinished!");
		if (id == 2)// this used to be id==2, but will leave until this yields
					// bugs
		{
			if (this.dialogTreePresenter.getNumberOfVisibleBranches() == 0) {
				setActiveGuiState(GuiStateEnum.ActiveScene);
			}
		}
		// must be in this order, because clear does't work unless mousable
		this.commandLinePresenter.setMouseable(true);
		this.commandLinePresenter.clear();

		if (masterPanel.getActiveState() == IMasterPanelFromMasterPresenter.GuiStateEnum.OnEnterScene) {
			this.masterPanel.setActiveState(IMasterPanelFromMasterPresenter.GuiStateEnum.ActiveScene);
		}
		IMasterPanelFromMasterPresenter.GuiStateEnum state = masterPanel.getActiveState();
		if (state == IMasterPanelFromMasterPresenter.GuiStateEnum.ActiveScene
				|| state == IMasterPanelFromMasterPresenter.GuiStateEnum.DialogTree) {
			if (this.host.isAutoplay() && !isAutoplayCancelled) {
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
	public void onMouseOverVerbsOrInventory(String displayName, String textualId, int icode) {
		commandLinePresenter.setCommandLineMouseOver(displayName, textualId, icode);
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
		boolean isCommandLineActive = masterPanel
				.getActiveState() == IMasterPanelFromMasterPresenter.GuiStateEnum.ActiveScene;
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
		this.insertionPointCalculator.clear();
	}

	public void setActiveGuiState(GuiStateEnum state) {
		this.masterPanel.setActiveState(state);

	}

	public void executeChainedAction(ChainableAction ba) {
		executeActionWithOnEveryFrameActionRunner(ba);
	}

	@Override
	public void enableClickToContinue() {
		this.loaderPresenter.onLoadingComplete();
	}

	public void setIsSayAlwaysWithoutIncremementing(boolean isSayWithoutIncremementing) {
		this.isSayNonIncremementing = isSayWithoutIncremementing;
	}

	public boolean getIsSayAlwaysWithoutIncremementing() {
		return isSayNonIncremementing;
	}

	public void setContinueAfterLoad(boolean isIgnore) {
		this.loaderPresenter.setContinueAfterLoad(isIgnore);

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
	public IDialogTreePanelFromDialogTreePresenter createDialogTreePanel(EventBus bus, ColorEnum fore, ColorEnum back,
			ColorEnum rollover) {
		return host.getFactory(bus, this).createDialogTreePanel(this, fore, back, rollover);
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

	@Override
	public void playSoundByStid(String stid) {
		mapOfSounds.get(stid).play();
	}

	public void setSoundtrack(String stid) {
		if (stid == null || stid.isEmpty()) {
			if (soundtrack != null)
				soundtrack.stop();
			return;
		}
		ISound sound = mapOfSounds.get(stid);
		soundtrack = this.getFactory().createSound(sound.getLocation());
		soundtrack.play();
	}

	@Override
	public double getSoundDurationByStid(String stid) {
		double dur = mapOfSounds.get(stid).getDuration();
		return dur;
	}

	@Override
	public boolean addMP3ForASoundObject(String name, String location) {
		ISound sound = this.getFactory().createSound(location);
		this.mapOfSounds.put(name, sound);

		return false;
	}

	@Override
	public void stopSoundByStid(String stid) {
		mapOfSounds.get(stid).stop();
	}

	public void clearValueRegistry() {
		host.clearValueRegistry();
	}

	public void clearSaidSpeech() {
		dialogTreePresenter.resetRecordOfSaidSpeech();
	}

	public IAuxGameScene queueMixinAndReturnScene(IMixin loader, IOnQueueResourcesImpl api) {
		IAuxGameScene blah2 = loader.onFillLoadList(api);
		return blah2;
	}

}
