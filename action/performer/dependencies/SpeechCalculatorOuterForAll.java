package com.github.a2g.core.action.performer.dependencies;

import java.util.ArrayList;

import com.github.a2g.core.interfaces.internal.IContext2d;
import com.github.a2g.core.interfaces.internal.IMeasureTextWidth;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.primitive.RectAndLeaderLine;

public class SpeechCalculatorOuterForAll {
	
 
	static public RectAndLeaderLine calculate(String speech, Rect maxRect, int radius, PointI mouth, int leaderWidth, int borderWidth, IMeasureTextWidth context)
	{
		RectAndLeaderLine ret = new RectAndLeaderLine();
		int fontHeight = 10;
		
		SpeechCalculatorInnerForAll fittedRect = new SpeechCalculatorInnerForAll(speech, maxRect, fontHeight, context);
		Rect max = fittedRect.getOuterRect();
		PointI centre = max.getCenter();

		// the mouth & centre coords are both relative to top left of viewport
		ret.isFromTop = mouth.getY() < centre.getY();
		ret.isPointingRight = mouth.getX() > centre.getX();

		// with the way I've set up the DOM, it seems that
		// the paragraph ignores its top and left style value,
		// and it is positioned by the coords passed when adding to the container.
		//  ie maxBaloonRect.getLeft, and maxBalloonRect.getTop
		// This means that before and after elements also inherit these coords
		// so the Xpos need only be the increment that we add to maxBaloonRect.getLeft
		// to get to the starting point of the leader line.
		// same with max/minimumStartOfLeaderLine

		ret.xPos = mouth.getX()-max.getLeft();
		//the xPos should be where the leaderline starts so that the perpendicular
		// edge of the leaderline points to the mouth..
		// but if its pointing right, the straight line is a whole leaderwidth away.
		// so we need to adjust for this.
		ret.xPos-=(ret.isPointingRight?leaderWidth:0);
		int minimumStartOfLeaderLine = radius-6;// <-- trial and error see how close to the corner we can position our leader
		int maximumStartOfLeaderLine = max.getWidth()-radius-leaderWidth+11;// <-- trial and error see how close to the corner we can position our leader
		if(ret.xPos>maximumStartOfLeaderLine)
			ret.xPos = maximumStartOfLeaderLine;
		if(ret.xPos<minimumStartOfLeaderLine)
			ret.xPos = minimumStartOfLeaderLine;

		ret.halfWidthOfLeaderLine = (leaderWidth/2);
		ret.heightOfLeaderLine = 2*ret.halfWidthOfLeaderLine;// since they are always square
		ret.afterBorderWidth = ret.halfWidthOfLeaderLine - borderWidth -1;// the -1 just looks better,
		ret.borderWidth = borderWidth;
		ret.radius = radius;

		// css styles, in chrome atleast, seem to draw the border
		// outside of the viewport if I don't factor in the border below
		ret.rectInPixels = new Rect(
				max.getLeft(),
				max.getTop(),
				max.getWidth()-2*borderWidth+1,
				max.getHeight()-2*borderWidth+1);
		return ret;
	}

	 
	
	static public ArrayList<String>  splitLines(IMeasureTextWidth ctx, double maxWidthBeforeWrapping, String speech) 
	{
		// We give a little "padding"
		// This should probably be an input param
		// but for the sake of simplicity we will keep it
		// this way
		maxWidthBeforeWrapping = maxWidthBeforeWrapping - 10;
		// We setup the text font to the context (if not already)
	 
		// We split the text by words 
		String[] words = speech.split(" ");
		String new_line = words[0];
		ArrayList<String> lines = new ArrayList<String>();
		for(int i = 1; i < words.length; ++i) {
			String nextBit = new_line + " " + words[i];
			double width = ctx.measureTextWidth(nextBit);
			if (width < maxWidthBeforeWrapping) {
				new_line += " " + words[i];
			} else {
				lines.add(new_line);
				new_line = words[i];
			}
		}
		lines.add(new_line);
		// DEBUG 
		// for(var j = 0; j < lines.length; ++j) {
		//    console.log("line[" + j + "]=" + lines[j]);
		// }
		return lines;
	}

	

}
