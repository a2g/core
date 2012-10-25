/*
 * Copyright 2012 Anthony Cassidy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.a2g.core.objectmodel;

import com.github.a2g.core.authoredscene.SceneAPI;




public class Animation {
    private String textualId;
    private ImageCollection imageAndPosCollection;
    private SceneObject parent;
    private boolean wasSetAsHomeAnimation;
    private boolean wasSetAsTalkingAnimation;
    private boolean wasSetAsCurrentAnimation;
    private SceneAPI.Special specialAnimationThisWasSetTo;
	private int code;

    public Animation(String textualId, SceneObject owningSceneObject) {
        this.parent = owningSceneObject;
        this.textualId = textualId;
        imageAndPosCollection = new com.github.a2g.core.objectmodel.ImageCollection();
        wasSetAsHomeAnimation = false;
        wasSetAsTalkingAnimation = false;
        wasSetAsCurrentAnimation = false;
        specialAnimationThisWasSetTo = null;
    }

    public ImageCollection getFrames() {
        return imageAndPosCollection;
    }

    public String getTextualId() {
        return textualId;
    }

    public SceneObject	getObject() {
        return parent;
    }

    public void setTextualId(String textualId) {
        this.textualId = textualId;
    }
    
    public void setCode(int code)
    {
    	this.code = code;
    }
    
    public int getCode()
    {
    	return code;
    }

    public ImageCollection getImageAndPosCollection() {
        return imageAndPosCollection;
    }

    public Image getDefaultFrame() {
        assert(imageAndPosCollection.getCount()
                != 0);
        if (imageAndPosCollection.getCount() == 0) {
            return null;
        }
        com.github.a2g.core.objectmodel.Image frame = imageAndPosCollection.at(
                0);

        return frame;
    }

    public int getLength() { 
        return imageAndPosCollection.getCount(); 
    }

    public int getLastFrame() {
        return imageAndPosCollection.getCount()
                - 1;
    }

    public void preloadAnimation() {}

    public void playAnimation(int delay) {}

    public void playAnimation() {}

    public void playAnimationNonBlocking(int delay) {}

    public void playAnimationNonBlocking() {}

    public void playAnimationHoldLastFrame(int delay) {}

    public void playAnimationHoldLastFrame() {}

    public void playAnimationHoldLastFrameNonBlocking(int delay) {}

    public void playAnimationHoldLastFrameNonBlocking() {}

    public void playAnimationRepeatWhilstVisible(int delay) {}

    public void playAnimationRepeatWhilstVisible() {}

    public void playAnimationBackwards(int delay) {}

    public void playAnimationBackwards() {}

    public void playAnimationBackwardsHoldLastFrame(int delay) {}

    public void playAnimationBackwardsHoldLastFrame() {}

    public void setAsSpecialAnimation(SceneAPI.Special special) {
        specialAnimationThisWasSetTo = special;
        if (parent != null) {
            parent.setSpecialAnimation(special,
                    textualId);
        }
    }

    public void setAsCurrentAnimationAndSetFrame(int i) {
        parent.setCurrentAnimation(textualId);
        parent.setCurrentFrame(i);
    }

    public void setAsCurrentAnimation() {
        this.wasSetAsCurrentAnimation = true;
        if (parent != null) {
            parent.setCurrentAnimation(textualId);
        }
    }

    public void setAsTalkingAnimation() {
        this.wasSetAsTalkingAnimation = true;
        if (parent != null) {
            parent.setTalkingAnimation( this.textualId);
        }
    }

    public void setAsHomeAnimation() {
        this.wasSetAsHomeAnimation = true;
        if (parent != null) {
            parent.setHomeAnimation(
                    this.textualId);
        }
    }

    public void setSceneObject(SceneObject parent) {
        this.parent = parent;
    }

    public SceneObject getSceneObject() {
        return parent;
    }

    public boolean getWasSetAsHomeAnimation() {
        return wasSetAsHomeAnimation;
    }

    public boolean getWasSetAsTalkingAnimation() {
        return wasSetAsTalkingAnimation;
    }

    public boolean getWasSetAsCurrentAnimation() {
        return wasSetAsCurrentAnimation;
    }

    public boolean getWasSetAsSpecialAnimation() {
        boolean wasSet = specialAnimationThisWasSetTo
                != null;

        return wasSet;
    }

    public SceneAPI.Special getDesignatedSpecialAnimation() {
        return specialAnimationThisWasSetTo;
    }

};
