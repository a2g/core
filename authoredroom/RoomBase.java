/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.authoredroom;


import java.util.ArrayList;

import com.github.a2g.core.Animation;
import com.github.a2g.core.InventoryItem;
import com.github.a2g.core.RoomObject;
import com.github.a2g.core.SentenceUnit;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.ICallbacksFromGameAction;
import com.github.a2g.core.action.NullParentAction;


public class RoomBase extends NullParentAction implements ICallbacksFromGameAction {
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

    private ArrayList<BaseAction> list;
    private ArrayList<BaseAction> parallelActionsToWaitFor;
    private int numberOfParallelActionsToWaitFor;

    public RoomBase() {
        list = new ArrayList<BaseAction>();
        parallelActionsToWaitFor = new ArrayList<BaseAction>();
        numberOfParallelActionsToWaitFor = 0;
    }

    public RoomObject getObject(int code) {
        RoomObject toReturn = getApi().getObject(
                code);

        return toReturn;
    }

    protected Animation getAnimation(int a) {
        Animation toReturn = getApi().getAnimation(
                a);

        return toReturn;
    }

    protected InventoryItem getInventoryItem(int i) {
        InventoryItem toReturn = getApi().getInventoryItem(
                i);

        return toReturn;
    }

    void executeParallelActions() {
        this.numberOfParallelActionsToWaitFor = this.parallelActionsToWaitFor.size();
        for (int i = 0; i
                < this.parallelActionsToWaitFor.size(); i++) {
            BaseAction a = this.parallelActionsToWaitFor.get(
                    i);

            a.setCallbacks(this);
            a.runGameAction();
        }
    }

    void processNextListOfParallelActions() {
        this.parallelActionsToWaitFor.clear();
        if (!this.list.isEmpty()) {
            // add next batch of contigous parallel actions
            while (!this.list.isEmpty()
                    && this.list.get(0).isParallel()) {
                BaseAction theAction = this.list.get(
                        0);

                this.list.remove(0);
                this.parallelActionsToWaitFor.add(
                        theAction);
            }

            // if there was no parallel actions then add the non parallel one 
            if (this.parallelActionsToWaitFor.isEmpty()) {
                BaseAction theAction = this.list.get(
                        0);

                this.list.remove(0);
                this.parallelActionsToWaitFor.add(
                        theAction);
            }

            // execute them	
            executeParallelActions();
        }
    }

    public int execute(BaseAction grandChildOfActionChain) {
        this.list = new ArrayList<BaseAction>();
        BaseAction a = grandChildOfActionChain;

        while (a.getParent() != null) {
            this.list.add(0, a);
            a = a.getParent();
        }

        processNextListOfParallelActions();

        return 0;
    }

    @Override
    public void onGameActionComplete(BaseAction a) {
        this.numberOfParallelActionsToWaitFor--;
        if (this.numberOfParallelActionsToWaitFor
                == 0) {
            processNextListOfParallelActions();
        }
    }

    public SentenceUnit codify(int a) {
        return new SentenceUnit("blah", "blah",
                a);
    }

    public boolean isTrue(String s) {
        return getApi().isTrue(s);
    }

    public int getValue(String s) {
        return getApi().getValue(s);
    }

    public void setValue(String s, int i) {
        getApi().setValue(s, i);
    }

    public boolean isLastRoom(String room) {
        String room1 = getApi().getLastRoom();
        String room2 = room.toString();

        return room1 == room2;
    }

    public String tr(String s) {
        return s;
    }
    
    public void setChoiceTalker(int objid) {
        getApi().getChoicesGui().setChoiceTalker(
                objid);
    }
}
