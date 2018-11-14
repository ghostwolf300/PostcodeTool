package com.ptool.gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import com.ptool.controller.DefaultController;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Box;


public class MTFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel centerPane;
	private MapAreaPane leftPanel;
	private JSplitPane mainSplit;
	private DefaultController controller;
	private JToolBar toolBar;
	private JButton btnOpen;
	private JButton btnNew;
	private JButton btnDelete;
	private Component horizontalGlue;
	private JDialog mapDialog=null;
	
	public MTFrame(DefaultController controller) {
		super();
		this.controller=controller;
		initialize();
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Map Tool 0.1");
		//Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setLayout(new BorderLayout(0, 0));
		//getContentPane().add(getLeftPanel(), BorderLayout.WEST);
		//getContentPane().add(getCenterPane(), BorderLayout.CENTER);
		//getContentPane().add(getRightPanel(),BorderLayout.EAST);
		getContentPane().add(getMainSplit(), BorderLayout.CENTER);
		getContentPane().add(getMapToolBar(),BorderLayout.NORTH);
		
		
	}
	
	private MapAreaPane getLeftPanel() {
		if (leftPanel == null) {
			leftPanel = new MapAreaPane(controller);
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
	
	private JToolBar getMapToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.setFloatable(false);
			toolBar.add(getBtnOpen());
			toolBar.add(getBtnNew());
			toolBar.add(getHorizontalGlue());
			toolBar.add(getBtnDelete());
		}
		return toolBar;
	}
	private JButton getBtnOpen() {
		if (btnOpen == null) {
			btnOpen = new JButton("");
			btnOpen.setIcon(new ImageIcon(MTFrame.class.getResource("/toolbarButtonGraphics/general/Open24.gif")));
			btnOpen.addActionListener(this);
		}
		return btnOpen;
	}
	private JButton getBtnNew() {
		if (btnNew == null) {
			btnNew = new JButton("");
			btnNew.setIcon(new ImageIcon(MTFrame.class.getResource("/toolbarButtonGraphics/general/Add24.gif")));
			btnNew.addActionListener(this);
		}
		return btnNew;
	}
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton("");
			btnDelete.setIcon(new ImageIcon(MTFrame.class.getResource("/toolbarButtonGraphics/general/Delete24.gif")));
			btnDelete.addActionListener(this);
		}
		return btnDelete;
	}
	private Component getHorizontalGlue() {
		if (horizontalGlue == null) {
			horizontalGlue = Box.createHorizontalGlue();
		}
		return horizontalGlue;
	}
	
	private JDialog getMapDialog() {
		if(mapDialog==null) {
			mapDialog=new MapDialog(controller);
		}
		return mapDialog;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnOpen)) {
			getMapDialog().setVisible(true);
			controller.loadMaps();
		}
		else if(e.getSource().equals(btnNew)) {
			
		}
		else if(e.getSource().equals(btnDelete)) {
			
		}
	}
}
 