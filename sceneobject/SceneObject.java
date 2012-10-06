/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.sceneobject;

import java.util.Map;
import java.util.TreeMap;


import com.github.a2g.core.authoredscene.ConstantsForAPI;
import com.github.a2g.core.authoredscene.SceneAPI;
import com.github.a2g.core.authoredscene.ConstantsForAPI.Special;
import com.github.a2g.core.objectmodel.FrameAndAnimation;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.PointF;
import com.github.a2g.core.primitive.Rect;


public class SceneObject {
    private static final int MAX_INT = 2147483647;
    private String homeAnimation;
    private Map<SceneAPI.Special, String> mapOfSpecialAnimations;
    private final String textualId;
    private String displayName;
    private AnimationCollection animationCollection;
    private FrameAndAnimation fak;
    private com.github.a2g.bridge.image.Image currentImage;
    private boolean visible;
    private double width;
    private double height;
    private int top; // needed for scrolling images (ie canoe background)
    private int left;
    private int topOffset;
    private int leftOffset;
    private int numberPrefix;
    private short objectCode;
    private ColorEnum talkingColor;
    private int talkingAnimationDelay;

    public SceneObject(String textualId, int width, int height) {
        this.currentImage = null;
        this.textualId = textualId;
        this.displayName = textualId;
        this.animationCollection = new AnimationCollection();
        this.fak = new FrameAndAnimation(
                this.textualId);
        this.visible = true;
        this.width = width;
        this.height = height;
        this.leftOffset = MAX_INT;
        this.topOffset = MAX_INT;
        this.mapOfSpecialAnimations = new TreeMap<Special, String>();
        this.numberPrefix = 0;
        this.homeAnimation = ConstantsForAPI.INITIAL;
        this.talkingAnimationDelay = 0;
       
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

    public String getTextualId() {
        return this.textualId;
    }

    public AnimationCollection getAnimations() {
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
        if (getAnimations().getCount() == 0) {
            // Log::NoSingleImage(QString("Here in IncrementFrame"));
            return;
        }

        Animation anim = getAnimations().at(
                this.fak.getCurrentAnimationTextualId());
        // Log::Images(QString("Progress to next frame of [%1] which is %2 / %3 %4").arg(this.fak.AnimName()).arg(this.fak.Frame()).arg(anim->GetFrames().Count()-1).arg( this.anims->At(this.fak.AnimName())->GetFrames().At(this.fak.Frame())));

        int i = this.fak.getCurrentFrame() + 1;

        if (i >= anim.getFrames().getCount()) {
            this.fak.setCurrentFrame(0);
        } else {
            this.fak.setCurrentFrame(i);
        }
        // do no update image here - since it is a heavy operation, we do it once per tick.
    }

    public void decrementFrameWithWraparound() {
        Animation anim = getAnimations().at(
                this.fak.getCurrentAnimationTextualId());
        // Log::Images(QString("Progress to next frame of [%1] which is %2 / %3 %4").arg(this.fak.AnimName()).arg(this.fak.Frame()).arg(anim->GetFrames().Count()-1).arg( this.anims->At(this.fak.AnimName())->GetFrames().At(this.fak.Frame())));

        int i = this.fak.getCurrentFrame() - 1;

        if (i < 0) {
            this.fak.setCurrentFrame(
                    anim.getFrames().getCount()
                            - 1);
        } else {
            this.fak.setCurrentFrame(i);
        }
        // do no update image here - since it is a heavy operation, we do it once per tick.
    }

    public void updateImage() {
        // 1. do this only when the this.currentImage != img
        Animation anim = this.animationCollection.at(
                fak.getCurrentAnimationTextualId());
        
        // if animation is set to something bad, then set it to back to initial
        if(anim==null)
        {
        	fak.setCurrentAnimationTextualId(ConstantsForAPI.INITIAL);
        	anim = this.animationCollection.at(
                    fak.getCurrentAnimationTextualId());
        }
        
        if(anim==null)
        {
        	anim = this.animationCollection.at(0);
        	fak.setCurrentAnimationTextualId(anim.getTextualId());
        }
        
        if (anim != null) {
            if (fak.getCurrentFrame()
                    >= anim.getLength()) {
                fak.setCurrentFrame(
                        anim.getLength() - 1);
            }

            com.github.a2g.bridge.image.Image current = anim.getImageAndPosCollection().at(
                    fak.getCurrentFrame());

            // yes current can equal null in some weird cases where I place breakpoints...
            if (current != null
                    && !current.equals(this)) {
                if (this.currentImage != null) {
                    this.currentImage.setVisible(
                            false, new Point(this.left,this.top));
                }
                this.currentImage = current;
            }
        }
        // 2, but do this always
        if (this.currentImage != null) {
            this.currentImage.setVisible(
                    this.visible, new Point(this.left,
                    this.top));
        }

    }

    public void setTalkingAnimationDelay(int delay) 
    {
    	this.talkingAnimationDelay = delay;
    }
    
    public int getTalkingAnimationDelay() 
    {
    	return this.talkingAnimationDelay;
    }

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

    public String getDisplayName() {
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
        if(currentImage==null)
        {
        	updateImage();
        }
        this.currentImage.setTopLeft(new Point(this.left,this.top));
    }

    public void setBaseMiddleY(double y) { 
        if (this.leftOffset == MAX_INT) {
            calculateOffsets();
        }
        int abs = (int) (y * this.height);
        int isolatedY = abs - this.topOffset;

        this.top = isolatedY;
        this.currentImage.setTopLeft(new Point(this.left,
                this.top));
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

    public int getY() { 
        return this.top;
    }

    void calculateOffsets() {
        Point p = getMiddleOfBaseAbsolute(
                ConstantsForAPI.INITIAL);

        this.leftOffset = p.getX(); // (minLeft +maxRight)/2, 
        this.topOffset = p.getY(); // maxBottom;
    }

    Point getMiddleOfBaseAbsolute(String animTextualId) {
        int minLeft = 1000;
        int maxRight = 0;
        int maxBottom = 0;
        Animation xanim = this.animationCollection.at(
                animTextualId);

        if (xanim != null) {
            for (int i = 0; i
                    < xanim.getLength(); i++) {
                com.github.a2g.bridge.image.Image img = xanim.getFrames().at(
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

    void setSpecialAnimation(Special type, String textualId) {
        this.mapOfSpecialAnimations.put(type,
                textualId);
    }

    public String getSpecialAnimation(Special type) {
        return this.mapOfSpecialAnimations.get(
                type);
    }
    public void setTalkingAnimation(String talkingAnimation)
    {
    	this.mapOfSpecialAnimations.put(Special.Talking, talkingAnimation);
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

    public String getCurrentAnimation() {
        String textualId = this.fak.getCurrentAnimationTextualId();

        return textualId;
    }

    public void setCurrentAnimation(String textualId2) {
        this.fak.setCurrentAnimationTextualId(
                textualId2);
        updateImage();
    }

    
    public String getHomeAnimation() {
        return homeAnimation;
    }

    public void setHomeAnimation(String homeAnimation) {
        this.homeAnimation = homeAnimation;
    }

    public void setCode(short objectCode) {
        this.objectCode = objectCode;
    }

    public short getCode() {
        return objectCode;

    }

    public void setTalkingColor(ColorEnum color) {
        this.talkingColor = color;
    }

    public ColorEnum getTalkingColor() {
        return this.talkingColor;
    }

}


;
