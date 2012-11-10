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


import java.util.Arrays;
import java.util.List;

import com.github.a2g.core.primitive.CodesForVerbs;


public class VerbCollection {
    private static final List<Verb> VERBS = Arrays.asList(
            new Verb("Walk", "Walk to AAA", CodesForVerbs.getCodeForVerb(0)),
            new Verb("Talk", "Talk to AAA", CodesForVerbs.getCodeForVerb(1)),
            new Verb("Examine", "Examine AAA", CodesForVerbs.getCodeForVerb(2)),
            new Verb("Grab", "Grab AAA", CodesForVerbs.getCodeForVerb(3)),
            new Verb("Cut",
            "Cut AAA|Cut AAA with BBB", CodesForVerbs.getCodeForVerb(4)),
            new Verb("Swing", "Swing AAA", CodesForVerbs.getCodeForVerb(5)),
            new Verb("Give",
            "Give AAA|Give AAA to BBB", CodesForVerbs.getCodeForVerb(6)),
            new Verb("Use",
            "Use AAA|Use AAA with BBB", CodesForVerbs.getCodeForVerb(7)),
            new Verb("Push", "Push AAA", CodesForVerbs.getCodeForVerb(8)),
            new Verb("Pull", "Pull AAA", CodesForVerbs.getCodeForVerb(9)),
            new Verb("Throw",
            "Throw AAA|Throw AAA at BBB", CodesForVerbs.getCodeForVerb(10)),
            new Verb("Eat", "Eat AAA", CodesForVerbs.getCodeForVerb(11)));
    VerbCollection() 
    {
    }
	 
    public Verb get(int i) {
        return VERBS.get(i);
    }
}
