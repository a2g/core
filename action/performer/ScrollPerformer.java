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

import com.github.a2g.core.interfaces.IScenePresenterFromScrollPerformer;
import com.github.a2g.core.primitive.PointF;


public class ScrollPerformer
{	
	private IScenePresenterFromScrollPerformer scene;
	double startCameraX;
	double endXMinusStartX;
	public ScrollPerformer() {}

	public void run(PointF start, PointF end) {
		startCameraX = scene.getCameraX();
		endXMinusStartX = end.getX() - start.getX(); 
	}
	
	public void onUpdate(double progress)
	{
		double x = startCameraX + progress * endXMinusStartX;

		scene.setCameraX(x);
	}

	public void onComplete()
	{
	 onUpdate(1.0);
	}
	
	public void setScene(IScenePresenterFromScrollPerformer scene) {
		this.scene = scene;
	}

}
