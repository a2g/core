package com.github.a2g.core.objectmodel;

import java.util.ArrayList;

import com.github.a2g.core.interfaces.nongame.platform.IPlatformDrawCalls;
import com.github.a2g.core.interfaces.nongame.platform.singles.IMeasureTextWidthAndHeight;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.RectI;
import com.github.a2g.core.primitive.SpeechBubble;
import com.github.a2g.core.primitive.LinesAndMaxWidth.LineAndPos;

public class SpeechCommon 
{
/*
 *  @Remarks: mainDraw must never change the font, since the data it processes has been calculated based on an existing font type and size
 */
	public static void mainDraw(IPlatformDrawCalls canvas, SpeechBubble all, ColorEnum speechColor, ColorEnum backgroundColor, boolean isDiagnosticsDisplayed ) 
	{
		int x = all.rectBubble.getLeft();
		int y = all.rectBubble.getTop();
		int w = all.rectBubble.getWidth();
		int h = all.rectBubble.getHeight();		

		canvas.fillRect(x, y, w, h, backgroundColor); 

		// red rect
		if(isDiagnosticsDisplayed){
			RectI r = all.rectInputInRed;
			canvas.drawSinglePixelRect(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight(), INPUT_RECT_COLOR);
		}
 
		canvas.drawPolygon(all.xPoints, all.yPoints,  all.yPoints.length, backgroundColor, speechColor, all.bubbleOutlineWidth);

		for(int i=0;i<all.lines.lines.size();i++)
		{
			LineAndPos l  = all.lines.lines.get(i);
			canvas.drawText(l.x, l.y, l.lineText,  speechColor);
		}
	}
	
	
	public final static ColorEnum INPUT_RECT_COLOR = ColorEnum.Red;

	public static ArrayList<SpeechBubble> getDebugLeaderLines(ColorEnum theColor, PointI resolution, IMeasureTextWidthAndHeight measurer) {
		ArrayList<SpeechBubble> toReturn = new ArrayList<SpeechBubble>();

		{
			SpeechBubble l = new SpeechBubble(); 
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
			SpeechBubble l = new SpeechBubble(); 
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

