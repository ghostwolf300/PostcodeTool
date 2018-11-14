package com.ptool.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ptool.controller.DefaultController;
import com.ptool.model.CollectionModel;
import com.ptool.pojo.CollectionStyleTO;
import com.ptool.pojo.CollectionTO;
import com.ptool.pojo.MapAreaTO;

import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.JList;

public class CollectionPane extends JPanel implements IView,ActionListener,ChangeListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSplitPane splitArea;
	private JPanel topPane;
	private JPanel bottomPane;
	private JToolBar toolBar;
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
	private JPanel areaListPane=null;
	private JSplitPane splitMain;
	
	private CollectionTO area=null;
	private JButton btnCopy;
	
	public CollectionPane() {
		initialize();
	}
	
	public CollectionPane(DefaultController controller) {
		this.controller=controller;
		this.controller.addView(this);
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		Dimension pd=this.getPreferredSize();
		pd.width=400;
		this.setPreferredSize(pd);
		Dimension maxd=this.getMaximumSize();
		maxd.width=400;
		this.setMaximumSize(maxd);
		Dimension mind=this.getMinimumSize();
		mind.width=400;
		this.setMinimumSize(mind);
		add(getSplitMain(), BorderLayout.CENTER);
	}
	private JSplitPane getSplitMain() {
		if (splitMain == null) {
			splitMain = new JSplitPane();
			splitMain.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitMain.setTopComponent(getAreaListPane());
			splitMain.setBottomComponent(getSplitArea());
			
		}
		return splitMain;
	}
	
	private JPanel getAreaListPane() {
		if(areaListPane==null) {
			areaListPane=new CollectionListPane(controller);
		}
		return areaListPane;
	}

	private JSplitPane getSplitArea() {
		if (splitArea == null) {
			splitArea = new JSplitPane();
			splitArea.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitArea.setLeftComponent(getTopPane());
			splitArea.setRightComponent(getBottomPane());
		}
		return splitArea;
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
			toolBar.add(getBtnCopy());
			toolBar.add(getBtnSave());
			toolBar.add(Box.createHorizontalGlue());
			toolBar.add(getBtnDelete());
		}
		return toolBar;
	}
	private JButton getBtnNew() {
		if (btnNew == null) {
			btnNew = new JButton("");
			btnNew.setIcon(new ImageIcon(CollectionPane.class.getResource("/toolbarButtonGraphics/general/New24.gif")));
			btnNew.setActionCommand("NEW");
			btnNew.addActionListener(this);
		}
		return btnNew;
	}
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton("");
			btnCopy.setIcon(new ImageIcon(CollectionPane.class.getResource("/toolbarButtonGraphics/general/Copy24.gif")));
			btnCopy.setActionCommand("COPY");
			btnCopy.addActionListener(this);
		}
		return btnCopy;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("");
			btnSave.setIcon(new ImageIcon(CollectionPane.class.getResource("/toolbarButtonGraphics/general/Save24.gif")));
			btnSave.setActionCommand("SAVE");
			btnSave.addActionListener(this);
		}
		return btnSave;
	}

	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton("");
			btnDelete.setIcon(new ImageIcon(CollectionPane.class.getResource("/toolbarButtonGraphics/general/Delete24.gif")));
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
			MapAreaListModel model=new MapAreaListModel();
			list.setModel(model);
			list.addKeyListener(this);
		}
		return list;
	}
	
	private void createNewArea() {
		area=new CollectionTO();
		CollectionStyleTO style=new CollectionStyleTO();;
		area.setStyle(style);
		fldName.setText(area.getName());
		btnBackground.setBackground(style.getBackgroundColor());
		btnLine.setBackground(style.getLineColor());
		fldTransparency.setText(Double.toString(style.getTransparency()));
		sliderTransparency.setValue((int) (100*style.getTransparency()));
		fldThickness.setText(Double.toString(style.getLineThickness()));
		sliderThickness.setValue((int)(style.getLineThickness()*10));
		((MapAreaListModel)list.getModel()).setPostcodes(null);
	}
	
	private void copyArea() {
		area=new CollectionTO(area);
		fldName.setText(area.getName());
	}
	
	private void updateArea() {
		area.setName(fldName.getText());
		area.getStyle().setBackgroundColor(btnBackground.getBackground());
		area.getStyle().setLineColor(btnLine.getBackground());
		area.getStyle().setTransparency(Double.valueOf(fldTransparency.getText()));
		area.getStyle().setLineThickness(Double.valueOf(fldThickness.getText()));
		List<MapAreaTO> postcodes=((MapAreaListModel)list.getModel()).getPostcodes();
		if(postcodes!=null) {
			area.setMapAreas(new HashSet<MapAreaTO>(postcodes));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(CollectionModel.P_POSTCODES)) {
			Set<MapAreaTO> postcodes=(Set<MapAreaTO>) pce.getNewValue();
			MapAreaListModel model=(MapAreaListModel) list.getModel();
			model.setPostcodes(new ArrayList<MapAreaTO>(postcodes));
		}
		else if(pce.getPropertyName().equals(CollectionModel.P_SELECTED)) {
			area=(CollectionTO)pce.getNewValue();
			CollectionStyleTO style=area.getStyle();
			fldName.setText(area.getName());
			btnBackground.setBackground(style.getBackgroundColor());
			btnLine.setBackground(style.getLineColor());
			fldTransparency.setText(Double.toString(style.getTransparency()));
			sliderTransparency.setValue((int) (100*style.getTransparency()));
			fldThickness.setText(Double.toString(style.getLineThickness()));
			sliderThickness.setValue((int)(style.getLineThickness()*10));
			if(area.getMapAreas()==null) {
				((MapAreaListModel)list.getModel()).setPostcodes(null);
			}
			else {
				((MapAreaListModel)list.getModel()).setPostcodes(new ArrayList<MapAreaTO>(area.getMapAreas()));
			}
			
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
		else if(e.getSource().equals(btnNew)) {
			createNewArea();
		}
		else if(e.getSource().equals(btnCopy)) {
			copyArea();
		}
		else if(e.getSource().equals(btnSave)) {
			updateArea();
			controller.saveCollection(area);
		}
		else if(e.getSource().equals(btnDelete)) {
			controller.removeCollection(area);
			createNewArea();
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

	public void keyPressed(KeyEvent e) {
		System.out.println("Got keyevent : "+e.getKeyCode());
		if(e.getKeyCode()==KeyEvent.VK_DELETE) {
			MapAreaTO pc=(MapAreaTO) list.getSelectedValue();
			if(pc!=null) {
				((MapAreaListModel)list.getModel()).remove(pc);
			}
		}
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}
}
