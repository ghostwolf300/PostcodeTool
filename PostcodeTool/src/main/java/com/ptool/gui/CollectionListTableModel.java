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
	
	private List<CollectionTO> areas=null;
	
	public CollectionListTableModel() {
		super();
	}
	
	public int getRowCount() {
		if(areas==null) {
			return 0;
		}
		else {
			return areas.size();
		}
	}

	public int getColumnCount() {
		return 3;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(areas==null) {
			return null;
		}
		else {
			CollectionTO area=areas.get(rowIndex);
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
			areas.get(row).setSelected(show);
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
	
	public List<CollectionTO> getAreas() {
		return areas;
	}

	public void setAreas(List<CollectionTO> areas) { 
		this.areas = areas;
		this.fireTableDataChanged();
	}
	
	public void addArea(CollectionTO area) {
		if(areas==null) {
			areas=new ArrayList<CollectionTO>();
		}
		areas.add(area);
		this.fireTableDataChanged();
	}
	
	public void removeArea(CollectionTO area) {
		if(areas!=null) {
			areas.remove(area);
			this.fireTableDataChanged();
		}
	}
	
	public CollectionTO getAreaAtRow(int row) {
		if(areas!=null) {
			return areas.get(row);
		}
		else {
			return null;
		}
	}

}
