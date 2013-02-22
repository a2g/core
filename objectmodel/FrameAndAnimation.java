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

package com.github.a2g.core.objectmodel;

import com.github.a2g.core.interfaces.ConstantsForAPI;





public class FrameAndAnimation {
	private int currentFrame;
	private String currentAnimationTextualId;
	public FrameAndAnimation(String owner) {
		this.currentFrame = 0;
		this.currentAnimationTextualId = ConstantsForAPI.INITIAL;
	}

	public void setCurrentFrame(int frame) {
		this.currentFrame = frame;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentAnimationTextualId(String animationTextualId) {
		this.currentAnimationTextualId = animationTextualId;
	}

	public String getCurrentAnimationTextualId() {
		return this.currentAnimationTextualId;
	}

}


;
