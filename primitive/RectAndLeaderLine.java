package com.github.a2g.core.primitive;


import java.util.ArrayList;

import com.github.a2g.core.interfaces.internal.IMeasureTextWidthAndHeight;
import com.github.a2g.core.primitive.LinesAndMaxWidth.LineAndPos;
import com.google.gwt.touch.client.Point;

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
	public double secondsForPage; 
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
	
	static final int MARGIN_TOP = 5;
	static final int MARGIN_LEFT = 5;
	static final int MARGIN_RIGHT = 5;
	static final int MARGIN_BOTTOM = 5;
	
	private void setPoint(int index, PointI point)
	{
		xPoints[index] = point.getX();
		yPoints[index] = point.getY();
	}
	public void generateSampleBubbleRectFromText( IMeasureTextWidthAndHeight measurer)
	{
		LineAndPos line0 = lines.lines.get(0);
		Point dim0 = measurer.measureTextWidthAndHeight(line0.line);
		Rect r = new Rect(line0.x*1.0, line0.y-dim0.getY(), dim0.getX(), dim0.getY());
		 
		for(int i=1;i<lines.lines.size();i++)
		{
			LineAndPos line = lines.lines.get(i);
			Point dim = measurer.measureTextWidthAndHeight(line.line);
			r.collateInPlace(new Rect(line.x*1.0, line.y-dim.getY(), dim.getX(), dim.getY()));
		}
		this.rectPurelyTextBoundsInYellow = r;
		this.rectBubble = new Rect(r.getLeft()-MARGIN_LEFT, r.getTop()-MARGIN_TOP, r.getWidth()+MARGIN_LEFT+MARGIN_RIGHT, r.getHeight()+MARGIN_TOP+MARGIN_BOTTOM);
		this.rectInputInRed = new Rect(r.getLeft()-2*MARGIN_LEFT, r.getTop()-2*MARGIN_TOP, r.getWidth()+2*MARGIN_LEFT+2*MARGIN_RIGHT, r.getHeight()+2*MARGIN_TOP+2*MARGIN_BOTTOM);
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
	

	static public ArrayList<RectAndLeaderLine> calculateLeaderLines(String[] pages, Rect maxRectI, int radius, PointI mouth, int leaderWidth, int borderWidth, IMeasureTextWidthAndHeight context)
	{
			
		// 1. create return value
		ArrayList<RectAndLeaderLine> toReturn = new ArrayList<RectAndLeaderLine>();

		// 2. set up maxWidthBeforeWrapping 
		int maxWidthBeforeWrapping = maxRectI.getWidth()-MARGIN_LEFT-MARGIN_RIGHT;

		// 3. first do one iteration to find the longest textwidth
		double maxTextWidth = 0;
		int theMostLines = 0;
		for(int i=0;i<pages.length;i++)
		{
			LinesAndMaxWidth lines = LinesAndMaxWidth.getArrayOfLinesSplitOnSpaceAndWidth(context, maxWidthBeforeWrapping, pages[i]);
			RectAndLeaderLine pageOfSpeech = new RectAndLeaderLine();
			pageOfSpeech.lines = lines;
			if( lines.lines.size()>theMostLines)
				theMostLines = lines.lines.size();
			if(lines.maxWidth>maxTextWidth)
				maxTextWidth = lines.maxWidth;
			toReturn.add(pageOfSpeech);
		}
		
		// 4. caculate line height
		int lineSpacing = 3;  
		int fontHeight = (int)context.measureTextWidthAndHeight("blah").getY();
		int heightPerLine = fontHeight + lineSpacing;
		
		// 5. calculate largest centred rect to hold all pages
		Rect minToHoldAllPages = new Rect (maxRectI.getCenter().getX() - maxTextWidth/2, maxRectI.getTop(), maxTextWidth, theMostLines*heightPerLine);
				
		// 6. calc x,y for all pages
		for(int i=0;i<toReturn.size();i++)
		{
			RectAndLeaderLine page = toReturn.get(i);
			for (int j = 0 ; j < page.lines.lines.size(); ++j) 
			{
				// fill in x and y for all the pages.
				LineAndPos line = page.lines.lines.get(j);
				line.x = (int)minToHoldAllPages.getLeft();
				line.y = (int)minToHoldAllPages.getTop()+(fontHeight+lineSpacing)*j+fontHeight; 
			}
			
			page.rectInputInRed = new Rect(maxRectI.getLeft(),maxRectI.getTop(),maxRectI.getWidth(), maxRectI.getHeight());
			page.rectPurelyTextBoundsInYellow = minToHoldAllPages;
			page.rectBubble = new Rect(minToHoldAllPages.getLeft()-MARGIN_LEFT,(int)minToHoldAllPages.getTop()-MARGIN_TOP, (int)minToHoldAllPages.getWidth()+MARGIN_LEFT+MARGIN_RIGHT, (int)minToHoldAllPages.getHeight()+MARGIN_TOP+MARGIN_BOTTOM);
		

			// generate leader lines
			Rect max = page.rectBubble;
			PointI centre = max.getCenter();

			// the mouth & centre coords are both relative to top left of viewport
			page.isFromTop = mouth.getY() < centre.getY();
			page.isPointingRight = mouth.getX() > centre.getX();

			// with the way I've set up the DOM, it seems that
			// the paragraph ignores its top and left style value,
			// and it is positioned by the coords passed when adding to the container.
			//  ie maxBaloonRect.getLeft, and maxBalloonRect.getTop
			// This means that before and after elements also inherit these coords
			// so the Xpos need only be the increment that we add to maxBaloonRect.getLeft
			// to get to the starting point of the leader line.
			// same with max/minimumStartOfLeaderLine

			page.xPos = mouth.getX()-max.getLeft();
			//the xPos should be where the leaderline starts so that the perpendicular
			// edge of the leaderline points to the mouth..
			// but if its pointing right, the straight line is a whole leaderwidth away.
			// so we need to adjust for this.
			page.xPos-=(page.isPointingRight?leaderWidth:0);
			int minimumStartOfLeaderLine = radius-6;// <-- trial and error see how close to the corner we can position our leader
			int maximumStartOfLeaderLine = max.getWidth()-radius-leaderWidth+11;// <-- trial and error see how close to the corner we can position our leader
			if(page.xPos>maximumStartOfLeaderLine)
				page.xPos = maximumStartOfLeaderLine;
			if(page.xPos<minimumStartOfLeaderLine)
				page.xPos = minimumStartOfLeaderLine;

			page.halfWidthOfLeaderLine = (leaderWidth/2);
			page.heightOfLeaderLine = 2*page.halfWidthOfLeaderLine;// since they are always square
			page.afterBorderWidth = page.halfWidthOfLeaderLine - borderWidth -1;// the -1 just looks better,
			page.borderWidth = borderWidth;
			page.radius = radius;

			// css styles, in chrome atleast, seem to draw the border
			// outside of the viewport if I don't factor in the border below
			//page.rectInPixels = new Rect(
			//		max.getLeft(),
			//		max.getTop(),
			//		max.getWidth()-2*borderWidth+1,
			//		max.getHeight()-2*borderWidth+1);
			
			//toReturn.add(page);
		}
		return toReturn;
	}
	public static String[] getDebugStrings() {
		return new String[]{ "to be or not to be", "This sentence has larger words"};
	}
	 
}
