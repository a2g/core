package com.github.a2g.core.objectmodel;

import java.util.ArrayList;

import com.github.a2g.core.interfaces.internal.IDrawCalls;
import com.github.a2g.core.interfaces.internal.IMeasureTextWidthAndHeight;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.RectI;
import com.github.a2g.core.primitive.RectAndLeaderLine;
import com.github.a2g.core.primitive.LinesAndMaxWidth.LineAndPos;

public class SpeechCommon 
{
/*
 *  @Remarks: mainDraw must never change the font, since the data it processes has been calculated based on an existing font type and size
 */
	public static void mainDraw(IDrawCalls canvas, RectAndLeaderLine all, ColorEnum speechColor ) 
	{
		int x = all.rectBubble.getLeft();
		int y = all.rectBubble.getTop();
		int w = all.rectBubble.getWidth();
		int h = all.rectBubble.getHeight();		

		canvas.fillRect(x, y, w, h, ColorEnum.White); 

		// red rect
		{
			RectI r = all.rectInputInRed;
			canvas.drawSinglePixelRect(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight(), INPUT_RECT_COLOR);
		}
		//canvas.drawSinglePixelRect(x, y, w, h, speechColor);
		//canvas.drawSinglePixelRect(x-1, y-1, w+2, h+2, speechColor);
		canvas.drawPolygon(all.xPoints, all.yPoints,  all.yPoints.length, ColorEnum.White, speechColor, all.bubbleOutlineWidth);

		for(int i=0;i<all.lines.lines.size();i++)
		{
			LineAndPos l  = all.lines.lines.get(i);
			canvas.drawText(l.x, l.y, l.line,  speechColor);
		}
	}
	
	
	public final static ColorEnum INPUT_RECT_COLOR = ColorEnum.Red;

	public static ArrayList<RectAndLeaderLine> getDebugLeaderLines(ColorEnum theColor, PointI resolution, IMeasureTextWidthAndHeight measurer) {
		ArrayList<RectAndLeaderLine> toReturn = new ArrayList<RectAndLeaderLine>();

		{
			RectAndLeaderLine l = new RectAndLeaderLine(); 
			l.lines.addLine(40, 45,"When CommonDraw ");
			l.lines.addLine(40, 60,"draws this rect:");
			l.lines.addLine(40, 75,"input rect should be " + INPUT_RECT_COLOR.toString());
			l.lines.addLine(40, 90,"this text should be " + theColor.toString());
			l.rectPurelyTextBoundsInYellow = new RectI (20,20,200,200);
			l.rectBubble = new RectI (20,20,190,190);
			l.rectInputInRed = new RectI (10,10,300,300);
			l.generateSampleBubbleRectFromText(measurer);
			l.populateXAndYPointsFromBubbleRect(resolution);
			toReturn.add(l);
		}
		{
			RectAndLeaderLine l = new RectAndLeaderLine(); 
			l.lines.addLine(240, 45,"To be ");
			l.lines.addLine(240, 60,"..or not..extra");
			l.lines.addLine(240, 75,"..to be");
			l.rectPurelyTextBoundsInYellow = new RectI (20,20,200,200);
			l.rectBubble = new RectI (230,25,100,100);
			l.rectInputInRed = new RectI (10,10,300,300);
			l.isPointingRight = true;
			l.generateSampleBubbleRectFromText(measurer);
			l.populateXAndYPointsFromBubbleRect(resolution);
			toReturn.add(l);
		}

		return toReturn;
	}

}

