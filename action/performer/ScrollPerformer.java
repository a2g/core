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

import com.github.a2g.core.interfaces.nongame.performer.IScrollPerformer;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromScrollPerformer;
import com.google.gwt.touch.client.Point;


public class ScrollPerformer implements IScrollPerformer
{
	private IScenePresenterFromScrollPerformer scene;
	double startCameraX;
	double endXMinusStartX;
	public ScrollPerformer() {}

	@Override
	public void runForScroll(Point start, Point end) {
		startCameraX = scene.getCameraX();
		endXMinusStartX = end.getX() - start.getX();
	}

	@Override
	public void onUpdateForScroll(double progress)
	{
		double x = startCameraX + progress * endXMinusStartX;

		scene.setCameraX(x);
	}

	@Override
	public void onCompleteForScroll()
	{
		onUpdateForScroll(1.0);
	}

	public void setScene(IScenePresenterFromScrollPerformer scene) {
		this.scene = scene;
	}

}
