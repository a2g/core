/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.Animation;
import com.github.a2g.core.RoomObject;
import com.github.a2g.core.action.BaseAction;


public class WalkToAction extends BaseAction {
    private RoomObject obj;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private Animation anim;
    private int framesInAnim;
    private int framesPlayedDuringWalk;

    public WalkToAction(BaseAction parent, int objId, double x, double y) {
        super(parent, parent.getApi());
        this.obj = getApi().getObject(objId);
        this.endX = x;
        this.endY = y;
        // this.delay = delay;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onUpdateGameAction(double progress) {
        double x = this.startX
                + progress
                        * (this.endX
                                - this.startX);
        double y = this.startY
                + progress
                        * (this.endY
                                - this.startY);

        this.obj.setBaseMiddleX(x);
        this.obj.setBaseMiddleY(y);
        int framesPlayedSoFar = (int) (this.framesPlayedDuringWalk
                * progress);
        int i = (this.framesInAnim != 0)
                ? framesPlayedSoFar
                        % this.framesInAnim
                        : 0;

        this.obj.setCurrentFrame(i);
    }

    @Override
    public void runGameAction() {

        // distance and time calulations
        this.startX = this.obj.getBaseMiddleX();
        this.startY = this.obj.getBaseMiddleY();
        double diffX = this.startX - this.endX;
        double diffY = this.startY - this.endY;
        double diffXSquared = diffX * diffX;
        double diffYSquared = diffY * diffY;
        double dist = Math.sqrt(
                diffXSquared + diffYSquared);

        this.framesPlayedDuringWalk = (int) (dist
                * 40);

        // anim
        String anim = "";
        int width = getApi().getRoomGui().getWidth();
        int height = getApi().getRoomGui().getHeight();

        if ((diffX * width) * (diffX * width)
                > (diffY * height)
                        * (diffY * height)) {
            if (this.endX < this.startX) {
                anim = this.obj.getSpecialAnimation(
                        com.github.a2g.core.authoredroom.RoomBase.Special.West);
            } else {
                anim = this.obj.getSpecialAnimation(
                        com.github.a2g.core.authoredroom.RoomBase.Special.East);
            }
        } else {
            if (this.endY < this.startY) {
                anim = this.obj.getSpecialAnimation(
                        com.github.a2g.core.authoredroom.RoomBase.Special.North);
            } else {
                anim = this.obj.getSpecialAnimation(
                        com.github.a2g.core.authoredroom.RoomBase.Special.South);
            }
        }
        this.obj.setCurrentAnimation(anim);
        this.anim = this.obj.animations().at(
                anim);
        if (this.anim != null) {
            this.framesInAnim = this.anim.getLength();
        } else {
            this.framesInAnim = 0;
        }

        this.run((int) (dist * 10000));
    }

    @Override // method in animation
    protected void onCompleteGameAction() {

        String anim = this.obj.getSpecialAnimation(
                com.github.a2g.core.authoredroom.RoomBase.Special.South);

        this.obj.setCurrentAnimation(anim);
        this.obj.setCurrentFrame(0);
    }

    @Override
    public boolean isParallel() {

        return false;
    }

}
