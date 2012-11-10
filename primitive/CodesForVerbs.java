package com.github.a2g.core.primitive;

import com.github.a2g.core.interfaces.ConstantsForAPI;

public class CodesForVerbs {
	public static int getCodeForVerb(int i)
	{
		return i * ConstantsForAPI.VERB_MULTIPLIER;
	}
	
	public static boolean isAVerb(int code)
	{
		int modulus = code%ConstantsForAPI.VERB_MULTIPLIER;
		return (modulus ==0);
	}
    
}
