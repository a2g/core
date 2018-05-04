package com.github.a2g.core.primitive;

import java.util.ArrayList;

import com.github.a2g.core.interfaces.internal.IMeasureTextWidthAndHeight;

public class LinesAndMaxWidth
{
	public class LineAndPos
	{
		public LineAndPos(String line, double lineWidth)
		{
			this.line = line;
			this.lineWidth = lineWidth;
		}
		public LineAndPos(int x, int y, String string) {
			this.line = string;
			this.x = x;
			this.y = y;
			this.lineWidth = 0;
		}
		
		public String line;
		public int x;
		public int y;
		public double lineWidth;
		public double startingTime;
	}
	
	public LinesAndMaxWidth()
	{
		this.lines = new ArrayList<LineAndPos>();
		this.maxWidth = 0;
	}
	
	public void addLine(String line, double width)
	{
		lines.add(new LineAndPos(line, width));
		this.maxWidth = Math.max(this.maxWidth,  width);
	}
	public LineAndPos addLine(int x, int y, String string)
	{
		LineAndPos a = new LineAndPos(x,y,string);
		lines.add(a);
		return a;
	}
	
	public void addLine(String line, double width, int x, int y)
	{
		LineAndPos toAdd = new LineAndPos(line, width);
		toAdd.x = x;
		toAdd.y = y;
		lines.add(toAdd);
		this.maxWidth = Math.max(this.maxWidth,  width);
	}
	public ArrayList<LineAndPos> lines;
	public double maxWidth;
	 
	static public LinesAndMaxWidth  getArrayOfLinesSplitOnSpaceAndWidth(IMeasureTextWidthAndHeight ctx, double maxWidthBeforeWrapping, String speech) 
	{
		// We give a little "padding"
		// This should probably be an input param
		// but for the sake of simplicity we will keep it
		// this way
		maxWidthBeforeWrapping = maxWidthBeforeWrapping - 10;
		// We setup the text font to the context (if not already)
	 
		// We split the text by words 
		String[] words = speech.split(" ");
		String lastLine = words[0];
		double lastWidth = ctx.measureTextWidthAndHeight(lastLine).getX();
	
		LinesAndMaxWidth toReturn = new LinesAndMaxWidth();
		
		
		for(int i = 1; i < words.length; ++i) {
			String testSentence = lastLine + " " + words[i];
			double testWidth = ctx.measureTextWidthAndHeight(testSentence).getX();
			if (testWidth < maxWidthBeforeWrapping) {
				lastLine += " " + words[i];
				lastWidth = testWidth;
			} else {
				
				if(lastWidth > toReturn.maxWidth )
					toReturn.maxWidth = lastWidth;
				toReturn.addLine(lastLine, lastWidth);
				lastLine = words[i];
			}
		}
		toReturn.addLine(lastLine, lastWidth);
		// DEBUG 
		// for(var j = 0; j < lines.length; ++j) {
		//    console.log("line[" + j + "]=" + lines[j]);
		// }
		return toReturn;
	}
}
