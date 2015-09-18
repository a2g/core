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

import com.github.a2g.core.interfaces.ConstantsForAPI.Special;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromWalkPerformer;
import com.github.a2g.core.interfaces.performer.IWalkPerformer;
import com.github.a2g.core.primitive.PointF;


public class WalkPerformer implements IWalkPerformer
{	
	private IScenePresenterFromWalkPerformer scene;
	private short ocode;
	


	public WalkPerformer(short ocode) {
		this.ocode = ocode;
	}
	
	@Override
	public void runForWalk( PointF startPt, PointF endPt) 
	{
		assert(scene!=null);
		String otid = scene.getOtidByCode(ocode);
		double startX = startPt.getX();
		double startY = startPt.getY();

		double diffX = startPt.getX() - endPt.getX();
		double diffY = startPt.getY() - endPt.getY();

		// anim
		String anim = "";
		int width = scene.getSceneGuiWidth();
		int height = scene.getSceneGuiHeight();

		if ((diffX * width) * (diffX * width) > (diffY * height)
				* (diffY * height)) {
			if (endPt.getX() < startX) {
				anim = scene.getSpecialAnimationByOtid(otid, Special.West);
			} else {
				anim = scene.getSpecialAnimationByOtid(otid, Special.East);
			}
		} else {
			if (endPt.getY() < startY) {
				anim = scene
						.getSpecialAnimationByOtid(otid, Special.North);
			} else {
				anim = scene
						.getSpecialAnimationByOtid(otid, Special.South);
			}
		}

		// we've set it up now, pass to MoveWhilstAnimatingAction to execute
		scene.setCurrentAnimationByAtid(anim);

	}

	@Override
	public void setSceneForWalk(IScenePresenterFromWalkPerformer scene) {
		this.scene = scene;
	}

}
