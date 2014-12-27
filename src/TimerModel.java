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
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Timer;

import javazoom.jl.decoder.JavaLayerException;

/**
 * The model for flash Timer.
 * @author Will Wen
 *
 */
public class TimerModel {
	private Timer flashTimer;
	private boolean isUtility;
	//final value used in timer
	private int finalFlashTimeMilliseconds;
	private int finalFlashTimeSeconds;
	
	//backup values in case delay is swapped
	private int backupFlashTimeNoUtilityMilliseconds;
	private int backupFlashTimerUtilityMilliseconds;
	
	
	private Timer timerIncrementer;
	static final int progressBarIncrementerMilliseconds = 1000; // every second,
																// update the
																// progress bar
	private TimerSound timerSound;
	private TimerPresenter timerPres;
	private Thread soundThread;
	
	public TimerModel(ActionListener presenter) {
		finalFlashTimeMilliseconds = 20 * 1000 ; // 5 minutes converted to
												// milliseconds
		timerPres = (TimerPresenter) presenter;
		timerSound = new TimerSound(this);
		soundThread = new Thread (timerSound); 
		//assert Values  based of final
		refreshFlashTimeSeconds();
		refreshFlashUtility();
		refreshFlashNoUtility();
		//default is no utility
		isUtility = false;
		flashTimer = new Timer(finalFlashTimeMilliseconds, presenter);
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
		return finalFlashTimeMilliseconds;
	}

	public int getFlashTimeSeconds() {
		return finalFlashTimeSeconds;
	}

	public void setFlashTimeMilliseconds(int flashTimeMilliseconds) {
		this.finalFlashTimeMilliseconds = flashTimeMilliseconds;
	}

	/**
	 * recalculate flashTimeSeonds based off flashTimeMilliseconds
	 */
	public void refreshFlashTimeSeconds() {
		this.finalFlashTimeSeconds = finalFlashTimeMilliseconds / 1000;;
	}

	/**
	 * recalculate flashTimerUtilityMilliseconds based off flashTimeMilliseconds
	 */
	public void refreshFlashUtility() {
		this.backupFlashTimerUtilityMilliseconds = (int) (finalFlashTimeMilliseconds * .9);
	}

	public void refreshFlashNoUtility() {
		this.backupFlashTimeNoUtilityMilliseconds = finalFlashTimeMilliseconds;
	}

	public boolean isUtility() {
		return isUtility;
	}

	public void setUtility(boolean isUtility) {
		this.isUtility = isUtility;
	}

	public int getFlashTimerUtilityMilliseconds() {
		return backupFlashTimerUtilityMilliseconds;
	}

	
	
	public void finalizeTimers(){
		if (isUtility){
			finalFlashTimeMilliseconds = backupFlashTimerUtilityMilliseconds;
		}
		else{
			finalFlashTimeMilliseconds = backupFlashTimeNoUtilityMilliseconds;
			}
		refreshFlashTimeSeconds();
		flashTimer.setInitialDelay(finalFlashTimeMilliseconds);
		flashTimer.setDelay(finalFlashTimeMilliseconds);
	}

	public TimerPresenter getTimerPres() {
		return timerPres;
	}

	public TimerSound getTimerSound() {
		return timerSound;
	}

	public Thread getSoundThread() {
		return soundThread;
	}
	

}
