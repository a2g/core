package com.github.a2g.core.objectmodel;

import java.util.List;

import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.ConstantsForAPI.WalkDirection;
import com.github.a2g.core.interfaces.internal.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.internal.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.internal.ITitleCardPresenterFromActions;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.RectAndLeaderLine;
import com.github.a2g.core.primitive.RectF;
import com.github.a2g.core.interfaces.internal.IMasterPanelFromMasterPresenter.GuiStateEnum;
import com.google.gwt.touch.client.Point;

/** 
 * @brief
 * These are all the action methods. They generally take text ids for everything,
 * so that the actions are easier to debug:
 * @ref com.github.a2g.core.interfaces.internal.IMasterPresenterFromActions "IMasterPresenterFromActions", 
 * @ref com.github.a2g.core.interfaces.internal.IInventoryPresenterFromActions "IInventoryPresenterFromActions",
 * @ref com.github.a2g.core.interfaces.internal.IScenePresenterFromActions "IScenePresenterFromActions", 
 * @ref com.github.a2g.core.interfaces.internal.IDialogTreePresenterFromActions "IDialogTreePresenterFromActions",
 * @ref com.github.a2g.core.interfaces.internal.ITitleCardPresenterFromActions "ITitleCardPresenterFromActions"
 * 
 * The API wants to prevent specific calls from being called inside the event handlers. As a result each handler has its own interface which only exposes a subset of the methods below. Use this class as the reference, and discover the methods which are exposed by each event handler by clicking on the links below:
 * 
 */
public class AllActionMethods implements 
IMasterPresenterFromActions, 
IInventoryPresenterFromActions,
IScenePresenterFromActions, 
IDialogTreePresenterFromActions,
ITitleCardPresenterFromActions 
{
	private MasterPresenter master;

	public AllActionMethods(MasterPresenter master) {
		this.master = master;
	}


	/**
	 * @name Inventory Methods 
	 * These methods take an @ref ocode as the
	 * first parameter. Behind the scenes they act on a @ref SceneObject.
	 * Don't worry about getting any of the constants mixed up: these scene
	 * object methods all takes parameters of type @b short so they 
	 * can't be used with the ids for Animations (which are of type @b String) 
	 * and InventoryItems (which are of type @b int).
	 */
	// /@{
	@Override
	public void setVisibleByItid(String itid, boolean isVisible) {
		master.getInventoryPresenter().getInventory().items().getByItid(itid)
		.setVisible(isVisible);

	}

	@Override
	public String getItidByCode(int icode) {
		return master.getInventoryPresenter().getInventoryItemByICode(icode)
				.getItid();
	}
	
	// /@}
	/**
	 * @name Master Methods 
	 */
	// /@{



	@Override
	public void setScenePixelSize(int width, int height) {
		master.setScenePixelSize(width, height);
	}

	
	@Override
	public void displayTitleCard(String text) {
		master.displayTitleCard(text);
	}

	@Override
	public boolean isSayNonIncrementing() {
		return master.isSayNonIncrementing();
	}

	@Override
	public void playSoundByStid(String stid) {
		master.playSoundByStid(stid);
	}

	@Override
	public double getSoundDurationByStid(String stid) {
		return master.getSoundDurationByStid(stid);
	}

	@Override
	public void stopSoundByStid(String stid) {
		master.stopSoundByStid(stid);
	}

	@Override
	public void setActiveGuiState(GuiStateEnum state) {
		master.setActiveGuiState(state);

	}


	
	
	@Override
	public void setValue(Object name, int value) {
		master.setValue(name, value);
	}

	//

	@Override
	public void shareWinning(String token) {
		master.shareWinning(token);
	}
	
	@Override
	public void switchToScene(String switchToThis, int arrivalSegment) {
		master.switchToScene(switchToThis, arrivalSegment);
	}
	
	// /@}
	/**
	 * @name Scene Methods 
	 */
	// /@{
	@Override
	public void alignBaseMiddleOfOldFrameToFrameOfThisAnimationByAtid(
			String atid, int frame) {
		master.getScenePresenter()
		.alignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimationByAtid(
				frame, atid);
	}


	@Override
	public void setSceneTalkerByAtid(String atid) {
		master.getScenePresenter().setSceneTalkerAtid(atid);
	}

	@Override
	public void setCurrentAnimationAndFrame(String atid, int frame) {
		String otid = getOtidByAtid(atid);
		SceneObject object = master.getScenePresenter().getObjectByOtid(otid);
		object.setCurrentAnimationAndFrame(atid, frame);
	}

	
	@Override
	public void setAnimationAsObjectSpecial(String atid, WalkDirection type) {
		String otid = getOtidByAtid(atid);
		SceneObject object = master.getScenePresenter().getObjectByOtid(otid);
		object.setSpecialAnimation(type, atid);
	}

	@Override
	public void setAnimationAsObjectInitial(String atid) {
		String otid = getOtidByAtid(atid);
		SceneObject object = master.getScenePresenter().getObjectByOtid(otid);
		object.setInitialAnimation(atid);
	}

	@Override
	public void setInitialAnimationByAtid(String atid) {
		String otid = getOtidByAtid(atid);
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		o.setInitialAnimation(atid);
		;
	}

	@Override
	public void setScaleOnCurrentFrameByOtid(String otid, double scale) {
		SceneObject object = master.getScenePresenter().getObjectByOtid(otid);
		object.setScale(scale);
		
	}

	@Override
	public List<Point> findPath(Point rawStart, Point rawEnd) {

		return master.getScenePresenter().findPath(rawStart, rawEnd);
	}
	
	@Override
	public int getSceneObjectCount() {
		return master.getScenePresenter().getSceneObjectCount();
	}

	@Override
	public String getOtidByIndex(int i) {
		return master.getScenePresenter().getOtidByIndex(i);
	}

	@Override
	public void setSpeechRect(String otid, int index) {
		SceneObject object = master.getScenePresenter().getObjectByOtid(otid);
		object.setSpeechRect(index);
	}
	
	@Override
	public void setVisibleByOtid(String otid, boolean isVisible) {

		SceneObject object = master.getScenePresenter().getObjectByOtid(otid);
		object.setVisible(isVisible);
	}

	@Override
	public double getScreenCoordsPerSecondByOtid(String otid) {
		return master.getScenePresenter().getObjectByOtid(otid)
				.getScreenCoordsPerSecond();
	}

	@Override
	public int getNumberOfFramesByAtid(String atid) {
		return master.getScenePresenter().getAnimationByAtid(atid).getLength();
	}

	@Override
	public String getAtidOfCurrentAnimationByOtid(String otid) {
		return master.getScenePresenter().getAtidOfCurrentAnimationByOtid(otid);
	}

	@Override
	public double getBaseMiddleXByOtid(String otid) {

		return master.getScenePresenter().getObjectByOtid(otid)
				.getBaseMiddleX();
	}

	@Override
	public double getBaseMiddleYByOtid(String otid) {
		return master.getScenePresenter().getObjectByOtid(otid)
				.getBaseMiddleY();
	}

	@Override
	public boolean isInANoGoZone(Point point) {
		return master.getScenePresenter().isInANoGoZone(point);
	}

	@Override
	public boolean doSwitchIfBeyondGate(Point point) {
		return master.getScenePresenter().doSwitchIfBeyondGate(point);
	}

	@Override
	public void setBaseMiddleXByOtid(String otid, double x) {

		master.getScenePresenter().getObjectByOtid(otid).setBaseMiddleX(x);
	}

	@Override
	public void setBaseMiddleYByOtid(String otid, double y) {
		master.getScenePresenter().getObjectByOtid(otid).setBaseMiddleY(y);
	}

	@Override
	public void setCurrentAnimationByAtid(String atid) {
		String otid = getOtidByAtid(atid);
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		o.setCurrentAnimation(atid);
	}

	@Override
	public void setCurrentFrameByOtid(String otid, int frame) {
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		if (o != null) {
			o.setCurrentFrame(frame);
		}
	}

	@Override
	public void setToInitialAnimationWithoutChangingFrameByOtid(String otid) {
		if (master.getScenePresenter().getObjectByOtid(otid) != null)
			master.getScenePresenter().getObjectByOtid(otid)
			.setToInitialAnimationWithoutChangingFrame();

	}

	@Override
	public String getOtidByCode(short ocode) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		return otid;
	}

	@Override
	public String getOtidOfDefaultSceneObject() {
		return master.getScenePresenter().getDefaultSceneObjectOtid();
	}

	@Override
	public String getOtidByAtid(String atid) {
		return master.getScenePresenter().getOtidByAtid(atid);
	}

	@Override
	public double getDurationByAtid(String animId) {
		return master.getScenePresenter().getAnimationByAtid(animId)
				.getDurationSecs();
	}

	@Override
	public String getAtidOfSceneDialogUs() {
		return master.getScenePresenter().getSceneDialoggerAtid();
	}
	@Override
	public String getAtidOfSceneDialogThem() {
		return master.getScenePresenter().getSceneDialoggeeAtid();
	}
	@Override
	public String getAtidOfSceneTalker() {
		return master.getScenePresenter().getSceneTalkerAtid();
	}

	@Override
	public void setStateOfPopup(boolean isVisible, String speech, String atid,
			RectAndLeaderLine rectAndLeaderLine, TalkPerformer sayAction) {
		master.getScenePresenter().setStateOfPopup(atid, isVisible, speech, rectAndLeaderLine, sayAction);
	}

	@Override
	public double getCameraX() {
		return master.getScenePresenter().getCameraY();

	}

	@Override
	public double getCameraY() {
		return master.getScenePresenter().getCameraX();
	}

	@Override
	public void setCameraX(double x) {
		master.getScenePresenter().setCameraX(x);

	}

	@Override
	public void setCameraY(double y) {
		master.getScenePresenter().setCameraY(y);

	}

	@Override
	public void setDisplayNameByOtid(String otid, String displayName) {
		master.getScenePresenter().getObjectByOtid(otid)
		.setDisplayName(displayName);

	}


	@Override
	public void setXByOtid(String otid, int i) {
		master.getScenePresenter().getObjectByOtid(otid).setX(i);
	}

	@Override
	public void setYByOtid(String otid, int i) {
		master.getScenePresenter().getObjectByOtid(otid).setY(i);
	}

	

	@Override
	public boolean getVisibleByOtid(String otid) {
		return master.getScenePresenter().getVisibleByOtid(otid);
	}

	@Override
	public int getCurrentFrameByOtid(String otid) {
		return master.getScenePresenter().getCurrentFrameByOtid(otid);
	}

	@Override
	public int getSceneGuiWidth() {
		return master.getScenePresenter().getSceneGuiWidth();
	}

	@Override
	public int getSceneGuiHeight() {
		return master.getScenePresenter().getSceneGuiHeight();
	}

	@Override
	public String getSpecialAnimationByOtid(String otid, WalkDirection type) {
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		return o.getSpecialAnimation(type);
	}
	@Override
	public double measureTextWidth(String text) {
		return master.getScenePresenter().measureTextWidth(text);
	}


	@Override
	public PointI GetMouthLocationByOtid(String otid) {
		SceneObject o = master.getScenePresenter().getObjectByOtid(otid);
		return o.getMouthLocation();
	}


	@Override
	public RectF getSpeechRectUsingContingencies(String atid) {
		return master.getScenePresenter().getSpeechRectUsingContingencies(atid);
	}


	
	
	// /@}
	/**
	 * @name Dialog Tree methods 
	 */
	// /@{
	
	
	@Override
	public void addBranch(int subBranchId, String lineOfDialog,
			boolean isAlwaysShown) {
		master.getDialogTreePresenter().addBranch(subBranchId, lineOfDialog,
				isAlwaysShown);

	}

	@Override
	public void setDialogTreeVisible(boolean isInDialogTreeMode) {
		master.getDialogTreePresenter()
		.setDialogTreeVisible(isInDialogTreeMode);
	}

	@Override
	public void updateDialogTree(DialogTree theDialogTree) {
		master.getDialogTreePresenter().updateDialogTree(theDialogTree);

	}
	
	// /@}
		/**
		 * @name Title Card methods 
		 */
		// /@{
	
	@Override
	public double getPopupDisplayDuration() {
		return master.getTitleCardPresenter().getPopupDisplayDuration();
	}


		@Override
		public void setSoundtrack(String stringValue) {
			master.setSoundtrack(stringValue);
		}


		




	// /@}

}
