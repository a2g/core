package com.github.a2g.core.platforms.swing.dependencies;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

public class DrawSpeechSwingLabel
{
	// to use BufferedImage bufferedImage = new BufferedImage(	resolution.getX(),	resolution.getY(),	BufferedImage.TYPE_INT_RGB);

	public static BufferedImage draw(RectAndLeaderLine all, ColorEnum speechColor, PointI resolution) 
	{
		Rect speechRect = all.rectInputInRed;
		BufferedImage  bufferedImage = new BufferedImage(
				speechRect.getWidth(),
				speechRect.getHeight(),
				BufferedImage.TYPE_INT_RGB);


		Graphics2D imageGraphics = bufferedImage.createGraphics();
		GradientPaint gp = new GradientPaint(
				20f,
				20f,
				Color.yellow,
				380f,
				280f,
				Color.white);
		imageGraphics.setPaint(gp);
		imageGraphics.fillRect(0, 0, speechRect.getWidth(), speechRect.getHeight());

		for(int i=0;i<all.lines.lines.size();i++)
		{
			LineAndPos s = all.lines.lines.get(i);

			imageGraphics.setFont(new Font("arial",10,10));
			imageGraphics.setColor(Color.red);
			imageGraphics.drawString(s.line, s.x, s.y);

			String html = "<html><body style='padding: 4px;"
					//+"height: "+speechRect.getHeight()+"px;"
					+"width: "+(speechRect.getWidth()*.7)+"px; '>"
					+ all.lines.lines.get(i).line;

			JLabel textLabel = new JLabel(html);

			textLabel.setSize(textLabel.getPreferredSize());
			//Dimension size = textLabel.getPreferredSize();
			Dimension size = new Dimension(speechRect.getWidth(), speechRect.getHeight() );
			textLabel.setSize(size);

			Dimension d = new Dimension(speechRect.getWidth(), speechRect.getHeight() );
			if(d.width>0)
			{
				BufferedImage bi = new BufferedImage(
						d.width,
						d.height,
						BufferedImage.TYPE_INT_ARGB);
				/*
				imageGraphics.fillRoundRect(
						0,
						0,
						speechRect.getWidth(),
						speechRect.getHeight(),
						10,
						10);
						*/
				textLabel.paint(imageGraphics); 
				//Rect r = speechRect;
				//g2.drawImage(bi, r.getLeft(), r.getTop(), r.getRight(), r.getBottom(), this);


				//ImageIcon ii = new ImageIcon(bufferedImage);
				//JLabel imageLabel = new JLabel(ii);
				//this.add(imageLabel);
			}

		}
		return bufferedImage;
	}
}
