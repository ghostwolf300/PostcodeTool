package com.ptool.gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import com.ptool.controller.DefaultController;
import com.ptool.model.AreaModel;
import com.ptool.pojo.AreaStyleTO;
import com.ptool.pojo.AreaTO;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class AreaListPane extends JPanel implements IView,TableModelListener,ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private DefaultController controller=null;
	private JTable table;
	
	public AreaListPane() {
		super();
		initialize();
	}
	
	public AreaListPane(DefaultController controller) {
		super();
		this.controller=controller;
		this.controller.addView(this);
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		add(getScrollPane(), BorderLayout.CENTER);
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	
	private List<AreaTO> getTestAreas(){
		List<AreaTO> areas=new ArrayList<AreaTO>();
		
		AreaTO a1=new AreaTO();
		a1.setName("Testi alue 01");
		AreaStyleTO s1=new AreaStyleTO();
		s1.setBackgroundColor(Color.decode("#ffd700"));
		s1.setLineColor(Color.decode("#000056"));
		a1.setStyle(s1);
		areas.add(a1);
		
		AreaTO a2=new AreaTO();
		a2.setName("Testi alue 02");
		AreaStyleTO s2=new AreaStyleTO();
		s2.setBackgroundColor(Color.decode("#4ac925"));
		s2.setLineColor(Color.decode("#E00707"));
		a2.setStyle(s2);
		areas.add(a2);
		
		AreaTO a3=new AreaTO();
		a3.setName("Testi alue 03");
		AreaStyleTO s3=new AreaStyleTO();
		s3.setBackgroundColor(Color.decode("#B536DA"));
		s3.setLineColor(Color.decode("#000000"));
		a3.setStyle(s3);
		areas.add(a3);
		
		return areas;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable(new AreaListTableModel());
			table.getColumnModel().getColumn(0).setCellRenderer(table.getDefaultRenderer(Boolean.class));
			table.getColumnModel().getColumn(0).setCellEditor(table.getDefaultEditor(Boolean.class));
			table.getColumnModel().getColumn(2).setCellRenderer(new StyleRenderer());
			table.getSelectionModel().addListSelectionListener(this);
			//((AreaListTableModel)table.getModel()).setAreas(getTestAreas());
		}
		return table;
	}

	@SuppressWarnings("unchecked")
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(AreaModel.P_AREAS)) {
			List<AreaTO> areas=(List<AreaTO>) pce.getNewValue();
			System.out.println("showing areas: "+areas.size());
			((AreaListTableModel)table.getModel()).setAreas(areas);
		}
		else if(pce.getPropertyName().equals(AreaModel.P_SELECTED)) {
			//AreaTO area=(AreaTO) pce.getNewValue();
			//TODO: select area from table
		}
		
	}

	public void tableChanged(TableModelEvent e) {
		if(e.getType()==TableModelEvent.UPDATE) {
			
		}
		
	}

	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
			int row=table.getSelectedRow();
			AreaTO area=((AreaListTableModel)table.getModel()).getAreaAtRow(row);
			controller.setSelectedArea(area);
		}
		
	}
}
