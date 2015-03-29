// Google wrote the source in this file first
// then Anthony Cassidy modified it
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
/*
 * Copyright 2011 Google Inc.
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
package com.github.a2g.core.platforms.java.animation;

/**
 * Base class for animation implementations.
 */
abstract class AnimationSchedulerImpl extends AnimationScheduler {

	/**
	 * The singleton instance of animation scheduler.
	 */
	static final AnimationScheduler INSTANCE;

	static {
		//AnimationScheduler impl = GWT.create(AnimationScheduler.class);
		AnimationScheduler impl = new AnimationSchedulerImplTimer();

		/*
		 * If the implementation isn't natively supported, revert back to the timer
		 * based implementation.
		 * 
		 * If impl==null (such as with GWTMockUitlities.disarm()), use null. We
		 * don't want to create a new AnimationSchedulerImplTimer in this case.
		 */
		if (impl instanceof AnimationSchedulerImpl) {
			if (!((AnimationSchedulerImpl) impl).isNativelySupported()) {
				impl = new AnimationSchedulerImplTimer();
			}
		}

		INSTANCE = impl;
	}

	/**
	 * Check if the implementation is natively supported.
	 * 
	 * @return true if natively supported, false if not
	 */
	protected abstract boolean isNativelySupported();
}