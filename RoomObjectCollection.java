/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;


public class RoomObjectCollection {
    private Map<String, RoomObject> theMap;

    public RoomObjectCollection() {
        theMap = new TreeMap<String, RoomObject>();
    }

    public ArrayList<RoomObject> getSortedList() {
        ArrayList<RoomObject> list = new ArrayList<RoomObject>();

        Iterator<RoomObject> it = theMap.values().iterator();

        while (it.hasNext()) {
            list.add(it.next());
        }

        Collections.sort(list,
                new Comparator<RoomObject>() {
            @Override
            public int compare(RoomObject o1, RoomObject o2) {
                return o1.getNumberPrefix()
                        - o2.getNumberPrefix();
            }
        });

        return list;

    }

    public RoomObject at(String keyword) {
        try {
            return theMap.get(keyword);
        } catch (Exception e) {}
        return null;
    }

    public void add(RoomObject roomObject) {
        theMap.put(roomObject.keyword(),
                roomObject);
    }

    public RoomObject at(int index) throws NoSuchElementException {
        RoomObject roomObject = null;
        Iterator<RoomObject> it = theMap.values().iterator();
        int i = 0;

        while (i <= index) {
            roomObject = it.next();
            i++;
        }

        return roomObject;
    }

    public int count() {
        return theMap.size();
    }

}
