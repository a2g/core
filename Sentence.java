/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import com.github.a2g.core.authoredroom.RoomBase;


public class Sentence {

    private String string;
    private int verbAsNumber;
    private SentenceUnit AAA;
    private SentenceUnit BBB;

    public Sentence() {
        this.string = "";
        this.verbAsNumber = 0; 
        this.AAA = new SentenceUnit("", "", 1);
        this.BBB = new SentenceUnit("", "", 1);
    }

    void setVerb(SentenceUnit snc) {
        this.string = snc.getDisplayName(); 
        int i = Integer.parseInt(snc.getTextualId());

        this.verbAsNumber = i;
    }

    void setAAA(SentenceUnit a) {
        this.string = this.string.replace("AAA", a.getDisplayName()); 
        this.AAA = a;
    }

    void setBBB(SentenceUnit b) {
        this.string = this.string.replace("BBB", b.getDisplayName()); 
        this.BBB = b;
    }

    final int getVerbAsVerbEnumeration() {
        return this.verbAsNumber;
    }

    final String getDisplayName() {
        return this.string;
    }

    final SentenceUnit getAAA() {
        return this.AAA;
    }

    final SentenceUnit getBBB() {
        return this.BBB;
    }

    final int getVerbAsCode() {
        return this.verbAsNumber * RoomBase.VERB_MULTIPLIER;
    }

    boolean isLookingForSecondObject() {
        boolean isLookingForSecondObject = this.AAA.getTextualId().length() > 0;

        return isLookingForSecondObject;
    }

}


;
