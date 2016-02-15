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

package com.github.a2g.core.objectmodel;

import com.github.a2g.core.event.PropertyChangeEvent;
import com.google.gwt.event.shared.EventBus;
/**
 *  
 * @author Admin
 *
 */
public class InventoryItem {

	private final Image image;
	private final String itid;
	private String displayName;
	private boolean visible;
	private EventBus bus;
	private int icode;

	public InventoryItem(EventBus bus, final String itid, final Image image,
			int icode, boolean isVisible) {
		assert (bus != null);
		this.icode = icode;
		this.image = image;
		this.itid = itid;
		this.visible = isVisible;
		this.displayName = itid;
		this.bus = bus;
	}

	public String getItid() {
		return itid;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		bus.fireEvent(new PropertyChangeEvent("CARRYING_" + itid, icode,
				visible ? 1 : 0));
	}

	public boolean isVisible() {
		return visible;
	}

	public final Image getImage() {
		return image;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public int getICode() {
		return icode;
	}

}
