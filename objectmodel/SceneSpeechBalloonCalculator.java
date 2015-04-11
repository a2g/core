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
	
	SceneSpeechBalloonCalculator(Rect maxBalloonRect, int radius, Point mouth, int leaderWidth, int borderWidth)
	{
		this.beforeBorderWidth = (leaderWidth/2);
		this.afterBorderWidth = beforeBorderWidth - borderWidth -1;// the -1 just looks better, 
		this.borderWidth = borderWidth;
		this.radius = radius;
		this.rectInPixels = maxBalloonRect;
		Point centre = maxBalloonRect.getCenter();
		isFromTop = mouth.getY() < centre.getY();
		isPointingRight = mouth.getX() > centre.getX();
		
		// with the way I've set up the DOM, it seems that 
		// the paragraph ignores its top and left style value,
		// and it is positioned by the coords passed when adding to the container.
		//  ie maxBaloonRect.getLeft, and maxBalloonRect.getTop
		// This means that before and after elements also inherit these coords
		// so the Xpos need only be the increment that we add to maxBaloonRect.getLeft
		// to get to the starting point of the leader line.
		// same with max/minimumStartOfLeaderLine
		
		xPos = mouth.getX()-maxBalloonRect.getLeft();
		//the xPos should be where the leaderline starts so that the perpendicular
		// edge of the leaderline points to the mouth..
		// but if its pointing right, the straight line is a whole leaderwidth away.
		// so we need to start the xPos earlier, if we want its trailing edge
		// to point to the the mouth.
		xPos-=(isPointingRight?leaderWidth:0);
		int minimumStartOfLeaderLine = radius-6;
		int maximumStartOfLeaderLine = maxBalloonRect.getWidth()-radius-leaderWidth+11;
		if(xPos>maximumStartOfLeaderLine)
			xPos = maximumStartOfLeaderLine;
		if(xPos<minimumStartOfLeaderLine)
			xPos = minimumStartOfLeaderLine;
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
