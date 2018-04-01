package com.github.a2g.core.action.performer.dependencies;

import java.util.ArrayList;

import com.github.a2g.core.interfaces.internal.IMeasureTextWidth;

public class SplitLines 
{
	
	static public ArrayList<String>  splitLines(IMeasureTextWidth ctx, double maxWidthBeforeWrapping, String speech) 
	{
		// We give a little "padding"
		// This should probably be an input param
		// but for the sake of simplicity we will keep it
		// this way
		maxWidthBeforeWrapping = maxWidthBeforeWrapping - 10;
		// We setup the text font to the context (if not already)
	 
		// We split the text by words 
		String[] words = speech.split(" ");
		String new_line = words[0];
		ArrayList<String> lines = new ArrayList<String>();
		for(int i = 1; i < words.length; ++i) {
			String nextBit = new_line + " " + words[i];
			double width = ctx.measureTextWidth(nextBit);
			if (width < maxWidthBeforeWrapping) {
				new_line += " " + words[i];
			} else {
				lines.add(new_line);
				new_line = words[i];
			}
		}
		lines.add(new_line);
		// DEBUG 
		// for(var j = 0; j < lines.length; ++j) {
		//    console.log("line[" + j + "]=" + lines[j]);
		// }
		return lines;
	}
}
