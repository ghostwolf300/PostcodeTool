package com.ptool.gui;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.ListModel;

import com.ptool.controller.DefaultController;
import com.ptool.model.PostcodeModel;
import com.ptool.pojo.PostcodeTO;

import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class LeftPanel extends JPanel implements IView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField fldSearchPostcode;
	private JButton btnSearchPostcode;
	private JScrollPane scrollPane;
	private JList list;
	private DefaultController controller=null;
	
	public LeftPanel(DefaultController controller) {
		super();
		this.controller=controller;
		this.controller.addView(this);
		initialize();
	}
	
	private void initialize() {
		setLayout(new MigLayout("", "[grow][]", "[][grow]"));
		add(getFldSearchPostcode(), "cell 0 0,growx");
		add(getBtnSearchPostcode(), "cell 1 0,aligny bottom");
		add(getScrollPane(), "cell 0 1 2 1,grow");
	}

	private JTextField getFldSearchPostcode() {
		if (fldSearchPostcode == null) {
			fldSearchPostcode = new JTextField();
			fldSearchPostcode.setColumns(10);
		}
		return fldSearchPostcode;
	}
	private JButton getBtnSearchPostcode() {
		if (btnSearchPostcode == null) {
			btnSearchPostcode = new JButton("Search");
		}
		return btnSearchPostcode;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}
	private JList getList() {
		if (list == null) {
			PostcodeListModel model=new PostcodeListModel();
			list = new JList(model);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(PostcodeModel.P_POSTCODES)) {
			PostcodeListModel model=(PostcodeListModel) list.getModel();
			List<PostcodeTO> postcodes=(List<PostcodeTO>) pce.getNewValue();
			model.setPostcodes(postcodes);
		}
		
	}
}
