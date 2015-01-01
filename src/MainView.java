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

/* JNativeHook: Global keyboard and mouse hooking for Java.
 * Copyright (C) 2006-2014 Alexander Barker.  All Rights Received.
 * https://github.com/kwhat/jnativehook/
 *
 * JNativeHook is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JNativeHook is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import javax.swing.*;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainView implements NativeKeyListener, WindowListener {
	private JFrame frame;
	private JPanel[] champs = new JPanel [5];
	private JMenuBar menuBar;
	private JMenu menu, subMenu;
	private JMenuItem menuItemSeconds;
	private JMenuItem menuItemSecondsAndMinutes;
	static boolean secondsOnly;
	private MainPresenter mainPres;

	public MainView(MainPresenter mainPres) {
		// default is seconds only.
		this.mainPres = mainPres;
		secondsOnly = true;
		constructMenu();
		frame = new JFrame("LoL Timer");
		frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][][grow]"));
		frame.setVisible(true);
		frame.setSize(1366, 768);
		frame.addWindowListener(this);
		
		refreshFrame();
	}

	public void refreshFrame() {
		frame.revalidate();
		frame.repaint();
	}


	public void constructAddPanels(){
		for (int i = 0; i < 5; i ++){
			champs[i]= mainPres.getTimerPres(i).getTimerView().returnPanelToAdd();
			frame.getContentPane().add(champs[i], "cell 1 "+ i + ",grow");
		}
	}

	private void constructMenu() {
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

	@Override
	public void windowActivated(WindowEvent arg0) {
		/* Unimplemented */
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
        //Clean up the native hook.
        GlobalScreen.unregisterNativeHook();
        System.runFinalization();
        System.exit(0);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {/* Unimplemented */}

	@Override
	public void windowDeactivated(WindowEvent arg0) {/* Unimplemented */}

	@Override
	public void windowDeiconified(WindowEvent arg0) {/* Unimplemented */}

	@Override
	public void windowIconified(WindowEvent arg0) {	/* Unimplemented */	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		}

		GlobalScreen.getInstance().addNativeKeyListener(this);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {/* Unimplemented */}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		if(arg0.getKeyCode() == NativeKeyEvent.VC_F8){
			mainPres.getTimerPres(1).getTimerView().pressStart();
		}
		else if(arg0.getKeyCode() == NativeKeyEvent.VC_F9){
			mainPres.getTimerPres(2).getTimerView().pressStart();
		}
		else if (arg0.getKeyCode() == NativeKeyEvent.VC_F10){
			mainPres.getTimerPres(3).getTimerView().pressStart();
		}
		else if (arg0.getKeyCode() == NativeKeyEvent.VC_F11){
			mainPres.getTimerPres(4).getTimerView().pressStart();
		}
		else if (arg0.getKeyCode() == NativeKeyEvent.VC_F12){
			mainPres.getTimerPres(5).getTimerView().pressStart();
		}

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {/* Unimplemented */}

}
