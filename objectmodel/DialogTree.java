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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DialogTree {
	private List<Integer> subBranches;
	private List<String> linesOfDialog;
	private Map<Integer,Boolean> mapOfIsAddableToSaidSpeechIndexedByBranch;


	DialogTree() {
		subBranches = new LinkedList<Integer>();
		linesOfDialog = new LinkedList<String>();
		mapOfIsAddableToSaidSpeechIndexedByBranch = new TreeMap<Integer,Boolean>();
		
	}
	
	String getDialogForId(int id)
	{
		for(int i=0;i<subBranches.size();i++)
		{
			if(subBranches.get(i)==id)
			{
				return linesOfDialog.get(i);
			}
		}
		return "";
	}

	void clear() {
		subBranches.clear();
		linesOfDialog.clear();
		mapOfIsAddableToSaidSpeechIndexedByBranch.clear();
	}

	public void addSubBranch(int branchId, String text, boolean isAddableToSaidSpeech) {
		subBranches.add(branchId);
		linesOfDialog.add(text);
		mapOfIsAddableToSaidSpeechIndexedByBranch.put(branchId, isAddableToSaidSpeech);
	}

	public List<Integer> getSubBranchIds() {
		return subBranches;
	}

	public List<String> getLinesOfDialog() {
		return linesOfDialog;
	}
	
	public boolean isAddableToSaidList(int branchId) {
		if(this.mapOfIsAddableToSaidSpeechIndexedByBranch.containsKey(branchId))
			return mapOfIsAddableToSaidSpeechIndexedByBranch.get(branchId);
		return true;
	}
}
