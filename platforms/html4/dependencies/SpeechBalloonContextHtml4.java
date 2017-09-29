package com.github.a2g.core.platforms.html4.dependencies;

import com.github.a2g.core.interfaces.internal.IContext2d;
import com.google.gwt.canvas.dom.client.Context2d;

public class SpeechBalloonContextHtml4 
implements IContext2d
{
	private Context2d context;

	public SpeechBalloonContextHtml4(Context2d context)
	{
		this.context = context;
	}

	@Override
	public void setFont(String font) {
		context.setFont(font);
	}

	@Override
	public double measureTextWidth(String text) {
		double d = context.measureText(text).getWidth();
		return d;
	}
}
