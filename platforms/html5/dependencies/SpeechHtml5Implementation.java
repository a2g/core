package com.github.a2g.core.platforms.html5.dependencies;

import com.github.a2g.core.interfaces.performer.IDrawSpeech;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.canvas.dom.client.Context2d;

public class SpeechHtml5Implementation implements IDrawSpeech{


	Context2d context; 

	public SpeechHtml5Implementation(Context2d g)
	{
		this.context = g;
	}
	@Override
	public void drawText(double x, double y, String line, ColorEnum fillColor) {
		context.beginPath();
		context.setFillStyle(fillColor.toString());
		//context.setStrokeStyle(textColor.toString());
		context.fillText(line, x, y);
		context.closePath();
	}

	@Override
	public void fillRect(int left, int top, int width, int height, ColorEnum fillColor) {
		context.beginPath();
		context.setFillStyle(fillColor.toString());
		context.fillRect(left, top, width, height);
		context.closePath();
	}

	@Override
	public void drawSinglePixelRect(int x, int y, int w, int h, ColorEnum lineColor) {
		context.beginPath();
		context.setStrokeStyle(lineColor.toString());
		context.rect(x, y, w, h);
		context.closePath();
	}
	
	@Override
	public void setFontNameAndHeight(String fontName, int fontHeight) {
 
		context.setFont(""+fontHeight+"px \""+fontName+"\"");
	}
	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints, ColorEnum fillColor, ColorEnum lineColor) {
		// https://www.kirupa.com/html5/drawing_triangles_on_the_canvas.htm
		context.beginPath();
		
		for(int i=0;i<nPoints;i++)
		{
			if(i==0)
			{
				context.moveTo(xPoints[i], yPoints[i]);
			}else
			{
				context.lineTo(xPoints[i], yPoints[i]);
			}
		}
		context.closePath();
		
		// the outline
		context.setLineWidth(10);
		context.setStrokeStyle(lineColor.toString().toLowerCase());
		context.stroke();
		 
		// the fill color
		context.setFillStyle(fillColor.toString().toLowerCase());
		context.fill();
	}
	 
}
