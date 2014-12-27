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
	private JPanel champ4;
	private JPanel champ5;
	private JMenuBar menuBar;
	private JMenu menu, subMenu;
	private JMenuItem menuItemSeconds;
	private JMenuItem menuItemSecondsAndMinutes;
	static boolean secondsOnly;
	private int idCounter;

	public MainView() {
		//default is seconds only.
		secondsOnly = true;
		idCounter = 1;
		constructMenu();
		frame = new JFrame("LoL Timer");
		frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][][grow]"));
		frame.setVisible(true);
		frame.setSize(1366, 768);

		champ1 = new TimerPresenter(this, dispenseID()).getTimerView().returnPanelToAdd();
		frame.getContentPane().add(champ1, "cell 1 0,grow");

		champ2 = new TimerPresenter(this, dispenseID()).getTimerView().returnPanelToAdd();
		frame.getContentPane().add(champ2, "cell 1 1,grow");

		champ3 = new TimerPresenter(this, dispenseID()).getTimerView().returnPanelToAdd();
		frame.getContentPane().add(champ3, "cell 1 2,grow");

		champ4 = new TimerPresenter(this, dispenseID()).getTimerView().returnPanelToAdd();
		frame.getContentPane().add(champ4, "cell 1 3,grow");

		champ5 = new TimerPresenter(this, dispenseID()).getTimerView().returnPanelToAdd();
		frame.getContentPane().add(champ5, "cell 1 4,grow");
		refreshFrame();
	}

	public void refreshFrame() {
		frame.revalidate();
		frame.repaint();
	}
	
	private int dispenseID(){
		int returnID= idCounter;
		idCounter ++ ;
		return returnID;
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
