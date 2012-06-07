/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class AnimationCollection {
    private Map<String, Animation> theMap;

    AnimationCollection() {
        theMap = new TreeMap<String, Animation>();
    }

    public Animation at(String textualId) {
    	if(textualId==null)
    		return null;
        String key = textualId.toUpperCase();

        if (theMap.containsKey(key)) {
            return theMap.get(key);
        }
        return null;
    }

    public void add(Animation animation) {
        theMap.put(
                animation.getTextualId().toUpperCase(),
                animation);
    }

    public Animation at(int index) {
        Animation anim = null;
        Iterator<Animation> it = theMap.values().iterator();

        for (int i = 0; i <= index; i++) {
            anim = it.next();
        }
        return anim;
    }

    public int getCount() {
        return theMap.size();
    }

}
