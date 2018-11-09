package com.ptool.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ptool.controller.DefaultController;
import com.ptool.model.AreaModel;
import com.ptool.pojo.AreaTO;
import com.ptool.pojo.PostcodeTO;

import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.JList;

public class AreaPane extends JPanel implements IView,ActionListener,ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private JButton btnSave;
	private AreaTO area=null;
	
	public AreaPane(DefaultController controller) {
		this.controller=controller;
		this.controller.addView(this);
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		add(getSplitPane(), BorderLayout.CENTER);
		Dimension pd=this.getPreferredSize();
		pd.width=400;
		this.setPreferredSize(pd);
		Dimension maxd=this.getMaximumSize();
		maxd.width=400;
		this.setMaximumSize(maxd);
		Dimension mind=this.getMinimumSize();
		mind.width=400;
		this.setMinimumSize(mind);
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
			toolBar.add(getBtnSave());
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
			btnPrevious.setActionCommand("PREV");
			btnPrevious.addActionListener(this);
		}
		return btnPrevious;
	}
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("");
			btnNext.setIcon(new ImageIcon(AreaPane.class.getResource("/toolbarButtonGraphics/navigation/Forward24.gif")));
			btnNext.setActionCommand("NEXT");
			btnNext.addActionListener(this);
		}
		return btnNext;
	}
	private JButton getBtnNew() {
		if (btnNew == null) {
			btnNew = new JButton("");
			btnNew.setIcon(new ImageIcon(AreaPane.class.getResource("/toolbarButtonGraphics/general/New24.gif")));
			btnNew.setActionCommand("NEW");
			btnNew.addActionListener(this);
		}
		return btnNew;
	}
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("");
			btnSave.setIcon(new ImageIcon(AreaPane.class.getResource("/toolbarButtonGraphics/general/Save24.gif")));
			btnSave.setActionCommand("SAVE");
			btnSave.addActionListener(this);
		}
		return btnSave;
	}

	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton("");
			btnDelete.setIcon(new ImageIcon(AreaPane.class.getResource("/toolbarButtonGraphics/general/Delete24.gif")));
			btnDelete.setActionCommand("DEL");
			btnDelete.addActionListener(this);
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
			btnBackground.setActionCommand("BACKGROUND");
			btnBackground.addActionListener(this);
		}
		return btnBackground;
	}
	private JSlider getSliderTransparency() {
		if (sliderTransparency == null) {
			sliderTransparency = new JSlider();
			sliderTransparency.setMinimum(10);
			sliderTransparency.setMaximum(100);
			sliderTransparency.setMajorTickSpacing(5);
			sliderTransparency.setSnapToTicks(true);
			sliderTransparency.addChangeListener(this);
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
			fldTransparency.setEditable(false);
		}
		return fldTransparency;
	}
	private JButton getBtnLine() {
		if (btnLine == null) {
			btnLine = new JButton("Line");
			btnLine.setActionCommand("LINE");
			btnLine.addActionListener(this);
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
			sliderThickness.setMinimum(10);
			sliderThickness.setMaximum(50);
			sliderThickness.setMajorTickSpacing(5);
			sliderThickness.setSnapToTicks(true);
			sliderThickness.addChangeListener(this);
		}
		return sliderThickness;
	}
	private JTextField getFldThickness() {
		if (fldThickness == null) {
			fldThickness = new JTextField();
			fldThickness.setColumns(10);
			fldThickness.setEditable(false);
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

	@SuppressWarnings("unchecked")
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(AreaModel.P_POSTCODES)) {
			Set<PostcodeTO> postcodes=(Set<PostcodeTO>) pce.getNewValue();
			PostcodeListModel model=(PostcodeListModel) list.getModel();
			model.setPostcodes(new ArrayList<PostcodeTO>(postcodes));
		}
		else if(pce.getPropertyName().equals(AreaModel.P_SELECTED)) {
			AreaTO area=(AreaTO)pce.getNewValue();
			fldName.setText(area.getName());
			btnBackground.setBackground(Color.decode(area.getBackgroundColor()));
			btnLine.setBackground(Color.decode(area.getLineColor()));
			fldTransparency.setText(Double.toString(area.getTransparency()));
			sliderTransparency.setValue((int) (100*area.getTransparency()));
			fldThickness.setText(Double.toString(area.getLineThickness()));
			sliderThickness.setValue((int)(area.getLineThickness()*10));
			this.area=area;
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnBackground)) {
			Color background=JColorChooser.showDialog(null,"Background Color",btnBackground.getBackground());
			btnBackground.setBackground(background);
		}
		else if(e.getSource().equals(btnLine)) {
			Color line=JColorChooser.showDialog(null,"Line Color",btnLine.getBackground());
			btnLine.setBackground(line);
		}
		
	}

	public void stateChanged(ChangeEvent e) {
		if(e.getSource().equals(sliderTransparency)) {
			double transparency=sliderTransparency.getValue()/100.0;
			fldTransparency.setText(Double.toString(transparency));
		}
		else if(e.getSource().equals(sliderThickness)) {
			double thickness=sliderThickness.getValue();
			fldThickness.setText(Double.toString(thickness/10.0));
		}
		
	}
	
}
