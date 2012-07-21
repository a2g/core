/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.ActionCallbackAPI;
import com.github.a2g.core.action.NullParentAction;
import com.github.a2g.core.action.PlayAnimationAction;
import com.github.a2g.core.action.PlayAnimationRepeatWhilstVisibleAction;
import com.github.a2g.core.action.SayAction;
import com.github.a2g.core.action.SayWithoutAnimationAction;
import com.github.a2g.core.action.SetActiveAnimationAction;
import com.github.a2g.core.action.SetActiveFrameAction;
import com.github.a2g.core.action.SetBaseMiddleXAction;
import com.github.a2g.core.action.SetBaseMiddleYAction;
import com.github.a2g.core.action.SetDisplayNameAction;
import com.github.a2g.core.action.SetHomeAnimationAction;
import com.github.a2g.core.action.SetInventoryVisibleAction;
import com.github.a2g.core.action.SetTalkingAnimationAction;
import com.github.a2g.core.action.SetTalkingAnimationDelayAction;
import com.github.a2g.core.action.SetVisibleAction;
import com.github.a2g.core.action.SleepAction;
import com.github.a2g.core.action.SwapPropertyAction;
import com.github.a2g.core.action.SwitchToAction;
import com.github.a2g.core.action.DialogTreeDisplayAction;
import com.github.a2g.core.action.DialogTreeEndAction;
import com.github.a2g.core.action.DialogTreeGoToAction;
import com.github.a2g.core.action.WaitForFrameAction;
import com.github.a2g.core.action.WalkToAction;
import com.github.a2g.core.action.BaseDialogTreeAction;
import com.github.a2g.core.authoredroom.InternalAPI;
import com.github.a2g.core.authoredroom.PointF;


// import com.google.gwt.animation.client.Animation;



public abstract class BaseAction extends com.google.gwt.animation.client.Animation {	
    private ActionCallbackAPI callbacks;
    protected BaseAction parent;
    private InternalAPI api;

    public enum SwapType {
        Visibility
    }

    // all the abstract have GameAction in them to let
    // the client know they are dealing with GameAction and not Animation
    abstract public boolean isParallel();

    abstract protected void onUpdateGameAction(double progress);

    abstract protected void onCompleteGameAction();

    abstract public void runGameAction();

    protected BaseAction(BaseAction parent, InternalAPI api) {
        this.parent = parent;
        this.callbacks = null;
        this.api = api;
    }
    ;

    public BaseDialogTreeAction branch(int branchId, String text) {
        return new DialogTreeDisplayAction(this,
                text, branchId);
    }

    public BaseAction doBoth(BaseAction a, BaseAction b) {
        return a.subroutine(b);
    }

    public BaseDialogTreeAction doNothing() {
        return new NullParentAction();
    }

    public BaseDialogTreeAction endTalkie() {
        return new DialogTreeEndAction(this);
    }

    public InternalAPI getApi() {
        return this.api;
    }

    public BaseAction getParent() {
        return this.parent;
    }

    public BaseDialogTreeAction gotoBranch(int branchId) {
        return new DialogTreeGoToAction(this,
                branchId);
    }

    @Override // method in animation
    protected void onComplete() {
        // do game action specific first e.g hide popup
        onCompleteGameAction();

        // then call the object that ran the action
        if (this.callbacks != null) {
            this.callbacks.onGameActionComplete(
                    this);
        }
    }

    @Override
    protected void onUpdate(double progress) {
        onUpdateGameAction(progress);
    }

    // plain..
    public BaseAction playAnimation(int animId) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        return a;
    }

    public BaseAction playAnimation(int animId, int delay) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setDelay(delay);
        return a;

    }

    // simple backwards
    public BaseAction playAnimationBackwards(int animId) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setBackwards(true);
        return a;
    }

    public BaseAction playAnimationBackwards(int animId, int delay) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setBackwards(true);
        a.setDelay(delay);
        return a;
    }

    // simple hold last frame
    public BaseAction playAnimationHoldLastFrame(int animId) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setHoldLastFrame(true);
        return a;
    }

    public BaseAction playAnimationHoldLastFrame(int animId, int delay) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setHoldLastFrame(true);
        a.setDelay(delay);
        return a;
    }

    // simple non blocking
    public BaseAction playAnimationNonBlocking(int animId) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setNonBlocking(true);
        return a;
    }

    public BaseAction playAnimationNonBlocking(int animId, int delay) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setNonBlocking(true);
        a.setDelay(delay);
        return a;
    }	

    // double combo1of3: backwards + hold last frame
    public BaseAction playAnimationBackwardsHoldLastFrame(int animId) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setBackwards(true);
        a.setHoldLastFrame(true);
        return a;
    }

    public BaseAction playAnimationBackwardsHoldLastFrame(int animId, int delay) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setBackwards(true);
        a.setHoldLastFrame(true);
        a.setDelay(delay);
        return a;
    }

    // double combo2of3: backwards + nonblocking
    public BaseAction playAnimationBackwardsNonBlocking(int animId) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setBackwards(true);
        a.setNonBlocking(true);
        return a;
    }

    public BaseAction playAnimationBackwardsNonBlocking(int animId, int delay) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setBackwards(true);
        a.setNonBlocking(true);
        a.setDelay(delay);
        return a;
    }

    // double combo2of3: holdLastFrame + nonblocking
    public BaseAction playAnimationHoldLastFrameNonBlocking(int animId) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setHoldLastFrame(true);
        a.setNonBlocking(true);
        return a;
    }

    public BaseAction playAnimationHoldLastFrameNonBlocking(int animId, int delay) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setHoldLastFrame(true);
        a.setNonBlocking(true);
        a.setDelay(delay);
        return a;
    }

    // ..and one method with the whole lot!
    public BaseAction playAnimationBackwardsHoldLastFrameNonBlocking(int animId) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setBackwards(true);
        a.setHoldLastFrame(true);
        a.setNonBlocking(true);
        return a;
    }

    public BaseAction playAnimationBackwardsHoldLastFrameNonBlocking(int animId, int delay) {
        PlayAnimationAction a = new PlayAnimationAction(
                this, animId);

        a.setBackwards(true);
        a.setHoldLastFrame(true);
        a.setNonBlocking(true);
        a.setDelay(delay);
        return a;
    }

    public BaseAction playAnimationRepeatWhilstVisible(int animId) {
        return new PlayAnimationRepeatWhilstVisibleAction(
                this, animId);
    }

    public BaseAction say(short objId, String speech) {
        return new SayAction(this, objId,
                speech);
        // return toReturn;
    }

    public BaseAction sayWithoutAnimation(short objId, String speed) {
        return new SayWithoutAnimationAction(
                this, objId, speed);
    }

    public BaseAction setActiveAnimation(int animId) {
        return new SetActiveAnimationAction(
                this, animId);
    }

    public BaseAction setActiveFrame(short objId, int frame) {
        return new SetActiveFrameAction(this,
                objId, frame);
    }

    protected void setApi(InternalAPI api) {
        this.api = api;
    }

    public BaseAction setBaseMiddleX(short objId, double x) {
        return new SetBaseMiddleXAction(this,
                objId, x);
    }

    public BaseAction setBaseMiddleY(short objId, double y) {
        return new SetBaseMiddleYAction(this,
                objId, y);
        // return toReturn;
    }

    public void setCallbacks(ActionCallbackAPI callbacks) {
        this.callbacks = callbacks;
    }

    public BaseAction setDisplayName(short objId, String displayName) {
        return new SetDisplayNameAction(this,
                objId, displayName);
    }

    public BaseAction setInventoryVisible(int invId, boolean isVisible) {
        return new SetInventoryVisibleAction(
                this, invId, isVisible);
    }

    public void setParent(BaseAction parent) {
        this.parent = parent;
    }

    public BaseAction setTalkingAnimation(int animId) {
        return new SetTalkingAnimationAction(
                this, animId);
        // return toReturn;
    }

    public BaseAction setTalkingAnimationDelay(short objId, int delay) {
        return new SetTalkingAnimationDelayAction(
                this, objId, delay);
        // return toReturn;
    }
    ;

    public BaseAction setVisible(short objId, boolean isVisible) {
        return new SetVisibleAction(this,
                objId, isVisible);
    }
    ;

    public BaseAction sleep(int milliseconds) {
        return new SleepAction(this,
                milliseconds);
    }

    public BaseAction subroutine(BaseAction orig) {
        // find root of orig
        BaseAction somewhereInOrig = orig;

        while (somewhereInOrig.getParent().getParent()
                != null) {
            somewhereInOrig = somewhereInOrig.getParent();
        }
        somewhereInOrig.setParent(this);
        return orig;
    }

    public BaseAction swapProperty(short objId1, short objId2,
            SwapType type) {
        return new SwapPropertyAction(this,
                objId1, objId2, type);
    }

    public BaseDialogTreeAction  switchTo(String roomName) {
        return new SwitchToAction(this,
                roomName);
        // return toReturn;
    }

    public BaseAction waitForFrame(short objId, int frame) {
        return new WaitForFrameAction(this,
                objId, frame);
    }

    
    public BaseAction setHomeAnimation(int animId) {
        return new SetHomeAnimationAction(this,
                animId);
    }


    public BaseAction walkTo(short objId, double x, double y) {
        return new WalkToAction(this, objId, x, y,0, false);
    }

    public BaseAction walkTo(short objId, PointF point) {
        return new WalkToAction(this, objId,
                point.getX(), point.getY(),0, false);
    }

    public BaseAction walkTo(short objId, double x, double y, int delay) {
        return new WalkToAction(this, objId, x, y, delay, false);
    }

    public BaseAction walkTo(short objId, PointF point, int delay) {
        return new WalkToAction(this, objId,point.getX(), point.getY(), delay, false);
    }
    

    public BaseAction walkToNonBlocking(short objId, double x, double y) {
        return new WalkToAction(this, objId, x, y,0, true);
    }

    public BaseAction walkToNonBlocking(short objId, PointF point) {
        return new WalkToAction(this, objId,
                point.getX(), point.getY(),0, true);
    }

    public BaseAction walkToNonBlocking(short objId, double x, double y, int delay) {
        return new WalkToAction(this, objId, x,
                y, delay, true);
    }

    public BaseAction walkToNonBlocking(short objId, PointF point, int delay) {
        return new WalkToAction(this, objId,
                point.getX(), point.getY(), delay, true);
    }    
  
}


;
