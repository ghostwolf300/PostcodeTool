package com.ptool.gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapPane;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.event.MapMouseListener;
import org.geotools.swing.tool.ScrollWheelTool;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.ptool.controller.DefaultController;
import com.ptool.model.MapModel;
import com.ptool.model.PostcodeModel;
import com.ptool.pojo.MapDataTO;
import com.ptool.pojo.PolygonTO;
import com.ptool.pojo.PostcodeTO;

public class PostcodeMapPane extends JMapPane implements IView,MapMouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultController controller=null;
	private MapContent map=null;
	private MapDataTO mapData=null;
	
	public PostcodeMapPane(DefaultController controller) {
		super();
		this.controller=controller;
		this.controller.addView(this);
		this.initialize();
	}
	
	private void initialize() {
		
		this.setBackground(Color.GREEN);
		
		this.setMapContent(getMap());
		GTRenderer renderer=new StreamingRenderer();
		this.setRenderer(renderer);
		
		this.addMouseListener(this);
		this.addMouseListener(new ScrollWheelTool(this));
	}
	
	private MapContent getMap() {
		if(map==null) {
			map=new MapContent();
			map.setTitle("Testi");
		}
		return map;
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
		/*CoordinateReferenceSystem crs=DefaultGeographicCRS.WGS84;
		ReferencedEnvelope bounds=new ReferencedEnvelope(mapData.getMinX(),mapData.getMaxX(),mapData.getMinY(),mapData.getMaxY(),crs);
		this.setDisplayArea(bounds);*/
		
		//map.addLayer(getBoundingBoxLayer());
	}
	
	private void updateMapContent(List<PostcodeTO> postcodes) {
		
		/*CoordinateReferenceSystem crs=DefaultGeographicCRS.WGS84;
		ReferencedEnvelope bounds=new ReferencedEnvelope(mapData.getMinX(),mapData.getMaxX(),mapData.getMinY(),mapData.getMaxY(),crs);
		this.setDisplayArea(bounds);*/
		
		map.addLayer(getPostcodeLayer(postcodes));
		
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
	
	private Layer getBoundingBoxLayer() {
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
		builder.setName("MyFeatureType");
		builder.setCRS(DefaultGeographicCRS.WGS84); // set crs        
		builder.add("rectangle",Polygon.class);

		// build the type
		SimpleFeatureType TYPE = builder.buildFeatureType();

		// create features using the type defined
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
		
		List<SimpleFeature> features = new ArrayList<SimpleFeature>();
		
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
		Coordinate[] coordinates=new Coordinate[5];
		coordinates[0]=new Coordinate(mapData.getMinX(),mapData.getMinY());
		coordinates[1]=new Coordinate(mapData.getMaxX(),mapData.getMinY());
		coordinates[2]=new Coordinate(mapData.getMaxX(),mapData.getMaxY());
		coordinates[3]=new Coordinate(mapData.getMinX(),mapData.getMaxY());
		coordinates[4]=new Coordinate(mapData.getMinX(),mapData.getMinY());
		LinearRing shell=geometryFactory.createLinearRing(coordinates);
		Polygon bounds=geometryFactory.createPolygon(shell);
		
		featureBuilder.add(bounds);
		SimpleFeature feature=featureBuilder.buildFeature(null);
		features.add(feature);
		
		
		Style style=SLD.createPolygonStyle(Color.RED, Color.WHITE, 0.25f);
		Layer layer = new FeatureLayer(DataUtilities.collection(features), style);
		layer.setTitle("Bounds");
		
		return layer;
	}
	
	private Layer getPostcodeLayer(List<PostcodeTO> postcodes) {
		
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
		builder.setName("MyFeatureType");
		
		CoordinateReferenceSystem crs=null;
		if(mapData.getCrs().equals("urn:ogc:def:crs:EPSG::4326")) {
			crs=DefaultGeographicCRS.WGS84;
		}
		else {
			try {
				crs=CRS.decode(mapData.getCrs());
			} 
			catch (NoSuchAuthorityCodeException e) {
				e.printStackTrace();
			} 
			catch (FactoryException e) {
				e.printStackTrace();
			}
		}
		
		builder.setCRS(crs); // set crs        
		builder.add("polygon", Polygon.class);

		// build the type
		SimpleFeatureType TYPE = builder.buildFeatureType();

		// create features using the type defined
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
		
		List<SimpleFeature> features = new ArrayList<SimpleFeature>();
		
		for(PostcodeTO pc : postcodes) {
			List<PolygonTO> postcodePolygons=pc.getPolygons();
			for(PolygonTO pcPoly : postcodePolygons) {
				featureBuilder.add(pcPoly.getGeometryPolygon());
				SimpleFeature feature=featureBuilder.buildFeature(null);
				features.add(feature);
			}
		}
		
		Style style=SLD.createPolygonStyle(Color.RED, Color.BLUE, 0.25f);
		Layer layer = new FeatureLayer(DataUtilities.collection(features), style);
		
		
		layer.setTitle("Postcodes");
		
		return layer;
	}

	public void onMouseClicked(MapMouseEvent ev) {
		// TODO Auto-generated method stub
		
	}

	public void onMouseDragged(MapMouseEvent ev) {
		// TODO Auto-generated method stub
		
	}

	public void onMouseEntered(MapMouseEvent ev) {
		// TODO Auto-generated method stub
		
	}

	public void onMouseExited(MapMouseEvent ev) {
		// TODO Auto-generated method stub
		
	}

	public void onMouseMoved(MapMouseEvent ev) {
		// TODO Auto-generated method stub
		
	}

	public void onMousePressed(MapMouseEvent ev) {
		// TODO Auto-generated method stub
		
	}

	public void onMouseReleased(MapMouseEvent ev) {
		// TODO Auto-generated method stub
		
	}

	public void onMouseWheelMoved(MapMouseEvent ev) {
		// TODO Auto-generated method stub
		
	}

	

}
