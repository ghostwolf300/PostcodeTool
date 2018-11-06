package com.ptool.gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;
import com.ptool.controller.DefaultController;


public class PToolFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel centerPane;
	
	private DefaultController controller;
	private LeftPanel leftPanel;
	
	public PToolFrame(DefaultController controller) {
		super();
		this.controller=controller;
		initialize();
	}
	
	private void initialize() {
		setTitle("Postcode Tool 0.1");
		//Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getLeftPanel(), BorderLayout.WEST);
		getContentPane().add(getCenterPane(), BorderLayout.CENTER);
	}
	
	private LeftPanel getLeftPanel() {
		if (leftPanel == null) {
			leftPanel = new LeftPanel(controller);
		}
		return leftPanel;
	}
	
	private JPanel getCenterPane() {
		if (centerPane == null) {
			centerPane = new JPanel();
		}
		return centerPane;
	}
	
}
 