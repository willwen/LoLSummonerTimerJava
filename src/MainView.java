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
import javax.swing.*;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView {
	private JFrame frame;
	private JPanel champ1;
	private JPanel champ2;
	private JPanel champ3;
	private JMenuBar menuBar;
	private JMenu menu, subMenu;
	private JMenuItem menuItemSeconds;
	private JMenuItem menuItemSecondsAndMinutes;
	static boolean secondsOnly;

	public MainView() {
		//default is seconds only.
		secondsOnly = true;
		constructMenu();
		frame = new JFrame("LoL Timer");
		frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow]", "[][161.00,grow][][][grow]"));
		frame.setVisible(true);
		frame.setSize(1366, 768);

		champ1 = new TimerPresenter(this).getTimerView().returnPanelToAdd();
		frame.getContentPane().add(champ1, "cell 1 0,grow");

		champ2 = new TimerPresenter(this).getTimerView().returnPanelToAdd();
		frame.getContentPane().add(champ2, "cell 1 1,grow");

		champ3 = new TimerPresenter(this).getTimerView().returnPanelToAdd();
		frame.getContentPane().add(champ3, "cell 1 2,grow");

		// for future champion rows
		// JPanel panel_1 = new JPanel();
		// frame.getContentPane().add(panel_1, "cell 1 1,grow");

		refreshFrame();
	}

	public void refreshFrame() {
		frame.revalidate();
		frame.repaint();
	}
	
	private void constructMenu(){
		menuBar = new JMenuBar();
		menu = new JMenu("Options");
		subMenu = new JMenu("Time");
		menu.add(subMenu);
		menuBar.add(menu);
		menuItemSeconds = new JMenuItem("Seconds Only");
		menuItemSecondsAndMinutes = new JMenuItem("Minutes and Seconds");
		subMenu.add(menuItemSeconds);
		subMenu.add(menuItemSecondsAndMinutes);
		menuItemSeconds.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				secondsOnly = true;
			}
		});
		
		menuItemSecondsAndMinutes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				secondsOnly = false;
			}
		});
	}

	public JMenu getSubMenu() {
		return subMenu;
	}

}
