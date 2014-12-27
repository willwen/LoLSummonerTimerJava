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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.gtranslate.Language;

/**
 * Presenter class does all the logistics for the TimerModel and Timer View.
 * 
 * @author Will Wen
 *
 */
public class TimerPresenter implements ActionListener {

	private int id;
	private TimerView timerView;
	private TimerModel timerModel;
	private ActionListener progressBarAction;
	private Integer progressBarCounter;
	private MainView parentView;

	/**
	 * Constructor for TimerPresenter
	 * 
	 * @param timerView
	 */
	public TimerPresenter(MainView parentView, int id) {
		this.id = id;
		this.progressBarCounter = 0;
		this.timerModel = new TimerModel(this);
		this.timerView = new TimerView(this);
		this.parentView = parentView;
		progressBarAction = new ActionListener() {
			Integer timeRemaining;

			/**
			 * Increments the progress bar counter and updates the progress bar
			 * and time label
			 * 
			 * @param e
			 */
			public void actionPerformed(ActionEvent e) {
				if (progressBarCounter > (timerModel.getFlashTimeSeconds()) - 1)
					timerModel.getTimerIncrementer().stop();
				else {
					progressBarCounter++;
					timerView.getProgressBar().setValue(progressBarCounter);
					timeRemaining = timerModel.getFlashTimeSeconds() - progressBarCounter;
					updateTimeLabel(timeRemaining);
				}
			}
		};

		this.timerModel.addEventToIncrementer(progressBarAction);

	}
	private void updateTimeLabel(Integer secondsRemaining) {
		if (MainView.secondsOnly) {
			timerView.getLblTime().setText(secondsRemaining.toString());
		} else {
			Integer seconds = (secondsRemaining % 60);
			Integer minutes = (secondsRemaining / 60);
			timerView.getLblTime().setText(minutes.toString() + ":" + seconds.toString());
		}
	}

	/**
	 * starts the flashTimer in TimerModel
	 */
	public void startFlashTimer() {
		this.timerModel.getFlashTimer().start();
	}

	/**
	 * starts the incrementTimer in TimerModel
	 */
	public void startIncrementTimer() {
		this.timerModel.getTimerIncrementer().start();
	}

	/**
	 * Action is performed whenever FlashTimer has finished.
	 * 
	 * @param arg0
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Flash is back up!");
		this.timerModel.getFlashTimer().stop();
		this.timerModel.getSoundThread().start();
	}

	public TimerModel getTimerModel() {
		return timerModel;
	}

	public Integer getProgressBarCounter() {
		return progressBarCounter;
	}

	public void setProgressBarCounter(Integer progressBarCounter) {
		this.progressBarCounter = progressBarCounter;
	}
	public TimerView getTimerView() {
		return timerView;
	}
	public MainView getParentView() {
		return parentView;
	}

	public int getId() {
		return id;
	}
	public void updateModelSound(String championName){
		timerModel.getTimerSound().updateSoundClip(championName);
	}

}
