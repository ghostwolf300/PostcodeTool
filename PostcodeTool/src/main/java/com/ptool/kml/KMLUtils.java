package com.ptool.kml;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import com.ptool.geo.Arc;
import com.ptool.geo.GeometryObject;
import com.ptool.geo.Polygon;
import com.ptool.geo.Position;

public class KMLUtils {
	
	private static KMLUtils instance;
	
	private KMLUtils() {
		
	}
	
	public static synchronized KMLUtils getInstance() {
		if(instance==null) {
			instance=new KMLUtils();
		}
		return instance;
	}
	
	public Document buildKML(List<GeometryObject> geoObjects) {
		
		Document doc=new Document();
		
		Element root=new Element("kml");
		Namespace gx=Namespace.getNamespace("gx", "http://www.google.com/kml/ext/2.2");
		Namespace kml=Namespace.getNamespace("kml", "http://www.opengis.net/kml/2.2");
		Namespace atom=Namespace.getNamespace("atom", "http://www.w3.org/2005/Atom");
		
		root.addNamespaceDeclaration(gx);
		root.addNamespaceDeclaration(kml);
		root.addNamespaceDeclaration(atom);
		
		root.addContent(getStyleNormal());
		root.addContent(getStyleHighlight());
		root.addContent(getStyleMap());
		
		Element folder=new Element("Folder");
		Element folderName=new Element("name");
		folderName.setText("Postinumerot");
		folder.addContent(folderName);
		root.addContent(folder);
		
		for(GeometryObject geo : geoObjects) {
			folder.addContent(getPostcodeArea(geo));
		}
		
		doc.setRootElement(root);
		
		return doc;
	}
	
	private Element getPostcodeArea(GeometryObject geo) {
		Element area=new Element("Placemark");
		Element areaName=new Element("name");
		areaName.setText((String) geo.getProperty("posti_alue"));
		area.addContent(areaName);
		Element styleUrl=new Element("styleUrl");
		styleUrl.setText("#m_ylw-pushpin");
		area.addContent(styleUrl);
		if(geo.getType()==GeometryObject.Type.Polygon) {
			area.addContent(getPolygon((Polygon)geo));
		}
		return area;
	}
	
	private Element getPolygon(Polygon p) {
		Element polygon=new Element("Polygon");
		
		Element tessellate=new Element("tessellate");
		tessellate.setText("1");
		polygon.addContent(tessellate);
		
		Element outerBoundaryIs=new Element("outerBoundaryId");
		Element linearRing=new Element("LinearRing");
		outerBoundaryIs.addContent(linearRing);
		
		Element coordinates=new Element("coordinates");
		String coordTxt=new String();
		for(Arc arc : p.getArcs()) {
			for(Position pos : arc.getPositions()) {
				coordTxt+=pos.getX()+","+pos.getY()+",0 ";
			}
		}
		coordinates.setText(coordTxt);
		linearRing.addContent(coordinates);
		
		polygon.addContent(outerBoundaryIs);
		return polygon;
	}
	
	private Element getStyleMap() {
		Element styleMap=new Element("StyleMap");
		styleMap.setAttribute("id", "m_ylw-pushpin");
		styleMap.addContent(getPair("normal","#s_ylw-pushpin"));
		styleMap.addContent(getPair("highlight","#s_ylw-pushpin_hl"));
		return styleMap;
	}
	
	private Element getPair(String key,String styleUrl) {
		Element pair=new Element("Pair");
		Element eKey=new Element("key");
		eKey.setText(key);
		Element eStyleUrl=new Element("styleUrl");
		eStyleUrl.setText(styleUrl);
		pair.addContent(eKey);
		pair.addContent(eStyleUrl);
		return pair;
	}
	
	private Element getStyleHighlight() {
		Element style=new Element("Style");
		style.setAttribute("id", "s_ylw-pushpin_hl");
		style.addContent(getIconStyle(1.3));
		style.addContent(getPolyStyle("bf0000ff"));
		return style;
	}
	
	private Element getStyleNormal() {
		Element style=new Element("Style");
		style.setAttribute("id", "s_ylw-pushpin");
		style.addContent(getIconStyle(1.3));
		style.addContent(getPolyStyle("bf0000ff"));
		return style;
	}
	
	private Element getIconStyle(double scl) {
		Element iconStyle=new Element("IconStyle");
		Element scale=new Element("scale");
		scale.setText(Double.toString(scl));
		iconStyle.addContent(scale);
		iconStyle.addContent(getIcon("http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png"));
		Element hotSpot=new Element("hotSpot");
		hotSpot.setAttribute("x", "20");
		hotSpot.setAttribute("y", "2");
		hotSpot.setAttribute("xunits", "pixels");
		hotSpot.setAttribute("yunits", "pixels");
		iconStyle.addContent(hotSpot);
		return iconStyle;
	}
	
	private Element getIcon(String link) {
		Element icon=new Element("Icon");
		Element href=new Element("href");
		href.setText(link);
		icon.addContent(href);
		return icon;
	}
	
	private Element getPolyStyle(String color) {
		Element polyStyle=new Element("PolyStyle");
		Element eColor=new Element("color");
		eColor.setText(color);
		polyStyle.addContent(eColor);
		return polyStyle;
	}
	
}
