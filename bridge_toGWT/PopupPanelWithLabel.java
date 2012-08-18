package com.github.a2g.core.bridge;
import com.github.a2g.core.authoredscene.ColorEnum;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class PopupPanelWithLabel {
	private PopupPanel popup;
	private Label labelInPopup;
	
	public PopupPanelWithLabel(String speech, ColorEnum color)
	{
		this.popup = new PopupPanel();
		this.popup.setTitle(speech);
		this.labelInPopup = new Label(speech);

		this.popup.setWidget(labelInPopup);
		
		if(color!=null)
		{
			DOM.setStyleAttribute(labelInPopup.getElement(), "color",
				color.toString());
			DOM.setStyleAttribute(popup.getElement(), "borderColor",
				color.toString());
		}
	}
	public void show()
	{
		popup.show();
	}
	
	public void setPopupPosition(int x, int y)
	{
		popup.setPopupPosition(x,y);
	}
	public void updateText(String string)
	{
		popup.setWidget(new Label(string));
	}
	public void hide()
	{
		popup.hide();
	}
	
}
