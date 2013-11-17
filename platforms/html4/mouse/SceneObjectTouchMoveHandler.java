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

package com.github.a2g.core.platforms.html4.mouse;


import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.github.a2g.core.interfaces.InternalAPI;

class CustomAnimation extends Animation
{
	InternalAPI api;
	double startCameraX;
	int touchX;
	int prevX;

	public CustomAnimation(InternalAPI api, int touchX, int prevX)
	{
		this.api=api;
		this.touchX = touchX;
		this.prevX = prevX;
		startCameraX = api.getSceneGui().getCameraX();
	}
	@Override
	protected void onComplete() {
		super.onComplete();
		api.getSceneGui().setCameraX(startCameraX+ getDistanceToScrollX());
	}

	@Override
	protected void onStart()
	{
		super.onStart();
	}

	@Override
	protected void onUpdate(double progress) {
		double x = startCameraX
				+ progress
				* getDistanceToScrollX();

		api.getSceneGui().setCameraX(x);
	}

	double getDistanceToScrollX()
	{
		if(touchX > prevX)
			return -.2;
		else
			return .2;
	}
}

public class SceneObjectTouchMoveHandler
implements TouchMoveHandler, TouchStartHandler {
	private final InternalAPI api;
	private int prevX;

	public SceneObjectTouchMoveHandler( InternalAPI api) {
		this.api = api;

	}

	@Override
	public void onTouchMove(TouchMoveEvent event)
	{
		event.preventDefault();
		if (event.getTouches().length() > 0)
		{
			Touch touch = event.getTouches().get(0);
			int touchX = touch.getScreenX();
			Animation animation = new CustomAnimation(api, touchX, prevX);
			prevX = touchX;
			animation.run(2000);
		}

		event.preventDefault();
	}

	@Override
	public void onTouchStart(TouchStartEvent event)
	{
		if (event.getTouches().length() > 0)
		{
			Touch touch = event.getTouches().get(0);
			prevX = touch.getScreenX();
		}
	}

}