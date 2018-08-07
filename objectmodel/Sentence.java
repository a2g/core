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

import com.github.a2g.core.interfaces.game.scene.ConstantsForAPI;

public class Sentence {

	private String string;
	private int verbAsNumber;
	private SentenceItem AAA;
	private SentenceItem BBB;

	public Sentence() {
		this.string = "";
		this.verbAsNumber = 0;
		this.AAA = new SentenceItem();
		this.BBB = new SentenceItem();
	}

	void setVerb(SentenceItem snc, boolean useTwoObjectForm) {
		this.string = useTwoObjectForm ? snc.getDisplayNameAfterDivider() : snc
				.getDisplayNameBeforeDivider();
		int i = snc.getCode() / ConstantsForAPI.VERB_MULTIPLIER;
		;
		this.verbAsNumber = i;
	}

	void setAAA(SentenceItem a) {
		this.string = this.string.replace("AAA", a.getDisplayName());
		this.AAA = a;
	}

	void setBBB(SentenceItem b) {
		this.string = this.string.replace("BBB", b.getDisplayName());
		this.BBB = b;
	}

	final int getVerbAsVerbEnumeration() {
		return this.verbAsNumber;
	}

	final String getDisplayName() {
		return this.string;
	}

	final SentenceItem getAAA() {
		return this.AAA;
	}

	final SentenceItem getBBB() {
		return this.BBB;
	}

	final int getVerbAsCode() {
		return this.verbAsNumber * ConstantsForAPI.VERB_MULTIPLIER;
	}

	boolean isLookingForSecondObject() {
		boolean isLookingForSecondObject = this.AAA.getTextualId().length() > 0;

		return isLookingForSecondObject;
	}

};
