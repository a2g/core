package com.github.a2g.core.objectmodel;

import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.Rect;


public class SceneSpeechBalloonCalculator 
{
	private boolean isFromTop;
	private boolean isPointingRight;
	private int xPos;
	private Rect rectInPixels;
	private int radius;
	private int beforeBorderWidth;
	private int afterBorderWidth;
	private int borderWidth;
	
	SceneSpeechBalloonCalculator(Rect maxBalloonRect, int radius, Point mouth, int leaderHeight, int borderWidth)
	{
		this.beforeBorderWidth = (leaderHeight/2);
		this.afterBorderWidth = beforeBorderWidth - borderWidth -1;// the -1 just looks better, 
		this.borderWidth = borderWidth;
		this.radius = radius;
		this.rectInPixels = maxBalloonRect;
		Point bubble = maxBalloonRect.getCenter();
		isFromTop = mouth.getY() > bubble.getY();
		isPointingRight = mouth.getX() > bubble.getX();
		xPos = mouth.getX();
		int left = maxBalloonRect.getLeft()+radius;
		int right = maxBalloonRect.getRight()-radius;
		if(xPos>right)
			xPos = right;
		if(xPos<left)
			xPos = left;
	}
	boolean isFromTop()
	{
		return isFromTop;
	}
	
	boolean isPointingRight()
	{
		return isPointingRight;
	}
	
	int getLeaderLineX()
	{
		return xPos;
	}
	
	Rect getRectInPixels()
	{
		return rectInPixels;
	}
	public int getRadius() {
		return radius;
	}
	public int getBeforeBorderWidth() {
		return beforeBorderWidth;
		
	}
	public int getAfterBorderWidth() {
		return afterBorderWidth;	
	}
	public int getBorderWidth() {
		return borderWidth;
		
	}

}
