package com.github.a2g.core.objectmodel;

import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.action.ChainableAction;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.ConstantsForAPI.Special;
import com.github.a2g.core.interfaces.ILoad;
import com.github.a2g.core.interfaces.IOnDialogTree;
import com.github.a2g.core.interfaces.IOnDoCommand;
import com.github.a2g.core.interfaces.IOnEntry;
import com.github.a2g.core.interfaces.IOnEveryFrame;
import com.github.a2g.core.interfaces.IOnFillLoadList;
import com.github.a2g.core.interfaces.IOnMovementBeyondAGate;
import com.github.a2g.core.interfaces.IOnPreEntry;
import com.github.a2g.core.interfaces.IMasterPanelFromMasterPresenter.GuiStateEnum;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointF;
import com.github.a2g.core.primitive.RectF;

/** MasterProxyForGameScene */
public class MasterProxyForGameScene implements IOnFillLoadList, IOnEntry,
IOnPreEntry, IOnEveryFrame, IOnDoCommand, IOnDialogTree,
IOnMovementBeyondAGate {
	private MasterPresenter master;

	MasterProxyForGameScene(MasterPresenter master) {
		this.master = master;
	}

	/**
	 * @name SceneObject Methods All these methods have an @ref ocode as the
	 *       first parameter. Behind the scenes they act on a @ref SceneObject.
	 *       Don't worry about getting any of the constants mixed up these scene
	 *       object methods all takes shorts so they can't be used with the ids
	 *       for Animations (Strings) and InventoryItems (ints).
	 */
	// /@{
	@Override
	public void setBoundaryCrossObject(short ocode) {
		master.setBoundaryCrossObject(ocode);

	}

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
	public void setAnimationTalkingColor(String atid, ColorEnum red) {

		master.getScenePresenter().getAnimationByAtid(atid).setTalkingColor(red);

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
	public void setAsDefaultSceneObject(short ocode) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		master.getScenePresenter().setOtidOfDefaultSceneObject(otid);

	}

	// /@}

	/**
	 * @name Inventory Methods All of these methods take an @ref icode as the
	 *       first parameter. Behind the scenes they act on an @ref
	 *       InventoryItem. Inventory item icodes don't work as parameters to
	 *       the Object Methods, so you'll get a compiler warning. The inventory
	 *       item methods are similarly named, but all have "InventoryItem" in
	 *       their names.
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
		master.getInventoryPresenter().getInventoryItemByICode(icode)
		.setDisplayName(displayName);

	}

	// /@}

	/**
	 * @name Animation Methods All these methods have an @ref atid as the first
	 *       parameter. Behind the scenes they act on a @ref Animation, or a @ref
	 *       SceneObject that owns it.
	 */
	// /@{

	@Override
	public void setAnimationAsObjectSpecial(String atid, Special type) {
		Animation a = master.getScenePresenter().getAnimationByAtid(atid);
		a.setAsSpecialAnimation(type);

	}

	@Override
	public void setAnimationAsSceneTalker(String atid) {
		master.getScenePresenter().setSceneTalkerAtid(atid);

	}



	@Override
	public void setAnimationAsObjectCurrent(String atid) {
		master.getScenePresenter().getAnimationByAtid(atid)
		.setAsCurrentAnimation();
	}

	@Override
	public void alignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation(
			String atid, int frame) {
		master.getScenePresenter()
		.alignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimationByAtid(
				frame, atid);

	}

	@Override
	public void setAnimationDuration(String atid, double secs) {
		master.getScenePresenter().getAnimationByAtid(atid)
		.setDurationSecs(secs);

	}

	@Override
	public void setAnimationAsObjectCurrentAndSetFrame(String atid, int frame) {
		master.getScenePresenter().getAnimationByAtid(atid)
		.setAsCurrentAnimationAndSetFrame(frame);

	}

	@Override
	public void setAnimationAsDialogTalker(String atid) {
		master.getDialogTreePresenter().setDialogTreeTalkAnimation(atid);
	}

	@Override
	public boolean isAnimation(String atid) {
		return master.getScenePresenter().getAnimationByAtid(atid).getFrames()
				.getCount() > 0;
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

	// /@}

	/**
	 * @name Verb Methods These items act on the @ref VerbsPresenter object
	 *       Behind the scenes they act on a SceneObject. They all have "Verb"
	 *       in their names.
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
	public IGameScene getSceneByName(String string) {
		return master.getSceneByName(string);
	}

	@Override
	public void setValue(Object key, int value) {
		master.setValue(key, value);
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
	public int getValue(Object name) {
		return master.getValue(name);
	}

	@Override
	public boolean isTrue(Object name) {
		return master.isTrue(name);
	}

	@Override
	public IGameScene getCurrentScene() {
		return master.getCurrentScene();
	}

	@Override
	public void switchToScene(String scene) {
		master.switchToScene(scene);

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
		return master.createChainRootAction();
	}

	@Override
	public void addBoundaryGate(double tlx,double tly,double brx,double bry,Object sceneToSwitchTo) {
		master.addBoundaryGate(tlx,tly,brx,bry,sceneToSwitchTo);
	}

	@Override
	public void addBoundaryPoint(double  x, double y) {
		master.addBoundaryPoint(x,y);
	}

	@Override
	public void setActiveGuiState(GuiStateEnum state) {
		master.setActiveGuiState(state);

	}

	@Override
	public void executeChainedAction(ChainableAction ba) {
		master.executeChainedAction(ba);

	}
	// /@}

	@Override
	public void setAnimationMaxTalkRect(String atid, RectF rectF) {
		master.getScenePresenter().getAnimationByAtid(atid).setMaxSpeechBalloonRect(rectF);

	}

	@Override
	public void setStateOfPopup(String atid, boolean isVisible, String speech,
			TalkPerformer sayAction) {
		master.getScenePresenter().setStateOfPopup(atid, isVisible, speech, sayAction);
		
	}

	@Override
	public void displayTitleCard(String text) {
		master.displayTitleCard(text);
		
	}

	@Override
	public void setInventoryItemVisible(int icode, boolean isVisible) {
		master.getInventoryPresenter().getInventoryItemByICode(icode).setVisible(isVisible);

	}

	@Override
	public void addMP3ForASoundObject(String name, String location) {
		master.addMP3ForASoundObject(name, location);
		
	}
	public String getOtidOfAtid(String atid) {
		String toReturn = "";
		ScenePresenter pres = master.getScenePresenter();
		Animation a = pres.getAnimationByAtid(atid);
		if (a != null) {
			SceneObject o = a.getObject();
			if (o != null)
				toReturn = o.getOtid();
		}
		return toReturn;
	}
	
	@Override
	public void setAnimationAsObjectInitial(String atid) {
		String otid = getOtidOfAtid(atid);
		SceneObject object = master.getScenePresenter().getObjectByOtid(otid);
		object.setInitialAnimation(atid);

	}

}
