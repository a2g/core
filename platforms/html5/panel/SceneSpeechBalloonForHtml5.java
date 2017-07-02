package com.github.a2g.core.platforms.html5.panel;

import com.github.a2g.core.objectmodel.SceneSpeechBalloonCalculator;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
class MyPanel extends Label
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

public class SceneSpeechBalloonForHtml5 extends VerticalPanel
{
	private FlowPanel before;
	private MyPanel  pe;
	private FlowPanel after;
	private ColorEnum borderColor;

	public SceneSpeechBalloonForHtml5() {
		this.borderColor = ColorEnum.Red;

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

		// after
		after.getElement().getStyle().setProperty("content", "");
		after.getElement().getStyle().setProperty("position","absolute");
		after.getElement().getStyle().setProperty("width", "0");
		after.getElement().getStyle().setProperty("height","0");

		pe.getPE().getStyle().setProperty("textAlign", "center");
		pe.getPE().getStyle().setProperty("lineHeight", "12px");
		pe.getPE().getStyle().setProperty("backgroundColor", "#fff");
		pe.getPE().getStyle().setProperty("marginTop", "0px");
		pe.getPE().getStyle().setProperty("marginBottom", "0px");


		//drop shadow makes it look worse.
		//		pe.getStyle().setProperty("WebkitBoxShadow", "2px 2px 4px #888");
		//		pe.getStyle().setProperty("MozBoxShadow", "2px 2px 4px #888");
		//		pe.getStyle().setProperty("boxShadow", "2px 2px 4px #888");

	}

	public void setBorderColor(ColorEnum talkingColor)
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

	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
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


		//warning: if these 'border's are not set first, the visual result is weird
		before.getElement().getStyle().setProperty("border", ""+c.getHalfWidthOfLeaderLine()+"px solid");
		after.getElement().getStyle().setProperty("border", ""+c.getAfterBorderWidth() +"px solid");
		pe.getPE().getStyle().setProperty("border", ""+c.getBorderWidth()+"px solid "+borderColor.toString().toLowerCase());


		// first do rectangle
		pe.getPE().getStyle().setProperty("top", "" +(c.getRectInPixels().getTop())+"px");
		pe.getPE().getStyle().setProperty("left", "" +(c.getRectInPixels().getLeft())+"px");
		pe.getPE().getStyle().setProperty("width", "" +(c.getRectInPixels().getWidth()-1)+"px");
		pe.getPE().getStyle().setProperty("height", ""+(c.getRectInPixels().getHeight()-1)+"px");
		pe.getPE().getStyle().setProperty("WebkitBorderRadius", c.getRadius() +"px");
		pe.getPE().getStyle().setProperty("MozBorderRadius", c.getRadius() +"px");
		pe.getPE().getStyle().setProperty("borderRadius", c.getRadius() +"px");

		boolean isFromTop = c.isFromTop();
		boolean isPointingRight = c.isPointingRight();
		int heightInPixels = c.getRectInPixels().getHeight();

		int top = c.isFromTop()? -c.getHeightOfLeaderLine() : heightInPixels-1;
		top+=+c.getBorderWidth();

		int left = c.getLeaderLineX();

		// before
		before.getElement().getStyle().setProperty("borderColor", getColor1(!isFromTop)+getColor1(isPointingRight)+getColor1(isFromTop)+getColor1(!isPointingRight));
		before.getElement().getStyle().setProperty("left", left + "px");
		before.getElement().getStyle().setProperty("top", top + "px");


		// after
		after.getElement().getStyle().setProperty("borderColor", getColor2(!isFromTop)+getColor2(isPointingRight)+getColor2(isFromTop)+getColor2(!isPointingRight));
		after.getElement().getStyle().setProperty("left", (left+(isPointingRight?5:3))+ "px");
		after.getElement().getStyle().setProperty("top", (top+(isFromTop?8:0))+ "px");


	}



}
