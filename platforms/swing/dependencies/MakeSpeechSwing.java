package com.github.a2g.core.platforms.swing.dependencies;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import com.github.a2g.core.action.performer.dependencies.LinesAndMaxWidth.LineAndPos;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.primitive.RectAndLeaderLine; 

public class MakeSpeechSwing 
{
	// to use BufferedImage bufferedImage = new BufferedImage(	resolution.getX(),	resolution.getY(),	BufferedImage.TYPE_INT_RGB);

	public static BufferedImage make(BufferedImage bufferedImage, RectAndLeaderLine all, ColorEnum speechColor) 
	{
	 Rect speechRect = all.rectInputInRed;
		
	 
		Graphics2D imageGraphics = bufferedImage.createGraphics();
		GradientPaint gp = new GradientPaint(
				20f,
				20f,
				Color.white,
				380f,
				280f,
				Color.white);
		imageGraphics.setPaint(gp);
		imageGraphics.fillRect(0, 0, speechRect.getWidth(), speechRect.getHeight());


		Dimension d = new Dimension(speechRect.getWidth(), speechRect.getHeight() );
		if(d.width>0)
		{
			BufferedImage bi = new BufferedImage(
					d.width,
					d.height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.createGraphics();
			g.setColor(new Color(255, 255, 255, 128));//white, semi-transparent

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

			g2.setColor(new Color(speechColor.r, speechColor.g, speechColor.b));
			g2.drawRect(0+1, 0+1, speechRect.getWidth()-3, speechRect.getHeight()-3);
			g2.drawRect(0, 0, speechRect.getWidth()-1, speechRect.getHeight()-1);
		}
		return bufferedImage;

	}

 
 
}
