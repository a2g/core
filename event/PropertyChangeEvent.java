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

package com.github.a2g.core.event;

import com.google.gwt.event.shared.GwtEvent;

public class PropertyChangeEvent extends
GwtEvent<PropertyChangeEventHandlerAPI> {
	public static Type<PropertyChangeEventHandlerAPI> TYPE = new Type<PropertyChangeEventHandlerAPI>();
	// private final FriendSummaryDTO friend;
	private final String name;
	private final int id;
	private int value;

	public PropertyChangeEvent(String name, int id, int value) {
		this.name = name;
		this.id = id;
		this.value = value;
	}

	@Override
	public Type<PropertyChangeEventHandlerAPI> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PropertyChangeEventHandlerAPI handler) {
		handler.onPropertyChange(this);
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
