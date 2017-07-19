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

package com.github.a2g.core.platforms.swing;

import com.github.a2g.core.interfaces.internal.IBaseActionFromSystemAnimation;
import com.github.a2g.core.interfaces.internal.ISystemAnimation;

public class SystemAnimationForSwing extends
com.github.a2g.core.platforms.swing.animation.Animation implements
ISystemAnimation {
	IBaseActionFromSystemAnimation callbacks;
	boolean isEaseToAndFrom;
	private boolean isCancelled;

	public SystemAnimationForSwing(IBaseActionFromSystemAnimation callbacks) {
		this.isEaseToAndFrom = false;
		this.callbacks = callbacks;
		this.isCancelled = false;
	}

	@Override
	protected double interpolate(double progress) {
		if (isEaseToAndFrom)
			return super.interpolate(progress);
		else
			return progress;

	}

	@Override
	protected void onUpdate(double progress) {
		// I'm not sure why, but this class:
		// com.github.a2g.core.platforms.java.animation.Animation
		// (that this inherits from, and was written by google)
		// does not check to see if an animation was cancelled before
		// it starts.
		if(!isCancelled)
		{
			callbacks.onUpdate(progress);
		}
	}

	@Override
	protected void onComplete() {
		callbacks.onComplete();
	}

	@Override
	public void setEaseToAndFrom(boolean isEaseToAndFrom) {
		this.isEaseToAndFrom = isEaseToAndFrom;

	}
	
	@Override
	public void cancel()
	{
		isCancelled = true;
		super.cancel();
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

}