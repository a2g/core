package com.github.a2g.core.objectmodel;

import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.action.ChainableAction;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.ConstantsForAPI.WalkDirection;
import com.github.a2g.core.interfaces.IOnDialogTree;
import com.github.a2g.core.interfaces.IOnDoCommand;
import com.github.a2g.core.interfaces.IOnEntry;
import com.github.a2g.core.interfaces.IOnEveryFrame;
import com.github.a2g.core.interfaces.IOnFillLoadList;
import com.github.a2g.core.interfaces.IOnPreEntry;
import com.github.a2g.core.interfaces.internal.ILoad;
import com.github.a2g.core.interfaces.internal.IMasterPanelFromMasterPresenter.GuiStateEnum;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.RectF;

/** 
 * @brief
 * These are all the game methods, but these methods may or may not be available
 * on the interface for a specific handler. Check each interface individually:
 * @ref com.github.a2g.core.interfaces.IOnFillLoadList "IOnFillLoadList" ,
 * @ref com::github::a2g::core::interfaces::IOnEntry "IOnEntry" , 
 * @ref com.github.a2g.core.interfaces.IOnPreEntry "IOnPreEntry" , 
 * @ref com.github.a2g.core.interfaces.IOnEveryFrame "IOnEveryFrame" ,
 * @ref com.github.a2g.core.interfaces.IOnDoCommand "IOnDoCommand" ,
 * @ref com.github.a2g.core.interfaces.IOnDialogTree "IOnDoCommand" ,
 * 
 * The API wants to prevent specific calls from being called inside the event handlers. As a result each handler has its own interface which only exposes a subset of the methods below. Use this class as the reference, and discover the methods which are exposed by each event handler by clicking on the links below:
 * 
 */
public class AllGameMethods implements IOnFillLoadList, IOnEntry,
IOnPreEntry, IOnEveryFrame, IOnDoCommand, IOnDialogTree {
	private MasterPresenter master;

	AllGameMethods(MasterPresenter master) {
		this.master = master;
	}

	/**
	 * @name SceneObject Methods 
	 * These methods take an @ref ocode as the
	 * first parameter. Behind the scenes they act on a @ref SceneObject.
	 * Don't worry about getting any of the constants mixed up: these scene
	 * object methods all takes parameters of type @b short so they 
	 * can't be used with the ids for Animations (which are of type @b String) 
	 * and InventoryItems (which are of type @b int).
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
	public void setDisplayName(short ocode, String displayName) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid)
		.setDisplayName(displayName);

	}

	@Override
	public void setScreenCoordsPerSecond(short ocode, double coordsPerSecond) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid)
		.setScreenCoordsPerSecond(coordsPerSecond);

	}

	@Override
	public void setParallaxX(short ocode, double x) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setParallaxX(x);
	}

	@Override
	public void setVisible(short ocode, boolean visible) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setVisible(visible);
	}

	@Override
	public double getBaseMiddleX(short ocode) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		return master.getScenePresenter().getObjectByOtid(otid)
				.getBaseMiddleX();
	}

	@Override
	public double getBaseMiddleY(short ocode) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		return master.getScenePresenter().getObjectByOtid(otid)
				.getBaseMiddleY();
	}

	@Override
	public void setBaseMiddleX(short ocode, double baseMiddleX) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid)
		.setBaseMiddleX(baseMiddleX);
	}

	@Override
	public void setBaseMiddleY(short ocode, double baseMiddleY) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid)
		.setBaseMiddleY(baseMiddleY);
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
		master.getScenePresenter().getObjectByOtid(otid)
		.incrementFrameWithWraparound();
	}

	@Override
	public void updateToCorrectImage(short ocode) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).updateToCorrectImage();
	}

	@Override
	public int getCurrentFrame(short ocode) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		return master.getScenePresenter().getObjectByOtid(otid)
				.getCurrentFrame();
	}

	@Override
	public void setCurrentFrame(short ocode, int i) {

		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().getObjectByOtid(otid).setCurrentFrame(i);

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
		if(otid.length()>0 && otid.length()<70)
		{
			short ocode = master.getScenePresenter().getCodeByOtid(otid);
			return ocode;
		}
		return 0;
	}

	// /@}

	/**
	 * @name InventoryItem Methods 
	 * 
	 * All of these have "InventoryItem" as the second word, so when thinking
	 * about setting a property of an animation, you need only worry about
	 * methods that have InventoryItem as the second word and no others.
	 *
	 * These methods take an @ref icode as the
	 * first parameter. Behind the scenes they act on an @ref
	 * InventoryItem. Inventory item icodes don't work as parameters to
	 * the Object Methods, so you'll get a compiler warning. The inventory
	 * item methods are similarly named, but all have "InventoryItem" in
	 * their names.
	 */
	// /@{
	@Override
	public boolean isInventoryItemVisible(int icode) {
		return master.getInventoryPresenter().getInventoryItemByICode(icode)
				.isVisible();
	}

	@Override
	public void showInventoryItem(int icode) {
		master.getInventoryPresenter().getInventoryItemByICode(icode).setVisible(true);

	}

	@Override
	public void hideInventoryItem(int icode) {
		master.getInventoryPresenter().getInventoryItemByICode(icode)
		.setVisible(false);

	}

	@Override
	public void setInventoryItemDisplayName(int icode, String displayName) {
		InventoryItem i = master.getInventoryPresenter().getInventoryItemByICode(icode);
		i.setDisplayName(displayName);

	}

	@Override
	public void setInventoryItemVisible(int icode, boolean isVisible) {
		master.getInventoryPresenter().getInventoryItemByICode(icode).setVisible(isVisible);
	}

	@Override
	public void hideInventoryItemsAllOfThem() {
		master.getInventoryPresenter().hideAllInventory();
	}

	// /@}

	/**
	 * @name Animation Methods 
	 * All of these have "Animation" as the second word, so when thinking
	 * about setting a property of an animation, you need only worry about
	 * methods that have Animation as the second word - no more.
	 * 
	 * These methods have an @ref atid as the first
	 * parameter. Behind the scenes they act on a @ref Animation, or a @ref
	 * SceneObject that owns it.
	 * 
	 * Scene categorizations:
	 * - The SceneTalker is used only in @ref Scene interactions.
	 * - The SceneAsker is used only in @ref DialogTree interactions.
	 * - The SceneAnswerer is used only in @ref DialogTree interactions.
	 * 
	 * Object categorizations:
	 * - ObjectInitial - the animation most often defaulted to.
	 * - ObjectSpecial - the method used to set N,E,S,W.
	 * - ObjectCurrent - the currently active animation.
	 */
	// /@{

	@Override
	public void setAnimationAsSceneTalker(String atid) {
		master.getScenePresenter().setSceneTalkerAtid(atid);
	}
	
	
	@Override
	public void setAnimationAsSceneAsker(String atid) {
		master.getScenePresenter().setSceneAskerAtid(atid);
	}

	
	@Override
	public void setAnimationAsSceneAnswerer(String atid) {
		master.getScenePresenter().setSceneAnswererAtid(atid);
	}

	@Override
	public void setAnimationAsObjectInitial(String atid) {
		String otid = getOtidByAtid(atid);
		SceneObject object = master.getScenePresenter().getObjectByOtid(otid);
		object.setInitialAnimation(atid);

	}

	@Override
	public void setAnimationAsObjectWalkDirection(String atid, WalkDirection type) {
		String otid = getOtidByAtid(atid);
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		o.setSpecialAnimation(type, atid);
	}

	@Override
	public void setAnimationAsObjectCurrent(String atid) {
		String otid = getOtidByAtid(atid);
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		o.setCurrentAnimation(atid);
	}
	
	@Override
	public void setAnimationMaxTalkRect(String atid, RectF rectF) {
		master.getScenePresenter().getAnimationByAtid(atid).setMaxSpeechBalloonRect(rectF);

	}

	@Override
	public void setAnimationDuration(String atid, double secs) {
		master.getScenePresenter().getAnimationByAtid(atid)
		.setDurationSecs(secs);

	}

	@Override
	public void setAnimationAsObjectCurrentAndSetFrame(String atid, int frame) {
		String otid = getOtidByAtid(atid);
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		o.setCurrentAnimationAndFrame(atid, frame);
	}

	@Override
	public int getAnimationLastFrame(String atid) {
		return master.getScenePresenter().getAnimationByAtid(atid).getFrames()
				.getCount();
	}

	@Override
	public int getAnimationLength(String atid) {
		return master.getScenePresenter().getAnimationByAtid(atid).getFrames()
				.getCount();

	}


	@Override
	public void setAnimationTalkingColor(String atid, ColorEnum red) {

		master.getScenePresenter().getAnimationByAtid(atid).setTalkingColor(red);

	}


	@Override
	public boolean isAnimation(String atid) {
		return master.getScenePresenter().getAnimationByAtid(atid).getFrames()
				.getCount() > 0;
	}
	
	public String getOtidByAtid(String atid) {
		return master.getScenePresenter().getOtidByAtid(atid);
	}
	
	// /@}

	/**
	 * @name Verb Methods 
	 * These items act on the @ref VerbsPresenter object
	 * Behind the scenes they act on a SceneObject. They all have "Verb"
	 * in their names.
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
	 * @name General Methods 
	 * These are a real mixed batch
	 */
	// /@{

	@Override
	public void addEssential(ILoad load) {
		master.addEssential(load);

	}

	@Override
	public void setScenePixelSize(int width, int height) {
		master.setScenePixelSize(width, height);

	}

	@Override
	public void kickStartLoading() {
		master.kickStartLoading();

	}

	@Override
	public void clearAllLoadedLoads() {
		master.clearAllLoadedLoads();
	}

	@Override
	public void setContinueAfterLoad(boolean isContinueImmediately) {
		master.setContinueAfterLoad(isContinueImmediately);
	}

	@Override
	public void setValue(Object key, int value) {
		master.setValue(key, value);
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
	public IGameScene getSceneByName(Object string) {
		return master.getSceneByName(string.toString());
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
	public void setIsSayAlwaysWithoutIncremementing(
			boolean isSayWithoutIncremementing) {
		master.setIsSayAlwaysWithoutIncremementing(isSayWithoutIncremementing);

	}

	@Override
	public boolean getIsSayAlwaysWithoutIncremementing() {
		return master.getIsSayAlwaysWithoutIncremementing();
	}

	@Override
	public void shareWinning(String token) {
		master.shareWinning(token);

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
		master.displayTitleCard(text);

	}


	@Override
	public void addMP3ForASoundObject(String name, String location) {
		master.addMP3ForASoundObject(name, location);

	}

	@Override
	public void alignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation(
			int frame, String atid) {
		master.getScenePresenter()
		.alignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimationByAtid(
				frame, atid);

	}
	
	@Override
	public void setStateOfPopup(boolean isVisible, String speech, String atid,
			TalkPerformer sayAction) {
		master.getScenePresenter().setStateOfPopup(atid, isVisible, speech, sayAction);

	}
	
	@Override
	public void setTitleCard(String titlecard) {
		master.getTitleCardPresenter().setText(titlecard);
	}
	
	@Override
	public void addObstacleRect(double x, double y, double right, double bottom) {
		master.getScenePresenter().addObstacleRect(x, y, right, bottom);

	}
	
	@Override
	public void addBoundaryGate(double tlx,double tly,double brx,double bry,Object sceneToSwitchTo, int arrivalSegment) {
		master.getScenePresenter().addBoundaryGate(tlx,tly,brx,bry,sceneToSwitchTo, arrivalSegment);
	}

	@Override
	public void addBoundaryPoint(double  x, double y) {
		master.getScenePresenter().addBoundaryPoint(x,y);
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






}
