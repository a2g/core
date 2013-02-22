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


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;


public class SceneObjectCollection {
	private Map<String, SceneObject> theMap;

	public SceneObjectCollection() {
		theMap = new TreeMap<String, SceneObject>();
	}

	public ArrayList<SceneObject> getSortedList() {
		ArrayList<SceneObject> list = new ArrayList<SceneObject>();

		Iterator<SceneObject> it = theMap.values().iterator();

		while (it.hasNext()) {
			list.add(it.next());
		}

		Collections.sort(list,
				new Comparator<SceneObject>() {
			@Override
			public int compare(SceneObject o1, SceneObject o2) {
				return o1.getNumberPrefix()
						- o2.getNumberPrefix();
			}
		});

		return list;

	}

	public SceneObject at(String textualId) {
		try {
			return theMap.get(textualId);
		} catch (Exception e) {}
		return null;
	}

	public void add(SceneObject sceneObject) {
		theMap.put(sceneObject.getTextualId(),
				sceneObject);
	}

	public SceneObject at(int index) throws NoSuchElementException {
		if(index>=theMap.size())
			throw new NoSuchElementException();
		SceneObject sceneObject = null;
		Iterator<SceneObject> it = theMap.values().iterator();
		int i = 0;

		while (i <= index) {
			sceneObject = it.next();
			i++;
		}

		return sceneObject;
	}

	public int count() {
		return theMap.size();
	}

}
