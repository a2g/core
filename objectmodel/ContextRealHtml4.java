package com.github.a2g.core.objectmodel;

import com.github.a2g.core.interfaces.internal.IContext2d;
import com.google.gwt.canvas.dom.client.Context2d;

public class ContextRealHtml4 
implements IContext2d
{
	private Context2d context;

	ContextRealHtml4(Context2d context)
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
