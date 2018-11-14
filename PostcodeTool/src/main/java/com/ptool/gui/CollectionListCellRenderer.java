package com.ptool.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.ptool.pojo.CollectionStyleTO;
import com.ptool.pojo.CollectionTO;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class CollectionListCellRenderer extends JPanel implements ListCellRenderer<CollectionTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox checkBox;
	private JLabel lblBackground;
	public CollectionListCellRenderer() {
		initialize();
	}
	private void initialize() {
		Dimension d=new Dimension(280,38);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		setBackground(Color.WHITE);
		add(getCheckBox());
		add(getLblBackground());
	}

	public Component getListCellRendererComponent(JList<? extends CollectionTO> list, CollectionTO area, int index,
			boolean isSelected, boolean cellHasFocus) {
		CollectionStyleTO style=area.getStyle();
		checkBox.setText(area.getName());
		lblBackground.setBackground(style.getBackgroundColor());
		lblBackground.setBorder(BorderFactory.createLineBorder(style.getLineColor(),1));
		return this;
	}

	private JCheckBox getCheckBox() {
		if (checkBox == null) {
			checkBox = new JCheckBox("<Area name>");
			checkBox.setOpaque(false);
		}
		return checkBox;
	}
	private JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			Dimension d=new Dimension(60,28);
			lblBackground.setMinimumSize(d);
			lblBackground.setMaximumSize(d);
			lblBackground.setPreferredSize(d);
			lblBackground.setOpaque(true);
			lblBackground.setBackground(Color.WHITE);
			lblBackground.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
		return lblBackground;
	}
}
