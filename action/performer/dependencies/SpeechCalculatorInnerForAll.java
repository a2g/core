package com.github.a2g.core.action.performer.dependencies;

import java.util.ArrayList;

import com.github.a2g.core.interfaces.internal.IContext2d;
import com.github.a2g.core.interfaces.internal.IMeasureTextWidth;
import com.github.a2g.core.primitive.Rect;
import com.google.gwt.touch.client.Point;

public class SpeechCalculatorInnerForAll {
	 private Rect debugRect;
	private Rect oliveRect;
	private Rect yellowRect;
	ArrayList<String> lines;
	ArrayList<Point> points;
	  public SpeechCalculatorInnerForAll(String speechText, Rect maxRect, int fontHeight, IMeasureTextWidth context)
      {
		  int marginTop = 5;
		  int marginLeft = 5;
		  int marginRight = 5;
		  int marginBottom = 5;
    	  int x = maxRect.getLeft()+marginLeft;
			int y = maxRect.getTop()+marginTop;
			int w = maxRect.getWidth()-marginLeft-marginRight;
			maxRect.getHeight();
			int lineSpacing = 3; 
			int ygjsetc = 5;

		
			debugRect = new Rect(x,y,maxRect.getWidth(), maxRect.getHeight());
			// Paint text
			lines = SpeechCalculatorOuterForAll.splitLines(context, w, speechText);
			points = new ArrayList<Point>();
			
			// Block of text height
			int both = lines.size() * (fontHeight + lineSpacing + ygjsetc);
			//if (both < h) 
			
			double lowestY = y;
			double lowestX = 10000;
			double largestWidth = 0;
			{
				// We determine the y of the first line
				int ly = y;
				
				ly+=fontHeight+lineSpacing;
				double lx = 0;
				for (int j = 0 ; j < lines.size(); ++j, ly+=fontHeight+lineSpacing) 
				{
					// We continue to centralize the lines
					String line = lines.get(j);
					double widthOfText = context.measureTextWidth(line);
					lx = x+(w/2)  -   (context.measureTextWidth(line))/2;
					// DEBUG 
					//console.log("ctx2d.fillText('"+ lines[j] +"', "+ lx +", " + ly + ")");
					
					points.add( new Point(lx,ly));
					
					if(lx<lowestX)
						lowestX = lx;
					if(widthOfText>largestWidth)
						largestWidth = widthOfText;
				}
				
				
				oliveRect = new Rect((int)lowestX-marginLeft,(int)lowestY-marginTop, (int)largestWidth+marginLeft+marginRight, (int)both+marginTop+marginBottom);
				// now 
				
				yellowRect = new Rect((int)lowestX,(int)lowestY, (int)largestWidth, (int)both);
				
			
			
			}
      }

	public Rect getOuterRect() {
		return oliveRect;
	}

	public Rect getInnerRect() {
	 
		return yellowRect;
	}
	
	public Rect getDebugRect() {
		 
		return debugRect;
	}

	public ArrayList<String> getLines() {
		return lines;
	}
	public ArrayList<Point> getPoints() {
		return points;
	}
}
