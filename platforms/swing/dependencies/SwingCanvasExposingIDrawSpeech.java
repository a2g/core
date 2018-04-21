package com.github.a2g.core.platforms.swing.dependencies;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import com.github.a2g.core.interfaces.performer.IDrawSpeech;
import com.github.a2g.core.primitive.ColorEnum;

public class SwingCanvasExposingIDrawSpeech implements IDrawSpeech
{ 
	Graphics graphics; 
	
	public SwingCanvasExposingIDrawSpeech(Graphics g)
	{
		this.graphics = g;
	}

	@Override
	public void setFontNameAndHeight(String name, int pixelHeight) {
		graphics.setFont(new Font(name, Font.BOLD, pixelHeight));
		
	}

	@Override
	public void fillRect(int x, int y, int width, int height, ColorEnum fillColor) {
		graphics.setColor(new Color(fillColor.r, fillColor.g, fillColor.b));
		graphics.fillRect(x, y, width, height);
	}

	@Override
	public void drawSinglePixelRect(int x, int y, int width, int height, ColorEnum fillColor) {
	    graphics.setColor(new Color(fillColor.r, fillColor.g, fillColor.b));
		graphics.drawRect(x, y, width, height);
	}

	@Override
	public void fillText(double x, double y, String line, ColorEnum textColor, ColorEnum fillColor) {
		graphics.setColor(new Color(textColor.r, textColor.g, textColor.b));
		graphics.drawString(line, (int)x, (int)y);
		
	}
	
}
/*
g.fillRoundRect(
		0,
		0,
		speechRect.getWidth(),
		speechRect.getHeight(),
		10,
		10);
g.setColor(Color.black);

Graphics g2 = bufferedImage.getGraphics();
for(int i=0;i<all.lines.lines.size();i++)
{
	LineAndPos s = all.lines.lines.get(i);
	g2.drawString(s.line, s.x, s.y);
}


g2.drawRect(0+1, 0+1, speechRect.getWidth()-3, speechRect.getHeight()-3);
g2.drawRect(0, 0, speechRect.getWidth()-1, speechRect.getHeight()-1);*/