package com.ptool.gui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import com.ptool.controller.DefaultController;
import com.ptool.model.CollectionModel;
import com.ptool.pojo.CollectionStyleTO;
import com.ptool.pojo.CollectionTO;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.geotools.swing.MapLayerTable;
import org.geotools.swing.MapPane;

public class CollectionListPane extends JPanel implements IView,TableModelListener,ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private DefaultController controller=null;
	private JTable table;
	private MapPane map=null;
	
	public CollectionListPane() {
		super();
		initialize();
	}
	
	public CollectionListPane(DefaultController controller) {
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
			//scrollPane.setViewportView(getTable());
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	
	private List<CollectionTO> getTestAreas(){
		List<CollectionTO> areas=new ArrayList<CollectionTO>();
		
		CollectionTO a1=new CollectionTO();
		a1.setName("Testi alue 01");
		CollectionStyleTO s1=new CollectionStyleTO();
		s1.setBackgroundColor(Color.decode("#ffd700"));
		s1.setLineColor(Color.decode("#000056"));
		a1.setStyle(s1);
		areas.add(a1);
		
		CollectionTO a2=new CollectionTO();
		a2.setName("Testi alue 02");
		CollectionStyleTO s2=new CollectionStyleTO();
		s2.setBackgroundColor(Color.decode("#4ac925"));
		s2.setLineColor(Color.decode("#E00707"));
		a2.setStyle(s2);
		areas.add(a2);
		
		CollectionTO a3=new CollectionTO();
		a3.setName("Testi alue 03");
		CollectionStyleTO s3=new CollectionStyleTO();
		s3.setBackgroundColor(Color.decode("#B536DA"));
		s3.setLineColor(Color.decode("#000000"));
		a3.setStyle(s3);
		areas.add(a3);
		
		return areas;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable(new CollectionListTableModel());
			table.getColumnModel().getColumn(0).setCellRenderer(table.getDefaultRenderer(Boolean.class));
			table.getColumnModel().getColumn(0).setCellEditor(table.getDefaultEditor(Boolean.class));
			table.getColumnModel().getColumn(2).setCellRenderer(new StyleRenderer());
			table.getColumnModel().getColumn(0).setMaxWidth(30);
			table.getColumnModel().getColumn(1).setPreferredWidth(300);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			table.getSelectionModel().addListSelectionListener(this);
			table.getModel().addTableModelListener(this);
			//((CollectionListTableModel)table.getModel()).setAreas(getTestAreas());
		}
		return table;
	}
	
	@SuppressWarnings("unchecked")
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(CollectionModel.P_COLLECTIONS)) {
			List<CollectionTO> areas=(List<CollectionTO>) pce.getNewValue();
			System.out.println("showing areas: "+areas.size());
			((CollectionListTableModel)table.getModel()).setCollections(areas);
		}
		else if(pce.getPropertyName().equals(CollectionModel.P_SELECTED)) {
			//AreaTO area=(AreaTO) pce.getNewValue();
			//TODO: select area from table
		}
		
	}

	public void tableChanged(TableModelEvent e) {
		if(e.getColumn()==0) {
			CollectionTO coll=((CollectionListTableModel)e.getSource()).getAreaAtRow(e.getLastRow());
			if(coll.isSelected()) {
				controller.showCollection(coll);
			}
			else {
				controller.hideCollection(coll);
			}
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
			int row=table.getSelectedRow();
			if(row!=-1) {
				CollectionTO coll=((CollectionListTableModel)table.getModel()).getAreaAtRow(row);
				controller.selectCollection(coll);
			}
		}
		
	}
}
