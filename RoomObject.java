/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import java.util.Map;
import java.util.TreeMap;

import com.github.a2g.core.authoredroom.ColorEnum;
import com.github.a2g.core.authoredroom.Point;
import com.github.a2g.core.authoredroom.PointF;
import com.github.a2g.core.authoredroom.Rect;
import com.github.a2g.core.authoredroom.RoomBase;
import com.github.a2g.core.authoredroom.RoomBase.Special;


public class RoomObject {
    private static final int MAX_INT = 2147483647;
    private String homeAnimation;
    private Map<RoomBase.Special, String> mapOfSpecialAnimations;
    private final String keyword;
    private String displayName;
    private AnimationCollection animationCollection;
    private FrameAndAnimationKeyword fak;
    private com.github.a2g.core.Image currentImage;
    private boolean visible;
    private double width;
    private double height;
    private int top; // needed for scrolling images (ie canoe background)
    private int left;
    private int topOffset;
    private int leftOffset;
    private int numberPrefix;
    private int objectCode;
    private ColorEnum talkingColor;

    public RoomObject(String keyword, int width, int height) {
        this.currentImage = null;
        this.keyword = keyword;
        this.displayName = keyword;
        this.animationCollection = new AnimationCollection();
        this.fak = new FrameAndAnimationKeyword(
                this.keyword);
        this.visible = true;
        this.width = width;
        this.height = height;
        this.leftOffset = MAX_INT;
        this.topOffset = MAX_INT;
        this.mapOfSpecialAnimations = new TreeMap<Special, String>();
        this.numberPrefix = 0;
        this.homeAnimation = RoomBase.INITIAL;
       
        // talkingColro deliberately null, so the 
        // default color can be in one spot: the sayaction 
        this.talkingColor = null;
    }

    public void setNumberPrefix(int number) {
        this.numberPrefix = number;
    }

    public int getNumberPrefix() {
        return this.numberPrefix;
    }

    public String keyword() {
        return this.keyword;
    }

    public AnimationCollection animations() {
        return this.animationCollection;
    }

    public int getCurrentFrame() {
        int i = this.fak.getCurrentFrame();

        return i;
    }

    public void setCurrentFrame(int i) {
        this.fak.setCurrentFrame(i);
        updateImage();
    }

    public void incrementFrameWithWraparound() {
        if (animations().getCount() == 0) {
            // Log::NoSingleImage(QString("Here in IncrementFrame"));
            return;
        }

        Animation anim = animations().at(
                this.fak.getCurrentAnimationKeyword());
        // Log::Images(QString("Progress to next frame of [%1] which is %2 / %3 %4").arg(this.fak.AnimName()).arg(this.fak.Frame()).arg(anim->GetFrames().Count()-1).arg( this.anims->At(this.fak.AnimName())->GetFrames().At(this.fak.Frame())));

        int i = this.fak.getCurrentFrame() + 1;

        if (i >= anim.getFrames().count()) {
            this.fak.setCurrentFrame(0);
        } else {
            this.fak.setCurrentFrame(i);
        }
        // do no update image here - since it is a heavy operation, we do it once per tick.
    }

    public void decrementFrameWithWraparound() {
        Animation anim = animations().at(
                this.fak.getCurrentAnimationKeyword());
        // Log::Images(QString("Progress to next frame of [%1] which is %2 / %3 %4").arg(this.fak.AnimName()).arg(this.fak.Frame()).arg(anim->GetFrames().Count()-1).arg( this.anims->At(this.fak.AnimName())->GetFrames().At(this.fak.Frame())));

        int i = this.fak.getCurrentFrame() - 1;

        if (i < 0) {
            this.fak.setCurrentFrame(
                    anim.getFrames().count()
                            - 1);
        } else {
            this.fak.setCurrentFrame(i);
        }
        // do no update image here - since it is a heavy operation, we do it once per tick.
    }

    public void updateImage() {
        // 1. do this only when the this.currentImage != img
        Animation anim = this.animationCollection.at(
                fak.getCurrentAnimationKeyword());

        if (anim != null) {
            if (fak.getCurrentFrame()
                    >= anim.getLength()) {
                fak.setCurrentFrame(
                        anim.getLength() - 1);
            }

            com.github.a2g.core.Image current = anim.getImageAndPosCollection().at(
                    fak.getCurrentFrame());

            // yes current can equal null in some weird cases where I place breakpoints...
            if (current != null
                    && !current.equals(this)) {
                if (this.currentImage != null) {
                    this.currentImage.setVisible(
                            false, this.left,
                            this.top);
                }
                this.currentImage = current;
            }
        }
        // 2, but do this always
        if (this.currentImage != null) {
            this.currentImage.setVisible(
                    this.visible, this.left,
                    this.top);
        }

    }

    public void playAnimation(String animationKeyword, int delay) {}
    ;
    public void playAnimation(String animationKeyword) {}
    ;
    public void playAnimationWithoutBlocking(String animationKeyword, int delay) {}
    ;
    public void playAnimationWithoutBlocking(String animationKeyword) {}
    ;
    public void playAnimationHoldLastFrame(String animationKeyword, int delay) {}
    ;
    public void playAnimationHoldLastFrame(String animationKeyword) {}
    ;
    public void playAnimationHoldLastFrameNonBlocking(String animationKeyword, int delay) {}
    ;
    public void playAnimationHoldLastFrameNonBlocking(String animationKeyword) {}
    ;
    public void playAnimationRepeatWhilstVisible(String animationKeyword, int delay) {}
    ;
    public void playAnimationRepeatWhilstVisible(String animationKeyword) {}
    ;
    public void playAnimationBackwards(String animationKeyword, int delay) {}
    ;
    public void playAnimationBackwards(String animationKeyword) {}
    ;
    public void playAnimationBackwardsHoldLastFrame(String animationKeyword, int delay) {}
    ;
    public void playAnimationBackwardsHoldLastFrame(String animationKeyword) {}
    ;

    public void say(String s) {}

    public void setTalkingAnimationDelay(int delay) {}

    public void walkTo(Point point) {
        walkTo(point.getX(), point.getY());
    }

    public void walkTo(double x, double y) {
        // KillCurrentAnimationAndClearInstructions();
        PointF currentPoint = new PointF(
                getBaseMiddleX(),
                getBaseMiddleY());

        currentPoint.setX(
                currentPoint.getX()
                        * this.width);
        currentPoint.setY(
                currentPoint.getY()
                        * this.height);
    }

    public void setVisible(boolean visible) {
        if (this.visible != visible) {
            this.visible = visible;
            updateImage();
        }
    }

    public boolean isVisible() {
        return this.visible;
    }

    public String displayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setBaseMiddleX(double x) { 
        if (this.leftOffset == MAX_INT) {
            calculateOffsets();
        }
        int abs = (int) (x * this.width);
        int isolatedX = abs - this.leftOffset;

        this.left = isolatedX;
        this.currentImage.setXY(this.left,
                this.top);
    }

    public void setBaseMiddleY(double y) { 
        if (this.leftOffset == MAX_INT) {
            calculateOffsets();
        }
        int abs = (int) (y * this.height);
        int isolatedY = abs - this.topOffset;

        this.top = isolatedY;
        this.currentImage.setXY(this.left,
                this.top);
    }

    public double getBaseMiddleX() { 
        if (this.leftOffset == MAX_INT) {
            calculateOffsets();
        }
        int x = this.left + this.leftOffset;

        return x / this.width;
    }

    public double getBaseMiddleY() { 
        if (this.leftOffset == MAX_INT) {
            calculateOffsets();
        }

        int y = this.top + this.topOffset;

        return y / this.height;
    }

    public void setX(int x) { 
        this.left = x;
    }

    void setY(int y) { 
        this.top = y;
    }

    public int getX() { 
        return this.left;
    }

    int getY() { 
        return this.top;
    }

    void calculateOffsets() {
        Point p = getMiddleOfBaseAbsolute(
                RoomBase.INITIAL);

        this.leftOffset = p.getX(); // (minLeft +maxRight)/2, 
        this.topOffset = p.getY(); // maxBottom;
    }

    Point getMiddleOfBaseAbsolute(String animKeyword) {
        int minLeft = 1000;
        int maxRight = 0;
        int maxBottom = 0;
        Animation xanim = this.animationCollection.at(
                animKeyword);

        if (xanim != null) {
            for (int i = 0; i
                    < xanim.getLength(); i++) {
                com.github.a2g.core.Image img = xanim.getFrames().at(
                        i);
                Rect rect = img.getBoundingRect();

                if (rect.getLeft() < minLeft) {
                    minLeft = rect.getLeft();
                }
                if (rect.getRight() > maxRight) {
                    maxRight = rect.getRight();
                }
                if (rect.getBottom()
                        > maxBottom) {
                    maxBottom = rect.getBottom();
                }
            }
        }

        Point p = new Point(
                (minLeft + maxRight) / 2,
                maxBottom - 4);

        return p;
    }

    void setSpecialAnimation(Special type, String keyword) {
        this.mapOfSpecialAnimations.put(type,
                keyword);
    }

    public String getSpecialAnimation(Special type) {
        return this.mapOfSpecialAnimations.get(
                type);
    }

    public void setTalkingAnimation(String keyword) {
        this.mapOfSpecialAnimations.put(
                Special.Talking, keyword);
    }

    public String getTalkingAnimation() {
        if (this.mapOfSpecialAnimations.containsKey(
                Special.Talking)) {
            String animId = this.mapOfSpecialAnimations.get(
                    Special.Talking);

            return animId;
        }
        return "";
    }

    public String currentAnimation() {
        String keyword = this.fak.getCurrentAnimationKeyword();

        return keyword;
    }

    public void setCurrentAnimation(String keyword2) {
        this.fak.setCurrentAnimationKeyword(
                keyword2);
        updateImage();
    }

    public void setTalkingAnimation(int harryTalkBound) {}

    public String getHomeAnimation() {
        return homeAnimation;
    }

    public void setHomeAnimation(String homeAnimation) {
        this.homeAnimation = homeAnimation;
    }

    public void setObjectCode(int objectCode) {
        this.objectCode = objectCode;
    }

    public int getObjectCode() {
        return objectCode;

    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setTalkingColor(ColorEnum color) {
        this.talkingColor = color;
    }

    public ColorEnum getTalkingColor() {
        return this.talkingColor;
    }

}


;
