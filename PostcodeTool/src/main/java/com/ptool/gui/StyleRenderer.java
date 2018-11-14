package com.ptool.gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.ptool.pojo.CollectionStyleTO;

public class StyleRenderer extends JLabel implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StyleRenderer() {
		super();
		this.setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		CollectionStyleTO style=(CollectionStyleTO) value;
		setBackground(style.getBackgroundColor());
		setBorder(BorderFactory.createLineBorder(style.getLineColor(),1));
		return this;
	}

}
