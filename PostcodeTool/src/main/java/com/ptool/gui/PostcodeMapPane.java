package com.ptool.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.factory.CommonFactoryFinder;
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
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Fill;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.Symbolizer;
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
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.identity.FeatureId;
import org.opengis.filter.identity.Identifier;
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
	private FilterFactory2 ff=null;
	private StyleFactory sf=null;
	
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
		
		ff=CommonFactoryFinder.getFilterFactory2();
		sf=CommonFactoryFinder.getStyleFactory();
		
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
		else if(pce.getPropertyName().equals(PostcodeModel.P_SELECTED)) {
			highlightSelected((PostcodeTO)pce.getNewValue());
		}
		
	}
	
	private void highlightSelected(PostcodeTO postcode) {
		Set<FeatureId> selectedIds=new HashSet<FeatureId>();
		for(PolygonTO pcPoly : postcode.getPolygons()) {
			selectedIds.add(ff.featureId(pcPoly.toString()));
		}
		System.out.println("Displaying: "+selectedIds.size());
		this.displaySelectedFeatures(selectedIds);
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
		builder.add("postcode", String.class);
		builder.add("name",String.class);

		// build the type
		SimpleFeatureType TYPE = builder.buildFeatureType();

		// create features using the type defined
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
		
		List<SimpleFeature> features = new ArrayList<SimpleFeature>();
		
		for(PostcodeTO pc : postcodes) {
			List<PolygonTO> postcodePolygons=pc.getPolygons();
			for(PolygonTO pcPoly : postcodePolygons) {
				featureBuilder.add(pcPoly.getGeometryPolygon());
				featureBuilder.add(pc.getPostcode());
				featureBuilder.add(pc.getName());
				SimpleFeature feature=featureBuilder.buildFeature(pcPoly.toString());
				features.add(feature);
			}
		}
		//Style style=SLD.createPolygonStyle(Color.BLACK, Color.BLUE, 0.25f);
		Style style=createDefaultStyle();
		Layer layer = new FeatureLayer(DataUtilities.collection(features), style);
		
		
		layer.setTitle("Postcodes");
		
		return layer;
	}
	
	private void selectFeature(MapMouseEvent ev) {
		System.out.println("click at: "+ev.getWorldPos());
		java.awt.Point screenPos=ev.getPoint();
		Rectangle screenRect=new Rectangle(screenPos.x-2,screenPos.y-2,5,5);
		
		AffineTransform screenToWorld=this.getScreenToWorldTransform();
		Rectangle2D worldRect=screenToWorld.createTransformedShape(screenRect).getBounds2D();
		ReferencedEnvelope bbox=new ReferencedEnvelope(worldRect,this.getMapContent().getCoordinateReferenceSystem());
		
		Filter filter=ff.intersects(ff.property("polygon"), ff.literal(bbox));
		
		Layer layer=this.getMapContent().layers().get(0);
		Set<FeatureId> selectedIds=null;
		try {
			SimpleFeatureCollection selectedFeatures=(SimpleFeatureCollection)layer.getFeatureSource().getFeatures(filter);
			System.out.println("you selected #: "+selectedFeatures.size());
			SimpleFeatureIterator iter=selectedFeatures.features();
			selectedIds=new HashSet<FeatureId>();
			while(iter.hasNext()) {
				SimpleFeature feature=iter.next();
				selectedIds.add(feature.getIdentifier());
				System.out.println(feature.getIdentifier());
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		if(selectedIds!=null && !selectedIds.isEmpty()) {
			displaySelectedFeatures(selectedIds);
		}
		
	}
	
	private void displaySelectedFeatures(Set<FeatureId> selectedIds) {
		Style style=null;
		
		if(selectedIds.isEmpty()) {
			style=createDefaultStyle();
		}
		else {
			style=createSelectedStyle(selectedIds);
		}
		Layer layer=this.getMapContent().layers().get(0);
		((FeatureLayer) layer).setStyle(style);
		//this.repaint();
		
	}
	
	private Style createDefaultStyle() {
		Rule rule = createRule(Color.BLACK, Color.BLUE);

        FeatureTypeStyle fts = sf.createFeatureTypeStyle();
        fts.rules().add(rule);

        Style style = sf.createStyle();
        //Style style=SLD.createPolygonStyle(Color.BLACK, Color.BLUE, 0.25f);
        style.featureTypeStyles().add(fts);
        return style;
	}
	
	private Style createSelectedStyle(Set<FeatureId> selectedIds) {
		
		Rule selectedRule = createRule(Color.RED, Color.YELLOW);
		selectedRule.setFilter(ff.id(selectedIds));
		Rule otherRule=createRule(Color.BLACK, Color.BLUE);
		otherRule.setElseFilter(true);
		
        FeatureTypeStyle fts = sf.createFeatureTypeStyle();
        fts.rules().add(selectedRule);
        fts.rules().add(otherRule);

        Style style = sf.createStyle();
        style.featureTypeStyles().add(fts);
        return style;
	}
	
	private Rule createRule(Color outlineColor,Color fillColor) {
		Symbolizer symbolizer = null;
        Fill fill = null;
        Stroke stroke = sf.createStroke(ff.literal(outlineColor), ff.literal(1.0f));
        fill = sf.createFill(ff.literal(fillColor), ff.literal(1.0f));
        symbolizer = sf.createPolygonSymbolizer(stroke, fill, "polygon");
        
        Rule rule = sf.createRule();
        rule.symbolizers().add(symbolizer);
        return rule;
	}

	public void onMouseClicked(MapMouseEvent ev) {
		selectFeature(ev);
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
