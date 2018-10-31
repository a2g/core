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
import com.github.a2g.core.action.ChainToDialogAction;
import com.github.a2g.core.action.DialogBranchAction;
import com.github.a2g.core.action.DialogEndAction;
import com.github.a2g.core.action.SingleCallAction;
import com.github.a2g.core.action.TalkAction;
import com.github.a2g.core.action.WalkAction;
import com.github.a2g.core.action.performer.SingleCallPerformer.Type;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.game.chainables.IDialogChain;
import com.github.a2g.core.interfaces.game.chainables.IDialogChainEnd;
import com.github.a2g.core.interfaces.game.scene.ConstantsForAPI;
import com.github.a2g.core.objectmodel.ScenePresenter;
import com.github.a2g.core.primitive.A2gException;
import com.google.gwt.touch.client.Point;

public  class DialogChain extends DialogChainEnd
implements IDialogChain
{

    public DialogChain(BaseChain chain, BaseAction parent) {
        super(chain, parent);

    }
    
    // dialog - non end
  

    @Override
    public	IDialogChain them(String speech) {
        TalkAction s = new TalkAction( TalkPerformer.SCENE_DIALOG_THEM, speech);
        
        DialogChain chain = new DialogChain(this, s);
        return chain;
    }

    @Override
    public	IDialogChain us(String speech) {
        TalkAction s = new TalkAction( TalkPerformer.SCENE_DIALOG_US, speech);
        
        DialogChain chain = new DialogChain(this, s);
        return chain;
    }

    @Override
    public IDialogChain branchNormal(int branchId, boolean isOkToAdd, String text) 
    {
        return new DialogChain(this, new DialogBranchAction( text, branchId, isOkToAdd));
    }
    @Override
    public IDialogChain branchNormal(int branchId, String text) {
        return new DialogChain(this, new DialogBranchAction( text, branchId, true));
    }

    // dialog - end methods.
    @Override
    public IDialogChainEnd endDialogTree() {
        return new DialogChain( this, new DialogEndAction());
    }
    @Override
    public IDialogChainEnd chainTo(int branchId) {
        return new DialogChain(this, new ChainToDialogAction( branchId));
    }
    @Override
    public IDialogChainEnd switchTo(String sceneName, int entrySegment) {
        SingleCallAction a =  new SingleCallAction( Type.Switch);
        a.setString(sceneName);
        a.setInt(entrySegment);
        
        DialogChain chain = new DialogChain(this, a);
        return chain;
    }

    @Override
    public IDialogChainEnd walkAndScaleAlwaysSwitch(short ocode, Point p, double startScale, double endScale, String sceneName, int entrySegment) throws A2gException 
    {
        return walkAndScaleAlwaysSwitch( ocode, p, startScale, endScale, sceneName, entrySegment);
    }
    
    @Override
    public IDialogChain setValue(Object key, int value) {
        SingleCallAction a =  new SingleCallAction( Type.SetValue);
        a.setString(key.toString());
        a.setInt(value);
        
        DialogChain chain = new DialogChain(this, a);
        return chain;
    }

    @Override
    public IDialogChain setInventoryItemVisible(int icode, boolean value) {
        SingleCallAction a =  new SingleCallAction( Type.SetInventoryVisible);
        a.setICode(icode);
        a.setBoolean(value);
        
        DialogChain chain = new DialogChain(this, a);
        return chain;
    }

    @Override
    public IDialogChain sleep(int milliseconds){
        SingleCallAction a =  new SingleCallAction( Type.Sleep);
        a.setInt(milliseconds);
        
        DialogChain chain = new DialogChain(this, a);
        return chain;
    }

    @Override
    public IDialogChain setAnimationAsObjectInitial(String atid)
    {
        SingleCallAction a =  new SingleCallAction( Type.SetInitialAnimation);
        a.setAtid(atid);
        
        DialogChain chain = new DialogChain(this, a);
        return chain;
    }
    
    @Override
    public IDialogChain setAnimationAsObjectCurrent(String atid)
    {
        SingleCallAction a =  new SingleCallAction(Type.SetCurrentAnimation);
        a.setAtid(atid);
        
        DialogChain chain = new DialogChain(this, a);
        return chain;
    }
    @Override
    public IDialogChain branchSticky(int branchId, String text) {
        DialogBranchAction a = new DialogBranchAction( text, branchId, true);
        a.setIsExemptFromSaidList(true);
        
        DialogChain chain = new DialogChain(this, a);
        return chain;
    }
    
    @Override
    public IDialogChain branchSticky(int branchId, boolean isOkToAdd, String text) {
        DialogBranchAction a = new DialogBranchAction( text, branchId, isOkToAdd);
        a.setIsExemptFromSaidList(true);
        
        DialogChain chain = new DialogChain(this, a);
        return chain;
    }
    
    @Override
    public IDialogChainEnd branchTheObligatoryExit(String text) {
        DialogBranchAction a = new DialogBranchAction( text, ConstantsForAPI.DIALOG_HANDLER_EXIT, true);
        a.setIsExemptFromSaidList(true);
        
        DialogChain chain = new DialogChain(this, a);
        return chain;
    }


    @Override
    public IDialogChainEnd walkAlwaysSwitch(double x, double y, String sceneName, int entrySegment) {
        return walkAlwaysSwitch( new Point(x,y), sceneName, entrySegment);
    }

    @Override
    public IDialogChainEnd walkAlwaysSwitch(Point p, String sceneName, int entrySegment) {
        WalkAction walkTo = new WalkAction( ScenePresenter.DEFAULT_SCENE_OBJECT);
        walkTo.setEndX(p.getX());
        walkTo.setEndY(p.getY());
        walkTo.setToInitialAtEnd(false);

        SingleCallAction single =  new SingleCallAction(Type.Switch);
        single.setString(sceneName);
        single.setInt(entrySegment);
        
        
        DialogChain a = new DialogChain(this, walkTo);
        DialogChain b = new DialogChain(a, single);

        return b;
    }

 

    @Override
    public IDialogChain doNothing() {
        return new DialogChain(this,Actions.doNothing());
    }

    // plain..
    @Override
    public IDialogChain playAnimation(String atid) {
        return new DialogChain(this,Actions.playAnimation( atid));
    }

    // simple backwards
    @Override
    public IDialogChain playAnimationBackwards(String atid) {
        return new DialogChain(this,Actions.playAnimationBackwards( atid));
    }

    // simple hold last frame
    @Override
    public IDialogChain playAnimationHoldLastFrame(String atid) {
        return new DialogChain(this,Actions.playAnimationHoldLastFrame( atid));
    }

    // simple non blocking
    @Override
    public IDialogChain playAnimationNonBlocking(String atid) {
        return new DialogChain(this,Actions.playAnimationNonBlocking( atid));
    }

    // double combo1of3: backwards + hold last frame

    @Override
    public IDialogChain playAnimationBackwardsHoldLastFrame(String atid) {
        return new DialogChain(this,Actions.playAnimationBackwardsHoldLastFrame( atid));    
    }

    // double combo2of3: backwards + nonblocking
    @Override
    public IDialogChain playAnimationBackwardsNonBlocking(String atid) {
        return new DialogChain(this,Actions.playAnimationBackwardsNonBlocking( atid));    
    };

    // double combo2of3: holdLastFrame + nonblocking
    @Override
    public IDialogChain playAnimationHoldLastFrameNonBlocking(String atid) {
        return new DialogChain(this,Actions.playAnimationHoldLastFrameNonBlocking( atid));
    }

    // ..and one method with the whole lot!
    @Override
    public IDialogChain playAnimationBackwardsHoldLastFrameNonBlocking(String atid) {
        return new DialogChain(this,Actions.playAnimationBackwardsHoldLastFrameNonBlocking( atid));
    }


    @Override
    public IDialogChain setVisible(short ocode, boolean isVisible) {
        return new DialogChain(this,Actions.setVisible( ocode, isVisible));
    }


    @Override
    public IDialogChain swapVisibility(short ocodeA, short ocodeB) {
        return new DialogChain(this,Actions.swapVisibility( ocodeA, ocodeB));
    }


    @Override
    public IDialogChain waitForFrame(short ocode, int frame) {
        return new DialogChain(this,Actions.waitForFrame( ocode, frame));
    }


    @Override
    public IDialogChain walkNeverSwitch(double x, double y) {
        return new DialogChain(this,Actions.walkNeverSwitch( x,y));
    }

    @Override
    public IDialogChain walkNeverSwitch(Point end) {
        return new DialogChain(this,Actions.walkNeverSwitch( end));
    }


 

    @Override
    public IDialogChain walkNeverSwitch(short ocode, double x, double y) {
        return new DialogChain(this,Actions.walkNeverSwitch( ocode, x, y));
    }

    @Override
    public IDialogChain walkNeverSwitch(short ocode, Point end) {
        return new DialogChain(this,Actions.walkNeverSwitch( ocode, end));
    }

    @Override
    public IDialogChain playTitleCard(String text, double durationInSecs) {
        return new DialogChain(this,Actions.playTitleCard( text, durationInSecs));
    }


    @Override
    public IDialogChain playAnimationNonBlockingHoldLastFrame(String atid) {
        return new DialogChain(this,Actions.playAnimationNonBlockingHoldLastFrame(atid));
    }

    @Override
    public IDialogChain moveCameraToNewXPosition(double x, double durationInSecs) {
        return new DialogChain(this, Actions.moveCameraToNewXPosition(x,durationInSecs));
    }

    @Override
    public IDialogChain moveCameraToNewXPositionNonBlocking(double x,  double durationInSecs) {
        return new DialogChain(this,Actions.moveCameraToNewXPositionNonBlocking(x,durationInSecs));
    }

    @Override
    public IDialogChain moveCameraToNewYPosition(double y, double durationInSecs) {
        return new DialogChain(this,Actions.moveCameraToNewYPosition( y, durationInSecs));
    }

    @Override
    public IDialogChain moveCameraToNewYPositionNonBlocking(double y, double durationInSecs) {
        return new DialogChain(this, Actions.moveCameraToNewYPositionNonBlocking(y, durationInSecs));
    }


    @Override
    public IDialogChain playSound(String stid) {
        return new DialogChain(this,Actions.playSound( stid));
    }

    @Override
    public IDialogChain playSoundNonBlocking(String stid) {
        return new DialogChain(this,Actions.playSoundNonBlocking( stid));
    }

    @Override
    public IDialogChain walkAndTalkNeverSwitch(short ocode, double x, double y, String speech) {
        return new DialogChain(this,Actions.walkAndTalkNeverSwitch( ocode, x, y, speech));
    }

    @Override
    public IDialogChain walkAndScaleNeverSwitch(short ocode, Point p,double startScale, double endScale) {
        return new DialogChain(this, Actions.walkAndScaleNeverSwitch(ocode,p,startScale,endScale));
    }
    
    @Override
    public IDialogChain walkNeverSwitchNonBlocking(short ocode, double x, double y) {
        return new DialogChain(this, Actions.walkNeverSwitchNonBlocking(ocode,x,y));
    }



}
