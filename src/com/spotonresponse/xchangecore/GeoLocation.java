package com.spotonresponse.xchangecore;

import java.util.ArrayList;
import java.util.List;

public class GeoLocation {
	private List<String> points = new ArrayList<String>();

	public List<String> getPoints() {
		return this.points;
	}

	public void setPoints(String point) {
		this.points.add(point);
	}
}