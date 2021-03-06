package com.github.a2g.core.platforms.html5.dependencies;

import com.github.a2g.core.interfaces.nongame.platform.IPlatformDrawCalls;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.canvas.dom.client.Context2d;

public class PlatformDrawCallsHtml5 implements IPlatformDrawCalls
{
	Context2d context; 

	public PlatformDrawCallsHtml5(Context2d g)
	{
		this.context = g;
	}
	
	@Override
	public void drawText(double x, double y, String line, ColorEnum fillColor) {
		context.beginPath();
		context.setFillStyle(fillColor.toString().toLowerCase());
		//context.setStrokeStyle(textColor.toString());
		context.fillText(line, x, y);
		context.closePath();
	}

	@Override
	public void fillRect(int left, int top, int width, int height, ColorEnum fillColor) {
		context.beginPath();
		context.setFillStyle(fillColor.toString().toLowerCase());
		context.fillRect(left, top, width, height);
		context.closePath();
	}

	@Override
	public void drawSinglePixelRect(int x, int y, int w, int h, ColorEnum lineColor) {
		context.beginPath();
		context.setStrokeStyle(lineColor.toString().toLowerCase());
		context.rect(x, y, w, h);
		context.closePath();
	}
	

	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints, ColorEnum fillColor, ColorEnum lineColor, int brushWidth) {
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
		context.setLineWidth(brushWidth);
		context.setStrokeStyle(lineColor.toString().toLowerCase());
		context.stroke();
		 
		// the fill color
		context.setFillStyle(fillColor.toString().toLowerCase());
		context.fill();
	}
}
