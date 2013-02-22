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


import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class AnimationCollection
{
	private Map<String, Animation> theMap;

	AnimationCollection() {
		theMap = new TreeMap<String, Animation>();
	}

	public Animation at(String textualId) {
		if(textualId==null)
			return null;
		String key = textualId.toUpperCase();

		if (theMap.containsKey(key)) {
			return theMap.get(key);
		}
		return null;
	}

	public void add(Animation animation) {
		theMap.put(
				animation.getTextualId().toUpperCase(),
				animation);
	}

	public Animation at(int index) {
		Animation anim = null;
		Iterator<Animation> it = theMap.values().iterator();

		for (int i = 0; i <= index; i++) {
			anim = it.next();
		}
		return anim;
	}

	public int getCount() {
		return theMap.size();
	}

}
