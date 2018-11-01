package com.ptool.pojo;

import java.util.List;

public class Polygon {
	
	private int id;
	private List<Ring> rings=null;
	
	public Polygon() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Ring> getRings() {
		return rings;
	}

	public void setRings(List<Ring> rings) {
		this.rings = rings;
	}
	
	
}
