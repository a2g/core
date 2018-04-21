package com.github.a2g.core.action.performer.dependencies;

import java.util.ArrayList;

import com.github.a2g.core.action.performer.dependencies.LinesAndMaxWidth.LineAndPos;
import com.github.a2g.core.interfaces.internal.IMeasureTextWidthAndHeight;
import com.github.a2g.core.interfaces.performer.IDrawSpeech;
import com.github.a2g.core.platforms.html5.dependencies.CanvasEtcHtml5;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.primitive.RectAndLeaderLine;
import com.google.gwt.touch.client.Point;

public class CommonDraw 
{

	public static void drawSpeech(IDrawSpeech canvas, RectAndLeaderLine all, ColorEnum speechColor ) 
	{
		int x = all.rectTextAndMarginsInOlive.getLeft();
		int y = all.rectTextAndMarginsInOlive.getTop();
		int w = all.rectTextAndMarginsInOlive.getWidth();
		int h = all.rectTextAndMarginsInOlive.getHeight();		

		canvas.fillRect(x, y, w, h, ColorEnum.White);
		canvas.setFontNameAndHeight("Times New Roman", 15);

		// white for text background
		canvas.fillText(0, 0, "blahblah", speechColor, ColorEnum.White);

		// red rect
		{
			Rect r = all.rectInputInRed;
			canvas.drawSinglePixelRect(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight(), ColorEnum.Red);
		}
		canvas.drawSinglePixelRect(x, y, w, h, speechColor);
		canvas.drawSinglePixelRect(x-1, y-1, w+2, h+2, speechColor);

		for(int i=0;i<all.lines.lines.size();i++)
		{
			LineAndPos l  = all.lines.lines.get(i);
			canvas.fillText(l.x, l.y, l.line,  speechColor, ColorEnum.White);
		}


	}

}

