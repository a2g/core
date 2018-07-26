package com.github.a2g.core.primitive;


import java.util.ArrayList;

import com.github.a2g.core.interfaces.nongame.platform.singles.IMeasureTextWidthAndHeight;
import com.github.a2g.core.primitive.LinesAndMaxWidth.LineAndPos;
import com.google.gwt.touch.client.Point;

public class SpeechBubble 
{
	public SpeechBubble(int x, int y, String string) {
		init();
		lines.addLine(string, 1.0,x,y);
	}
	public SpeechBubble() {
		init();
	}

	private void init()
	{
		xPoints = new int[7];
		yPoints = new int[7];
		lines = new LinesAndMaxWidth();
		//isPointingRight = true;
	}

	public LinesAndMaxWidth lines;
	public double secondsForPage; 
	public String atid;
	public boolean isPointingDown;
	public boolean isVerticallyOriented;

	public boolean isPointingRight;
	public boolean isPointingLeft;
	public double startingTime;
	public int leaderLineX;

	public int radius;

	public int halfWidthOfLeaderLine;

	public int afterBorderWidth; 


	public int bubbleOutlineWidth;

	public int heightOfLeaderLine;
	public int xPos;

	public RectI rectInputInRed;
	public RectI rectBubble;
	public RectI rectPurelyTextBoundsInYellow;

	public int[] xPoints;
	public int[] yPoints;

	static final int MARGIN_TOP = 1;
	static final int MARGIN_LEFT = 15;
	static final int MARGIN_RIGHT = 15;
	static final int MARGIN_BOTTOM = 5;

	static final int bubbleLineWidth = 3;
	// buffer = margin + bubble_outline
	static final int BUFFER_TOP = MARGIN_TOP + bubbleLineWidth;
	static final int BUFFER_LEFT = MARGIN_LEFT + bubbleLineWidth;
	static final int BUFFER_RIGHT = MARGIN_RIGHT + bubbleLineWidth;
	static final int BUFFER_BOTTOM = MARGIN_BOTTOM + bubbleLineWidth;

	static final int LEADERLINE_FRACTION_DEONOMINATOR = 8;
	
	public String toString() { 
	    return ""+this.startingTime + this.lines.toString();
	} 
	
	private void setPoint(int index, PointI point)
	{
		xPoints[index] = point.getX();
		yPoints[index] = point.getY();
	}
	public void generateSampleBubbleRectFromText( IMeasureTextWidthAndHeight measurer)
	{
		LineAndPos line0 = lines.lines.get(0);
		Point dim0 = measurer.measureTextWidthAndHeight(line0.line);
		RectI r = new RectI(line0.x*1.0, line0.y-dim0.getY(), dim0.getX(), dim0.getY());

		for(int i=1;i<lines.lines.size();i++)
		{
			LineAndPos line = lines.lines.get(i);
			Point dim = measurer.measureTextWidthAndHeight(line.line);
			r.collateInPlace(new RectI(line.x*1.0, line.y-dim.getY(), dim.getX(), dim.getY()));
		}
		this.rectPurelyTextBoundsInYellow = r;
		this.rectBubble = new RectI(r.getLeft()-BUFFER_LEFT, r.getTop()-BUFFER_TOP, r.getWidth()+BUFFER_LEFT+BUFFER_RIGHT, r.getHeight()+BUFFER_TOP+BUFFER_BOTTOM);
		this.rectInputInRed = new RectI(r.getLeft()-2*BUFFER_LEFT, r.getTop()-2*BUFFER_TOP, r.getWidth()+2*BUFFER_LEFT+2*BUFFER_RIGHT, r.getHeight()+2*BUFFER_TOP+2*BUFFER_BOTTOM);
	}

	public void populateXAndYPointsFromBubbleRect(PointI resolution) {
		int halfThicknessOfLeaderLine = (int)(resolution.getX()/64.0);
		int lengthOfLeaderLine = (int)(resolution.getY()/LEADERLINE_FRACTION_DEONOMINATOR);
		if(isVerticallyOriented)
		{
			if(isPointingRight)
			{
				setPoint(0, rectBubble.getTopRight());
				PointI mp = PointI.getMidway(rectBubble.getTopRight(), rectBubble.getBottomRight());
				PointI a = new PointI(mp.getX(),mp.getY()-halfThicknessOfLeaderLine);//PointI a = PointI.getMidway(rectBubble.getTopRight(), p);
				PointI b = new PointI(mp.getX(),mp.getY()+halfThicknessOfLeaderLine);//PointI b = PointI.getMidway(rectBubble.getBottomRight(), p);

				setPoint(1, a);
				if(isPointingDown)
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
				if(isPointingDown)
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
		}else
		{
			if(isPointingDown)
			{
				setPoint(0, rectBubble.getTopRight());
				setPoint(1, rectBubble.getBottomRight());
				PointI mp = PointI.getMidway(rectBubble.getBottomLeft(), rectBubble.getBottomRight());
				PointI a = new PointI(mp.getX()+halfThicknessOfLeaderLine, mp.getY());//PointI a = PointI.getMidway(rectBubble.getTopRight(), p);
				PointI b = new PointI(mp.getX()-halfThicknessOfLeaderLine, mp.getY());//PointI b = PointI.getMidway(rectBubble.getBottomRight(), p);

				setPoint(2, a);
				if(isPointingRight)
				{
					setPoint(3, new PointI(a.getX(), mp.getY()+lengthOfLeaderLine));
				}
				else if(isPointingLeft)
				{
					setPoint(3, new PointI(b.getX(), mp.getY()+lengthOfLeaderLine));
				}
				else
				{
					setPoint(3, new PointI(mp.getX(), mp.getY()+lengthOfLeaderLine));
				}

				setPoint(4, b);
				setPoint(5, rectBubble.getBottomLeft());
				setPoint(6, rectBubble.getTopLeft());
			}
			else 
			{
				setPoint(0, rectBubble.getTopRight());
				setPoint(1, rectBubble.getBottomRight());
				setPoint(2, rectBubble.getBottomLeft());
				setPoint(3, rectBubble.getTopLeft());
				PointI mp = PointI.getMidway(rectBubble.getTopLeft(), rectBubble.getTopRight());
				PointI a = new PointI(mp.getX()-halfThicknessOfLeaderLine, mp.getY());//PointI a = PointI.getMidway(rectBubble.getTopRight(), p);
				PointI b = new PointI(mp.getX()+halfThicknessOfLeaderLine, mp.getY());//PointI b = PointI.getMidway(rectBubble.getBottomRight(), p);

				setPoint(4, a);
				if(isPointingRight)
				{
					setPoint(5, new PointI(b.getX(), mp.getY()-lengthOfLeaderLine));
				}
				else if(isPointingLeft)
				{
					setPoint(5, new PointI(a.getX(), mp.getY()-lengthOfLeaderLine));
				}
				else
				{
					setPoint(5, new PointI(mp.getX(), mp.getY()+lengthOfLeaderLine));
				}

				setPoint(6, b);
			}
		}

	}


	static public ArrayList<SpeechBubble> calculateWordWrappedPages(PointI resolution, String[] pages, IMeasureTextWidthAndHeight context, RectI headRect)
	{

		// 1. create return value
		ArrayList<SpeechBubble> toReturn = new ArrayList<SpeechBubble>();

		// 2. set up maxWidthBeforeWrapping 
		int maxWidthBeforeWrapping = resolution.getX()/3;

		// 3. first do one iteration to find the longest textwidth
		double maxTextWidth = 0;
		int theMostLines = 2;
		for(int i=0;i<pages.length;i++)
		{
			LinesAndMaxWidth lines = LinesAndMaxWidth.getArrayOfLinesSplitOnSpaceAndWidth(context, maxWidthBeforeWrapping, pages[i]);
			SpeechBubble pageOfSpeech = new SpeechBubble();
			pageOfSpeech.lines = lines;
			theMostLines = Math.max(theMostLines, lines.lines.size());
			maxTextWidth = Math.max(maxTextWidth, lines.maximumWidthOfOneOfOurLines);
			toReturn.add(pageOfSpeech);
		}

		// 4. caculate line height
		int lineSpacing = 3;  
		int fontHeight = (int)context.measureTextWidthAndHeight("blah").getY();
		int heightPerLine = fontHeight + lineSpacing;

		// 5. calculate largest centred rect to hold all pages
		int lengthOfLeaderLine = (int)(resolution.getY()/LEADERLINE_FRACTION_DEONOMINATOR);
		int avoidSharpPinToHead = lengthOfLeaderLine + (int)(resolution.getY()/90.0);
		PointI headMP = headRect.getCenter();
		RectI minToHoldAllPages = new RectI (headMP.getX() - (maxTextWidth/2), headRect.getTop()-theMostLines*heightPerLine-BUFFER_BOTTOM-avoidSharpPinToHead, maxTextWidth, theMostLines*heightPerLine);
		if(minToHoldAllPages.getLeft()<0)
			minToHoldAllPages = new RectI (BUFFER_LEFT, minToHoldAllPages.getTop(), minToHoldAllPages.getWidth(), minToHoldAllPages.getHeight());
		if(minToHoldAllPages.getRight()>resolution.getX())
			minToHoldAllPages = new RectI (resolution.getX() - BUFFER_RIGHT-minToHoldAllPages.getWidth(), minToHoldAllPages.getTop(), minToHoldAllPages.getWidth(), minToHoldAllPages.getHeight());

		// 6. calc x,y for all pages
		for(int i=0;i<toReturn.size();i++)
		{
			SpeechBubble page = toReturn.get(i);
			for (int j = 0 ; j < page.lines.lines.size(); ++j) 
			{
				// fill in x and y for all the pages.
				LineAndPos line = page.lines.lines.get(j);
				line.x = (int)minToHoldAllPages.getLeft();
				line.y = (int)minToHoldAllPages.getTop()+(fontHeight+lineSpacing)*j+fontHeight; 
			}

			page.rectInputInRed = new RectI(headRect.getLeft(),headRect.getTop(),headRect.getWidth(), headRect.getHeight());
			page.rectPurelyTextBoundsInYellow = minToHoldAllPages;
			page.rectBubble = new RectI(minToHoldAllPages.getLeft()-MARGIN_LEFT,(int)minToHoldAllPages.getTop()-MARGIN_TOP, (int)minToHoldAllPages.getWidth()+MARGIN_LEFT+MARGIN_RIGHT, (int)minToHoldAllPages.getHeight()+MARGIN_TOP+MARGIN_BOTTOM);
			page.bubbleOutlineWidth = bubbleLineWidth;

			// generate leader lines
			RectI max = page.rectBubble;
			{
				PointI centre = minToHoldAllPages.getCenter();

				// the mouth & centre coords are both relative to top left of viewport
				page.isVerticallyOriented = false;
				page.isPointingDown = true;//headCentre.getY() > centre.getY();
				page.isPointingRight = (headMP.getX() > centre.getX()+2);//+(resolution.getX()/4);
				page.isPointingLeft = (headMP.getX() < centre.getX()-3);//-(resolution.getX()/4);;
			}

			// with the way I've set up the DOM, it seems that
			// the paragraph ignores its top and left style value,
			// and it is positioned by the coords passed when adding to the container.
			//  ie maxBaloonRect.getLeft, and maxBalloonRect.getTop
			// This means that before and after elements also inherit these coords
			// so the Xpos need only be the increment that we add to maxBaloonRect.getLeft
			// to get to the starting point of the leader line.
			// same with max/minimumStartOfLeaderLine

			page.xPos = headMP.getX()-max.getLeft();
			//the xPos should be where the leaderline starts so that the perpendicular
			// edge of the leaderline points to the mouth..
			// but if its pointing right, the straight line is a whole leaderwidth away.
			// so we need to adjust for this.
			int radius = 6;
			int leaderWidth = 20;
			page.xPos-=(page.isPointingRight?leaderWidth:0);
			int minimumStartOfLeaderLine = radius-6;// <-- trial and error see how close to the corner we can position our leader
			int maximumStartOfLeaderLine = max.getWidth()-radius-leaderWidth+11;// <-- trial and error see how close to the corner we can position our leader
			if(page.xPos>maximumStartOfLeaderLine)
				page.xPos = maximumStartOfLeaderLine;
			if(page.xPos<minimumStartOfLeaderLine)
				page.xPos = minimumStartOfLeaderLine;

			page.halfWidthOfLeaderLine = (leaderWidth/2);
			page.heightOfLeaderLine = 2*page.halfWidthOfLeaderLine;// since they are always square
			page.afterBorderWidth = page.halfWidthOfLeaderLine - bubbleLineWidth -1;// the -1 just looks better,
			page.bubbleOutlineWidth = bubbleLineWidth;
			page.radius = radius;

			// css styles, in chrome atleast, seem to draw the border
			// outside of the viewport if I don't factor in the border below
			//page.rectInPixels = new Rect(
			//		max.getLeft(),
			//		max.getTop(),
			//		max.getWidth()-2*borderWidth+1,
			//		max.getHeight()-2*borderWidth+1);

			//toReturn.add(page);
			page.populateXAndYPointsFromBubbleRect(resolution);
		}


		return toReturn;
	}
	public static String[] getDebugStrings() {
		return new String[]{ "to be or not to be", "This sentence has larger words"};
	}

}
