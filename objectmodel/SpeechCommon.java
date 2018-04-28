package com.github.a2g.core.objectmodel;

import java.util.ArrayList;

import com.github.a2g.core.action.performer.dependencies.LinesAndMaxWidth.LineAndPos;
import com.github.a2g.core.interfaces.performer.IDrawSpeech;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.primitive.RectAndLeaderLine;

public class SpeechCommon 
{

	public static void drawSpeech(IDrawSpeech canvas, RectAndLeaderLine all, ColorEnum speechColor ) 
	{
		int x = all.rectBubble.getLeft();
		int y = all.rectBubble.getTop();
		int w = all.rectBubble.getWidth();
		int h = all.rectBubble.getHeight();		

		canvas.fillRect(x, y, w, h, ColorEnum.White);
		canvas.setFontNameAndHeight("arial", 15);

		// red rect
		{
			Rect r = all.rectInputInRed;
			canvas.drawSinglePixelRect(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight(), INPUT_RECT_COLOR);
		}
		//canvas.drawSinglePixelRect(x, y, w, h, speechColor);
		//canvas.drawSinglePixelRect(x-1, y-1, w+2, h+2, speechColor);
		canvas.drawPolygon(all.xPoints, all.yPoints,  all.yPoints.length, ColorEnum.White, speechColor);

		for(int i=0;i<all.lines.lines.size();i++)
		{
			LineAndPos l  = all.lines.lines.get(i);
			canvas.drawText(l.x, l.y, l.line,  speechColor);
		}
		
	

	}
	public final static ColorEnum INPUT_RECT_COLOR = ColorEnum.Red;

	public static ArrayList<RectAndLeaderLine> getDebugLeaderLines(ColorEnum theColor, PointI resolution) {
		ArrayList<RectAndLeaderLine> toReturn = new ArrayList<RectAndLeaderLine>();

		{
			RectAndLeaderLine l = new RectAndLeaderLine(); 
			l.lines.addLine(40, 45,"When CommonDraw ");
			l.lines.addLine(40, 60,"draws this rect:");
			l.lines.addLine(40, 75,"input rect should be " + INPUT_RECT_COLOR.toString());
			l.lines.addLine(40, 90,"this text should be " + theColor.toString());
			l.rectPurelyTextBoundsInYellow = new Rect (20,20,200,200);
			l.rectBubble = new Rect (20,20,190,190);
			l.rectInputInRed = new Rect (10,10,300,300);
			l.populateXAndYPointsFromBubbleRect(resolution);
			toReturn.add(l);
		}
		{
			RectAndLeaderLine l = new RectAndLeaderLine(); 
			l.lines.addLine(240, 45,"To be ");
			l.lines.addLine(240, 60,"Or not:");
			l.lines.addLine(240, 75,"tobe");
			l.rectPurelyTextBoundsInYellow = new Rect (20,20,200,200);
			l.rectBubble = new Rect (230,25,100,100);
			l.rectInputInRed = new Rect (10,10,300,300);
			l.isPointingRight = true;
			l.populateXAndYPointsFromBubbleRect(resolution);
			toReturn.add(l);
		}

		return toReturn;
	}

}

