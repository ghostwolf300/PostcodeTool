package com.ptool.gui;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.swing.JMapPane;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.ptool.controller.DefaultController;
import com.ptool.geo.GeoHelper;
import com.ptool.geo.PostcodeLayer;
import com.ptool.model.MapModel;
import com.ptool.model.PostcodeModel;
import com.ptool.pojo.MapDataTO;
import com.ptool.pojo.PostcodeTO;

public class PostcodeMapPane extends JMapPane implements IView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultController controller=null;
	private MapDataTO mapData=null;
	
	public PostcodeMapPane(DefaultController controller) {
		super();
		this.controller=controller;
		this.controller.addView(this);
		this.initialize();
	}
	
	private void initialize() {
		GTRenderer renderer=new StreamingRenderer();
		this.setRenderer(renderer);
	}
	
	@SuppressWarnings("unchecked")
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(MapModel.P_MAP_DATA)) {
			updateDisplayArea((MapDataTO) pce.getNewValue());
		}
		else if(pce.getPropertyName().equals(PostcodeModel.P_POSTCODES)) {
			updateMapContent((List<PostcodeTO>) pce.getNewValue());
		}
		
	}
	
	private void updateDisplayArea(MapDataTO mapData) {
		this.mapData=mapData;
		CoordinateReferenceSystem crs=GeoHelper.getInstance().getCRS(mapData.getCrs());
		ReferencedEnvelope bounds=new ReferencedEnvelope(mapData.getMinX(),mapData.getMinY(),mapData.getMaxX(),mapData.getMaxY(),crs);
		this.setDisplayArea(bounds);
	}
	
	private void updateMapContent(List<PostcodeTO> postcodes) {
		MapContent content=new MapContent();
		content.addLayer(getTitleLayer());
		this.setMapContent(content);
	}
	
	private Layer getTitleLayer() {
		Layer layer=new PostcodeLayer(this.getDisplayArea());
		layer.setTitle(mapData.getName());
		return layer;
	}

}
