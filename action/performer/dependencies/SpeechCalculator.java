package com.github.a2g.core.action.performer.dependencies;

import java.util.ArrayList;

import com.github.a2g.core.action.performer.dependencies.LinesAndMaxWidth.LineAndPos;
import com.github.a2g.core.interfaces.internal.IMeasureTextWidthAndHeight;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.primitive.RectAndLeaderLine;

public class SpeechCalculator 
{
	
 
	static public ArrayList<RectAndLeaderLine> calculate(String[] pages, Rect maxRectI, int radius, PointI mouth, int leaderWidth, int borderWidth, IMeasureTextWidthAndHeight context)
	{
			
		// 1. create return value
		ArrayList<RectAndLeaderLine> toReturn = new ArrayList<RectAndLeaderLine>();

		// 2. set up margins
		int marginTop = 5;
		int marginLeft = 5;
		int marginRight = 5;
		int marginBottom = 5;
		int w = maxRectI.getWidth()-marginLeft-marginRight;

		// 3. first do one iteration to find the longest textwidth
		double maxWidth = 0;
		for(int i=0;i<pages.length;i++)
		{
			LinesAndMaxWidth lines = LinesAndMaxWidth.getArrayOfLinesSplitOnSpaceAndWidth(context, w, pages[i]);
			RectAndLeaderLine pageOfSpeech = new RectAndLeaderLine();
			pageOfSpeech.lines = lines;
			if(lines.maxWidth>maxWidth)
				maxWidth = lines.maxWidth;
			toReturn.add(pageOfSpeech);
		}
			
		// 4. calc starting x,y
		int x = maxRectI.getLeft()+marginLeft;
		int y = maxRectI.getTop()+marginTop;
		maxRectI.getHeight();
		int lineSpacing = 3; 
		int ygjsetc = 5;
		// Block of text height
		int fontHeight = (int)context.measureTextWidthAndHeight("blah").getY();
	
		// 4. calc x,y for all pages
		for(int i=0;i<toReturn.size();i++)
		{
			double lowestY = y;
			double lowestX = 10000;
			double largestWidth = 0;

			// We determine the y of the first line
			int ly = y+=fontHeight+lineSpacing;
			double lx = 0;

			RectAndLeaderLine page = toReturn.get(i);
			int both = page.lines.lines.size() * (fontHeight + lineSpacing + ygjsetc);

			for (int j = 0 ; j < page.lines.lines.size(); ++j, ly+=fontHeight+lineSpacing) 
			{
				// We continue to centralize the lines
				LineAndPos line = page.lines.lines.get(j);
				String speech = line.line;
				//double widthOfText = context.measureTextWidthAndHeight(line).getX();
				//lx = x+(w/2)  - widthOfText/2;
				// DEBUG 
				//console.log("ctx2d.fillText('"+ lines[j] +"', "+ lx +", " + ly + ")");

				line.x = (int)lx;
				line.y = ly; 

				if(lx<lowestX)
					lowestX = lx;
				if(line.lineWidth>largestWidth)
					largestWidth = line.lineWidth;
			}

			page.rectInputInRed = new Rect(x,y,maxRectI.getWidth(), maxRectI.getHeight());
			page.rectPurelyTextBoundsInYellow = new Rect((int)lowestX,(int)lowestY, (int)largestWidth, (int)both);
			page.rectTextAndMarginsInOlive = new Rect((int)lowestX-marginLeft,(int)lowestY-marginTop, (int)largestWidth+marginLeft+marginRight, (int)both+marginTop+marginBottom);
			// now 

			// generate leader lines
			Rect max = page.rectTextAndMarginsInOlive;
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
			
			toReturn.add(page);
		}
		return toReturn;
	}


	

}
