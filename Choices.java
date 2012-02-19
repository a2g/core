package com.github.a2g.core;


import java.util.LinkedList;
import java.util.List;


public class Choices {
    private List<Integer> places;
    private List<String> texts;
		
    Choices() {
        places = new LinkedList<Integer>();
        texts = new LinkedList<String>();
    }

    void clear() {
        places.clear();
        texts.clear();
    }
	
    public void addChoice(int place, String text) {
        places.add(place);
        texts.add(text);
    }
	
    public List<Integer> getPlaces() {
        return places;
    }
	
    public List<String> getTexts() {
        return texts;
    }
}
