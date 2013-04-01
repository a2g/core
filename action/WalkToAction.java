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

package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.objectmodel.Animation;
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.action.ChainedAction;

public class WalkToAction 
extends ChainedAction 
{
	private SceneObject obj;
	private double startX;
	private double startY;
	private double endX;
	private double endY;
	private Animation anim;
	private int framesInAnim;
	private int framesPlayedDuringWalk;
	private int delay;
	private boolean isParallel;

	public WalkToAction(BaseAction parent, short objId, double x, double y, int delay, boolean isParallel) {
		super(parent, parent.getApi());
		this.obj = getApi().getObject(objId);
		this.endX = x;
		this.endY = y;
		this.delay = delay;
		this.isParallel = isParallel;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runGameAction() {

		// distance and time calculations
		this.startX = this.obj.getBaseMiddleX();
		this.startY = this.obj.getBaseMiddleY();
		double diffX = this.startX - this.endX;
		System.out.println(" walkto " + startX + " " + endX);
		double diffY = this.startY - this.endY;
		double diffXSquared = diffX * diffX;
		double diffYSquared = diffY * diffY;
		double dist = Math.sqrt(
				diffXSquared + diffYSquared);

		this.framesPlayedDuringWalk = (int) (dist
				* 40);

		// anim
		String anim = "";
		int width = getApi().getSceneGui().getWidth();
		int height = getApi().getSceneGui().getHeight();

		if ((diffX * width) * (diffX * width)
				> (diffY * height)
				* (diffY * height)) {
			if (this.endX < this.startX) {
				anim = this.obj.getSpecialAnimation(
						com.github.a2g.core.interfaces.SceneAPI.Special.West);
			} else {
				anim = this.obj.getSpecialAnimation(
						com.github.a2g.core.interfaces.SceneAPI.Special.East);
			}
		} else {
			if (this.endY < this.startY) {
				anim = this.obj.getSpecialAnimation(
						com.github.a2g.core.interfaces.SceneAPI.Special.North);
			} else {
				anim = this.obj.getSpecialAnimation(
						com.github.a2g.core.interfaces.SceneAPI.Special.South);
			}
		}
		this.obj.setCurrentAnimation(anim);
		this.anim = this.obj.getAnimations().at(
				anim);
		if (this.anim != null) {
			this.framesInAnim = this.anim.getLength();
		} else {
			this.framesInAnim = 0;
		}
		this.run((int) (dist * (10+delay) * 1000.0));
		
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		double x = this.startX
				+ progress
				* (this.endX
						- this.startX);
		double y = this.startY
				+ progress
				* (this.endY
						- this.startY);

		this.obj.setBaseMiddleX(x);
		this.obj.setBaseMiddleY(y);
		int framesPlayedSoFar = (int) (this.framesPlayedDuringWalk
				* progress);
		int i = (this.framesInAnim != 0)
				? framesPlayedSoFar
						% this.framesInAnim
						: 0;

		this.obj.setCurrentFrame(i);
	}

	@Override // method in animation
	protected void onCompleteGameAction() {

		String anim = this.obj.getSpecialAnimation(
				com.github.a2g.core.interfaces.SceneAPI.Special.South);

		this.obj.setCurrentAnimation(anim);
		this.obj.setCurrentFrame(0);
	}

	@Override
	public boolean isParallel() {

		return isParallel;
	}

}
