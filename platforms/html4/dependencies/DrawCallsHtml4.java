package com.github.a2g.core.platforms.html4.dependencies;

import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.RectI;
import com.github.a2g.core.primitive.SpeechBubble;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ParagraphElement;
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

public class DrawCallsHtml4 extends VerticalPanel
{
	private FlowPanel before;
	private MyPanel  pe;
	private FlowPanel after;
	private ColorEnum borderColor;

	public DrawCallsHtml4() {
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




	public void setLeaderLine(SpeechBubble c) {


		//warning: if these 'border's are not set first, the visual result is weird
		before.getElement().getStyle().setProperty("border", ""+c.halfWidthOfLeaderLine+"px solid");
		after.getElement().getStyle().setProperty("border", ""+c.afterBorderWidth +"px solid");
		pe.getPE().getStyle().setProperty("border", ""+c.bubbleOutlineWidth+"px solid "+borderColor.toString().toLowerCase());


		// first do rectangle
		RectI r = c.rectBubble;
		pe.getPE().getStyle().setProperty("top", "" +(r.getTop())+"px");
		pe.getPE().getStyle().setProperty("left", "" +(r.getLeft())+"px");
		pe.getPE().getStyle().setProperty("width", "" +(r.getWidth()-1)+"px");
		pe.getPE().getStyle().setProperty("height", ""+(r.getHeight()-1)+"px");
		pe.getPE().getStyle().setProperty("WebkitBorderRadius", c.radius +"px");
		pe.getPE().getStyle().setProperty("MozBorderRadius", c.radius +"px");
		pe.getPE().getStyle().setProperty("borderRadius", c.radius +"px");

		boolean isFromTop = c.isPointingDown;
		boolean isPointingRight = c.isPointingRight;
		int heightInPixels = c.rectBubble.getHeight();

		int top = c.isPointingDown? -c.heightOfLeaderLine : heightInPixels-1;
		top+=+c.bubbleOutlineWidth;

		int left = c.leaderLineX;

		// before
		before.getElement().getStyle().setProperty("borderColor", getColor1(!isFromTop)+getColor1(isPointingRight)+getColor1(isFromTop)+getColor1(!isPointingRight));
		before.getElement().getStyle().setProperty("left", left + "px");
		before.getElement().getStyle().setProperty("top", top + "px");
		before.getElement().getStyle().setProperty("borderWidth", 25+"px");
		before.getElement().getStyle().setProperty("borderStyle", "solid");


		// after
		after.getElement().getStyle().setProperty("borderColor", getColor2(!isFromTop)+getColor2(isPointingRight)+getColor2(isFromTop)+getColor2(!isPointingRight));
		after.getElement().getStyle().setProperty("left", (left+(isPointingRight?5:3))+ "px");
		after.getElement().getStyle().setProperty("top", (top+(isFromTop?8:0))+ "px");
		after.getElement().getStyle().setProperty("borderWidth", 23+"px");
		after.getElement().getStyle().setProperty("borderStyle", "solid");




	}



}
