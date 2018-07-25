package com.github.a2g.core.objectmodel;

import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.action.ChainableAction;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.ConstantsForAPI.WalkDirection;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.interfaces.ILoadKickStarter;
import com.github.a2g.core.interfaces.internal.IMasterPanelFromMasterPresenter.GuiStateEnum;
import com.github.a2g.core.interfaces.methods.IOnDialogTree;
import com.github.a2g.core.interfaces.methods.IOnDoCommand;
import com.github.a2g.core.interfaces.methods.IOnEnqueueResources;
import com.github.a2g.core.interfaces.methods.IOnEntry;
import com.github.a2g.core.interfaces.methods.IOnEveryFrame;
import com.github.a2g.core.interfaces.methods.IOnPreEntry;
import com.github.a2g.core.interfaces.platform.IBundleLoader;
import com.github.a2g.core.interfaces.platform.IPlatformResourceBundle;
import com.github.a2g.core.interfaces.IExtendsGameSceneLoader;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.SpeechBubble;
import com.google.gwt.touch.client.Point;
import com.github.a2g.core.primitive.RectF;

/**
 * @brief
 * @ref balooga blah blah These are all the game methods, but these methods may
 *      or may not be available on the interface for a specific handler. Check
 *      each interface individually:
 * @ref com.github.a2g.core.interfaces.IOnFillLoadList "IOnFillLoadList" ,
 * @ref com::github::a2g::core::interfaces::IOnEntry "IOnEntry" ,
 * @ref com.github.a2g.core.interfaces.IOnPreEntry "IOnPreEntry" ,
 * @ref com.github.a2g.core.interfaces.IOnEveryFrame "IOnEveryFrame" ,
 * @ref com.github.a2g.core.interfaces.IOnDoCommand "IOnDoCommand" ,
 * @ref com.github.a2g.core.interfaces.IOnDialogTree "IOnDoCommand" ,
 * 
 *      The API wants to prevent specific calls from being called inside the
 *      event handlers. As a result each handler has its own interface which
 *      only exposes a subset of the methods below. Use this class as the
 *      reference, and discover the methods which are exposed by each event
 *      handler by clicking on the links below:
 * 
 */
public class AllGameMethods
		implements IOnEnqueueResources, IOnEntry, IOnPreEntry, IOnEveryFrame, IOnDoCommand, IOnDialogTree {
	private MasterPresenter master;

	AllGameMethods(MasterPresenter master) {
		this.master = master;
	}

	/**
	 * @name SceneObject Methods
	 * @category stuff
	 * 
	 *           These methods take an @ref ocode as the first parameter. Behind
	 *           the scenes they act on a @ref SceneObject. Don't worry about
	 *           getting any of the constants mixed up: these scene object
	 *           methods all takes parameters of type @b short so they can't be
	 *           used with the ids for Animations (which are of type @b String)
	 *           and InventoryItems (which are of type @b int).
	 */

	@Override
	public void hide(short ocode) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setVisible(false);

	}

	@Override
	public void show(short ocode) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setVisible(true);

	}

	@Override
	public boolean isVisible(short ocode) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		return master.getScenePresenter().getObjectByOtid(otid).isVisible();
	}

	@Override
	public void setX(short ocode, double x) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setX(x);

	}

	@Override
	public void setY(short ocode, double y) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setY(y);

	}

	@Override
	public ChainableAction setDisplayName(short ocode, String displayName) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setDisplayName(displayName);
		return null;
	}

	@Override
	public void setScreenCoordsPerSecond(short ocode, double coordsPerSecond) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setScreenCoordsPerSecond(coordsPerSecond);

	}

	@Override
	public void setParallaxX(short ocode, double x) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setParallaxX(x);
	}

	@Override
	public ChainableAction setVisible(short ocode, boolean visible) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setVisible(visible);
		return null;
	}

	@Override
	public double getBaseMiddleX(short ocode) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		return master.getScenePresenter().getObjectByOtid(otid).getBaseMiddleX();
	}

	@Override
	public double getBaseMiddleY(short ocode) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		return master.getScenePresenter().getObjectByOtid(otid).getBaseMiddleY();
	}

	@Override
	public ChainableAction setBaseMiddleX(short ocode, double baseMiddleX) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setBaseMiddleX(baseMiddleX);
		return null;
	}

	@Override
	public ChainableAction setBaseMiddleY(short ocode, double baseMiddleY) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setBaseMiddleY(baseMiddleY);
		return null;
	}

	@Override
	public int getX(short ocode) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		return (int) master.getScenePresenter().getObjectByOtid(otid).getX();
	}

	@Override
	public int getY(short ocode) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		return (int) master.getScenePresenter().getObjectByOtid(otid).getY();
	}

	@Override
	public void incrementFrameWithWraparound(short ocode) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).incrementFrameWithWraparound();
	}

	@Override
	public void updateObjectToCorrectImage(short ocode) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).updateObjectToCorrectImage();
	}

	@Override
	public int getCurrentFrame(short ocode) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		return master.getScenePresenter().getObjectByOtid(otid).getCurrentFrame();
	}

	@Override
	public ChainableAction setCurrentFrame(short ocode, int i) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setCurrentFrame(i);
		return null;
	}

	@Override
	public String getCurrentAnimation(short ocode) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		return master.getScenePresenter().getAtidOfCurrentAnimationByOtid(otid);
	}

	@Override
	public void setDefaultSceneObject(short ocode) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().setOtidOfDefaultSceneObject(otid);

	}

	@Override
	public short getDefaultSceneObject() {
		String otid = master.getScenePresenter().getOtidOfDefaultSceneObject();
		if (otid.length() > 0 && otid.length() < 70) {
			short ocode = master.getScenePresenter().getCodeByOtid(otid);
			return ocode;
		}
		return 0;
	}

	// /@}

	/**
	 * @name InventoryItem Methods
	 * 
	 *       All of these have "InventoryItem" as the second word, so when
	 *       thinking about setting a property of an animation, you need only
	 *       worry about methods that have InventoryItem as the second word and
	 *       no others.
	 *
	 *       These methods take an @ref icode as the first parameter. Behind the
	 *       scenes they act on an @ref InventoryItem. Inventory item icodes
	 *       don't work as parameters to the Object Methods, so you'll get a
	 *       compiler warning. The inventory item methods are similarly named,
	 *       but all have "InventoryItem" in their names.
	 */
	// /@{
	@Override
	public boolean isInventoryItemVisible(int icode) {
		return master.getInventoryPresenter().getInventoryItemByICode(icode).isVisible();
	}

	@Override
	public void showInventoryItem(int icode) {
		master.getInventoryPresenter().getInventoryItemByICode(icode).setVisible(true);

	}

	@Override
	public void hideInventoryItem(int icode) {
		master.getInventoryPresenter().getInventoryItemByICode(icode).setVisible(false);

	}

	@Override
	public void setInventoryItemDisplayName(int icode, String displayName) {
		InventoryItem i = master.getInventoryPresenter().getInventoryItemByICode(icode);
		i.setDisplayName(displayName);

	}

	@Override
	public ChainableAction setInventoryItemVisible(int icode, boolean isVisible) {
		master.getInventoryPresenter().getInventoryItemByICode(icode).setVisible(isVisible);
		return null;
	}

	@Override
	public void hideInventoryItemsAllOfThem() {
		master.getInventoryPresenter().hideAllInventory();
	}

	// /@}

	/**
	 * @name Animation Methods All of these have "Animation" as the second word,
	 *       so when thinking about setting a property of an animation, you need
	 *       only worry about methods that have Animation as the second word -
	 *       no more.
	 * 
	 *       These methods have an @ref atid as the first parameter. Behind the
	 *       scenes they act on a @ref Animation, or a @ref SceneObject that
	 *       owns it.
	 * 
	 *       Scene categorizations: - The SceneTalker is used only in @ref Scene
	 *       interactions. - The SceneAsker is used only in @ref DialogTree
	 *       interactions. - The SceneAnswerer is used only in @ref DialogTree
	 *       interactions.
	 * 
	 *       Object categorizations: - ObjectInitial - the animation most often
	 *       defaulted to. - ObjectSpecial - the method used to set N,E,S,W. -
	 *       ObjectCurrent - the currently active animation.
	 */
	// /@{

	@Override
	public ChainableAction setAnimationAsSceneTalker(String atid) {
		master.getScenePresenter().setSceneTalkerAtid(atid);
		return null;
	}

	@Override
	public void setAnimationAsSceneDialogUs(String atid) {
		master.getScenePresenter().setSceneAskerAtid(atid);
	}

	@Override
	public void setAnimationAsSceneDialogThem(String atid) {
		master.getScenePresenter().setSceneAnswererAtid(atid);
	}

	@Override
	public ChainableAction setAnimationAsObjectInitial(String atid) {
		String otid = getOtidByAtid(atid);
		SceneObject object = master.getScenePresenter().getObjectByOtid(otid);
		object.setInitialAnimation(atid);
		return null;
	}

	@Override
	public ChainableAction setAnimationAsObjectWalkDirection(String atid, WalkDirection type) {
		String otid = getOtidByAtid(atid);
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		o.setSpecialAnimation(type, atid);
		return null;
	}

	@Override
	public ChainableAction setAnimationAsObjectCurrent(String atid) {
		String otid = getOtidByAtid(atid);
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		o.setCurrentAnimation(atid);
		return null;
	}

	@Override
	public void setAnimationDuration(String atid, double secs) {
		master.getScenePresenter().getAnimationByAtid(atid).setDurationSecs(secs);

	}

	@Override
	public ChainableAction setAnimationAsObjectCurrentAndSetFrame(String atid, int frame) {
		String otid = getOtidByAtid(atid);
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		o.setCurrentAnimationAndFrame(atid, frame);
		return null;
	}

	@Override
	public int getAnimationLastFrame(String atid) {
		return master.getScenePresenter().getAnimationByAtid(atid).getFrames().getCount();
	}

	@Override
	public int getAnimationLength(String atid) {
		return master.getScenePresenter().getAnimationByAtid(atid).getFrames().getCount();

	}

	@Override
	public void setAnimationTalkingColor(String atid, ColorEnum red) {

		master.getScenePresenter().getAnimationByAtid(atid).setTalkingColor(red);

	}

	@Override
	public boolean isAnimation(String atid) {
		return master.getScenePresenter().getAnimationByAtid(atid).getFrames().getCount() > 0;
	}

	public String getOtidByAtid(String atid) {
		return master.getScenePresenter().getOtidByAtid(atid);
	}

	// /@}

	/**
	 * @name Verb Methods
	 * @anchor balooga These items act on the @ref VerbsPresenter object Behind
	 *         the scenes they act on a SceneObject. They all have "Verb" in
	 *         their names.
	 */
	// /@{
	@Override
	public void removeVerbByCode(int vcode) {
		master.getVerbsPresenter().removeByCode(vcode);

	}

	@Override
	public void updateVerbUI() {
		master.getVerbsPresenter().updateVerbs();

	}

	// /@}

	/**
	 * @name General Methods These are a real mixed batch
	 */
	// /@{

	@Override
	public void queueEntireBundleLoader(IBundleLoader bundleLoader) {
		master.queueEntireBundleLoader(bundleLoader);
	}

	@Override
	public void queueSingleBundle(IPlatformResourceBundle loader) {
		master.queueSingleBundle(loader);

	}

	@Override
	public void clearAllLoadedLoads() {
		master.clearAllLoadedLoads();
	}

	@Override
	public ChainableAction setValue(Object key, int value) {
		master.setValue(key, value);
		return null;
	}

	@Override
	public int getValue(Object name) {
		return master.getValue(name);
	}

	@Override
	public boolean isTrue(Object name) {
		return master.isTrue(name);
	}

	@Override
	public String getCurrentSceneName() {
		return master.getCurrentSceneName();
	}

	@Override
	public void switchToScene(String scene, int arrivalSegment) {
		master.switchToScene(scene, arrivalSegment);

	}

	@Override
	public String getLastScene() {
		return master.getLastScene();
	}

	@Override
	public boolean isInDebugMode() {
		return master.isInDebugMode();
	}

	@Override
	public double getSceneGuiWidth() {
		return master.getScenePresenter().getSceneGuiWidth();
	}

	@Override
	public int getSceneGuiHeight() {
		return master.getScenePresenter().getSceneGuiHeight();
	}

	@Override
	public void setIsSayAlwaysWithoutIncremementing(boolean isSayWithoutIncremementing) {
		master.setIsSayAlwaysWithoutIncremementing(isSayWithoutIncremementing);

	}

	@Override
	public boolean getIsSayAlwaysWithoutIncremementing() {
		return master.getIsSayAlwaysWithoutIncremementing();
	}

	@Override
	public ChainableAction shareWinning(String token) {
		master.shareWinning(token);
		return null;
	}

	@Override
	public ChainRootAction createChainRootAction() {
		return MatOps.createChainRootAction();
	}

	@Override
	public void executeChainedAction(ChainableAction ba) {
		master.executeChainedAction(ba);

	}

	@Override
	public void setActiveGuiState(GuiStateEnum state) {
		master.setActiveGuiState(state);

	}

	@Override
	public void displayTitleCard(String text) {
		master.getScenePresenter().setTitleCard(text);

	}

	@Override
	public void queueMP3ForASoundObject(String name, String location) {
		master.queueMP3ForASoundObject(name, location);

	}

	
	@Override
	public void setStateOfPopup(boolean isVisible, SpeechBubble rectAndLeaderLine,
			TalkPerformer sayAction) {
		master.getScenePresenter().setStateOfPopup( isVisible, rectAndLeaderLine, sayAction);

	}

	@Override
	public void setTitleCard(String titlecard) {
		master.getScenePresenter().setTitleCard(titlecard);
	}

	@Override
	public void addObstacleRect(double x, double y, double right, double bottom) {
		master.getScenePresenter().addObstacleRect(x, y, right, bottom);

	}

	@Override
	public void addBoundaryGate(double tlx, double tly, double brx, double bry, Object sceneToSwitchTo,
			int arrivalSegment) {
		master.getScenePresenter().addBoundaryGate(tlx, tly, brx, bry, sceneToSwitchTo, arrivalSegment);
	}

	@Override
	public void addBoundaryPoint(double x, double y) {
		master.getScenePresenter().addBoundaryPoint(x, y);
	}
	// /@}

	@Override
	public void clearValuesAndSaidSpeech() {
		master.clearValueRegistry();

	}

	@Override
	public void clearSaidSpeech() {
		master.clearSaidSpeech();

	}

	@Override
	public ChainableAction setSoundtrack(String stid) {
		master.setSoundtrack(stid);
		return null;
	}

	@Override
	public int addRectangle(RectF rectF) {
		return master.getScenePresenter().addRectangle(rectF);
	}

	@Override
	public short getOCodeByAtid(String atid) {
		return master.getScenePresenter().getOCodeByAtid(atid);
	}

	@Override
	public void setHeadRectangleForAnimation(String atid, int index) {
		Animation a = master.getScenePresenter().getAnimationByAtid(atid);
		a.setHeadRectangleIndex(index);
	}

	@Override
	public ChainableAction setHeadRectangleForObject(short ocode, int index) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		o.setHeadRectangleByIndex(index);
		return null;
	}

	@Override
	public void setTalkingColor(short ocode, ColorEnum color) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		o.setTalkingColor(color);

	}

	@Override
	public void setScale(short ocode, double scale) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		o.setScale(scale);

	}

	@Override
	public int addHelperPoint(double x, double y) {
		return master.getScenePresenter().addHelperPoint(new Point(x, y));
	}

	@Override
	public void addObstacleRect(double x, double y, int helperIndex) {
		Point p = master.getScenePresenter().getHelperPoints().get(helperIndex);
		master.getScenePresenter().addObstacleRect(x, y, p.getX(), p.getY());

	}

	@Override
	public void addObstacleRect(int helperIndex, double x, double y) {
		Point p = master.getScenePresenter().getHelperPoints().get(helperIndex);
		master.getScenePresenter().addObstacleRect(p.getX(), p.getY(), x, y);

	}

	@Override
	public void addObstacleRect(int helper1, int helper2) {
		Point p1 = master.getScenePresenter().getHelperPoints().get(helper1);
		Point p2 = master.getScenePresenter().getHelperPoints().get(helper2);
		master.getScenePresenter().addObstacleRect(p1.getX(), p1.getY(), p2.getX(), p2.getY());

	}

	@Override
	public Point getHelperPoint(int helperIndex) {
		return master.getScenePresenter().getHelperPoints().get(helperIndex);
	}

	@Override
	public void addBoundaryPoint(int helperIndex) {
		Point p1 = master.getScenePresenter().getHelperPoints().get(helperIndex);
		master.getScenePresenter().addBoundaryPoint(p1.getX(), p1.getY());

	}

	@Override
	public void setClumpWithPrevious(short ocode, boolean isClump) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setClumpWithPrevious(isClump);

	}

	@Override
	public int getViewportWidth() {
		return master.getScenePresenter().getSceneGuiWidth();
	}

	@Override
	public IGameScene queueSharedSceneAndReturnScene(IExtendsGameSceneLoader loader) {
		return master.queueSharedSceneAndReturnScene(loader);
	}

	@Override
	public ILoadKickStarter createReturnObject(IGameScene scene) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChainableAction alignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation(String atid, int frame) {
		master.getScenePresenter().alignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimationByAtid(frame, atid);
		return null;
	}
	 


}
