package com.ptool.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.ptool.controller.DefaultController;

public class CenterPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mapPane=null;
	private JPanel areaPane=null;
	private JSplitPane mapSplit=null;
	private DefaultController controller=null;
	
	public CenterPanel(DefaultController controller) {
		super();
		this.controller=controller;
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0,0));
		add(getMapSplit(),BorderLayout.CENTER);
	}
	
	private JPanel getMapPane() {
		if(mapPane==null) {
			mapPane=new MTMapPane(controller);
		}
		return mapPane;
	}
	
	private JPanel getAreaPane() {
		if(areaPane==null) {
			areaPane=new CollectionPane(controller);
		}
		return areaPane;
	}
	
	private JSplitPane getMapSplit() {
		if(mapSplit==null) {
			mapSplit=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,getMapPane(),getAreaPane());
			mapSplit.setOneTouchExpandable(true);
			mapSplit.setResizeWeight(0.6);
		}
		return mapSplit;
	}
	
}
