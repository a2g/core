package com.github.a2g.core.interfaces.performer;

import com.github.a2g.core.interfaces.internal.IMeasureTextWidthAndHeight;

public interface ISpeechCommonExpandedSet 
extends ISpeechCommonMainDraw, 
IMeasureTextWidthAndHeight
{
	
	public void setFontNameAndHeight(String name, int pixelHeight);
 
}
