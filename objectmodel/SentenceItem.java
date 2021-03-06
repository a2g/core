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

import com.github.a2g.core.primitive.CodesForVerbs;
import com.github.a2g.core.primitive.IsInventory;

public class SentenceItem {

	private String displayName;
	private String textualId;
	private int code;

	public SentenceItem(String displayName, String textualId, int code) {
		this.displayName = displayName;
		this.textualId = textualId;
		this.code = code;
	}

	public SentenceItem() {
		this.displayName = "";
		this.textualId = "";
		this.code = 1;
	}

	public int getLength() {
		return this.displayName.length();
	}

	public String getDisplayNameAfterDivider() {
		int i = this.displayName.lastIndexOf("|");

		if (i != -1) {
			return this.displayName.substring(i + 1);
		}

		return this.displayName;

	}

	public String getDisplayNameBeforeDivider() {
		int i = this.displayName.lastIndexOf("|");

		if (i != -1) {
			return this.displayName.substring(0, i);
		}

		return this.displayName;
	}

	public final String getDisplayName() {
		return this.displayName;
	}

	public final String getTextualId() {
		return this.textualId;
	}

	public final int getCode() {
		return this.code;
	}

	public boolean isObjectOrInv() {
		boolean isVerb = CodesForVerbs.isAVerb(code);

		if (code > 1 && !isVerb)
			return true;
		return false;
	}

	public boolean isEmpty() {
		boolean isEmpty = code == 1;
		return isEmpty;
	}

	public boolean isInventory() {
		return IsInventory.isInventory(this.getCode());
	}

}
