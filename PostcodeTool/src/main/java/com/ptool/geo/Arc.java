package com.ptool.geo;

import java.util.ArrayList;
import java.util.List;

public class Arc {
	
	private long index=-1;
	private List<Position> positions=null;

	public Arc() {
	
	}
	
	public Arc(long index) {
		this.index=index;
	}
	
	public Arc(List<Position> positions) {
		this.positions = positions;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	
	public void addPosition(Position position) {
		if(positions==null) {
			positions=new ArrayList<Position>();
		}
		positions.add(position);
	}

	@Override
	public String toString() {
		String text=null;
		if(positions==null) {
			text="Arc [positions is null]";
		}
		else if(positions.size()==0) {
			text="Arc [positions is empty]";
		}
		else {
			int i=0;
			text="Arc [positions:\n";
			for(Position p : positions) {
				text+=i+" : "+p.toString();
				i++;
			}
			text+="]";
		}
		return text;
	}
	
	
	
	
}
