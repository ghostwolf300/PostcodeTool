package com.ptool.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.ptool.controller.DefaultController;

import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.beans.PropertyChangeEvent;

import javax.swing.Box;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.JList;

public class AreaPane extends JPanel implements IView {
	private JSplitPane splitPane;
	private JPanel topPane;
	private JPanel bottomPane;
	private JToolBar toolBar;
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnNew;
	private JButton btnDelete;
	private JPanel areaDataPane;
	private JLabel lblName;
	private JTextField fldName;
	private JButton btnBackground;
	private JSlider sliderTransparency;
	private JLabel lblTransparency;
	private JTextField fldTransparency;
	private JButton btnLine;
	private JLabel lblThickness;
	private JSlider sliderThickness;
	private JTextField fldThickness;
	private JScrollPane scrollPane;
	private JList list;
	private DefaultController controller=null;
	
	
	public AreaPane(DefaultController controller) {
		this.controller=controller;
		this.controller.addView(this);
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		add(getSplitPane(), BorderLayout.CENTER);
	}
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane.setLeftComponent(getTopPane());
			splitPane.setRightComponent(getBottomPane());
		}
		return splitPane;
	}
	private JPanel getTopPane() {
		if (topPane == null) {
			topPane = new JPanel();
			topPane.setLayout(new BorderLayout(0, 0));
			topPane.add(getToolBar(), BorderLayout.NORTH);
			topPane.add(getAreaDataPane(), BorderLayout.CENTER);
		}
		return topPane;
	}
	private JPanel getBottomPane() {
		if (bottomPane == null) {
			bottomPane = new JPanel();
			bottomPane.setLayout(new BorderLayout(0, 0));
			bottomPane.add(getScrollPane());
		}
		return bottomPane;
	}
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.setFloatable(false);
			toolBar.add(getBtnNew());
			toolBar.add(getBtnPrevious());
			toolBar.add(getBtnNext());
			toolBar.add(Box.createHorizontalGlue());
			toolBar.add(getBtnDelete());
		}
		return toolBar;
	}
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton("");
			btnPrevious.setIcon(new ImageIcon(AreaPane.class.getResource("/toolbarButtonGraphics/navigation/Back24.gif")));
		}
		return btnPrevious;
	}
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("");
			btnNext.setIcon(new ImageIcon(AreaPane.class.getResource("/toolbarButtonGraphics/navigation/Forward24.gif")));
		}
		return btnNext;
	}
	private JButton getBtnNew() {
		if (btnNew == null) {
			btnNew = new JButton("");
			btnNew.setIcon(new ImageIcon(AreaPane.class.getResource("/toolbarButtonGraphics/general/New24.gif")));
		}
		return btnNew;
	}
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton("");
			btnDelete.setIcon(new ImageIcon(AreaPane.class.getResource("/toolbarButtonGraphics/general/Delete24.gif")));
		}
		return btnDelete;
	}
	private JPanel getAreaDataPane() {
		if (areaDataPane == null) {
			areaDataPane = new JPanel();
			areaDataPane.setLayout(new MigLayout("", "[][grow][][][grow]", "[][][]"));
			areaDataPane.add(getLblName(), "cell 0 0,alignx trailing");
			areaDataPane.add(getFldName(), "cell 1 0 4 1,growx");
			areaDataPane.add(getBtnBackground(), "flowx,cell 1 1,growx,aligny baseline");
			areaDataPane.add(getLblTransparency(), "cell 2 1");
			areaDataPane.add(getSliderTransparency(), "cell 3 1");
			areaDataPane.add(getFldTransparency(), "cell 4 1,growx");
			areaDataPane.add(getBtnLine(), "cell 1 2,growx");
			areaDataPane.add(getLblThickness(), "cell 2 2");
			areaDataPane.add(getSliderThickness(), "cell 3 2");
			areaDataPane.add(getFldThickness(), "cell 4 2,growx");
		}
		return areaDataPane;
	}
	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("Name:");
		}
		return lblName;
	}
	private JTextField getFldName() {
		if (fldName == null) {
			fldName = new JTextField();
			fldName.setText("<New>");
			fldName.setColumns(10);
		}
		return fldName;
	}
	private JButton getBtnBackground() {
		if (btnBackground == null) {
			btnBackground = new JButton("Background");
		}
		return btnBackground;
	}
	private JSlider getSliderTransparency() {
		if (sliderTransparency == null) {
			sliderTransparency = new JSlider();
		}
		return sliderTransparency;
	}
	private JLabel getLblTransparency() {
		if (lblTransparency == null) {
			lblTransparency = new JLabel("Transparency:");
		}
		return lblTransparency;
	}
	private JTextField getFldTransparency() {
		if (fldTransparency == null) {
			fldTransparency = new JTextField();
			fldTransparency.setColumns(10);
		}
		return fldTransparency;
	}
	private JButton getBtnLine() {
		if (btnLine == null) {
			btnLine = new JButton("Line");
		}
		return btnLine;
	}
	private JLabel getLblThickness() {
		if (lblThickness == null) {
			lblThickness = new JLabel("Thickness:");
		}
		return lblThickness;
	}
	private JSlider getSliderThickness() {
		if (sliderThickness == null) {
			sliderThickness = new JSlider();
		}
		return sliderThickness;
	}
	private JTextField getFldThickness() {
		if (fldThickness == null) {
			fldThickness = new JTextField();
			fldThickness.setColumns(10);
		}
		return fldThickness;
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
			list = new JList();
			PostcodeListModel model=new PostcodeListModel();
			list.setModel(model);
		}
		return list;
	}

	public void modelPropertyChange(PropertyChangeEvent pce) {
		// TODO Auto-generated method stub
		
	}
}
