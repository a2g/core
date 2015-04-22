package com.github.a2g.core.objectmodel;

public class PrerecordedCommand {

	 int verb;
	 int obj1;
	 int obj2;
	 
	public PrerecordedCommand(int verb, int obj1, int obj2)
	{
		this.verb = verb;
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	
	int getVerb(){ return verb;}
	int getObj1(){ return obj1;}
	int getObj2(){ return obj2;}
}
