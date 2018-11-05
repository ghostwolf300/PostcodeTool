package com.ptool.gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import javax.swing.JList;

public class PToolFrame extends JFrame {
	private JPanel leftPane;
	private JPanel centerPane;
	private JTextField fldPostcodeSearch;
	private JButton btnPostcodeSearch;
	private JScrollPane scrollPane;
	private JList list;
	
	
	private DefaultController controller;
	
	public PToolFrame() {
		initialize();
	}
	
	private void initialize() {
		setTitle("Postcode Tool 0.1");
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getLeftPane(), BorderLayout.WEST);
		getContentPane().add(getCenterPane(), BorderLayout.CENTER);
	}

	private JPanel getLeftPane() {
		if (leftPane == null) {
			leftPane = new JPanel();
			leftPane.setLayout(new MigLayout("", "[grow][]", "[][grow]"));
			leftPane.add(getFldPostcodeSearch(), "cell 0 0,growx");
			leftPane.add(getBtnPostcodeSearch(), "cell 1 0");
			leftPane.add(getScrollPane(), "cell 0 1 2 1,grow");
		}
		return leftPane;
	}
	private JPanel getCenterPane() {
		if (centerPane == null) {
			centerPane = new JPanel();
		}
		return centerPane;
	}
	private JTextField getFldPostcodeSearch() {
		if (fldPostcodeSearch == null) {
			fldPostcodeSearch = new JTextField();
			fldPostcodeSearch.setColumns(10);
		}
		return fldPostcodeSearch;
	}
	private JButton getBtnPostcodeSearch() {
		if (btnPostcodeSearch == null) {
			btnPostcodeSearch = new JButton("Search");
		}
		return btnPostcodeSearch;
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
		}
		return list;
	}
}
 