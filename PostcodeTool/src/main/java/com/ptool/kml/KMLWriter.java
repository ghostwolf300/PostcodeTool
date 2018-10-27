package com.ptool.kml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;

public class KMLWriter {
	
	private static KMLWriter instance;
	
	private KMLWriter() {
		
	}
	
	public static synchronized KMLWriter getInstance() {
		if(instance==null) {
			instance=new KMLWriter();
		}
		return instance;
	}
	
	public void write(Document doc,String fileName) {
		File file=new File(fileName);
        XMLOutputter outputter = new XMLOutputter();
        FileWriter writer=null;
		try {
			writer = new FileWriter(file);
			outputter.output(doc,writer);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				writer.close();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
