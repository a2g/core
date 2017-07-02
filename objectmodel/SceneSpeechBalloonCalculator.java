package com.github.a2g.core.objectmodel;

import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.Rect;

public class SceneSpeechBalloonCalculator {
	private boolean isFromTop;
	private boolean isPointingRight;
	private int xPos;
	private Rect rectInPixels;
	private int radius;
	private int halfWidthOfLeaderLine;
	private int afterBorderWidth;
	private int borderWidth;
	private int heightOfLeaderLine;

	public SceneSpeechBalloonCalculator() {
	}

	public Rect getRectInPixels(Rect max, int radius, Point mouth, int leaderWidth, int borderWidth) {
		Point centre = max.getCenter();

		// the mouth & centre coords are both relative to top left of viewport
		isFromTop = mouth.getY() < centre.getY();
		isPointingRight = mouth.getX() > centre.getX();

		// with the way I've set up the DOM, it seems that
		// the paragraph ignores its top and left style value,
		// and it is positioned by the coords passed when adding to the
		// container.
		// ie maxBaloonRect.getLeft, and maxBalloonRect.getTop
		// This means that before and after elements also inherit these coords
		// so the Xpos need only be the increment that we add to
		// maxBaloonRect.getLeft
		// to get to the starting point of the leader line.
		// same with max/minimumStartOfLeaderLine

		xPos = mouth.getX() - max.getLeft();
		// the xPos should be where the leaderline starts so that the
		// perpendicular
		// edge of the leaderline points to the mouth..
		// but if its pointing right, the straight line is a whole leaderwidth
		// away.
		// so we need to adjust for this.
		xPos -= (isPointingRight ? leaderWidth : 0);
		int minimumStartOfLeaderLine = radius - 6;// <-- trial and error see how
													// close to the corner we
													// can position our leader
		int maximumStartOfLeaderLine = max.getWidth() - radius - leaderWidth + 11;// <--
																					// trial
																					// and
																					// error
																					// see
																					// how
																					// close
																					// to
																					// the
																					// corner
																					// we
																					// can
																					// position
																					// our
																					// leader
		if (xPos > maximumStartOfLeaderLine)
			xPos = maximumStartOfLeaderLine;
		if (xPos < minimumStartOfLeaderLine)
			xPos = minimumStartOfLeaderLine;

		this.halfWidthOfLeaderLine = (leaderWidth / 2);
		this.heightOfLeaderLine = 2 * halfWidthOfLeaderLine;// since they are
															// always square
		this.afterBorderWidth = halfWidthOfLeaderLine - borderWidth - 1;// the
																		// -1
																		// just
																		// looks
																		// better,
		this.borderWidth = borderWidth;
		this.radius = radius;

		// css styles, in chrome atleast, seem to draw the border
		// outside of the viewport if I don't factor in the border below
		this.rectInPixels = new Rect(max.getTop(), max.getLeft(), max.getWidth() - 2 * borderWidth + 1,
				max.getHeight() - 2 * borderWidth + 1);
		return this.rectInPixels;
	}

	boolean isFromTop() {
		return isFromTop;
	}

	boolean isPointingRight() {
		return isPointingRight;
	}

	int getLeaderLineX() {
		return xPos;
	}

	Rect getRectInPixels() {
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

	public static Rect getRectThatFitsText(String speech, Rect maxBalloonRect) 
	{
		Rect r = maxBalloonRect;
		Rect toReturn = new Rect(r.getLeft(),r.getRight(),r.getWidth(),r.getHeight());
		return toReturn;
	}

}
