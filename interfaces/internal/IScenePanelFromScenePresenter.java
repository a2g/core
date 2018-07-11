/*
 * Copyright 2012 Anthony Cassidy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.SpeechBubble;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.EventBus;

public interface IScenePanelFromScenePresenter 
extends IMeasureTextWidthAndHeight
, ISetFontNameAndHeight
{
	void clear();

	Image createNewImageAndAddHandlers(LoadHandler lh,
			IPackagedImage imageResource,
			IScenePresenterFromSceneMouseOver api, EventBus bus, int x, int y,
			String objectTextualId, short objectCode);

	void setScenePixelSize(int width, int height);

	void setVisible(boolean b);

	void setCameraOffset(int x, int y);

	void setStateOfPopup(boolean isVisible, ColorEnum talkingColor, SpeechBubble rectAndLeaderLine, TalkPerformer sayAction);

	void onSceneEntry(String string); 

	void incrementFont();
	
	void decrementFont();
}