package com.ptool.gui;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ptool.controller.DefaultController;
import com.ptool.model.MapAreaModel;
import com.ptool.pojo.MapAreaTO;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class MapAreaPane extends JPanel implements IView,ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField fldSearchPostcode;
	private JButton btnSearchPostcode;
	private JScrollPane scrollPane;
	private JList list;
	private DefaultController controller=null;
	
	public MapAreaPane(DefaultController controller) {
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
		Dimension pd=this.getPreferredSize();
		pd.width=400;
		this.setPreferredSize(pd);
		
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
			MapAreaListModel model=new MapAreaListModel();
			list = new JList(model);
			list.addListSelectionListener(this);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(MapAreaModel.P_MAP_AREAS)) {
			MapAreaListModel model=(MapAreaListModel) list.getModel();
			List<MapAreaTO> postcodes=(List<MapAreaTO>) pce.getNewValue();
			model.setPostcodes(postcodes);
			System.out.println("Postcodes here");
		}
		else if(pce.getPropertyName().equals(MapAreaModel.P_SELECTED)) {
			
		}
		
	}

	public void valueChanged(ListSelectionEvent lse) {
		if(!lse.getValueIsAdjusting()) {
			int index=lse.getLastIndex();
			MapAreaListModel model=(MapAreaListModel) list.getModel();
			MapAreaTO postcode=model.getElementAt(index);
			Set<MapAreaTO> selectedSet=new HashSet<MapAreaTO>();
			selectedSet.add(postcode);
			controller.selectMapAreas(selectedSet);
			
		}
	}
}
