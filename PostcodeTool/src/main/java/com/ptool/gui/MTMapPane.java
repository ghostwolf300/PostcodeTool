package com.ptool.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

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
import org.locationtech.jts.geom.Polygon;

import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.identity.FeatureId;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.ptool.controller.DefaultController;
import com.ptool.model.CollectionModel;
import com.ptool.model.MapModel;
import com.ptool.model.MapAreaModel;
import com.ptool.pojo.CollectionStyleTO;
import com.ptool.pojo.CollectionTO;
import com.ptool.pojo.MapDataTO;
import com.ptool.pojo.MapImageParametersTO;
import com.ptool.pojo.PolygonTO;
import com.ptool.pojo.MapAreaTO;

public class MTMapPane extends JMapPane implements IView,MapMouseListener,ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DefaultController controller=null;
	private MapContent map=null;
	private MapDataTO mapData=null;
	private Layer postcodeLayer=null;
	private Map<String,Layer> areaLayers=null;
	private FilterFactory2 ff=null;
	private StyleFactory sf=null;
	private JPopupMenu popup=null;
	
	private Set<MapAreaTO> selectedPostcodes=null;
	
	public MTMapPane(DefaultController controller) {
		super();
		this.controller=controller;
		this.controller.addView(this);
		this.initialize();
	}
	
	private void initialize() {
		Dimension pd=this.getPreferredSize();
		pd.width=800;
		this.setPreferredSize(pd);
		/*Dimension md=this.getMinimumSize();
		md.width=800;
		this.setMinimumSize(md);*/
		this.setBackground(Color.GREEN);
		
		this.setMapContent(getMap());
		GTRenderer renderer=new StreamingRenderer();
		this.setRenderer(renderer);
		
		ff=CommonFactoryFinder.getFilterFactory2();
		sf=CommonFactoryFinder.getStyleFactory();
		
		this.createPopup();
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
	
	private void createPopup() {
		popup=new JPopupMenu();
		JMenuItem itemAddToArea=new JMenuItem("Add to area");
		itemAddToArea.addActionListener(this);
		popup.add(itemAddToArea);
	}
	
	private void highlightSelected(Set<MapAreaTO> postcodes) {
		Set<FeatureId> selectedIds=new HashSet<FeatureId>();
		for(MapAreaTO pc : postcodes) {
			for(PolygonTO pcPoly : pc.getPolygons()) {
				selectedIds.add(ff.featureId(pcPoly.toString()));
			}
		}
		System.out.println("Displaying: "+selectedIds.size());
		this.displaySelectedFeatures(selectedIds);
	}
	
	private void updateDisplayArea(MapDataTO mapData) {
		this.mapData=mapData;
		System.out.println(mapData);
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
		ReferencedEnvelope bounds = new ReferencedEnvelope(mapData.getMinX(),mapData.getMaxX(),mapData.getMinY(),mapData.getMaxY(), crs);
		this.setDisplayArea(bounds);
		//map.layers().add(0, getBoundingBoxLayer());
		map.addLayer(getBoundingBoxLayer());
	}
	
	private void updateMapContent(List<MapAreaTO> postcodes) {
		
		postcodeLayer=getPostcodeLayer(postcodes);
		map.addLayer(postcodeLayer);
		
	}
	
	private SimpleFeatureTypeBuilder getPolygonTypeBuilder() {
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
		builder.setName("PolygonType");
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
		builder.setCRS(crs);
		builder.add("polygon",Polygon.class);
		return builder;
	}
	
	private Layer getAreaLayer(CollectionTO area) {
		
		SimpleFeatureTypeBuilder builder = getPolygonTypeBuilder();
		builder.add("postcode", String.class);
		builder.add("name",String.class);
		builder.add("pcObject",MapAreaTO.class);
		builder.add("areaId",Integer.class);
		
		// build the type
		SimpleFeatureType TYPE = builder.buildFeatureType();

		// create features using the type defined
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
		List<SimpleFeature> features = new ArrayList<SimpleFeature>();
		
		for(MapAreaTO pc : area.getMapAreas()) {
			List<PolygonTO> postcodePolygons=pc.getPolygons();
			for(PolygonTO pcPoly : postcodePolygons) {
				featureBuilder.add(pcPoly.getGeometryPolygon());
				featureBuilder.add(pc.getName1());
				featureBuilder.add(pc.getName2());
				featureBuilder.add(pc);
				featureBuilder.add(area.getId());
				SimpleFeature feature=featureBuilder.buildFeature(pcPoly.toString());
				features.add(feature);
			}
		}
		Style style=createAreaStyle(area.getStyle());
		Layer layer = new FeatureLayer(DataUtilities.collection(features), style,"AREA");
		areaLayers.put(String.valueOf(area.getId()), layer);
		return layer;
	}
	
	private void clearAreaLayers() {
		System.out.println("layer count: "+map.layers().size());
		/*if(map.layers().size()>2) {
			for(int i=2;i<map.layers().size();i++) {
				map.layers().remove(i);
			}
		}*/
		if(areaLayers!=null) {
			Set<String> keys=areaLayers.keySet();
			for(String key : keys) {
				map.removeLayer(areaLayers.get(key));
			}
		}
	}
	
	private Layer getBoundingBoxLayer() {
		SimpleFeatureTypeBuilder builder = getPolygonTypeBuilder();

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
		
		
		Style style=createTransparentStyle();
		Layer layer = new FeatureLayer(DataUtilities.collection(features), style);
		layer.setTitle("Bounds");
		
		return layer;
	}
	
	private Layer getPostcodeLayer(List<MapAreaTO> postcodes) {
		
		SimpleFeatureTypeBuilder builder = getPolygonTypeBuilder();
		builder.add("postcode", String.class);
		builder.add("name",String.class);
		builder.add("pcObject",MapAreaTO.class);

		// build the type
		SimpleFeatureType TYPE = builder.buildFeatureType();

		// create features using the type defined
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
		
		List<SimpleFeature> features = new ArrayList<SimpleFeature>();
		
		for(MapAreaTO pc : postcodes) {
			List<PolygonTO> postcodePolygons=pc.getPolygons();
			for(PolygonTO pcPoly : postcodePolygons) {
				featureBuilder.add(pcPoly.getGeometryPolygon());
				featureBuilder.add(pc.getName1());
				featureBuilder.add(pc.getName2());
				featureBuilder.add(pc);
				SimpleFeature feature=featureBuilder.buildFeature(pcPoly.toString());
				features.add(feature);
			}
		}
		//Style style=SLD.createPolygonStyle(Color.BLACK, Color.BLUE, 0.25f);
		Style style=createDefaultStyle();
		Layer layer = new FeatureLayer(DataUtilities.collection(features), style,"POSTCODES");
		
		return layer;
	}
	
	private void selectFeatures(MapMouseEvent ev) {
		System.out.println("click at: "+ev.getWorldPos());
		java.awt.Point screenPos=ev.getPoint();
		Rectangle screenRect=new Rectangle(screenPos.x-2,screenPos.y-2,5,5);
		
		AffineTransform screenToWorld=this.getScreenToWorldTransform();
		Rectangle2D worldRect=screenToWorld.createTransformedShape(screenRect).getBounds2D();
		ReferencedEnvelope bbox=new ReferencedEnvelope(worldRect,this.getMapContent().getCoordinateReferenceSystem());
		
		Filter filter=ff.intersects(ff.property("polygon"), ff.literal(bbox));
		
		Set<FeatureId> selectedIds=null;
		
		try {
			SimpleFeatureCollection selectedFeatures=(SimpleFeatureCollection)postcodeLayer.getFeatureSource().getFeatures(filter);
			System.out.println("you selected #: "+selectedFeatures.size());
			SimpleFeatureIterator iter=selectedFeatures.features();
			selectedIds=new HashSet<FeatureId>();
			
			if(selectedPostcodes==null) {
				selectedPostcodes=new HashSet<MapAreaTO>();
			}
			else {
				selectedPostcodes.clear();
			}
			
			while(iter.hasNext()) {
				SimpleFeature feature=iter.next();
				MapAreaTO pc=(MapAreaTO) feature.getAttribute("pcObject");
				selectedIds.add(feature.getIdentifier());
				selectedPostcodes.add(pc);
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		if(selectedIds!=null && !selectedIds.isEmpty()) {
			controller.selectMapAreas(selectedPostcodes);
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
		((FeatureLayer) postcodeLayer).setStyle(style);
	}
	
	private Style createAreaStyle(CollectionStyleTO areaStyle) {
		Rule rule=createRule(areaStyle.getLineColor(),areaStyle.getBackgroundColor(),(float)areaStyle.getTransparency(),(float)areaStyle.getLineThickness());
		FeatureTypeStyle fts=sf.createFeatureTypeStyle();
		fts.rules().add(rule);
		Style style=sf.createStyle();
		style.featureTypeStyles().add(fts);
		return style;
	}
	
	private Style createTransparentStyle() {
		Rule rule=createRule(Color.BLACK,Color.WHITE,0.0f,1.0f);
		FeatureTypeStyle fts=sf.createFeatureTypeStyle();
		fts.rules().add(rule);
		Style style=sf.createStyle();
		style.featureTypeStyles().add(fts);
		return style;
	}
	
	private Style createDefaultStyle() {
		Rule rule = createRule(Color.BLACK, Color.BLUE,1.0f,1.0f);

        FeatureTypeStyle fts = sf.createFeatureTypeStyle();
        fts.rules().add(rule);

        Style style = sf.createStyle();
        //Style style=SLD.createPolygonStyle(Color.BLACK, Color.BLUE, 0.25f);
        style.featureTypeStyles().add(fts);
        return style;
	}
	
	private Style createSelectedStyle(Set<FeatureId> selectedIds) {
		
		Rule selectedRule = createRule(Color.RED, Color.YELLOW,1.0f,1.0f);
		selectedRule.setFilter(ff.id(selectedIds));
		Rule otherRule=createRule(Color.BLACK, Color.BLUE,1.0f,1.0f);
		otherRule.setElseFilter(true);
		
        FeatureTypeStyle fts = sf.createFeatureTypeStyle();
        fts.rules().add(selectedRule);
        fts.rules().add(otherRule);

        Style style = sf.createStyle();
        style.featureTypeStyles().add(fts);
        return style;
	}
	
	private Rule createRule(Color outlineColor,Color fillColor,float opacity,float thickness) {
		Symbolizer symbolizer = null;
        Fill fill = null;
        Stroke stroke = sf.createStroke(ff.literal(outlineColor), ff.literal(thickness));
        fill = sf.createFill(ff.literal(fillColor), ff.literal(opacity));
        symbolizer = sf.createPolygonSymbolizer(stroke, fill, "polygon");
        
        Rule rule = sf.createRule();
        rule.symbolizers().add(symbolizer);
        return rule;
	}
	
	private void showAreas(Set<CollectionTO> areas) {
		if(areas==null || areas.size()==0) {
			System.out.println("no areas selected");
			clearAreaLayers();
		}
		else {
			if(areaLayers==null) {
				areaLayers=new HashMap<String,Layer>();	
			}
			if(areaLayers.size()>0) {
				clearAreaLayers();
			}
			for(CollectionTO area : areas) {
				System.out.println("show: "+area.getName());
				if(area.getMapAreas()!=null && area.getMapAreas().size()>0) {
					map.addLayer(getAreaLayer(area));
				}
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(MapModel.P_MAP_DATA)) {
			System.out.println("received map data...");
			updateDisplayArea((MapDataTO) pce.getNewValue());
		}
		else if(pce.getPropertyName().equals(MapAreaModel.P_MAP_AREAS)) {
			System.out.println("received postcodes...");
			updateMapContent((List<MapAreaTO>) pce.getNewValue());
			
		}
		else if(pce.getPropertyName().equals(MapAreaModel.P_SELECTED)) {
			highlightSelected((Set<MapAreaTO>)pce.getNewValue());
		}
		else if(pce.getPropertyName().equals(CollectionModel.P_DISPLAYED_COLLECTIONS)) {
			Set<CollectionTO> collSet=(Set<CollectionTO>) pce.getNewValue();
			showAreas(collSet);
		}
		else if(pce.getPropertyName().equals(CollectionModel.P_COLLECTIONS)) {
			/*List<CollectionTO> collList=(List<CollectionTO>) pce.getNewValue();
			Set<CollectionTO> collSet=new HashSet<CollectionTO>(collList);
			showAreas(collSet);*/
		}
		
	}
	
	private void screenMapSize() {
		//ReferencedEnvelope displayArea=this.getDisplayArea();
		ReferencedEnvelope displayArea=this.getMapContent().getMaxBounds();
		
		AffineTransform at=this.getMapContent().getViewport().getWorldToScreen();
		double minX=displayArea.getMinX();
		double minY=displayArea.getMinY();
		double maxX=displayArea.getMaxX();
		double maxY=displayArea.getMaxY();
		double w=maxX-minX;
		double h=maxY-minY;
		Rectangle2D.Double worldRect=new Rectangle2D.Double(minX,minY,w,h);
		Rectangle2D screenRect=at.createTransformedShape(worldRect).getBounds2D();
		int width=(int) screenRect.getWidth();
		int height=(int) screenRect.getHeight();
		System.out.println(width + " "+height);
		MapImageParametersTO imgParams=new MapImageParametersTO(minX,minY,maxX,maxY,width,height,MapImageParametersTO.CRS_EPSG_3067);
		controller.loadMapImage(imgParams);
		
	}

	public void onMouseClicked(MapMouseEvent ev) {
		selectFeatures(ev);
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
		if(ev.isPopupTrigger()) {
			popup.show(ev.getComponent(), ev.getX(), ev.getY());
			screenMapSize();
		}
		
	}

	public void onMouseWheelMoved(MapMouseEvent ev) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		controller.addMapAreasToCollection(selectedPostcodes);
	}

	

}
