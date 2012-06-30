/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.authoredroom;


import com.github.a2g.core.SentenceUnit;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.BaseDialogTreeAction;
import com.github.a2g.core.authoredroom.OnFillLoadListAPIImpl.LoadKickStarter;

public interface ConstantsForAPI {
	 public static final int MAX_OBJS = 32; // if you want a large range of consecutive odd numbers that produce unique products, then the lower bound of that range odd number needs to be sufficiently high
	    public static final int STARTING_ODD = 1787;

	    public static final int VERB_MULTIPLIER = (MAX_OBJS
	            * 2
	                    + STARTING_ODD)
	                            * (MAX_OBJS * 2
	                                    + STARTING_ODD);

	    public static final int WALK = 0
	            * VERB_MULTIPLIER;
	    public static final int TALK = 1
	            * VERB_MULTIPLIER;
	    public static final int EXAMINE = 2
	            * VERB_MULTIPLIER;
	    public static final int GRAB = 3
	            * VERB_MULTIPLIER;
	    public static final int CUT = 4
	            * VERB_MULTIPLIER;
	    public static final int SWING = 5
	            * VERB_MULTIPLIER;
	    public static final int GIVE = 6
	            * VERB_MULTIPLIER;
	    public static final int USE = 7
	            * VERB_MULTIPLIER;
	    public static final int PUSH = 8
	            * VERB_MULTIPLIER;
	    public static final int PULL = 9
	            * VERB_MULTIPLIER;
	    public static final int THROW = 10
	            * VERB_MULTIPLIER;
	    public static final String INITIAL = "INITIAL";

	    public static enum Special {
	        North, East, South, West, Talking
	    }
  }
