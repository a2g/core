// Google wrote the code in this file first
// then Anthony Cassidy modified it
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
/*
 * Copyright 2007 Google Inc.
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
package com.github.a2g.core.swing.animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

/**
 * A simplified, browser-safe timer class. This class serves the same purpose as
 * java.util.Timer, but is simplified because of the single-threaded
 * environment.
 * 
 * <p>
 * To schedule a timer, simply create a subclass of it (overriding {#run})
 * and call {#schedule} or {#scheduleRepeating}.
 * </p>
 * 
 * <p>
 * NOTE: If you are using a timer to schedule a UI animation, use
 * {@link com.github.a2g.core.swing.animation.AnimationScheduler} instead. The
 * browser can optimize your animation for maximum performance.
 * </p>
 * 
 * <p>
 * <h3>Example</h3>
 * </p>
 */
public abstract class Timer implements ActionListener
{

	private static Map<Integer, javax.swing.Timer> timers = new TreeMap<Integer, javax.swing.Timer>();
	/*
  static {
    hookWindowClosing();
  }*/
	public Timer()
	{
		//super(0, null);
	}

	private static void clearInterval(int id)
	{
		if(timers.containsKey(id))
		{

			timers.get(id).stop();
		}
	};

	private static void clearTimeout(int id)
	{
		if(timers.containsKey(id))
		{
			timers.get(id).stop();
		}
	}

	private static int createInterval(final Timer callback, int period)
	{
		javax.swing.Timer timer = new javax.swing.Timer(period,callback);
		int timerId = timer.hashCode();
		timers.put(timerId, timer);
		timer.addActionListener(callback);
		timer.setRepeats(true);
		timer.setInitialDelay(period);
		timer.start();

		return timerId;
	}


	private static int createTimeout(final Timer callback, int delay)
	{
		javax.swing.Timer timer = new javax.swing.Timer(delay,callback);
		int timerId = timer.hashCode();
		timers.put(timerId, timer);
		timer.addActionListener(callback);
		timer.setRepeats(false);
		//timer.setInitialDelay(period)
		timer.start();

		return timerId;
	}
	/*
  private static void hookWindowClosing() {
    // Catch the window closing event.
    Window.addCloseHandler(new CloseHandler<Window>() {

      public void onClose(CloseEvent<Window> event) {
        while (timers.size() > 0) {
          timers.get(0).cancel();
        }
      }
    });
  }*/

	private boolean isRepeating;

	private int timerId;

	/**
	 * Cancels this timer.
	 */
	public void cancel() {

		if (isRepeating) {
			clearInterval(timerId);
		} else {
			clearTimeout(timerId);
		}
		timers.remove(timerId);
	}

	/**
	 * This method will be called when a timer fires. Override it to implement the
	 * timer's logic.
	 */
	public abstract void run();

	/**
	 * Schedules a timer to elapse in the future.
	 * 
	 * @param delayMillis how long to wait before the timer elapses, in
	 *          milliseconds
	 */
	public void schedule(int delayMillis) {
		if (delayMillis < 0) {
			throw new IllegalArgumentException("must be non-negative");
		}
		cancel();
		isRepeating = false;
		timerId = createTimeout(this, delayMillis);
		//timers.put(timerId, this);

	}

	/**
	 * Schedules a timer that elapses repeatedly.
	 * 
	 * @param periodMillis how long to wait before the timer elapses, in
	 *          milliseconds, between each repetition
	 */
	public void scheduleRepeating(int periodMillis) {
		if (periodMillis <= 0) {
			throw new IllegalArgumentException("must be positive");
		}
		cancel();
		isRepeating = true;
		timerId = createInterval(this, periodMillis);
		//timers.put(timerId, this);
	}

	/*
	 * Called by native code when this timer fires.
	 */
	final void fire() {
		// If this is a one-shot timer, remove it from the timer list. This will
		// allow it to be garbage collected.

		if (!isRepeating) {
			cancel();
			timers.remove(timerId);
		}

		// Run the timer's code.
		run();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fire();

	}
}
