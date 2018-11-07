package com.ptool.gui;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.DataUtilities;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapPane;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
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
		MapContent content=new MapContent();
		content.setTitle("Testi");
		this.setMapContent(content);
		GTRenderer renderer=new StreamingRenderer();
		this.setRenderer(renderer);
		this.setBackground(Color.GREEN);
	}
	
	@SuppressWarnings("unchecked")
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(MapModel.P_MAP_DATA)) {
			System.out.println("received map data...");
			updateDisplayArea((MapDataTO) pce.getNewValue());
		}
		else if(pce.getPropertyName().equals(PostcodeModel.P_POSTCODES)) {
			System.out.println("received postcodes...");
			updateMapContent((List<PostcodeTO>) pce.getNewValue());
		}
		
	}
	
	private void updateDisplayArea(MapDataTO mapData) {
		this.mapData=mapData;
		System.out.println(mapData);
		/*CoordinateReferenceSystem crs=GeoHelper.getInstance().getCRS(mapData.getCrs());
		ReferencedEnvelope bounds=new ReferencedEnvelope(mapData.getMinX(),mapData.getMinY(),mapData.getMaxX(),mapData.getMaxY(),crs);
		this.setDisplayArea(bounds);*/
	}
	
	private void updateMapContent(List<PostcodeTO> postcodes) {
		this.getMapContent().addLayer(getPointLayer());
	}
	
	private Layer getTitleLayer() {
		Layer layer=new PostcodeLayer(this.getDisplayArea());
		layer.setTitle(mapData.getName());
		return layer;
	}
	
	private Layer getPointLayer() {
		
		double x=60.29954942;
		double y=24.97964102;

		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
		builder.setName("MyFeatureType");
		builder.setCRS(DefaultGeographicCRS.WGS84); // set crs        
		builder.add("location", Point.class);

		// build the type
		SimpleFeatureType TYPE = builder.buildFeatureType();

		// create features using the type defined
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

		Point point = geometryFactory.createPoint(new Coordinate(x, y));
		String name="My Point";
		int number=1;
		
		featureBuilder.add(point);
		//featureBuilder.add(name);
		//featureBuilder.add(number);
		
		List<SimpleFeature> features = new ArrayList<SimpleFeature>();
		
		SimpleFeature feature = featureBuilder.buildFeature(null);
		features.add(feature); // Add feature 1, 2, 3, etc

		Style style = SLD.createPointStyle("Star", Color.BLUE, Color.BLUE, 0.3f, 15);
		Layer layer = new FeatureLayer(DataUtilities.collection(features), style);
		layer.setTitle("NewPointLayer");
		
		return layer;
	}

}
