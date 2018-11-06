package com.ptool.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractModel {
	
	protected PropertyChangeSupport propChangeSupport=null;
	
	public AbstractModel() {
		propChangeSupport=new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupport.removePropertyChangeListener(listener);
	}
	
	public void firePropertyChange(String propName,Object oldValue,Object newValue) {
		propChangeSupport.firePropertyChange(propName, oldValue, newValue);
	}
}
