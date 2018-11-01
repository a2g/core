/*
 * Copyright 2012 Anthony Cassidy
 *
 * Licensed under the Apache License, Version 2.0 (the "License")); you may not
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

package com.github.a2g.core.chain;


import com.github.a2g.core.action.Actions;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.DialogEnterAction;
import com.github.a2g.core.action.SingleCallAction;
import com.github.a2g.core.action.WalkAction;
import com.github.a2g.core.action.performer.SingleCallPerformer.Type;
import com.github.a2g.core.interfaces.game.chainables.ISceneChain;
import com.github.a2g.core.interfaces.game.chainables.ISceneChainEnd;
import com.github.a2g.core.interfaces.game.chainables.ISceneChainRoot;
import com.github.a2g.core.interfaces.game.handlers.IOnDoCommand;
import com.github.a2g.core.interfaces.game.scene.IGameScene;
import com.github.a2g.core.interfaces.game.scene.ConstantsForAPI.WalkDirection;
import com.github.a2g.core.objectmodel.ScenePresenter;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.A2gException;
import com.google.gwt.touch.client.Point; 

public class SceneChain 
extends SceneChainEnd 
implements ISceneChain {

    public enum SwapType {
        Visibility
    }

    public SceneChain(BaseChain parent, BaseAction action) {
        super(parent, action);
    }

    @Override
    public ISceneChainEnd doBoth(ISceneChain a, ISceneChain b) {
        return Actions.doBoth(a,b);
    }

    @Override
    public ISceneChainEnd activateDialogTreeMode(int branchId) {
        return new SceneChainEnd(this, new DialogEnterAction(branchId));
    }
    
    @Override
    public ISceneChain doNothing() {
        return new SceneChain(this, Actions.doNothing());
    }
    
    @Override
    public ISceneChainEnd switchTo(String sceneName, int entrySegment) {
        return new SceneChainEnd(this, Actions.switchTo(sceneName, entrySegment));
    }

    // plain..
    @Override
    public ISceneChain playAnimation(String atid) {
        return new SceneChain(this, Actions.playAnimation(atid));
    }
    
    @Override
    public ISceneChainEnd onDoCommand(IGameScene scene, IOnDoCommand api, ISceneChainRoot ba, int verb, SentenceItem itemA, SentenceItem itemB, double x, double y) throws A2gException {
        
        ISceneChainEnd secondStep = scene.onDoCommand(api,  api.createChainRootAction(), verb, itemA, itemB, x, y);
        return Actions.subroutine(this, secondStep);
    }


    // simple backwards
    @Override
    public ISceneChain playAnimationBackwards(String atid) {
        return new SceneChain(this, Actions.playAnimationBackwards(atid));
    }

    // simple hold last frame
    @Override
    public ISceneChain playAnimationHoldLastFrame(String atid) {
        return new SceneChain(this, Actions.playAnimationHoldLastFrame(atid));
    }

    // simple non blocking
    @Override
    public ISceneChain playAnimationNonBlocking(String atid) {
        return new SceneChain(this, Actions.playAnimationNonBlocking( atid));
    }

    // double combo1of3: backwards + hold last frame
    @Override
    public ISceneChain playAnimationBackwardsHoldLastFrame(String atid) {
        return new SceneChain(this, Actions.playAnimationBackwardsHoldLastFrame(atid));
    }

    // double combo2of3: backwards + nonblocking
    @Override
    public ISceneChain playAnimationBackwardsNonBlocking(String atid) {
        return new SceneChain(this, Actions.playAnimationBackwardsNonBlocking( atid));
    }

    // double combo2of3: holdLastFrame + nonblocking
    @Override
    public ISceneChain playAnimationHoldLastFrameNonBlocking(String atid) {
        return new SceneChain(this, Actions.playAnimationHoldLastFrameNonBlocking(atid));
    }

    // ..and one method with the whole lot!
    @Override
    public ISceneChain playAnimationBackwardsHoldLastFrameNonBlocking(String atid) {
        return new SceneChain(this, Actions.playAnimationBackwardsHoldLastFrameNonBlocking(atid));
    }

    @Override
    public ISceneChain talk(String animCode, String speech) {
        return new SceneChain(this, Actions.talk(animCode, speech));
    }
    @Override
    public ISceneChain talk(String speech) {
        return new SceneChain(this, Actions.talk(speech));
    }

    @Override
    public ISceneChain talkWithoutIncrementingFrame(String animCode,String speech) {
        return new SceneChain(this, Actions.talkWithoutIncrementingFrame(animCode, speech));
    }

    @Override
    public ISceneChain talkWithoutIncrementingFrameNonBlocking(String animCode, String speech) {
        return new SceneChain(this, Actions.talkWithoutIncrementingFrameNonBlocking( animCode, speech));
    }

    @Override
    public ISceneChain talkWithoutIncrementingFrame(String speech) {
        return new SceneChain(this, Actions.talkWithoutIncrementingFrame( speech));
    }

    @Override
    public ISceneChain talkWithoutIncrementingFrameNonBlocking(String speech) {
        return new SceneChain(this, Actions.talkWithoutIncrementingFrameNonBlocking(speech));
    }

    @Override
    public ISceneChain setAnimationAsObjectCurrent(String atid) {
        return new SceneChain(this, Actions.setAnimationAsObjectCurrent(atid));
    }

    @Override
    public ISceneChain setAnimationAsObjectCurrentAndSetFrame(String atid, int frame) {
        return new SceneChain(this, Actions.setAnimationAsObjectCurrentAndSetFrame(atid, frame));
    }

    @Override
    public ISceneChain setCurrentFrame(short ocode, int frame) {
        return new SceneChain(this, Actions.setCurrentFrame( ocode, frame));
    }

    @Override
    public ISceneChain setBaseMiddleX(short ocode, double x) {
        return new SceneChain(this, Actions.setBaseMiddleX( ocode, x));
    }

    @Override
    public ISceneChain setBaseMiddleY(short ocode, double y) {
        return new SceneChain(this, Actions.setBaseMiddleY(ocode,y));
    }

    @Override
    public ISceneChain setDisplayName(short ocode, String displayName) {
        return new SceneChain(this, Actions.setDisplayName( ocode, displayName));
    }

    @Override
    public ISceneChain setInventoryItemVisible(int icode, boolean isVisible) {
        return new SceneChain(this, Actions.setInventoryItemVisible( icode, isVisible));
    }

    @Override
    public ISceneChain setVisible(short ocode, boolean isVisible) {
        return new SceneChain(this, Actions.setVisible( ocode, isVisible));
    }

    @Override
    public ISceneChain sleep(int milliseconds) {
        return new SceneChain(this, Actions.sleep( milliseconds));
    }


    @Override
    public ISceneChain subroutine(ISceneChain secondStep) {
        return Actions.subroutine(this, secondStep);
    }
/*
    @Override
    public ISceneChainEnd subroutine(ISceneChainEnd secondStep) {
        return Actions.subroutine(this, secondStep);
    }
*/
    @Override
    public ISceneChain swapVisibility(short ocodeA, short ocodeB) {
        return new SceneChain(this, Actions.swapVisibility( ocodeA, ocodeB));
    }



    @Override
    public ISceneChain waitForFrame(short ocode, int frame) {
        return new SceneChain(this, Actions.waitForFrame( ocode, frame));
    }


    @Override
    public ISceneChain walkNeverSwitch(double x, double y) {
        return new SceneChain(this, Actions.walkNeverSwitch(x,y));
    }

    @Override
    public ISceneChain walkNeverSwitch(Point end) {
        return new SceneChain(this, Actions.walkNeverSwitch( end));
    }

    @Override
    public ISceneChainEnd walkTo(double x, double y) {
        return new SceneChain(this, Actions.walkTo(x,y));
    }

    @Override
    public ISceneChainEnd walkTo(Point end) {
        return new SceneChain(this, Actions.walkTo(end));
    }

    @Override
    public ISceneChainEnd walkAlwaysSwitch(double x, double y, String sceneName, int entrySegment) throws A2gException {
        // best to throw this exception now, inside the Scene handler, rather
        // than when it is executed, which might be much later at the 
        // end of an asycnhronous animation execution chain.
        if(sceneName==null)
            throw new A2gException ("ISceneChain::walkAlwaysSwitch");
        return walkAlwaysSwitch( new Point(x,y), sceneName, entrySegment);
    }
    
    @Override
    public ISceneChainEnd walkAlwaysSwitch(Point p, String sceneName, int entrySegment) throws A2gException {
        // best to throw this exception now, inside the Scene handler, rather
        // than when it is executed, which might be much later at the 
        // end of an asycnhronous animation execution chain.
        if(sceneName==null)
            throw new A2gException (sceneName);
        WalkAction walkAction = new WalkAction( ScenePresenter.DEFAULT_SCENE_OBJECT);
        walkAction.setEndX(p.getX());
        walkAction.setEndY(p.getY());
        walkAction.setToInitialAtEnd(false);

        SingleCallAction switchAction = new SingleCallAction(Type.Switch);
        switchAction.setString(sceneName);
        switchAction.setInt(entrySegment);

        SceneChain a = new SceneChain(this, walkAction);
        SceneChain b = new SceneChain(a, switchAction);
        return b;
    }

    @Override
    public ISceneChainEnd walkAndScaleAlwaysSwitch(short ocode, Point p, double startScale, double endScale, String sceneName, int entrySegment) throws A2gException {
        // best to throw this exception now, inside the Scene handler, rather
        // than when it is executed, which might be much later at the 
        // end of an asycnhronous animation execution chain.
        if(sceneName==null)
            throw new A2gException(sceneName);
        WalkAction walk = new WalkAction(ocode);
        walk.setEndX(p.getX());
        walk.setEndY(p.getY());
        walk.setStartScale(startScale);
        walk.setEndScale(endScale);
        walk.setToInitialAtEnd(false);

        SingleCallAction single = new SingleCallAction(Type.Switch);
        single.setString(sceneName);
        single.setInt(entrySegment);

        SceneChain a = new SceneChain(this, walk);
        SceneChain b = new SceneChain(a, single);
        return b;
    }
 
    @Override
    public ISceneChain walkNeverSwitch(short ocode, double x, double y) {
        return new SceneChain(this, Actions.walkNeverSwitch( ocode, x, y));
    }

    @Override
    public ISceneChain walkNeverSwitch(short ocode, Point end) {
        return new SceneChain(this, Actions.walkNeverSwitch( ocode, end));
    }

    @Override
    public ISceneChain playTitleCard(String text, double durationInSecs) {
        return new SceneChain(this, Actions.playTitleCard( text, durationInSecs));
    }

    @Override
    public ISceneChain setTitleCard(String text) {
        return new SceneChain(this, Actions.setTitleCard( text));
    }

    @Override
    public ISceneChain playAnimationNonBlockingHoldLastFrame(String atid) {
        return new SceneChain(this, Actions.playAnimationNonBlockingHoldLastFrame( atid));
    }

    @Override
    public ISceneChain setValue(Object object, int i) {
        return new SceneChain(this, Actions.setValue( object, i));
    }

    @Override
    public ISceneChain moveWhilstAnimating(short ocode, double x, double y) {
        return new SceneChain(this, Actions.moveWhilstAnimating( ocode, x,y));
    }

    @Override
    public ISceneChain moveWhilstAnimatingInY(short objId, double y) {
        return new SceneChain(this, Actions.moveWhilstAnimatingInY(objId,y));
    }

    @Override
    public ISceneChain moveWhilstAnimatingNonBlocking(short objId, double x, double y) {
        return new SceneChain(this, Actions.moveWhilstAnimatingNonBlocking(objId,x,y));
    }

    @Override
    public ISceneChain moveWhilstAnimatingLinear(short objId, double x, double y) {
        return new SceneChain(this, Actions.moveWhilstAnimatingLinear(objId,x,y));
    }

    @Override
    public ISceneChain moveWhilstAnimatingLinearNonBlocking(short objId, double x, double y) {
        return new SceneChain(this, Actions.moveWhilstAnimatingLinearNonBlocking(objId, x,y));
    }

    @Override
    public ISceneChain moveCameraToNewXPosition(double x, double durationInSecs) {
        return new SceneChain(this, Actions.moveCameraToNewXPosition( x, durationInSecs));
    }

    @Override
    public ISceneChain moveCameraToNewXPositionNonBlocking(double x, double durationInSecs) {
        return new SceneChain(this, Actions.moveCameraToNewXPositionNonBlocking( x, durationInSecs));
    }

    @Override
    public ISceneChain moveCameraToNewYPosition(double y, double durationInSecs) {
        return new SceneChain(this, Actions.moveCameraToNewYPosition( y,durationInSecs));
    }

    @Override
    public ISceneChain moveCameraToNewYPositionNonBlocking(double y, double durationInSecs) {
        return new SceneChain(this, Actions.moveCameraToNewYPositionNonBlocking(y,durationInSecs));
    }

    @Override
    public ISceneChain hideAll() {
        return new SceneChain(this, Actions.hideAll());
    }

    @Override
    public ISceneChain setToInitialPosition(short ocode) {
        return new SceneChain(this, Actions.setToInitialPosition( ocode));
    }


    @Override
    public ISceneChain alignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation(String atid, int frame) {
        return new SceneChain(this, Actions.alignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation( atid, frame));
    }

    @Override
    public ISceneChain alignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation(String atid) {
        return new SceneChain(this, Actions.alignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation( atid));
    }

    @Override
    public ISceneChain alignBaseMiddleOfOldFrameToLastFrameOfNewAnimation(String atid) {
        return new SceneChain(this, Actions.alignBaseMiddleOfOldFrameToLastFrameOfNewAnimation( atid));
    }

    @Override
    public ISceneChain shareWinning(String string) {
        return new SceneChain(this, Actions.shareWinning( string));
    }

    @Override
    public ISceneChain setSoundtrack(String stid) {
        return new SceneChain(this, Actions.setSoundtrack( stid));
    }

    @Override
    public ISceneChain playSound(String stid) {
        return new SceneChain(this, Actions.playSound( stid));
    }

    @Override
    public ISceneChain playSoundNonBlocking(String stid) {
        return new SceneChain(this, Actions.playSoundNonBlocking(stid));
    }

    @Override
    public ISceneChain setAnimationAsObjectWalkDirection(String atid, WalkDirection type) {
        return new SceneChain(this, Actions.setAnimationAsObjectWalkDirection(atid, type));
    }

    @Override
    public ISceneChain setAnimationAsSceneTalker(String atid) {
        return new SceneChain(this, Actions.setAnimationAsSceneTalker( atid));
    }

    @Override
    public ISceneChain setAnimationAsObjectInitial(String atid) {
        return new SceneChain(this, Actions.setAnimationAsObjectInitial( atid));
    }

    @Override
    public ISceneChain setHeadRectangleForObject(short ocode, int index) {
        return new SceneChain(this, Actions.setHeadRectangleForObject( ocode, index));
    }

    @Override
    public ISceneChain walkAndTalkNeverSwitch(short ocode, double x, double y, String speech) {
        return new SceneChain(this, Actions.walkAndTalkNeverSwitch( ocode, x, y, speech));
    }

    @Override
    public ISceneChain walkAndScaleNeverSwitch(short ocode, Point p, double startScale, double endScale) {
       return new SceneChain(this, Actions.walkAndScaleNeverSwitch( ocode, p, startScale, endScale));
    }

    @Override
    public ISceneChain walkNeverSwitchNonBlocking(short ocode, double x,double y) {
        return new SceneChain(this, Actions.walkNeverSwitchNonBlocking( ocode, x, y));
    }

    @Override
    public ISceneChain look(String atid, double duration) {
        return new SceneChain(this, Actions.look( atid, duration));
    }


}
