package com.github.a2g.core.objectmodel;


import com.github.a2g.core.authoredscene.InternalAPI;


public class CommandLine {

    private SentenceUnit defaultVerb;
    private SentenceUnit lockedInVerb;
    private SentenceUnit lockedInObject1;
    private SentenceUnit lockedInObject2;
    private SentenceUnit rolledOver;
    private String typeOfRollover;

    private boolean isMouseable; // whether rolling over the verbs will update the commandline
    private boolean isVisible;
    
    public CommandLine(InternalAPI api) {
        this.defaultVerb = new SentenceUnit(
                "Walk to AAA", "0", -1);
        this.lockedInVerb = new SentenceUnit(
                "", "", -1);
        this.lockedInObject1 = new SentenceUnit(
                "", "", -1);
        this.lockedInObject2 = new SentenceUnit(
                "", "", -1);
        this.rolledOver = new SentenceUnit("",
                "", -1);
        this.typeOfRollover = "";
        this.isMouseable = true;
        this.isVisible = true;
    }
    
    public void setMouseOver(String displayName, String textualId, int code) {
        this.rolledOver = new SentenceUnit(
                displayName, textualId, code); 
    }

    static boolean isAVerb(SentenceUnit snc) {
        return snc.getDisplayName().contains(
                "AAA")
                        || snc.getDisplayName().contains(
                                "BBB");
    }

    public Sentence getSentence() {
        Sentence text = new Sentence();

        // choose Verb: one or two object form	
        if (!isAVerb(this.rolledOver)) {
            if (this.lockedInVerb.getLength()
                    > 0) {
                // if contains 2 forms, then refine the appearance
                // bool isFirstObjectLockedIn = this.lockedInObject1.length();
                // text.SetVerb(isFirstObjectLockedIn? this.lockedInVerb.mid(i+1) : this.lockedInVerb.left(i));
                // if its a 2-form verb, then show extended form no matter what
                text.setVerb(
                        this.lockedInVerb.getDisplayNameAfterDivider());
                
                if (this.lockedInVerb.getDisplayName().contains(
                        "BBB")) {
                    if (this.lockedInObject2.getDisplayName().isEmpty()) {
                        if (this.lockedInObject1.getDisplayName().isEmpty()) {
                            this.typeOfRollover = "A";
                            text.setAAA(
                                    this.rolledOver);
                            // text.SetBBB("");
                        } else {
                            this.typeOfRollover = "B";
                            text.setAAA(
                                    this.lockedInObject1);
                            text.setBBB(
                                    this.rolledOver);
                        }
                    } else {
                        text.setAAA(
                                this.lockedInObject1);
                        text.setBBB(
                                this.lockedInObject2);
                    }
                } else {
                    if (this.lockedInObject1.getLength()
                            > 0) {
                        text.setAAA(
                                this.lockedInObject1);
                    } else {
                        this.typeOfRollover = "A";
                        text.setAAA(
                                this.rolledOver);
                    }
                }
            } else if (this.lockedInObject1.getLength()
                    > 0) {
                this.typeOfRollover = "";
                text.setVerb(this.defaultVerb);
                text.setAAA(
                        this.lockedInObject1);
            } else {
                this.typeOfRollover = "A";
                text.setVerb(this.defaultVerb);
                text.setAAA(this.rolledOver);
            }
        } else {
            this.typeOfRollover = "V";

            boolean isFirstObjectLockedIn = this.lockedInObject1.getLength()
                    > 0;
            boolean isSecondObjectLockedIn = this.lockedInObject2.getLength()
                    > 0;

            // if obj1, set obj1
            if (isFirstObjectLockedIn) {
                text.setVerb(
                        this.rolledOver.getDisplayNameAfterDivider());
                text.setAAA(
                        this.lockedInObject1);
                if (isSecondObjectLockedIn) {
                    text.setBBB(
                            this.lockedInObject2);
                }   
            } else {
                text.setVerb(
                        this.rolledOver.getDisplayNameBeforeDivider());
            }
        }

        // text += "Verb "+ this.lockedInVerb + "obj1 " + this.lockedInObject1 + "obj2 " + this.lockedInObject2;

        return text;
    }
    
    public void clear() {
        this.lockedInObject1 = new SentenceUnit(
                "", "", -1);
        this.lockedInObject2 = new SentenceUnit(
                "", "", -1);
        this.lockedInVerb = new SentenceUnit(
                "", "", -1);
        this.rolledOver = new SentenceUnit("",
                "", -1);
        this.typeOfRollover = "";
    }

    public void doNextBestThingToExecute() {
        if (this.typeOfRollover == "A") {
            this.lockedInObject1 = this.rolledOver;
            this.rolledOver = new SentenceUnit(
                    "", "", -1);
            this.typeOfRollover = "";
        } else if (this.typeOfRollover == "B") {
            this.lockedInObject2 = this.rolledOver;
            this.rolledOver = new SentenceUnit(
                    "", "", -1);
            this.typeOfRollover = "";
        } else if (this.typeOfRollover == "V") {
            this.lockedInVerb = this.rolledOver;
            this.rolledOver = new SentenceUnit(
                    "", "", -1);
            this.typeOfRollover = "";
        }
    }

    public boolean isOkToExecute() {
        if (!this.isMouseable) { 
            return false;
        }

        String command = getSentence().getDisplayName();

        return !command.contains("AAA")
                && !command.contains("BBB");
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public int getVerbAsVerbEnumeration() {
        int currentVerb = getSentence().getVerbAsVerbEnumeration();

        return currentVerb;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setMouseable(boolean mouseable) {
        this.isMouseable = mouseable;
    }

}
