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
import java.util.List;

import com.github.a2g.core.primitive.CodesForVerbs;


public class VerbCollection {
	private List<Verb> verbs;
	//VerbCollectionCallbackAPI api;
	VerbCollection(/*VerbCollectionCallbackAPI api*/)
	{
		//this.api = api;
		verbs = new ArrayList<Verb>();

		verbs.add(new Verb("Walk", "Walk to AAA", CodesForVerbs.getCodeForVerb(0)));
		verbs.add(new Verb("Talk", "Talk to AAA", CodesForVerbs.getCodeForVerb(1)));
		verbs.add(new Verb("Examine", "Examine AAA", CodesForVerbs.getCodeForVerb(2)));
		verbs.add(new Verb("Grab", "Grab AAA", CodesForVerbs.getCodeForVerb(3)));
		verbs.add(new Verb("Cut", "Cut AAA|Cut AAA with BBB", CodesForVerbs.getCodeForVerb(4)));
		verbs.add(new Verb("Swing", "Swing AAA", CodesForVerbs.getCodeForVerb(5)));
		verbs.add(new Verb("Give","Give AAA|Give AAA to BBB", CodesForVerbs.getCodeForVerb(6)));
		verbs.add(new Verb("Use","Use AAA|Use AAA with BBB", CodesForVerbs.getCodeForVerb(7)));
		verbs.add(new Verb("Push", "Push AAA", CodesForVerbs.getCodeForVerb(8)));
		verbs.add(new Verb("Pull", "Pull AAA", CodesForVerbs.getCodeForVerb(9)));
		verbs.add(new Verb("Throw", "Throw AAA|Throw AAA at BBB", CodesForVerbs.getCodeForVerb(10)));
		verbs.add(new Verb("Eat", "Eat AAA", CodesForVerbs.getCodeForVerb(11)));
	}

	public Verb get(int i) {
		return verbs.get(i);
	}

	public void removeByCode(int verbCode)
	{
		for(int i = 0 ; i<verbs.size();i++)
		{
			if(verbs.get(i).getCode() == verbCode)
			{
				verbs.remove(i);
				//api.update();
				break;
			}
		}
	}

	public int getNumberOfRows() {
		Double d= Math.sqrt(verbs.size()*1.0)+.9;
		int numRows = d.intValue();
		return numRows;
	}

	public int getNumberOfColumns() {
		int numRows = getNumberOfRows();
		Double e = (verbs.size()/(numRows*1.0))+.90;

		int numCols = e.intValue();
		return numCols;
	}

	public int size()
	{
		int size = verbs.size();
		return size;
	}
}
