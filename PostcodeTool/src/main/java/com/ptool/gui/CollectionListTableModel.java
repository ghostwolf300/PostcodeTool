package com.ptool.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.ptool.pojo.CollectionTO;

public class CollectionListTableModel extends AbstractTableModel {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int COL_SHOW=0;
	public static final int COL_NAME=1;
	public static final int COL_STYLE=2;
	
	private List<CollectionTO> collections=null;
	
	public CollectionListTableModel() {
		super();
	}
	
	public int getRowCount() {
		if(collections==null) {
			return 0;
		}
		else {
			return collections.size();
		}
	}

	public int getColumnCount() {
		return 3;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(collections==null) {
			return null;
		}
		else {
			CollectionTO area=collections.get(rowIndex);
			switch(columnIndex) {
				case COL_SHOW :
					return area.isSelected();
				case COL_NAME :
					return area.getName();
				case COL_STYLE :
					return area.getStyle();
				default :
					return null;
			}
		}
	}
	
	public void setValueAt(Object value,int row,int col) {
		if(col==COL_SHOW) {
			boolean show=(Boolean) value;
			collections.get(row).setSelected(show);
			this.fireTableCellUpdated(row, col);
		}
	}
	
	public boolean isCellEditable(int row,int col) {
		if(col==COL_SHOW) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public List<CollectionTO> getCollections() {
		return collections;
	}

	public void setCollections(List<CollectionTO> areas) { 
		this.collections = areas;
		this.fireTableDataChanged();
	}
	
	public void addArea(CollectionTO area) {
		if(collections==null) {
			collections=new ArrayList<CollectionTO>();
		}
		collections.add(area);
		this.fireTableDataChanged();
	}
	
	public void removeArea(CollectionTO area) {
		if(collections!=null) {
			collections.remove(area);
			this.fireTableDataChanged();
		}
	}
	
	public CollectionTO getAreaAtRow(int row) {
		if(collections!=null) {
			return collections.get(row);
		}
		else {
			return null;
		}
	}

}
