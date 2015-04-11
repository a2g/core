package com.github.a2g.core.objectmodel;

import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Rect;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
class MyPanel extends SimplePanel
{
	public MyPanel()
	{
		super(Document.get().createPElement());
	}
	public ParagraphElement getPE()
	{
		Element p = this.getElement();
		return (ParagraphElement)p;
	}
}

public class SceneSpeechBalloon extends VerticalPanel 
{
	FlowPanel before;
	MyPanel  pe;
	FlowPanel after;
	ColorEnum borderColor;
	int left;
	int top;
	private int widthInPixels;
	private int heightInPixels;

	public SceneSpeechBalloon() {
		this.borderColor = ColorEnum.Red;
		widthInPixels = 100;
		heightInPixels = 100;
		top = 3;
		left = 0;
		before = new FlowPanel();
		pe = new MyPanel();
		after = new FlowPanel();

		this.add(before);
		this.add(pe);
		this.add(after);

		// before
		before.getElement().getStyle().setProperty("content", "");
		before.getElement().getStyle().setProperty("position", "absolute");
		before.getElement().getStyle().setProperty("width", "0");
		before.getElement().getStyle().setProperty("height", "0");
		before.getElement().getStyle().setProperty("right", "35px");
		before.getElement().getStyle().setProperty("bottom", "100px");
		

		// after
		after.getElement().getStyle().setProperty("content", "");
		after.getElement().getStyle().setProperty("position","absolute");
		after.getElement().getStyle().setProperty("width", "0");
		after.getElement().getStyle().setProperty("height","0");
		after.getElement().getStyle().setProperty("right", "38px");
		after.getElement().getStyle().setProperty("bottom", "100px");
	
		pe.getPE().getStyle().setProperty("textAlign", "center");
		pe.getPE().getStyle().setProperty("lineHeight", "100px");
		pe.getPE().getStyle().setProperty("backgroundColor", "#fff");


		//drop shadow makes it look worse.
		//		pe.getStyle().setProperty("WebkitBoxShadow", "2px 2px 4px #888");
		//		pe.getStyle().setProperty("MozBoxShadow", "2px 2px 4px #888");
		//		pe.getStyle().setProperty("boxShadow", "2px 2px 4px #888");

	}

	void setBorderColor(ColorEnum talkingColor)		
	{
		borderColor = talkingColor;
		if (talkingColor != null) 
		{
			pe.getPE().getStyle().setProperty("borderColor", talkingColor.toString());
		}
	}

	public void setText(String speech) {
		pe.getPE().setInnerText(speech);
	}

	public void setVisible(boolean visible)
	{
		super.setVisible(true);
	}

	



	String getColor1(boolean isOn)
	{
		return isOn? borderColor.toString().toLowerCase()+" " : "transparent ";
	}
	String getColor2(boolean isOn)
	{
		return isOn? "#fff " : "transparent ";
	}


	

	public void setLeaderLine(SceneSpeechBalloonCalculator c) {
		
		// first do rectangle
		this.widthInPixels = c.getRectInPixels().getWidth();
		this.heightInPixels = c.getRectInPixels().getHeight();
		pe.getPE().getStyle().setProperty("top", "" +(c.getRectInPixels().getTop())+"px");
		pe.getPE().getStyle().setProperty("left", "" +(c.getRectInPixels().getLeft())+"px");
		pe.getPE().getStyle().setProperty("width", "" +(c.getRectInPixels().getWidth()-1)+"px");
		pe.getPE().getStyle().setProperty("height", ""+(c.getRectInPixels().getHeight()-1)+"px");
		pe.getPE().getStyle().setProperty("WebkitBorderRadius", c.getRadius() +"px");
		pe.getPE().getStyle().setProperty("MozBorderRadius", c.getRadius() +"px");
		pe.getPE().getStyle().setProperty("borderRadius", c.getRadius() +"px");
		pe.getPE().getStyle().setProperty("border", ""+c.getBorderWidth()+"px solid #f00");
		
		boolean isFromTop = c.isFromTop();
		boolean isPointingRight = c.isPointingRight();
		top = c.isFromTop()? -22 : this.heightInPixels+15;
		left = c.getLeaderLineX();
		
		// now before
		before.getElement().getStyle().setProperty("borderColor", getColor1(!isFromTop)+getColor1(isPointingRight)+getColor1(isFromTop)+getColor1(!isPointingRight));
		before.getElement().getStyle().setProperty("left", left + "px");
		before.getElement().getStyle().setProperty("top", top + "px");
		before.getElement().getStyle().setProperty("border", ""+c.getBeforeBorderWidth()+"px solid");

		// noe after
		after.getElement().getStyle().setProperty("borderColor", getColor2(!isFromTop)+getColor2(isPointingRight)+getColor2(isFromTop)+getColor2(!isPointingRight));
		after.getElement().getStyle().setProperty("left", (left+(isPointingRight?5:3))+ "px");
		after.getElement().getStyle().setProperty("top", (top+(isFromTop?8:0))+ "px");
		after.getElement().getStyle().setProperty("border", ""+c.getAfterBorderWidth() +"px solid");
		
		
	}
		
	

}
