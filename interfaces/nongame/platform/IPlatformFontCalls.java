package com.github.a2g.core.interfaces.nongame.platform;

import com.github.a2g.core.interfaces.nongame.platform.singles.IMeasureTextWidthAndHeight;
import com.github.a2g.core.interfaces.nongame.platform.singles.ISetFontNameAndHeight;

/*
 * An abstraction of a font object on both GWT and Java AWT
 */
public interface IPlatformFontCalls extends
	IMeasureTextWidthAndHeight,
	ISetFontNameAndHeight
{
}
