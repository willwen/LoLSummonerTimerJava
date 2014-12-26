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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * The view for only one champion/timer slide. Max of 5 will make the main View
 * Complete.
 * 
 * @author Will Wen
 *
 */
public class TimerView {
	private JTextField championTextField;
	private JPanel panel;
	private JLabel champ1;
	private JRadioButton utilityButton;
	private JProgressBar progressBar;
	private JButton btnResetTimer;
	private JLabel flashIcon;
	private JPanel startRestartButtonHolder;
	private JButton btnStartTimer;
	private TimerPresenter timerPres;
	private JPanel champTextPanel;
	private JLabel errorChampName;
	private JLabel lblTime;
	private String iconCopyright = "<html>" + "Icon credit goes to  RIOT INC." + "<br>"
			+ "http://na.leagueoflegends.com/" + "</html>";
	private JLabel utilIconLabel;
	TimerView(TimerPresenter timerPres) {
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		champ1 = new JLabel("Champ");
		champ1.setToolTipText(iconCopyright);
		panel.add(champ1);

		champTextPanel = new JPanel();
		champTextPanel.setSize(100, 100);
		champTextPanel.setLayout(new BoxLayout(champTextPanel, BoxLayout.Y_AXIS));

		panel.add(champTextPanel);

		errorChampName = new JLabel();
		errorChampName.setVisible(false);

		champTextPanel.add(errorChampName);

		championTextField = new JTextField();
		champTextPanel.add(championTextField);
		championTextField.setToolTipText("Champion Name");
		championTextField.setColumns(10);

		utilityButton = new JRadioButton("3 Points in Util");
		panel.add(utilityButton);
		
		utilIconLabel = new JLabel("");
		utilIconLabel.setIcon(new ImageIcon("spellResources\\Summoner's_Insight_mastery_s3.png"));
		panel.add(utilIconLabel);

		lblTime = new JLabel("Time Left");
		lblTime.setForeground(Color.RED);
		panel.add(lblTime);

		progressBar = new JProgressBar(0, timerPres.getTimerModel().getFlashTimeSeconds());

		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setToolTipText("Time until back up\r\n");
		panel.add(progressBar);

		startRestartButtonHolder = new JPanel();

		panel.add(startRestartButtonHolder);
		startRestartButtonHolder.setLayout(new BoxLayout(startRestartButtonHolder, BoxLayout.Y_AXIS));

		btnStartTimer = new JButton("Start Timer");
		startRestartButtonHolder.add(btnStartTimer);

		btnResetTimer = new JButton("Reset Timer");
		btnResetTimer.setEnabled(false);
		startRestartButtonHolder.add(btnResetTimer);

		startRestartButtonHolder.setMaximumSize(new Dimension(
				(int) (btnStartTimer.getSize().getHeight() + btnResetTimer.getSize().getHeight()),
				(int) btnResetTimer.getSize().getWidth()));

		flashIcon = new JLabel("");
		flashIcon.setToolTipText(iconCopyright);
		flashIcon.setIcon(new ImageIcon("spellResources\\Flash.png"));
		panel.add(flashIcon);

		this.timerPres = timerPres;

		registerListeners();

	}

	public JPanel returnPanelToAdd() {
		return panel;
	}
	/**
	 * Adds actionListeners for all appropriate Swing Components
	 */
	private void registerListeners() {
		btnStartTimer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Starting Timer");
				validateFieldsStartButton();
				timerPres.startFlashTimer();
				timerPres.startIncrementTimer();
				System.out.println("Started TImer");
			}

		});

		btnResetTimer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Restarting Timer");
				validateFieldsRestartButton();
				timerPres.getTimerModel().getFlashTimer().restart();
				timerPres.getTimerModel().getFlashTimer().stop();
				timerPres.getTimerModel().getTimerIncrementer().restart();
				timerPres.getTimerModel().getTimerIncrementer().stop();
				System.out.println("Restarted Timer");

			}
		});

		utilityButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// utility reduces summoner spell cooldown by 10 %
				if (utilityButton.isSelected()) {
					changeFlashUtility();
				} else {
					changeFlashNoUtility();
				}
			}
		});

		championTextField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ImageIcon champIcon = new ImageIcon("championResources\\" + championTextField.getText()
							+ "_Square_0.png");
					if (champIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
						champ1.setIcon(champIcon);
						champ1.setText(championTextField.getText());
						errorChampName.setVisible(false);
					} else
						throw new IllegalArgumentException("image not found");
				} catch (Exception exception) {
					errorChampName.setText("Not a valid Champion Name");
					errorChampName.setForeground(Color.RED);
					errorChampName.setVisible(true);
				} finally {
					champ1.revalidate();
					champ1.repaint();
				}
			}
		});

	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public JLabel getLblTime() {
		return lblTime;
	}
	/**
	 * Validate Fields when the start button is pressed
	 */
	private void validateFieldsStartButton() {
		btnStartTimer.setEnabled(false);
		btnResetTimer.setEnabled(true);
		utilityButton.setEnabled(false);
		timerPres.getParentView().getSubMenu().setEnabled(false);
	}
	/**
	 * Validate Fields when the reset button is pressed
	 */
	private void validateFieldsRestartButton() {
		timerPres.setProgressBarCounter(0);
		progressBar.setValue(0);
		btnStartTimer.setEnabled(true);
		btnResetTimer.setEnabled(false);
		lblTime.setText("Time Left");
		utilityButton.setSelected(false);
		utilityButton.setEnabled(true);
		changeFlashNoUtility();
		timerPres.getParentView().getSubMenu().setEnabled(true);
	}
	/**
	 * Change flash cooldown in TimerModel with Utility
	 */
	private void changeFlashUtility() {
		timerPres.getTimerModel().setFlashTimeMilliseconds((int) ((5 * 60 * 1000) * .90));
		timerPres.getTimerModel().refreshFlashTimeSeconds();
		refreshProgressBarMax();
		System.out.println("Successfully changed flash time to "
				+ timerPres.getTimerModel().getFlashTimeSeconds());
	}
	
	/**
	 * Change flash cooldown in TimerModel without Utility
	 */
	private void changeFlashNoUtility() {
		timerPres.getTimerModel().setFlashTimeMilliseconds((int) ((5 * 60 * 1000)));
		timerPres.getTimerModel().refreshFlashTimeSeconds();
		refreshProgressBarMax();
		System.out.println("Successfully changed flash time back to normal at"
				+ timerPres.getTimerModel().getFlashTimeSeconds());
	}
	/**
	 * refreshes the progressBar's Max Value
	 */
	private void refreshProgressBarMax(){
		 progressBar.setMaximum(timerPres.getTimerModel().getFlashTimeSeconds());
	}
}
