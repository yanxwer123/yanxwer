package com.kld.app.view.main;
 
import javax.swing.JTabbedPane;
 
public class PerTabbedPane extends JTabbedPane {
	public PerTabbedPane() {
		  super();
		  initialize();
		 }
		 
		 private void initialize() {
		  setUI(new PerTabbedPaneUI());
		 
		 }
}
