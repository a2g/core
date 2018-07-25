package com.github.a2g.core.interfaces.platform;

import com.github.a2g.core.interfaces.internal.IMeasureTextWidthAndHeight;
import com.github.a2g.core.interfaces.internal.ISetFontNameAndHeight;

/*
 * An abstraction of a font object on both GWT and Java AWT
 */
public interface IPlatformFontCalls extends
	IMeasureTextWidthAndHeight,
	ISetFontNameAndHeight
{
}
