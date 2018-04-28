package com.github.a2g.core.primitive;


import com.github.a2g.core.action.performer.dependencies.LinesAndMaxWidth;

public class RectAndLeaderLine 
{
	public RectAndLeaderLine(int x, int y, String string) {
		init();
		lines.addLine(string, 1.0,x,y);
	}
	public RectAndLeaderLine() {
		init();
	}
	
	void init()
	{
		xPoints = new int[7];
		yPoints = new int[7];
		lines = new LinesAndMaxWidth();
		//isPointingRight = true;
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
	public Rect rectBubble;
	public Rect rectPurelyTextBoundsInYellow;
	
	public int[] xPoints;
	public int[] yPoints;
	
	private void setPoint(int index, PointI point)
	{
		xPoints[index] = point.getX();
		yPoints[index] = point.getY();
	}
	public void populateXAndYPointsFromBubbleRect(PointI resolution) {
		int halfThicknessOfLeaderLine = (int)(resolution.getX()/64.0);
		int lengthOfLeaderLine = (int)(resolution.getX()/16.0);
		if(isPointingRight)
		{
			setPoint(0, rectBubble.getTopRight());
			PointI mp = PointI.getMidway(rectBubble.getTopRight(), rectBubble.getBottomRight());
			PointI a = new PointI(mp.getX(),mp.getY()-halfThicknessOfLeaderLine);//PointI a = PointI.getMidway(rectBubble.getTopRight(), p);
			PointI b = new PointI(mp.getX(),mp.getY()+halfThicknessOfLeaderLine);//PointI b = PointI.getMidway(rectBubble.getBottomRight(), p);
		
			setPoint(1, a);
			if(isFromTop)
			{
				setPoint(2, new PointI(mp.getX()+lengthOfLeaderLine,a.getY()));
			}
			else
			{
				setPoint(2, new PointI(mp.getX()+lengthOfLeaderLine,b.getY()));
			}
				
			setPoint(3, b);
			setPoint(4, rectBubble.getBottomRight());
			setPoint(5, rectBubble.getBottomLeft());
			setPoint(6, rectBubble.getTopLeft());
		}
		else
		{
			setPoint(0, rectBubble.getTopLeft());
			PointI mp = PointI.getMidway(rectBubble.getTopLeft(), rectBubble.getBottomLeft());
			PointI a = new PointI(mp.getX(),mp.getY()-halfThicknessOfLeaderLine);//PointI a = PointI.getMidway(rectBubble.getTopLeft(), p);
			PointI b = new PointI(mp.getX(),mp.getY()+halfThicknessOfLeaderLine);//PointI b = PointI.getMidway(rectBubble.getBottomLeft(), p);
		
			setPoint(1, a);
			if(isFromTop)
			{
				setPoint(2, new PointI(mp.getX()-lengthOfLeaderLine,a.getY()));
			}
			else
			{
				setPoint(2, new PointI(mp.getX()-lengthOfLeaderLine,b.getY()));
			}
				
			setPoint(3, b);
			setPoint(4, rectBubble.getBottomLeft());
			setPoint(5, rectBubble.getBottomRight());
			setPoint(6, rectBubble.getTopRight());
		}
		
	}
	 
}
