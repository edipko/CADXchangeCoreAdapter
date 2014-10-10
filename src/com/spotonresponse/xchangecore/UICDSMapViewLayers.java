package com.spotonresponse.xchangecore;

import java.io.Serializable;

public class UICDSMapViewLayers implements Serializable {
	private static final long serialVersionUID = 1L;
	private String MapLayerService;
	private String MapLayerTitle;
	private String MapLayerOnlineResource;
	private String MapLayerName;
	private String MapLayerAbstract;

	public String getMapLayerService() {
		return this.MapLayerService;
	}

	public void setMapLayerService(String mapLayerService) {
		this.MapLayerService = mapLayerService;
	}

	public String getMapLayerTitle() {
		return this.MapLayerTitle;
	}

	public void setMapLayerTitle(String mapLayerTitle) {
		this.MapLayerTitle = mapLayerTitle;
	}

	public String getMapLayerOnlineResource() {
		return this.MapLayerOnlineResource;
	}

	public void setMapLayerOnlineResource(String mapLayerOnlineResource) {
		this.MapLayerOnlineResource = mapLayerOnlineResource;
	}

	public String getMapLayerName() {
		return this.MapLayerName;
	}

	public void setMapLayerName(String mapLayerName) {
		this.MapLayerName = mapLayerName;
	}

	public String getMapLayerAbstract() {
		return this.MapLayerAbstract;
	}

	public void setMapLayerAbstract(String mapLayerAbstract) {
		this.MapLayerAbstract = mapLayerAbstract;
	}
}