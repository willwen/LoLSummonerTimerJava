/*******************************************************************************
 * Copyright (c) 12/25/14 Will Wen
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Will Wen 
 *******************************************************************************/
import java.awt.event.ActionListener;

import javax.swing.Timer;
/**
 * The model for flash Timer.
 * @author Will Wen
 *
 */
public class TimerModel {
	private Timer flashTimer;
	private int flashTimeMilliseconds;
	private int flashTimeSeconds;
	private Timer timerIncrementer;
	static final int progressBarIncrementerMilliseconds = 1000; // every second,
																// update the
																// progress bar

	public TimerModel(ActionListener presenter) {
		flashTimeMilliseconds = 5 * 1000 * 60; // 5 minutes converted to
												// milliseconds
		flashTimeSeconds = flashTimeMilliseconds / 1000;
		flashTimer = new Timer(flashTimeMilliseconds, presenter);
		flashTimer.setRepeats(false);
		timerIncrementer = new Timer(progressBarIncrementerMilliseconds, null);
	}

	public Timer getFlashTimer() {
		return this.flashTimer;
	}

	public Timer getTimerIncrementer() {
		return this.timerIncrementer;
	}

	public void addEventToIncrementer(ActionListener e) {
		this.timerIncrementer.addActionListener(e);
	}

	public int getFlashTimeMilliseconds() {
		return flashTimeMilliseconds;
	}

	public int getFlashTimeSeconds() {
		return flashTimeSeconds;
	}

	public void setFlashTimeMilliseconds(int flashTimeMilliseconds) {
		this.flashTimeMilliseconds = flashTimeMilliseconds;
	}

	/**
	 * recalculate flashTimeSeonds based off flashTimeMilliseconds
	 */
	public void refreshFlashTimeSeconds() {
		this.flashTimeSeconds = flashTimeMilliseconds / 1000;;
	}

}
