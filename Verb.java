/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


public class Verb {
    private final String sentenceText;
    private final String buttonText;

    public Verb(String buttonText, String sentenceText) {
        this.sentenceText = sentenceText;
        this.buttonText = buttonText;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getSentenceText() {
        return sentenceText;
    }
}
