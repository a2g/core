/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;


public class InventoryItemCollection {
    private Map<String, InventoryItem> theMap;

    public InventoryItemCollection() {
        theMap = new TreeMap<String, InventoryItem>();
    }

    public InventoryItem at(String keyword) {
        try {
            return theMap.get(keyword);
        } catch (Exception e) {}
        return null;
    }

    public void add(InventoryItem item) {
        theMap.put(item.getKeyword(), item);
    }

    public InventoryItem at(int index) throws NoSuchElementException {
        InventoryItem item = null;
        Iterator<InventoryItem> iter = theMap.values().iterator();
        int i = 0;

        while (i <= index) {
            item = iter.next();
            i++;
        }

        return item;
    }

    public int getCount() {
        return theMap.size();
    }

}
