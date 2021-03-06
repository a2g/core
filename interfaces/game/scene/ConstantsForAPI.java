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

package com.github.a2g.core.interfaces.game.scene;

 

public interface ConstantsForAPI {
	public static final int MAX_OBJS = 32; // if you want a large range of
	// consecutive odd numbers that
	// produce unique products, then the
	// lower bound of that range odd
	// number needs to be sufficiently
	// high
	public static final int STARTING_ODD = 1787;

	public static final int VERB_MULTIPLIER = (MAX_OBJS * 2 + STARTING_ODD)
			* (MAX_OBJS * 2 + STARTING_ODD);

	public enum Verb
	{
		Walk(0 * VERB_MULTIPLIER),
		Talk ( 1 * VERB_MULTIPLIER),
		Examine ( 2 * VERB_MULTIPLIER),
		Grab ( 3 * VERB_MULTIPLIER),
		Cut ( 4 * VERB_MULTIPLIER),
		Swing ( 5 * VERB_MULTIPLIER),
		TurnOn ( 6 * VERB_MULTIPLIER),
		Use ( 7 * VERB_MULTIPLIER),
		Push ( 8 * VERB_MULTIPLIER),
		Pull ( 9 * VERB_MULTIPLIER),
		Throw ( 10 * VERB_MULTIPLIER),
		Eat ( 11 * VERB_MULTIPLIER),
		;
		public int code;        
		Verb(int code) {
			this.code=code;        
		}
	
	    public static Verb getEnum(int value)
	    {                               
	        switch(value)               
	        {                           
	        case 0 * VERB_MULTIPLIER: return Walk;
	        case ( 1 * VERB_MULTIPLIER): return Talk;
	        case ( 2 * VERB_MULTIPLIER): return Examine;
	        case ( 3 * VERB_MULTIPLIER): return Grab;
	        case ( 4 * VERB_MULTIPLIER): return Cut;
	        case ( 5 * VERB_MULTIPLIER): return Swing;
	        case ( 6 * VERB_MULTIPLIER): return TurnOn;
	        case ( 7 * VERB_MULTIPLIER): return Use;
	        case ( 8 * VERB_MULTIPLIER): return Push;
	        case ( 9 * VERB_MULTIPLIER): return Pull;
	        case ( 10 * VERB_MULTIPLIER): return Throw;
	        case ( 11 * VERB_MULTIPLIER): return Eat;
	        };
	        return null;
	    }
	}

	public static final int WALK = 0 * VERB_MULTIPLIER;
	public static final int TALK = 1 * VERB_MULTIPLIER;
	public static final int EXAMINE = 2 * VERB_MULTIPLIER;
	public static final int GRAB = 3 * VERB_MULTIPLIER;
	public static final int CUT = 4 * VERB_MULTIPLIER;
	public static final int SWING = 5 * VERB_MULTIPLIER;
	public static final int TURN_ON = 6 * VERB_MULTIPLIER;
	public static final int USE = 7 * VERB_MULTIPLIER;
	public static final int PUSH = 8 * VERB_MULTIPLIER;
	public static final int PULL = 9 * VERB_MULTIPLIER;
	public static final int THROW = 10 * VERB_MULTIPLIER;
	public static final int EAT = 11 * VERB_MULTIPLIER;
	public static final int SLEEP = 12 * VERB_MULTIPLIER;
	public static final int SWITCH = 13*VERB_MULTIPLIER ;
	public static final int DIALOG = 14*VERB_MULTIPLIER ;

	/**
	 * this should be used instead of the magic number -1
	 * when one wants to specify aligning to the last frame
	 * in any of the .Align methods of BaseAction.
	 * 
	 * -1 should never be used as a magic number
	 */
	public static final int ALIGN_TO_LAST = -1;
	/**
	 * when processing dialog trees, the branch
	 * given the value of -1 is special
	 * in that if -1 is the branch id for
	 * a line of dialog, then after that line
	 * of dialog is spoken, it is NOT removed
	 * from the available options.
	 * 
	 * -1 should never be used as a magic number
	 */
	public static final int DIALOG_HANDLER_EXIT = -1;
	public static final int DIALOG_HANDLER_ROOT = 0;

	public static enum WalkDirection {
		North(1), East(2), South(3), West(4);
		WalkDirection(int i)
		{
			m_val = i;
		}
		public int toInt() {
			return m_val;
		}
		private int m_val;
	}
}
