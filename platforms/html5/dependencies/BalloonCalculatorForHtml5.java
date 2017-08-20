package com.github.a2g.core.platforms.html5.dependencies;

import java.util.ArrayList;

import com.github.a2g.core.interfaces.internal.IContext2d;
import com.github.a2g.core.objectmodel.SpeechBalloonCalculator;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Rect;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.touch.client.Point;

public class BalloonCalculatorForHtml5 {
	 private Rect strokeRect;
	private Rect oliveRect;
	private Rect yellowRect;
	ArrayList<String> lines;
	ArrayList<Point> points;
	  public BalloonCalculatorForHtml5(String speechText, Rect maxRect, int fontHeight, IContext2d context)
      {
		  int marginTop = 5;
		  int marginLeft = 5;
		  int marginRight = 5;
		  int marginBottom = 5;
    	  int x = maxRect.getLeft()+marginLeft;
			int y = maxRect.getTop()+marginTop;
			int w = maxRect.getWidth()-marginLeft-marginRight;
			int h = maxRect.getHeight()-marginTop-marginBottom;
			int lineSpacing = 3; 
			int ygjsetc = 5;

		
			strokeRect = new Rect(x,y,maxRect.getWidth(), maxRect.getHeight());
			// Paint text
			lines = SpeechBalloonCalculator.splitLines(context, w, "arial", speechText);
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

	public Rect getOliveRect() {
		return oliveRect;
	}

	public Rect getYellowRect() {
		// TODO Auto-generated method stub
		return yellowRect;
	}

	public ArrayList<String> getLines() {
		return lines;
	}
	public ArrayList<Point> getPoints() {
		return points;
	}
}
