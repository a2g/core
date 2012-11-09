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


public class VerbCollection {
    private static final List<Verb> VERBS = Arrays.asList(
            new Verb("Walk", "Walk to AAA"),
            new Verb("Talk", "Talk to AAA"),
            new Verb("Examine", "Examine AAA"),
            new Verb("Grab", "Grab AAA"),
            new Verb("Cut",
            "Cut AAA|Cut AAA with BBB"),
            new Verb("Swing", "Swing AAA"),
            new Verb("Give",
            "Give AAA|Give AAA to BBB"),
            new Verb("Use",
            "Use AAA|Use AAA with BBB"),
            new Verb("Push", "Push AAA"),
            new Verb("Pull", "Pull AAA"),
            new Verb("Throw",
            "Throw AAA|Throw AAA at BBB"),
            new Verb("Eat", "Eat AAA"));
    VerbCollection() 
    {
    }
	 
    public Verb get(int i) {
        return VERBS.get(i);
    }
}
