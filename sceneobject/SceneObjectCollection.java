/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.sceneobject;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;


public class SceneObjectCollection {
    private Map<String, SceneObject> theMap;

    public SceneObjectCollection() {
        theMap = new TreeMap<String, SceneObject>();
    }

    public ArrayList<SceneObject> getSortedList() {
        ArrayList<SceneObject> list = new ArrayList<SceneObject>();

        Iterator<SceneObject> it = theMap.values().iterator();

        while (it.hasNext()) {
            list.add(it.next());
        }

        Collections.sort(list,
                new Comparator<SceneObject>() {
            @Override
            public int compare(SceneObject o1, SceneObject o2) {
                return o1.getCodePrefix()
                        - o2.getCodePrefix();
            }
        });

        return list;

    }

    public SceneObject at(String textualId) {
        try {
            return theMap.get(textualId);
        } catch (Exception e) {}
        return null;
    }

    public void add(SceneObject sceneObject) {
        theMap.put(sceneObject.getTextualId(),
                sceneObject);
    }

    public SceneObject at(int index) throws NoSuchElementException {
        SceneObject sceneObject = null;
        Iterator<SceneObject> it = theMap.values().iterator();
        int i = 0;

        while (i <= index) {
            sceneObject = it.next();
            i++;
        }

        return sceneObject;
    }

    public int count() {
        return theMap.size();
    }

}
