package com.ptool.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.ptool.gui.IView;
import com.ptool.model.AbstractModel;


public abstract class AbstractController implements PropertyChangeListener {
	
	private List<AbstractModel> registeredModels=null;
	private List<IView> registeredViews=null;
	
	public AbstractController() {
		registeredModels=new ArrayList<AbstractModel>();
		registeredViews=new ArrayList<IView>();
	}
	
	public void addModel(AbstractModel model) {
		registeredModels.add(model);
		model.addPropertyChangeListener(this);
	}
	
	public void removeModel(AbstractModel model) {
		registeredModels.remove(model);
		model.removePropertyChangeListener(this);
	}
	
	public void addView(IView view) {
		registeredViews.add(view);
	}
	
	public void removeView(IView view) {
		registeredViews.remove(view);
	}
	
	public void propertyChange(PropertyChangeEvent pce) {
		for(IView view : registeredViews) {
			view.modelPropertyChange(pce);
		}

	}
	
	protected void setModelProperty(String propertyName, Object newValue) {

        for (AbstractModel model: registeredModels) {
            try {

                Method method = model.getClass().
                    getMethod("set"+propertyName, new Class[] {
                                                      newValue.getClass()
                                                  }


                             );
                method.invoke(model, newValue);

            } catch (Exception ex) {
                //  Handle exception.
            }
        }
    }

}
