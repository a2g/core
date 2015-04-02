package com.github.a2g.core.objectmodel;

import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.user.client.ui.SimplePanel;

public class SpeechBalloon extends SimplePanel 
{
	ParagraphElement  pe;
	
	public SpeechBalloon() {
		super(Document.get().createPElement());
		
		Element p = this.getElement();
		pe = (ParagraphElement)p;
		pe.addClassName("down-pointing-left");
		pe.setId("theballoon");
		
//		pe.getStyle().setProperty("color", "#ff0000");
//		pe.getStyle().setProperty("fontcolor", "#00ff00");
//		pe.getStyle().setProperty("textcolor", "#0000ff");
//		
//		pe.getStyle().setProperty("position", "relative");
//		pe.getStyle().setProperty("width", "200px");
//		pe.getStyle().setProperty("height", "100px");
//		pe.getStyle().setProperty("textAlign", "center");
//		pe.getStyle().setProperty("lineHeight", "100px");
//		pe.getStyle().setProperty("backgroundColor", "#fff");
//		pe.getStyle().setProperty("border", "8px solid #666");
//		pe.getStyle().setProperty("WebkitBorderRadius", "30px");
//		pe.getStyle().setProperty("MozBorderRadius", "30px");
//		pe.getStyle().setProperty("borderRadius", "30px");
//		pe.getStyle().setProperty("WebkitBoxShadow", "2px 2px 4px #888");
//		pe.getStyle().setProperty("MozBoxShadow", "2px 2px 4px #888");
//		pe.getStyle().setProperty("boxShadow", "2px 2px 4px #888");

	}
	
	void setBorderColor(ColorEnum talkingColor)		
	{
		if (talkingColor != null) 
		{
			pe.getStyle().setProperty("borderColor", talkingColor.toString());
		}
	}

	public void setText(String speech) {
		pe.setInnerText(speech);
	}
	
	public void setVisible(boolean visible)
	{
		super.setVisible(true);
	}
}
