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





public class SetRolloverEvent extends GwtEvent<SetRolloverEventHandlerAPI> {
	public static Type<SetRolloverEventHandlerAPI> TYPE = new Type<SetRolloverEventHandlerAPI>();
	// private final FriendSummaryDTO friend;
	private final String displayName;
	private final String textualId;
	private final int code;

	public String getDisplayName() {
		return displayName;
	}

	public String getTextualId() {
		return textualId;
	}

	public int getCode() {
		return code;
	}

	public SetRolloverEvent(String displayName, String textualId, int code) {
		this.displayName = displayName;
		this.textualId = textualId;
		this.code = code;
	}

	@Override
	public Type<SetRolloverEventHandlerAPI> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SetRolloverEventHandlerAPI handler) {
		handler.onSetMouseOver(displayName,
				textualId, code);
	}
}
