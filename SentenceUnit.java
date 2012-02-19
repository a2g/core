/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


public class SentenceUnit {

    private String displayName;
    private String keyword;
    private int number;

    public SentenceUnit(String displayName, String keyword, int number) {
        this.displayName = displayName;
        this.keyword = keyword;
        this.number = number;
    }

    public int getLength() {
        return this.displayName.length();
    }

    public SentenceUnit getDisplayNameAfterDivider() {
        int i = this.getDisplayName().lastIndexOf(
                "|");

        if (i != -1) {
            return new SentenceUnit(
                    this.displayName.substring(
                            i),
                            this.keyword,
                            this.number);
        }
        return  new SentenceUnit(
                this.displayName, this.keyword,
                this.number);
    	
    }

    public SentenceUnit getDisplayNameBeforeDivider() {
        int i = this.getDisplayName().lastIndexOf(
                "|");

        if (i != -1) {
            return new SentenceUnit(
                    this.displayName.substring(
                            0, i + 1),
                            this.keyword,
                            this.number);
        }
        return  new SentenceUnit(
                this.displayName, this.keyword,
                this.number);
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final String getKeyword() {
        return this.keyword;
    }

    public final int getNumber() {
        return this.number;
    }
}


;

