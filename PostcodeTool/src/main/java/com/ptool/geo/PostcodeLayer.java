package com.ptool.geo;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.Layer;

public class PostcodeLayer extends Layer {
	
	private ReferencedEnvelope bounds=null;
	
	public PostcodeLayer(ReferencedEnvelope bounds) {
		super();
		this.bounds=bounds;
	}
	
	@Override
	public ReferencedEnvelope getBounds() {
		return bounds;
	}

}
