package com.ptool.gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.ptool.controller.DefaultController;


public class PToolFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel centerPane;
	private PostcodePane leftPanel;
	private JSplitPane mainSplit;
	private DefaultController controller;
	
	public PToolFrame(DefaultController controller) {
		super();
		this.controller=controller;
		initialize();
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Postcode Tool 0.1");
		//Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setLayout(new BorderLayout(0, 0));
		//getContentPane().add(getLeftPanel(), BorderLayout.WEST);
		//getContentPane().add(getCenterPane(), BorderLayout.CENTER);
		//getContentPane().add(getRightPanel(),BorderLayout.EAST);
		getContentPane().add(getMainSplit(), BorderLayout.CENTER);
		
	}
	
	private PostcodePane getLeftPanel() {
		if (leftPanel == null) {
			leftPanel = new PostcodePane(controller);
		}
		return leftPanel;
	}
	
	private JPanel getCenterPane() {
		if (centerPane == null) {
			//centerPane = new JPanel();
			centerPane=new CenterPanel(controller);
		}
		return centerPane;
	}

	private JSplitPane getMainSplit() {
		if(mainSplit==null) {
			mainSplit=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,getLeftPanel(),getCenterPane());
		}
		return mainSplit;
	}
	
}
 