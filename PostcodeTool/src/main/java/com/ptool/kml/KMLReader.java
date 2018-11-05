package com.ptool.kml;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class KMLReader {
	
	private static KMLReader instance;
	
	private KMLReader() {
		
	}
	
	public static synchronized KMLReader getInstance() {
		if(instance==null) {
			instance=new KMLReader();
		}
		return instance;
	}
	
	public Document readKMLFile(String path) {
		Document doc=null;
		SAXBuilder builder=new SAXBuilder();
		File kmlFile=new File(path);
		
		try {
			doc=builder.build(kmlFile);
		} 
		catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return doc;
	}
}
