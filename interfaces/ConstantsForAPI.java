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

package com.github.a2g.core.interfaces;

public interface ConstantsForAPI {
	public static final int MAX_OBJS = 32; // if you want a large range of consecutive odd numbers that produce unique products, then the lower bound of that range odd number needs to be sufficiently high
	public static final int STARTING_ODD = 1787;

	public static final int VERB_MULTIPLIER = (MAX_OBJS
			* 2
			+ STARTING_ODD)
			* (MAX_OBJS * 2
					+ STARTING_ODD);

	public static final int WALK = 0
			* VERB_MULTIPLIER;
	public static final int TALK = 1
			* VERB_MULTIPLIER;
	public static final int EXAMINE = 2
			* VERB_MULTIPLIER;
	public static final int GRAB = 3
			* VERB_MULTIPLIER;
	public static final int CUT = 4
			* VERB_MULTIPLIER;
	public static final int SWING = 5
			* VERB_MULTIPLIER;
	public static final int GIVE = 6
			* VERB_MULTIPLIER;
	public static final int USE = 7
			* VERB_MULTIPLIER;
	public static final int PUSH = 8
			* VERB_MULTIPLIER;
	public static final int PULL = 9
			* VERB_MULTIPLIER;
	public static final int THROW = 10
			* VERB_MULTIPLIER;
	public static final String INITIAL = "INITIAL";

	public static enum Special {
		North, East, South, West, Talking
	}
}
