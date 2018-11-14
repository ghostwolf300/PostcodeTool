package com.ptool.gui;

import javax.swing.JDialog;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.ptool.controller.DefaultController;
import com.ptool.model.MapModel;
import com.ptool.pojo.MapDataTO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JPanel;

public class MapDialog extends JDialog implements IView, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JList<MapDataTO> list;
	private JPanel panel;
	private JButton btnCancel;
	private JButton btnOk;
	private DefaultController controller=null;
	
	public MapDialog() {
		super();
		initialize();
	}
	
	public MapDialog(DefaultController controller) {
		super();
		this.controller=controller;
		this.controller.addView(this);
		initialize();
	}
	
	private void initialize() {
		setTitle("Maps");
		//setModal(true);
		Dimension d=new Dimension(600,400);
		setSize(d);
		setResizable(false);
		getContentPane().add(getScrollPane(), BorderLayout.CENTER);
		getContentPane().add(getPanel(), BorderLayout.SOUTH);
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}
	private JList<MapDataTO> getList() {
		if (list == null) {
			list = new JList<MapDataTO>();
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setModel(new MapListModel());
		}
		return list;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnCancel());
			panel.add(getBtnOk());
		}
		return panel;
	}
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.setActionCommand("Cancel");
			btnCancel.addActionListener(this);
		}
		return btnCancel;
	}
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("OK");
			btnOk.setActionCommand("OK");
			btnOk.addActionListener(this);
		}
		return btnOk;
	}

	@SuppressWarnings("unchecked")
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(MapModel.P_MAPS)) {
			List<MapDataTO> maps=(List<MapDataTO>) pce.getNewValue();
			System.out.println("Dialog: maps loaded size="+maps.size());
			MapListModel model=(MapListModel) list.getModel();
			model.setMaps(maps);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnOk)) {
			MapDataTO map=list.getSelectedValue();
			if(map!=null) {
				this.setVisible(false);
				controller.showMap(map);
			}
		}
		else if(e.getSource().equals(btnCancel)) {
			this.setVisible(false);
		}
		
	}
}
