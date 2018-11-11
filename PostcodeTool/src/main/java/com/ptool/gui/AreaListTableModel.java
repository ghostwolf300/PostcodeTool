package com.ptool.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.ptool.pojo.AreaTO;

public class AreaListTableModel extends AbstractTableModel {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int COL_SHOW=0;
	public static final int COL_NAME=1;
	public static final int COL_STYLE=2;
	
	private List<AreaTO> areas=null;
	
	public AreaListTableModel() {
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
			AreaTO area=areas.get(rowIndex);
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
	
	public List<AreaTO> getAreas() {
		return areas;
	}

	public void setAreas(List<AreaTO> areas) { 
		this.areas = areas;
		this.fireTableDataChanged();
	}
	
	public void addArea(AreaTO area) {
		if(areas==null) {
			areas=new ArrayList<AreaTO>();
		}
		areas.add(area);
		this.fireTableDataChanged();
	}
	
	public void removeArea(AreaTO area) {
		if(areas!=null) {
			areas.remove(area);
			this.fireTableDataChanged();
		}
	}
	
	public AreaTO getAreaAtRow(int row) {
		if(areas!=null) {
			return areas.get(row);
		}
		else {
			return null;
		}
	}

}
