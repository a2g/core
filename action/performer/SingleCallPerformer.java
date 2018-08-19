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

package com.github.a2g.core.action.performer;

import com.github.a2g.core.interfaces.game.scene.ConstantsForAPI.WalkDirection;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromSingleCallPerformer;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromSingleCallPerformer;


public class SingleCallPerformer
{	
	public enum Type 
	{
		SetBaseMiddleX
		, SetBaseMiddleY
		, AlignBaseMiddle
		, SetCurrentFrame
		, HideAll
		, SetCurrentAnimation
		, SetVisible
		, SetDisplayName
		, SetInitialAnimation
		, ShareWinning
		, SetValue
		, SetToInitialPosition
		, SetSceneTalker
		, SetCurrentAnimationAndFrame
		, Sleep
		, SetInventoryVisible
		, SwapVisibility
		, Switch
		, SetAnimationSpecial
		, SetAnimationAsSceneTalker
		, SetAnimationObjectInitial
		, SetSoundtrack
		, SetHeadRectangle
		, SetTitleCard
	}
	Type type;
	private double d;
	private boolean isTrue;
	private String atid;
	private short ocode;
	private short ocode2;
	private int icode;
	private String stringValue;
	private int intValue;
	private IScenePresenterFromSingleCallPerformer scene;
	private IInventoryPresenterFromSingleCallPerformer inventory;

	public SingleCallPerformer( Type type) {
		this.type = type;
	}
	
	public void setDouble(double d){ this.d =d ;}
	public void setOCode(short ocode){ this.ocode = ocode;}
	public void setOCode2(short ocode2){ this.ocode2 = ocode2;}
	public void setICode(int icode){ this.icode = icode;}
	public void setAtid(String atid){ this.atid = atid;}
	public void setString(String string){ this.stringValue = string;}
	public void setBoolean(boolean isTrue){ this.isTrue = isTrue;}
	public void setInt(int intValue){ this.intValue = intValue;}

	public SingleCallPerformer(short ocode) {
		this.ocode = ocode;
	}
	
	public int run( ) 
	{
		 if(type==Type.Sleep)
			 return intValue;
		 return 1;
	}
	public boolean onComplete()
	{
		String otid;
		String otidA;
		String otidB;
		String itid;
		switch(type)
		{
		case Switch:
			scene.switchToScene(stringValue, this.intValue);
			return true;
		case SetBaseMiddleX:
			otid = scene.getOtidByCode(ocode);
			scene.setBaseMiddleXByOtid(otid, this.d);
			return false;
		case SetBaseMiddleY:
			otid = scene.getOtidByCode(ocode);
			scene.setBaseMiddleYByOtid(otid, this.d);
			return false;
		case AlignBaseMiddle:
			scene.alignBaseMiddleOfOldFrameToFrameOfThisAnimationByAtid(atid, intValue);
			return false;
		case SetCurrentFrame:
			otid = scene.getOtidByCode(ocode);
			scene.setCurrentFrameByOtid(otid, intValue);
			return false;
		case  HideAll:
			int count = scene.getSceneObjectCount();
			for (int i = 0; i < count; i++) {
				otid = scene.getOtidByIndex(i);
				scene.setVisibleByOtid(otid, false);
			}
			return false;
		case SetCurrentAnimation:
			scene.setCurrentAnimationByAtid(atid);
			return false;
		case SetVisible:
			otid = scene.getOtidByCode(ocode);
			scene.setVisibleByOtid(otid, this.isTrue);
			return false;
		case SetDisplayName:
			otid = scene.getOtidByCode(ocode);
			scene.setDisplayNameByOtid(otid, this.stringValue);
			return false;
		case SetSoundtrack:
			scene.setSoundtrack(this.stringValue);
			return false;
		case SetInitialAnimation:
			scene.setInitialAnimationByAtid(atid);
			return false;
		case SetValue:
			scene.setValue(stringValue, intValue);
			return false;
		case SetTitleCard:
			scene.setTitleCard(stringValue);
			return false;
		case ShareWinning:
			scene.shareWinning(stringValue);
			return false;
		case SetToInitialPosition:
			otid = scene.getOtidByCode(ocode);
			scene.setXByOtid(otid, 0);
			scene.setYByOtid(otid, 0);
			return false;
		case SetSceneTalker:
			scene.setSceneTalkerByAtid(atid);
			return false;
		case SetCurrentAnimationAndFrame:
			scene.setCurrentAnimationAndFrame(atid, intValue);
			return false;
		case Sleep:
			return false;
		case SetInventoryVisible:
			itid = inventory.getItidByCode(icode);
			inventory.setVisibleByItid(itid, this.isTrue);
			return false;
		case SwapVisibility:
			otidA = scene.getOtidByCode(ocode);
			otidB = scene.getOtidByCode(ocode2);
			boolean newA = scene.getVisibleByOtid(otidB);
			boolean newB = scene.getVisibleByOtid(otidA);

			scene.setVisibleByOtid(otidA, newA);
			scene.setVisibleByOtid(otidB, newB);
			return false;
		
		case SetAnimationAsSceneTalker:
			scene.setSceneTalkerByAtid(atid);
			return false;
		case SetAnimationObjectInitial:
			scene.setAnimationAsObjectInitial(atid);
			return false;
		case SetHeadRectangle:
			otid = scene.getOtidByCode(ocode);
			scene.setHeadRectangleByIndex(otid, intValue);
			return false;
		case SetAnimationSpecial:
			if(intValue==WalkDirection.North.toInt())
			{
				scene.setAnimationAsObjectSpecial(atid, WalkDirection.North);
			}else if(intValue==WalkDirection.East.toInt())
				
			{
				scene.setAnimationAsObjectSpecial(atid, WalkDirection.East);
				
			}else if(intValue==WalkDirection.South.toInt())
			{
				scene.setAnimationAsObjectSpecial(atid, WalkDirection.South);
				
			}else if(intValue==WalkDirection.West.toInt())
			{
				scene.setAnimationAsObjectSpecial(atid, WalkDirection.West);
			}
			return false;
				
		 default:
			break;
		}
		return false;
	}
 
	public void setScene(IScenePresenterFromSingleCallPerformer scene) {
		this.scene = scene;
	}
	
	public void setInventory(IInventoryPresenterFromSingleCallPerformer inventory) {
		this.inventory = inventory;
	}

	public Type getType(){ return type;}
	
}
