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

package com.github.a2g.core.platforms.html4;

import com.github.a2g.core.interfaces.ISystemAnimation;
import com.github.a2g.core.interfaces.IBaseActionFromSystemAnimation;

public class SystemAnimationForHtml4 extends
com.google.gwt.animation.client.Animation implements ISystemAnimation {
	boolean isEaseToAndFrom;
	IBaseActionFromSystemAnimation callbacks;

	public SystemAnimationForHtml4(IBaseActionFromSystemAnimation callbacks) {
		this.isEaseToAndFrom = false;
		this.callbacks = callbacks;
	}

	@Override
	protected double interpolate(double progress) {
		if (isEaseToAndFrom)
			return (1 + Math.cos(Math.PI + progress * Math.PI)) / 2;
		else
			return progress;

	}

	@Override
	protected void onUpdate(double progress) {
		callbacks.onUpdate(progress);

	}

	@Override
	protected void onComplete() {
		callbacks.onComplete();
	}

	@Override
	public void setEaseToAndFrom(boolean isEaseToAndFrom) {
		this.isEaseToAndFrom = isEaseToAndFrom;

	}

}