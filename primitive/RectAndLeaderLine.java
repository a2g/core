package com.github.a2g.core.primitive;

import com.github.a2g.core.action.performer.dependencies.LinesAndMaxWidth;

public class RectAndLeaderLine 
{
	public RectAndLeaderLine(int x, int y, String string) {
		lines = new LinesAndMaxWidth();
		lines.addLine(string, 1.0,x,y);
	}
	public RectAndLeaderLine() {
		lines = new LinesAndMaxWidth();
	}
	
	public LinesAndMaxWidth lines;
	 
	public String atid;
	public boolean isFromTop;

	public boolean isPointingRight;

	public int leaderLineX;

	public int radius;

	public int halfWidthOfLeaderLine;

	public int  afterBorderWidth; 

	public int borderWidth;

	public int heightOfLeaderLine;
    public int xPos;

	public Rect rectInputInRed;
	public Rect rectTextAndMarginsInOlive;
	public Rect rectPurelyTextBoundsInYellow;
}
