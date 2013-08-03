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

package com.github.a2g.core.gwt.factory;

import com.github.a2g.core.interfaces.SystemAnimationAPI;
import com.github.a2g.core.interfaces.SystemAnimationCallbackAPI;

public class GWTSystemAnimation
extends com.google.gwt.animation.client.Animation
implements SystemAnimationAPI
{
	boolean isLinear;
	SystemAnimationCallbackAPI callbacks;
	public GWTSystemAnimation(SystemAnimationCallbackAPI callbacks, boolean isLinear)
	{
		this.isLinear  = isLinear;
		this.callbacks = callbacks;
	}

	@Override
	protected double interpolate(double progress)
	{
		if(isLinear)
			return progress;
		else
			return (1 + Math.cos(Math.PI + progress * Math.PI)) / 2;
	}

	@Override
	protected void onUpdate(double progress) {
		callbacks.onUpdate(progress);

	}

	@Override
	protected void onComplete()
	{
		callbacks.onComplete();
	}

}