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
		//getPolygon
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
				coordTxt+=pos.getX()+","+pos.getY()+" ";
			}
		}
		coordinates.setText(coordTxt);
		linearRing.addContent(coordinates);
		
		polygon.addContent(outerBoundaryIs);
		return polygon;
	}
	
}
