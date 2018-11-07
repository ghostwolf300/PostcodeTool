package com.ptool.geo;

import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class GeoHelper {
	
	private static GeoHelper instance;
	
	private GeoHelper() {
		
	}
	
	public static synchronized GeoHelper getInstance() {
		if(instance==null) {
			instance=new GeoHelper();
		}
		return instance;
	}
	
	public CoordinateReferenceSystem getCRS(String text) {
		CoordinateReferenceSystem crs=null;
		try {
			crs=CRS.decode(text);
		} 
		catch (NoSuchAuthorityCodeException e) {
			e.printStackTrace();
		} 
		catch (FactoryException e) {
			e.printStackTrace();
		}
		return crs;
	}
	
}
