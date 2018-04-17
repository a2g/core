package com.github.a2g.core.platforms.html5.dependencies;


import com.github.a2g.core.action.performer.dependencies.LinesAndMaxWidth.LineAndPos;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.primitive.RectAndLeaderLine; 

public class DrawSpeechHtml5 {

	public static void make(CanvasEtcHtml5 canvasEtcHtml5, RectAndLeaderLine all, ColorEnum speechColor ) {
		canvasEtcHtml5.setFillStyle(ColorEnum.White.toString());
		canvasEtcHtml5.setFont("16px \"Times New Roman\"");
		Rect r = all.rectTextAndMarginsInOlive;// getRectGivenSpeechAndMaxRect(this.speechText,
								// this.speechMaxRect, backBufferContext);
		canvasEtcHtml5.fillRect(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight());
		canvasEtcHtml5.setFillStyle(speechColor.name());
		//canvasEtcHtml5.fillText(all.speeech, r.getLeft(), r.getTop(), r.getWidth());

		int x = r.getLeft();
		int y = r.getTop();
		int w = r.getWidth();
		int h = r.getHeight();

		canvasEtcHtml5.setLineWidth(1);
		canvasEtcHtml5.setStrokeStyle("black");
		canvasEtcHtml5.strokeRect(x, y, w, h);

		for(int i=0;i<all.lines.lines.size();i++)
		{
			LineAndPos l  = all.lines.lines.get(i);
			canvasEtcHtml5.fillText(l.line, l.x, l.y);
		}
		
		
	}

}
