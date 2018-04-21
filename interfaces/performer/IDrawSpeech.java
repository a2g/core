package com.github.a2g.core.interfaces.performer;

import com.github.a2g.core.primitive.ColorEnum;

public interface IDrawSpeech 
{
	
	public void setFontNameAndHeight(String name, int pixelHeight);

	public void fillRect(int x, int y, int width, int height, ColorEnum fillColor);
	public void drawSinglePixelRect(int x, int y, int w, int h, ColorEnum lineColor);
	public void fillText(double x, double y, String line, ColorEnum textColor, ColorEnum fillColor );
	
}
