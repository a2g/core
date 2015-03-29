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

package com.github.a2g.core.interfaces;

import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.objectmodel.Inventory;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.EventBus;

public interface IInventoryPanelFromInventoryPresenter {

	void setVisible(boolean isVisible);

	void updateInventory(Inventory inventory);

	void clear();

	Image createNewImageAndAdddHandlers(IPackagedImage imageResource,
			LoadHandler lh, EventBus bus, String objectTextualId,
			int objectCode, int i, int j);

	void setScenePixelSize(int width, int height);

	void setDimensionsOfPanel(int width, int height);

	void setLeftArrowVisible(boolean visible);

	void setRightArrowVisible(boolean visible);
}