package com.ptool.gui;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CheckBoxRenderer() {
		super();
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		boolean show=(Boolean) value;
		if(show) {
			this.setSelected(true);
		}
		else {
			this.setSelected(false);
		}
		return this;
	}

}
