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



import java.util.Vector;


public class ImageCollection {
	private Vector<Image> theVector;

	ImageCollection() {
		theVector = new Vector<Image>();
	}

	public com.github.a2g.core.objectmodel.Image at(int index) {
		if (index < 0
				|| index >= theVector.size()) {
			return null;
		}
		return theVector.get(index);
	}

	public void add(Image image) {
		theVector.add(image);
	}

	public int getCount() {
		return theVector.size();
	}

}
