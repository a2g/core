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

import com.github.a2g.core.interfaces.IScenePresenterFromSingleCallPerformer;


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
		, SetAsInitialAnimation
		, ShareWinning
		, SetValue
		, SetToInitialPosition
		, SetSceneTalker
		, SetCurrentAnimationAndFrame
		, Sleep
	}
	Type type;
	private double d;
	private boolean isTrue;
	private String atid;
	private short ocode;
	private String stringValue;
	private int intValue;
	private IScenePresenterFromSingleCallPerformer scene;

	public SingleCallPerformer( Type type) {
		this.type = type;
	}
	
	public void setDouble(double d){ this.d =d ;}
	public void setOCode(short o){ this.ocode = o;}
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
		switch(type)
		{
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
			scene.setAsACurrentAnimationByAtid(atid);
			return false;
		case SetVisible:
			otid = scene.getOtidByCode(ocode);
			scene.setVisibleByOtid(otid, this.isTrue);
			return false;
		case SetDisplayName:
			otid = scene.getOtidByCode(ocode);
			scene.setDisplayNameByOtid(otid, this.stringValue);
			return false;
		case SetAsInitialAnimation:
			scene.setAsAnInitialAnimationByAtid(atid);
			return false;
		case SetValue:
			scene.setValue(stringValue, intValue);
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
		 default:
			break;
		}
		return false;
	}
 
	public void setScene(IScenePresenterFromSingleCallPerformer scene) {
		this.scene = scene;
	}

	public Type getType(){ return type;}
	
}
