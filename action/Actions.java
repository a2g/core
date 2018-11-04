package com.github.a2g.core.action;

import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.action.performer.SingleCallPerformer.Type;
import com.github.a2g.core.chain.DialogChain;
import com.github.a2g.core.chain.SceneChain;
import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.github.a2g.core.interfaces.game.chainables.ISceneChain;
import com.github.a2g.core.interfaces.game.chainables.ISceneChainEnd;
import com.github.a2g.core.interfaces.game.handlers.IOnDoCommand;
import com.github.a2g.core.interfaces.game.scene.IGameScene;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformMasterPanel.GuiStateEnum;
import com.github.a2g.core.interfaces.game.scene.ConstantsForAPI.WalkDirection;
import com.github.a2g.core.objectmodel.ScenePresenter;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.A2gException;
import com.google.gwt.touch.client.Point;

public class Actions {

    
    public static ISceneChainEnd doBoth(ISceneChain a, ISceneChain b) {
        return a.subroutine(b);
    }

    
    public static BaseAction doNothing() {
        DoNothingAction a = new DoNothingAction();
        return a;
    }

    
    public static BaseAction activateDialogTreeMode(int branchId) {
        return new DialogEnterAction(branchId);
    }

    // plain..
    
    public static BaseAction playAnimation(String atid) {
        PlayAnimationAction a = new PlayAnimationAction( atid);
        return a;
    }

    // simple backwards
    
    public static BaseAction playAnimationBackwards(String atid) {
        PlayAnimationAction a = new PlayAnimationAction( atid);

        a.setBackwards(true);
        return a;
    }

    // simple hold last frame
    
    public static BaseAction playAnimationHoldLastFrame(String atid) {
        PlayAnimationAction a = new PlayAnimationAction( atid);

        a.setHoldLastFrame(true);
        return a;
    }

    // simple non blocking
    
    public static BaseAction playAnimationNonBlocking(String atid) {
        PlayAnimationAction a = new PlayAnimationAction( atid);

        a.setParallel(true);
        return a;
    }

    // double combo1of3: backwards + hold last frame

    
    public static BaseAction playAnimationBackwardsHoldLastFrame(String atid) {
        PlayAnimationAction a = new PlayAnimationAction( atid);
        a.setBackwards(true);
        a.setHoldLastFrame(true);
        return a;
    }

    // double combo2of3: backwards + nonblocking
    
    public static BaseAction playAnimationBackwardsNonBlocking(String atid) {
        PlayAnimationAction a = new PlayAnimationAction( atid);

        a.setBackwards(true);
        a.setParallel(true);
        return a;
    }

    // double combo2of3: holdLastFrame + nonblocking
    
    public static BaseAction playAnimationHoldLastFrameNonBlocking(String atid) {
        PlayAnimationAction a = new PlayAnimationAction( atid);

        a.setHoldLastFrame(true);
        a.setParallel(true);
        return a;
    }

    // ..and one method with the whole lot!
    
    public static BaseAction playAnimationBackwardsHoldLastFrameNonBlocking(
            String atid) {
        PlayAnimationAction a = new PlayAnimationAction( atid);

        a.setBackwards(true);
        a.setHoldLastFrame(true);
        a.setParallel(true);
        return a;
    }

    
    public static BaseAction talk(String animCode, String speech) {
        TalkAction s = new TalkAction( animCode, speech);
        return s;
    }

    
    public static BaseAction talk(String speech) {
        TalkAction s = new TalkAction( TalkPerformer.SCENE_TALKER, speech);
        s.setNonIncrementing(TalkPerformer.NonIncrementing.FromAPI);
        return s;
    }

    
    public static BaseAction talkWithoutIncrementingFrame(String animCode,
            String speech) {
        TalkAction s = new TalkAction( animCode, speech);
        s.setNonIncrementing(TalkPerformer.NonIncrementing.True);
        return s;
    }

    
    public static BaseAction talkWithoutIncrementingFrameNonBlocking(
            String animCode, String speech) {
        TalkAction s = new TalkAction( animCode, speech);
        s.setNonIncrementing(TalkPerformer.NonIncrementing.True);
        s.setParallel(true);
        return s;
    }

    
    public static BaseAction talkWithoutIncrementingFrame(String speech) {
        TalkAction s = new TalkAction( TalkPerformer.SCENE_TALKER, speech);
        s.setNonIncrementing(TalkPerformer.NonIncrementing.True);
        return s;
    }

    
    public static BaseAction talkWithoutIncrementingFrameNonBlocking(String speech) {
        TalkAction s = new TalkAction( TalkPerformer.SCENE_TALKER, speech);
        s.setNonIncrementing(TalkPerformer.NonIncrementing.True);
        s.setParallel(true);
        return s;
    }

    
    public static BaseAction setAnimationAsObjectCurrent(String atid) {
        SingleCallAction a = new SingleCallAction(
                Type.SetCurrentAnimation);
        a.setAtid(atid);
        return a;
    }

    
    public static BaseAction setAnimationAsObjectCurrentAndSetFrame(String atid, int frame) {
        SingleCallAction a = new SingleCallAction(
                Type.SetCurrentAnimationAndFrame);
        a.setAtid(atid);
        a.setInt(frame);
        return a;
    }

    
    public static BaseAction setCurrentFrame(short ocode, int frame) {
        SingleCallAction a = new SingleCallAction( Type.SetCurrentFrame);
        a.setOCode(ocode);
        a.setInt(frame);
        return a;
    }

    
    public static BaseAction setBaseMiddleX(short ocode, double x) {
        SingleCallAction a = new SingleCallAction( Type.SetBaseMiddleX);
        a.setOCode(ocode);
        a.setDouble(x);
        return a;
    }

    
    public static BaseAction setBaseMiddleY(short ocode, double y) {
        SingleCallAction a = new SingleCallAction( Type.SetBaseMiddleY);
        a.setOCode(ocode);
        a.setDouble(y);
        return a;
    }

    
    public static BaseAction setDisplayName(short ocode, String displayName) {
        SingleCallAction a = new SingleCallAction( Type.SetDisplayName);
        a.setOCode(ocode);
        a.setString(displayName);
        return a;
    }

    
    public static BaseAction setInventoryItemVisible(int icode, boolean isVisible) {
        SingleCallAction a = new SingleCallAction(
                Type.SetInventoryVisible);
        a.setICode(icode);
        a.setBoolean(isVisible);
        return a;
    }

    
    public static BaseAction setVisible(short ocode, boolean isVisible) {
        SingleCallAction a = new SingleCallAction( Type.SetVisible);
        a.setOCode(ocode);
        a.setBoolean(isVisible);
        return a;
    }

    
    public static BaseAction sleep(int milliseconds) {
        SingleCallAction a = new SingleCallAction( Type.Sleep);
        a.setInt(milliseconds);
        return a;
    }

    
    public static ISceneChainEnd onDoCommand(ISceneChain base, IGameScene scene, IOnDoCommand api,
            DialogChain ba, int verb, SentenceItem itemA,
            SentenceItem itemB, double x, double y) throws A2gException {
            ISceneChainEnd secondStep = scene.onDoCommand(api,
                api.createChainRootAction(), verb, itemA, itemB, x, y);
        return subroutine(base,  secondStep);
    }

    
    public static ISceneChain subroutine(ISceneChain base, ISceneChain secondStep) {
        
        // find root of orig
        IBaseChain somewhereInOrig = secondStep;

        while (somewhereInOrig.getParent().getParent() != null) {
            somewhereInOrig = somewhereInOrig.getParent();
        }
        somewhereInOrig.setParent(base);
        return secondStep;
        
    }

    
    public static ISceneChainEnd subroutine(ISceneChain base, ISceneChainEnd secondStep)   {
        
        // find root of orig
        IBaseChain somewhereInOrig = secondStep;

        while (somewhereInOrig.getParent().getParent() != null) {
            somewhereInOrig = somewhereInOrig.getParent();
        }
        somewhereInOrig.setParent(base);
        return secondStep;
        
    }

    
    public static BaseAction swapVisibility(short ocodeA, short ocodeB) {
        SingleCallAction a = new SingleCallAction( Type.SwapVisibility);
        a.setOCode(ocodeA);
        a.setOCode2(ocodeB);
        return a;
    }

    
    public static BaseAction switchTo(String sceneName, int entrySegment) throws A2gException {
        // best to throw this exception now, inside the Scene handler, rather
        // than when it is executed, which might be much later at the 
        // end of an asycnhronous animation execution chain.
        if(sceneName==null)
            throw new A2gException(sceneName);
        
        SingleCallAction a = new SingleCallAction( Type.Switch);
        a.setString(sceneName);
        a.setInt(entrySegment);
        return a;
    }

    
    public static BaseAction waitForFrame(short ocode, int frame) {
        return new WaitForFrameAction( ocode, frame);
    }


    
    public static BaseAction walkNeverSwitch(double x, double y) {
        return walkNeverSwitch( new Point(x, y));
    }

    
    public static BaseAction walkNeverSwitch(Point end) {
        WalkAction a = new WalkAction( ScenePresenter.DEFAULT_SCENE_OBJECT);
        a.setEndX(end.getX());
        a.setEndY(end.getY());
        return a;
    }
    
    public static BaseAction walkNeverSwitch(short ocode, double x, double y) {
        return walkNeverSwitch( ocode, new Point(x, y));
    }

    
    public static BaseAction walkNeverSwitch(short ocode, Point end) {
        WalkAction a = new WalkAction( ocode);
        a.setEndX(end.getX());
        a.setEndY(end.getY());
        return a;
    }

    
    public static BaseAction playTitleCard(String text, double durationInSecs) {
        return new TitleCardAction( text, durationInSecs);
    }

    
    public static BaseAction setTitleCard(String text) {
        SingleCallAction s=  new SingleCallAction( Type.SetTitleCard);
        s.setString(text);
        return s;
    }
    
    
    public static BaseAction playAnimationNonBlockingHoldLastFrame(String atid) {
        PlayAnimationAction a = new PlayAnimationAction( atid);

        a.setHoldLastFrame(true);
        a.setParallel(true);
        return a;
    }

    
    public static BaseAction setValue(Object object, int i) {
        SingleCallAction a = new SingleCallAction( Type.SetValue);
        a.setString(object.toString());
        a.setInt(i);
        return a;
    }

    
    public static BaseAction moveWhilstAnimating(short ocode, double x, double y) {
        MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction( ocode);
        a.setEndX(x);
        a.setEndY(y);
        return a;
    }

    
    public static BaseAction moveWhilstAnimatingInY(short objId, double y) {
        MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction( objId);
        a.setEndY(y);
        return a;
    }

    
    public static BaseAction moveWhilstAnimatingNonBlocking(short objId,
            double x, double y) {
        MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction( objId);
        a.setEndX(x);
        a.setEndY(y);
        a.setParallel(true);
        return a;
    }

    
    public static BaseAction moveWhilstAnimatingLinear(short objId, double x,
            double y) {
        MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction( objId);
        a.setEndX(x);
        a.setEndY(y);
        a.setLinear(true);
        return a;
    }

    
    public static BaseAction moveWhilstAnimatingLinearNonBlocking(short objId,
            double x, double y) {
        MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction( objId);
        a.setEndX(x);
        a.setEndY(y);
        a.setLinear(true);
        a.setParallel(true);
        return a;
    }

    
    public static BaseAction moveCameraToNewXPosition(double x,
            double durationInSecs) {
        ScrollCameraXAction a = new ScrollCameraXAction( x, durationInSecs);
        a.setParallel(false);
        return a;
    }

    
    public static BaseAction moveCameraToNewXPositionNonBlocking(double x,
            double durationInSecs) {
        ScrollCameraXAction a = new ScrollCameraXAction( x, durationInSecs);
        a.setParallel(true);
        return a;
    }

    
    public static BaseAction moveCameraToNewYPosition(double y,
            double durationInSecs) {
        ScrollCameraYAction a = new ScrollCameraYAction( y, durationInSecs);
        a.setParallel(false);
        return a;
    }

    
    public static BaseAction moveCameraToNewYPositionNonBlocking(double y,
            double durationInSecs) {
        ScrollCameraYAction a = new ScrollCameraYAction( y, durationInSecs);
        a.setParallel(true);
        return a;
    }

    
    public static BaseAction hideAll() {
        SingleCallAction a = new SingleCallAction( Type.HideAll);
        return a;
    }

    
    public static BaseAction setToInitialPosition(short ocode) {
        SingleCallAction a = new SingleCallAction(
                Type.SetToInitialPosition);
        a.setOCode(ocode);
        return a;
    }

    
    
    public static BaseAction alignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation(
            String atid, int frame) {
        SingleCallAction a = new SingleCallAction( Type.AlignBaseMiddle);
        a.setAtid(atid);
        a.setInt(frame);
        return a;
    }

    
    public static BaseAction alignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation(
            String atid) {
        SingleCallAction a = new SingleCallAction( Type.AlignBaseMiddle);
        a.setAtid(atid);
        a.setInt(0);
        return a;
    }

    
    public static BaseAction alignBaseMiddleOfOldFrameToLastFrameOfNewAnimation(
            String atid) {
        SingleCallAction a = new SingleCallAction( Type.AlignBaseMiddle);
        a.setAtid(atid);
        a.setInt(-17);
        return a;
    }

    
    public static BaseAction shareWinning(String string) {
        SingleCallAction a = new SingleCallAction( Type.ShareWinning);
        a.setString(string);
        return a;
    }



    
    public static BaseAction setSoundtrack(String stid) {
        SingleCallAction a = new SingleCallAction( Type.SetSoundtrack);
        a.setString(stid);
        return a;
    }

    
    public static BaseAction playSound(String stid) {
        PlaySoundAction a = new PlaySoundAction( stid);
        return a;
    }

    
    public static BaseAction playSoundNonBlocking(String stid) {
        PlaySoundAction a = new PlaySoundAction( stid);
        a.setParallel(true);
        return a;
    }

    
    public static BaseAction setAnimationAsObjectWalkDirection(String atid,
            WalkDirection type) {
        SingleCallAction s = new SingleCallAction(
                Type.SetAnimationSpecial);
        s.setAtid(atid);
        s.setString(type.toString());
        s.setInt(type.toInt());
        return s;
    }

    
    public static BaseAction setAnimationAsSceneTalker(String atid) {
        SingleCallAction s = new SingleCallAction(
                Type.SetAnimationAsSceneTalker);
        s.setAtid(atid);
        return s;
    }

    
    public static BaseAction setAnimationAsObjectInitial(String atid) {
        SingleCallAction s = new SingleCallAction(
                Type.SetAnimationObjectInitial);
        s.setAtid(atid);
        return s;
    }

    
    public static BaseAction setHeadRectangleForObject(short ocode, int index) {
        SingleCallAction s = new SingleCallAction( Type.SetHeadRectangle);
        s.setOCode(ocode);
        s.setInt(index);
        return s;
    }

    
    public static BaseAction walkAndTalkNeverSwitch(short ocode, double x,
            double y, String speech) {
        WalkAndTalkAction s = new WalkAndTalkAction( ocode, speech);
        s.setNonIncrementing(TalkPerformer.NonIncrementing.True);
        s.setEndX(x);
        s.setEndY(y);
        return s;

    }

    
    public static BaseAction walkAndScaleNeverSwitch(short ocode, Point p,
            double startScale, double endScale) {
        WalkAction s = new WalkAction( ocode);
        s.setEndX(p.getX());
        s.setEndY(p.getY());
        s.setStartScale(startScale);
        s.setEndScale(endScale);
        return s;

    }

    
    public static BaseAction walkNeverSwitchNonBlocking(short ocode, double x, double y) {
        WalkAction s = new WalkAction( ocode);
        s.setEndX(x);
        s.setEndY(y);
        s.setParallel(true);
        return s;
    }
    
    
    
    public static BaseAction look(String atid, double duration) {
        LookAction s = new LookAction( atid, duration);
        return s;
    }

    public static BaseAction walkTo(double x, double y) {
        return walkTo( new Point(x, y));
    }

    public static BaseAction walkTo(Point end) {
        short dso = ScenePresenter.DEFAULT_SCENE_OBJECT;
        WalkMaybeSwitchAction a = new WalkMaybeSwitchAction( dso);
        a.setEndX(end.getX());
        a.setEndY(end.getY());
        return a;
    }
    
    public static IBaseChain getChainForWalkAndScaleAction(SceneChain thisChain, short ocode, Point p, double startScale, double endScale) {
        WalkAction walk = new WalkAction(ocode);
        walk.setEndX(p.getX());
        walk.setEndY(p.getY());
        walk.setStartScale(startScale);
        walk.setEndScale(endScale);
        walk.setToInitialAtEnd(false);
    
        SceneChain a = new SceneChain(thisChain, walk);
        return a;
    }


    public static BaseAction setGuiState(GuiStateEnum state) {
        SingleCallAction a = new SingleCallAction( Type.SetGuiState);
        a.setString(state.toString());
        return a;
    }
}
