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
import java.util.NoSuchElementException;
import java.util.TreeMap;


public class InventoryItemCollection {
	private Map<String, InventoryItem> theMap;

	public InventoryItemCollection() {
		theMap = new TreeMap<String, InventoryItem>();
	}

	public InventoryItem getByItid(String itid) {
		try {
			return theMap.get(itid);
		} catch (Exception e) {}
		return null;
	}

	public void add(InventoryItem item) {
		theMap.put(item.getItid(), item);
	}

	public InventoryItem getByIndex(int index) throws NoSuchElementException {
		InventoryItem item = null;
		Iterator<InventoryItem> iter = theMap.values().iterator();
		int i = 0;

		while (i <= index) {
			item = iter.next();
			i++;
		}

		return item;
	}

	public int getCount() {
		return theMap.size();
	}

}
