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
	private int halfWidthOfLeaderLine;
	private int afterBorderWidth;
	private int borderWidth;
	private int heightOfLeaderLine;
	
	SceneSpeechBalloonCalculator(Rect max, int radius, Point mouth, int leaderWidth, int borderWidth)
	{
		this.halfWidthOfLeaderLine = (leaderWidth/2);
		this.heightOfLeaderLine = 2*halfWidthOfLeaderLine;// since they are always square
		this.afterBorderWidth = halfWidthOfLeaderLine - borderWidth -1;// the -1 just looks better, 
		this.borderWidth = borderWidth;
		this.radius = radius;

		// css styles, in chrome atleast, seem to draw the border
		// outside of the viewport if I don't factor in the border below
		this.rectInPixels = new Rect(
				max.getTop(), 
				max.getLeft(),
				max.getWidth()-2*borderWidth+1,
				max.getHeight()-2*borderWidth+1);
		Point centre = rectInPixels.getCenter();
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
		
		xPos = mouth.getX()-rectInPixels.getLeft();
		//the xPos should be where the leaderline starts so that the perpendicular
		// edge of the leaderline points to the mouth..
		// but if its pointing right, the straight line is a whole leaderwidth away.
		// so we need to start the xPos earlier, if we want its trailing edge
		// to point to the the mouth.
		xPos-=(isPointingRight?leaderWidth:0);
		int minimumStartOfLeaderLine = radius-6;
		int maximumStartOfLeaderLine = rectInPixels.getWidth()-radius-leaderWidth+11;
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
	public int getHalfWidthOfLeaderLine() {
		return halfWidthOfLeaderLine;
		
	}
	public int getAfterBorderWidth() {
		return afterBorderWidth;	
	}
	public int getBorderWidth() {
		return borderWidth;
		
	}
	public int getHeightOfLeaderLine() {
		return heightOfLeaderLine;
	}

}
