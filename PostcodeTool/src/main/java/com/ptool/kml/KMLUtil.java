package com.ptool.kml;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

import com.ptool.geo.Arc;
import com.ptool.geo.GeometryCollection;
import com.ptool.geo.GeometryObject;

import com.ptool.geo.Position;
import com.ptool.pojo.PostcodeTO;
import com.ptool.pojo.RingTO;
import com.ptool.pojo.CoordinateTO;
import com.ptool.pojo.PolygonTO;

public class KMLUtil {
	
	private static KMLUtil instance;
	
	private Document kmlDoc=null;
	private SAXBuilder builder=null;
	
	private KMLUtil() {
		
	}
	
	public static synchronized KMLUtil getInstance() {
		if(instance==null) {
			instance=new KMLUtil();
		}
		return instance;
	}
	
	/*public Document buildKML(List<GeometryObject> geoObjects) {
		
		Document doc=new Document();
		
		Namespace xmlns=Namespace.getNamespace("http://www.opengis.net/kml/2.2");
		Namespace gx=Namespace.getNamespace("gx", "http://www.google.com/kml/ext/2.2");
		Namespace kml=Namespace.getNamespace("kml", "http://www.opengis.net/kml/2.2");
		Namespace atom=Namespace.getNamespace("atom", "http://www.w3.org/2005/Atom");
		
		Element root=new Element("kml",xmlns);
		
		root.addNamespaceDeclaration(gx);
		root.addNamespaceDeclaration(kml);
		root.addNamespaceDeclaration(atom);
		
		Element eDoc=new Element("Document");
		
		eDoc.addContent(getStyleNormal());
		eDoc.addContent(getStyleHighlight());
		eDoc.addContent(getStyleMap());
		
		Element folder=new Element("Folder");
		Element folderName=new Element("name");
		folderName.setText("Postinumerot");
		folder.addContent(folderName);
		eDoc.addContent(folder);
		
		for(GeometryObject geo : geoObjects) {
			folder.addContent(getPostcodeArea(geo));
		}
		
		root.addContent(eDoc);
		doc.setRootElement(root);
		
		return doc;
	}*/
	
	/*private Element getPostcodeArea(GeometryObject geo) {
		Element area=null;
		if(geo.getType()==GeometryObject.Type.Polygon) {
			Polygon p=(Polygon)geo;
			area=getPlacemark(p,(String)p.getProperty("posti_alue"));
		}
		else if(geo.getType()==GeometryObject.Type.GeometryCollection) {
			GeometryCollection geoColl=(GeometryCollection)geo;
			area=getPolygonFolder(geoColl);
		}
		else {
			//Unknown type
		}
		return area;
	}*/
	
	/*private Element getPlacemark(Polygon p,String name) {
		Element placeMark=new Element("Placemark");
		Element areaName=new Element("name");
		areaName.setText(name);
		placeMark.addContent(areaName);
		Element styleUrl=new Element("styleUrl");
		styleUrl.setText("#m_ylw-pushpin");
		placeMark.addContent(styleUrl);
		placeMark.addContent(getPolygon(p));
		return placeMark;
	}*/
	
	private Element getPlacemark(Element polygon,String name) {
		Element placeMark=new Element("Placemark");
		Element areaName=new Element("name");
		areaName.setText(name);
		placeMark.addContent(areaName);
		Element styleUrl=new Element("styleUrl");
		styleUrl.setText("#m_ylw-pushpin");
		placeMark.addContent(styleUrl);
		placeMark.addContent(polygon);
		return placeMark;
	}
	
	private Element getPlacemarkElement(String name,PolygonTO polygon) {
		Element placeMark=new Element("Placemark");
		Element areaName=new Element("name");
		areaName.setText(name);
		placeMark.addContent(areaName);
		Element styleUrl=new Element("styleUrl");
		styleUrl.setText("#m_ylw-pushpin");
		placeMark.addContent(styleUrl);
		placeMark.addContent(getPolygonElement(polygon));
		return placeMark;
	}
	
	private Element getPolygonElement(PolygonTO polygon) {
		Element ePoly=new Element("Polygon");
		
		Element tessellate=new Element("tessellate");
		tessellate.setText("1");
		ePoly.addContent(tessellate);
		
		Element outerBoundaryIs=new Element("outerBoundaryIs");
		Element linearRing=new Element("LinearRing");
		outerBoundaryIs.addContent(linearRing);
		Element coordinates=new Element("coordinates");
		
		List<RingTO> rings=polygon.getRings();
		RingTO outerRing=rings.get(0);
		
		String coordTxt=new String();
		for(CoordinateTO c : outerRing.getCoordinates()) {
			coordTxt+=c.getX()+","+c.getY()+",0 ";
		}
		
		coordinates.setText(coordTxt);
		linearRing.addContent(coordinates);
		
		ePoly.addContent(outerBoundaryIs);
		return ePoly;
	}
	
	/*private Element getPolygonFolder(GeometryCollection geoColl) {
		Element folder=new Element("Folder");
		Element folderName=new Element("name");
		folderName.setText((String) geoColl.getProperty("posti_alue"));
		folder.addContent(folderName);
		for(GeometryObject geo : geoColl.getGeometries()) {
			if(geo.getType()==GeometryObject.Type.Polygon) {
				folder.addContent(getPlacemark((Polygon)geo,folderName.getText()));
			}
		}
		return folder;
	}*/
	
	/*private Element getPolygon(Polygon p) {
		Element polygon=new Element("Polygon");
		
		Element tessellate=new Element("tessellate");
		tessellate.setText("1");
		polygon.addContent(tessellate);
		
		Element outerBoundaryIs=new Element("outerBoundaryIs");
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
	}*/
	
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
		style.addContent(getLineStyle(2.0,"ff0000ff"));
		style.addContent(getPolyStyle("bf0000ff"));
		return style;
	}
	
	private Element getStyleNormal() {
		Element style=new Element("Style");
		style.setAttribute("id", "s_ylw-pushpin");
		style.addContent(getIconStyle(1.1));
		style.addContent(getLineStyle(2.0,"ff0000ff"));
		style.addContent(getPolyStyle("9900ffff"));
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
	
	private Element getLineStyle(double width,String color) {
		Element lineStyle=new Element("LineStyle");
		Element eColor=new Element("color");
		eColor.setText(color);
		Element eWidth=new Element("width");
		eWidth.setText(Double.toString(width));
		lineStyle.addContent(eColor);
		lineStyle.addContent(eWidth);
		return lineStyle;
	}
	
	public void createKMLDocument(String name) {
		
		kmlDoc=new Document();
		
		Namespace xmlns=Namespace.getNamespace("http://www.opengis.net/kml/2.2");
		Namespace gx=Namespace.getNamespace("gx", "http://www.google.com/kml/ext/2.2");
		Namespace kml=Namespace.getNamespace("kml", "http://www.opengis.net/kml/2.2");
		Namespace atom=Namespace.getNamespace("atom", "http://www.w3.org/2005/Atom");
		
		Element root=new Element("kml",xmlns);
		
		root.addNamespaceDeclaration(gx);
		root.addNamespaceDeclaration(kml);
		root.addNamespaceDeclaration(atom);
		
		Element eDoc=new Element("Document");
		
		eDoc.addContent(getStyleNormal());
		eDoc.addContent(getStyleHighlight());
		eDoc.addContent(getStyleMap());
		
		/*Element folder=new Element("Folder");
		Element folderName=new Element("name");
		folderName.setText(name);
		folder.addContent(folderName);
		eDoc.addContent(folder);*/
		
		root.addContent(eDoc);
		kmlDoc.setRootElement(root);
		
	}
	
	public Document getKmlDoc() {
		return kmlDoc;
	}

	public void setKmlDoc(Document kmlDoc) {
		this.kmlDoc = kmlDoc;
	}

	public void addPostcode(String postcode, String xmlString) {
		
		Document xmlSnip=null;
		
		if(builder==null) {
			builder=new SAXBuilder();
		}
		
		try {
			xmlSnip=builder.build(new StringReader(xmlString));
		} 
		catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(postcode);
		Element polygon=xmlSnip.getRootElement().clone();
		Element folder=kmlDoc.getRootElement().getChild("Document");
		folder.addContent(getPlacemark(polygon,postcode));
		
	}
	
	public void addPostcode(PostcodeTO postcode) {
		Element folder=kmlDoc.getRootElement().getChild("Document");
		for(PolygonTO polygon : postcode.getPolygons()) {
			folder.addContent(getPlacemarkElement(postcode.getName(),polygon));
		}
		
	}
	
	public List<Polygon> extractPolygons(){
		Element root=kmlDoc.getRootElement();
		Namespace ns=Namespace.getNamespace("http://www.opengis.net/kml/2.2");
		Element docElement=root.getChild("Document", ns);
		List<Element> placemarkElements=docElement.getChildren("Placemark",ns);
		List<Polygon> polygons=new ArrayList<Polygon>();
		for(Element placemarkElement : placemarkElements) {
			polygons.add(readPolygonData(placemarkElement));
		}
		return polygons;
	}
	
	private Polygon readPolygonData(Element ePlacemark) {
		GeometryFactory factory=new GeometryFactory();
		LinearRing shell=null;
		Coordinate[] coordinates=null;
		Polygon polygon=null;
		Namespace ns=ePlacemark.getNamespace();
		String coordinatesText=ePlacemark
				.getChild("Polygon",ns)
				.getChild("outerBoundaryIs",ns)
				.getChild("LinearRing",ns)
				.getChild("coordinates",ns).getText();
		
		String[] coordinatePairs=coordinatesText.split(",0 ");
		coordinates=new Coordinate[coordinatePairs.length-1];
		for(int c=0;c<coordinatePairs.length-1;c++) {
			String[] coordinatePair=coordinatePairs[c].split(",");
			double x=Double.valueOf(coordinatePair[0]);
			double y=Double.valueOf(coordinatePair[1]);
			coordinates[c]=new Coordinate(x,y);
			System.out.println(x+"\t"+y);
		}
		
		shell=factory.createLinearRing(coordinates);
		polygon=factory.createPolygon(shell);
		
		return polygon;
	}
	
	
}
