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

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.a2g.core.event.ExecuteCommandEvent;
import com.github.a2g.core.event.ExecuteCommandEventHandlerAPI;
import com.github.a2g.core.event.SetRolloverEvent;
import com.github.a2g.core.event.SetRolloverEventHandlerAPI;
import com.github.a2g.core.interfaces.internal.ICommandLinePanelFromCommandLinePresenter;
import com.github.a2g.core.interfaces.internal.ICommandLinePresenter;
import com.github.a2g.core.interfaces.internal.IHostingPanel;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromCommandLine;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.LogNames;
import com.google.gwt.event.shared.EventBus;

public class CommandLinePresenter implements ExecuteCommandEventHandlerAPI,
SetRolloverEventHandlerAPI, ICommandLinePresenter {
	private static final Logger COMMANDS_VIA_GUI = Logger.getLogger(LogNames.COMMANDS_VIA_GUI);

	private IMasterPresenterFromCommandLine api;
	private ICommandLinePanelFromCommandLinePresenter view;
	private CommandLine model;
	private double debugX;
	private double debugY;

	public CommandLinePresenter(final IHostingPanel panel, EventBus bus,
			IMasterPresenterFromCommandLine api) {
		this.model = new CommandLine(api);
		this.api = api;
		this.view = api.getFactory().createCommandLinePanel(ColorEnum.Purple,
				ColorEnum.Black, ColorEnum.Red);
		panel.setThing(view);

		bus.addHandler(ExecuteCommandEvent.TYPE, this);
		bus.addHandler(SetRolloverEvent.TYPE, this);
	}

	public void setVisible(boolean isVisible) {
		model.setVisible(isVisible);
		view.setVisible(isVisible);
	}

	public void setMouseable(boolean mouseable) {
		model.setMouseable(mouseable);
		update();
	}

	@Override
	public void setXYForDebugging(double x, double y) {
		if (x != -1) {
			this.debugX = (int) (x * 1000);
			this.debugY = (int) (y * 1000);
		}
	}

	@Override
	public void setCommandLineMouseOver(String displayName, String otid,
			int code) {
		model.setMouseOver(displayName, otid, code);
		update();
	}

	@Override
	public boolean onClick(double x, double y) {
		if (!this.api.isCommandLineActive())
			return false;

		if (isOkToExecute()) {
			COMMANDS_VIA_GUI.log(Level.FINE, "OnExecuteCommand::execute "+model.getSentence().getDisplayName());
			this.execute(x, y);
			return true;
		}

		this.doNextBestThingToExecute();

		return false;
	}

	public void clear() {
		model.clear();
		update();
	}

	private void doNextBestThingToExecute() {
		model.doNextBestThingToExecute();
		update();
	}

	private boolean isOkToExecute() {
		boolean isOkToExecute = model.isOkToExecute();

		return isOkToExecute;
	}

	private void update() {

		// get sentence gets the sentence template filled out
		// as much as it can.
		Sentence sentence = getSentence();

		// so we should fill in remaining AAA and BBB with blank
		sentence.setBBB(new SentenceItem());
		sentence.setAAA(new SentenceItem());

		// then get the display name
		String displayName = sentence.getDisplayName();

		// ...and display it
		view.setText("(." + debugX + ",." + debugY + ")  " + displayName);
	}

	private Sentence getSentence() {
		Sentence sentence = model.getSentence();

		return sentence;
	}

	private void execute(double x, double y) {
		// if its incomplete, the clear everything..
		if (!model.isOkToExecute()) {
			clear();
			return;
		}

		if (this.api != null) {
			int verbAsCode = model.getSentence().getVerbAsCode();
			SentenceItem sentenceA = model.getSentence().getAAA();
			SentenceItem sentenceB = model.getSentence().getBBB();
			// clamp the mouse pointer to within the viewport coords
			if (x > 1.0)
				x = 1.0;
			if (x < 0.0)
				x = 0.0;
			if (y > 1.0)
				y = 1.0;
			if (y < 0.0)
				y = 0.0;

			api.doCommand(verbAsCode, getSentence().getVerbAsVerbEnumeration(),
					sentenceA, sentenceB, x, y);

		}

	}

	public ICommandLinePanelFromCommandLinePresenter getView() {
		return view;
	}

	public String getDisplayName() {
		String displayName = model.getSentence().getDisplayName();
		return displayName;
	}

	public void setVerbItemItem(SentenceItem verb, SentenceItem fullItem,
			SentenceItem fullItem2) {
		model.setVerbItemItem(verb,fullItem,fullItem2);
		update();
	}

}
