package com.github.a2g.core.objectmodel;

import com.github.a2g.core.action.SayAction;
import com.github.a2g.core.interfaces.ConstantsForAPI.Special;
import com.github.a2g.core.interfaces.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.ILoad;
import com.github.a2g.core.interfaces.IOnFillLoadList;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointF;
import com.google.gwt.event.shared.EventBus;

public class MasterProxyForActions implements IOnFillLoadList,
		IInventoryPresenterFromActions, IScenePresenterFromActions,
		IDialogTreePresenterFromActions, ITitleCardPresenterFromActions {
	private MasterPresenter master;

	MasterProxyForActions(MasterPresenter master) {
		this.master = master;
	}

	@Override
	public void setVisibleByItid(String itid, boolean isVisible) {
		master.getInventoryPresenter().getInventory().items().getByItid(itid)
				.setVisible(isVisible);

	}

	@Override
	public String getItidByCode(int icode) {
		return master.getInventoryPresenter().getInventoryItem(icode).getItid();
	}

	@Override
	public IGameScene getSceneByName(String string) {
		return master.getSceneByName(string);
	}

	@Override
	public void clearAllLoadedLoads() {
		master.getLoaderPresenter().clearAllLoadedLoads();
	}

	@Override
	public void setContinueAfterLoad(boolean isContinueImmediately) {
		master.getLoaderPresenter().setContinueAfterLoad(isContinueImmediately);

	}

	@Override
	public void setValue(Object name, int value) {
		master.setValue(name, value);
	}

	@Override
	public void alignBaseMiddleOfOldFrameToFrameOfThisAnimationByAtid(
			String atid, int frame) {
		master.getScenePresenter()
				.alignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimationByAtid(
						frame, atid);
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
	public void setVisibleByOtid(String otid, boolean isVisible) {
		master.getScenePresenter().getObjectByOtid(otid).setVisible(isVisible);
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
	public boolean isInANoGoZone(PointF point) {
		return master.isInANoGoZone(point);
	}

	@Override
	public void fireOnMovementBeyondAGateIfRelevant(PointF point) {
		master.fireOnMovementBeyondAGateIfRelevant(point);

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
	public void setAsACurrentAnimationByAtid(String atid) {
		master.getScenePresenter().getAnimationByAtid(atid)
				.setAsCurrentAnimation();

	}

	@Override
	public void setCurrentFrameByOtid(String otid, int frame) {
		master.getScenePresenter().getObjectByOtid(otid).setCurrentFrame(frame);

	}

	@Override
	public void setToInitialAnimationWithoutChangingFrameByOtid(String otid) {
		master.getScenePresenter().getObjectByOtid(otid)
				.setToInitialAnimationWithoutChangingFrame();

	}

	@Override
	public String getOtidByCode(short ocode) {
		String otid = master.getScenePresenter().getOtidByCode(ocode);
		return master.getScenePresenter().getObjectByOtid(otid).getOtid();
	}

	@Override
	public String getOtidOfDefaultWalkObject() {
		return master.getScenePresenter().getSceneWalkerOtid();
	}

	@Override
	public String getOtidOfAtid(String atid) {
		return master.getScenePresenter().getAnimationByAtid(atid).getObject()
				.getOtid();
	}

	@Override
	public double getDurationByAtid(String animId) {
		return master.getScenePresenter().getAnimationByAtid(animId)
				.getDurationSecs();
	}

	@Override
	public ColorEnum getTalkingColorByOtid(String otid) {
		return master.getScenePresenter().getObjectByOtid(otid)
				.getTalkingColor();
	}

	@Override
	public String getAtidOfDefaultSayAnim() {
		return master.getScenePresenter().getSceneTalkerAtid();
	}

	@Override
	public void setStateOfPopup(boolean isVisible, double x, double y,
			ColorEnum talkingColor, String speech, SayAction sayAction) {
		master.getScenePresenter().setStateOfPopup(isVisible, x, y,
				talkingColor, speech, sayAction);
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
	public void setAsAnInitialAnimationByAtid(String atid) {
		master.getScenePresenter().getAnimationByAtid(atid)
				.setAsInitialAnimation();
		;
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
	public void setValue(String name, int i) {
		master.setValue(name, i);
	}

	@Override
	public void shareWinning(String token) {
		master.shareWinning(token);
	}

	@Override
	public boolean getVisibleByOtid(String otidB) {
		return master.getScenePresenter().getVisibleByOtid(otidB);
	}

	@Override
	public void switchToScene(String switchToThis) {
		master.switchToScene(switchToThis);
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
	public String getSpecialAnimationByOtid(String otid, Special type) {
		return master.getScenePresenter().getObjectByOtid(otid)
				.getSpecialAnimation(type);
	}

	@Override
	public void addBranch(int subBranchId, String lineOfDialog,
			boolean isAlwaysShown) {
		master.getDialogTreePresenter().addBranch(subBranchId, lineOfDialog,
				isAlwaysShown);

	}

	@Override
	public void executeBranchOnCurrentScene(int branchId) {
		master.executeBranchOnCurrentScene(branchId);
	}

	@Override
	public void setDialogTreeActive(boolean isInDialogTreeMode) {
		master.setDialogTreeActive(isInDialogTreeMode);
	}

	@Override
	public void displayTitleCard(String text) {
		master.displayTitleCard(text);
	}

	@Override
	public double getPopupDisplayDuration() {
		return master.getTitleCardPresenter().getPopupDisplayDuration();
	}

	@Override
	public void setDialogTreeVisible(boolean isInDialogTreeMode) {
		master.getDialogTreePresenter()
				.setDialogTreeVisible(isInDialogTreeMode);
	}

	@Override
	public void updateDialogTree(DialogTree theDialogTree, EventBus bus) {
		master.getDialogTreePresenter().updateDialogTree(theDialogTree, bus);

	}

	@Override
	public void setDialogTreeTalkAnimation(String atid) {
		master.getDialogTreePresenter().setDialogTreeTalkAnimation(atid);

	}

	@Override
	public String getDialogTreeTalkAnimation() {
		return master.getDialogTreePresenter().getDialogTreeTalkAnimation();
	}

	@Override
	public void addEssential(ILoad blah) {
		master.addEssential(blah);

	}

	@Override
	public void setScenePixelSize(int width, int height) {
		master.setScenePixelSize(width, height);
	}

	@Override
	public void kickStartLoading() {
		master.kickStartLoading();

	}

}
