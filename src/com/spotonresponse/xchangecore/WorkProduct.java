package com.spotonresponse.xchangecore;

import com.mysql.jdbc.StringUtils;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class WorkProduct extends BaseTokenXMLHandler implements Parseable {
	static Logger logger = Logger.getLogger(WorkProduct.class);
	GeoLocation gl;
	String descriptor = null;
	private String WpID;
	private String Ver;
	private String Type;
	private String Checksum;
	private String State;
	private String Code;
	private String Style;
	private String Created;
	private String CreatedBy;
	private String LastUpdated;
	private String LastUpdatedBy;
	private String Kilobytes;
	private String MimeType;
	private List<String> AssociatedGroups = new ArrayList<String>();
	private String IgID;
	private String Organization;
	private String EventDescriptor;
	private String Name;
	private String Snippet;
	private String Position;
	private String Radius;
	private String PostalAddress;
	private List<GeoLocation> geolocations = new ArrayList<GeoLocation>();

	private UICDSMapViewLayers mapLayers = null;

	private List<UICDSMapViewLayers> mapLayersArray = new ArrayList<UICDSMapViewLayers>();

	public String getWpID() {
		return this.WpID;
	}

	public String getVer() {
		return this.Ver;
	}

	public String getType() {
		return this.Type;
	}

	public String getChecksum() {
		return this.Checksum;
	}

	public String getState() {
		return this.State;
	}

	public String getCode() {
		return this.Code;
	}

	public void setWpID(String WpID) {
		this.WpID = WpID;
	}

	public void setVer(String Ver) {
		this.Ver = Ver;
	}

	public void setType(String Type) {
		this.Type = Type;
	}

	public void setChecksum(String Checksum) {
		this.Checksum = Checksum;
	}

	public void setState(String State) {
		this.State = State;
	}

	public void setCode(String Code) {
		this.Code = Code;
	}

	public String getCreated() {
		return this.Created;
	}

	public String getCreatedBy() {
		return this.CreatedBy;
	}

	public String getLastUpdated() {
		return this.LastUpdated;
	}

	public String getLastUpdatedBy() {
		return this.LastUpdatedBy;
	}

	public String getKilobytes() {
		return this.Kilobytes;
	}

	public String getMimeType() {
		return this.MimeType;
	}

	public List<String> getAssociatedGroups() {
		return this.AssociatedGroups;
	}

	public String getIgID() {
		return this.IgID;
	}

	public void setCreated(String Created) {
		this.Created = Created;
	}

	public void setCreatedBy(String CreatedBy) {
		this.CreatedBy = CreatedBy;
	}

	public void setLastUpdated(String LastUpdated) {
		this.LastUpdated = LastUpdated;
	}

	public void setLastUpdatedBy(String LastUpdatedBy) {
		this.LastUpdatedBy = LastUpdatedBy;
	}

	public void setKilobytes(String Kilobytes) {
		this.Kilobytes = Kilobytes;
	}

	public void setMimeType(String MimeType) {
		this.MimeType = MimeType;
	}

	public void setAssociatedGroup(String AssociatedGroup) {
		this.AssociatedGroups.add(AssociatedGroup);
	}

	public void setIgID(String AssociatedGroup) {
		this.IgID = AssociatedGroup;
	}

	public String getOrganization() {
		return this.Organization;
	}

	public String getEventDescriptor() {
		return this.EventDescriptor;
	}

	public String getName() {
		return this.Name;
	}

	public String getSnipet() {
		return this.Snippet;
	}

	public void setOrganization(String organization) {
		this.Organization = organization;
	}

	public void setEventDescriptor(String e_desc) {
		this.EventDescriptor = e_desc;
	}

	public void setName(String e_id) {
		this.Name = e_id;
	}

	public void setSnippet(String snip) {
		this.Snippet = snip;
	}

	public String getPosition() {
		return this.Position;
	}

	public String getRadius() {
		return this.Radius;
	}

	public String getPostalAddress() {
		return this.PostalAddress;
	}

	public List<GeoLocation> getGeoLocations() {
		return this.geolocations;
	}

	public void setPosition(String pos) {
		this.Position = pos;
	}

	public void setRadius(String radius) {
		this.Radius = radius;
	}

	public void setPostalAddress(String address) {
		this.PostalAddress = address;
	}

	public void setGeoLocation(GeoLocation geo) {
		this.geolocations.add(geo);
	}

	public void setMapLayer(UICDSMapViewLayers mvl) {
		this.mapLayersArray.add(mvl);
	}

	public List<UICDSMapViewLayers> getMapArrayList() {
		return this.mapLayersArray;
	}

	public boolean parse(String currentElement, String value, String currentPath) {
		boolean ret = false;

		String BasePath = "WorkProductList.WorkProduct.PackageMetadata.";
		String DigestPath = "WorkProductList.WorkProduct.Digest.";
		String MapPath = "WorkProductList.WorkProduct.StructuredPayload.ViewContext.LayerList.Layer";
		try {
			if (currentPath.contains(MapPath)) {
				//logger.debug("Inside Mapcontext: " + currentPath.toString());
				if (currentPath.equals(MapPath + ".Server")) {
					if (!StringUtils.isEmptyOrWhitespaceOnly(value)) {
						this.mapLayers.setMapLayerService(value);
						//logger.debug("Service set");
					}
				} else if (currentPath.equals(MapPath
						+ ".Server.OnlineResource")) {
					this.mapLayers.setMapLayerOnlineResource(value);
					//logger.debug("Online resrouce set");
				} else if (currentPath.equals(MapPath + ".Name")) {
					this.mapLayers.setMapLayerName(value);
					//logger.debug("Name set");
				} else if (currentPath.equals(MapPath + ".Title")) {
					this.mapLayers.setMapLayerTitle(value);
					//logger.debug("Title set");
				} else if (currentPath.equals(MapPath + ".Abstract")) {
					this.mapLayers.setMapLayerAbstract(value);
					//logger.debug("Abstract set");
				} else if (value.equals("#ENDMAP_LAYER#")) {
					setMapLayer(this.mapLayers);
					//logger.debug("End map layer");
				}
				if (value.equals("#STARTMAP_LAYER#")) {
					this.mapLayers = new UICDSMapViewLayers();
					//logger.debug("New map layer started");
				}

				ret = true;
			}

			if (currentPath.equals(BasePath
					+ "WorkProductIdentification.Identifier")) {
				if (value.contains("MapViewContext")) {
					this.gotMapContext = true;
				}
				setWpID(value);
				ret = true;
			} else if (currentPath.equals(BasePath
					+ "WorkProductIdentification.Version")) {
				setVer(value);
				ret = true;
			} else if (currentPath.equals(BasePath
					+ "WorkProductIdentification.Type")) {
				setType(value);
				ret = true;
			} else if (currentPath.equals(BasePath
					+ "WorkProductIdentification.Checksum")) {
				setChecksum(value);
				ret = true;
			} else if (currentPath.equals(BasePath
					+ "WorkProductIdentification.State")) {
				setState(value);
				ret = true;
			} else if (currentPath.equals(BasePath
					+ "WorkProductProperties.AssociatedGroups.Identifier")) {
				setIgID(value);
				ret = true;
			} else if (currentPath.equals(BasePath
					+ "WorkProductProperties.Created")) {
				setCreated(value);
				ret = true;
			} else if (currentPath.equals(BasePath
					+ "WorkProductProperties.CreatedBy")) {
				setCreatedBy(value);
				ret = true;
			} else if (currentPath.equals(BasePath
					+ "WorkProductProperties.LastUpdated")) {
				setLastUpdated(value);
				ret = true;
			} else if (currentPath.equals(BasePath
					+ "WorkProductProperties.LastUpdatedBy")) {
				setLastUpdatedBy(value);
				ret = true;
			} else if (currentPath.equals(BasePath
					+ "WorkProductProperties.Kilobytes")) {
				setKilobytes(value);
				ret = true;
			} else if (currentPath.equals(BasePath
					+ "WorkProductProperties.MimeType")) {
				setMimeType(value);
				ret = true;
			} else if (currentPath.equals(BasePath
					+ "WorkProductProperties.AssociatedGroups.Identifier")) {
				setAssociatedGroup(value);
				ret = true;
			} else if (currentPath.equals(DigestPath
					+ "Organization.Name.Value")) {
				setOrganization(value);
				ret = true;
			} else if (currentPath.contains(DigestPath + "Event.Descriptor")) {
				setEventDescriptor(value);
				ret = true;
			} else if (currentPath.equals(DigestPath + "Event.What")) {
				setCode(value);
				ret = true;
			} else if (currentPath.contains(DigestPath + "Event.Identifier")) {
				if (getWpID().contains("SOI")) {
					setName(value);
					setSnippet(value);
				} else if (currentPath.contains("#label#")) {
					setSnippet(value);
				} else {
					setName(value);
				}

				ret = true;
			} else if (currentPath.equals(DigestPath
					+ "Location.PhysicalAddress.postalAddress")) {
				setPostalAddress(value);
				ret = true;
			} else if (currentPath.contains("GeoLocation")) {
				if (currentPath.equals(DigestPath
						+ "Location.GeoLocation.Point.Point.pos")) {
					if (value.contains(",")) {
						setPosition(value);
					} else {
						String[] loc = value.split("[ ]+");
						setPosition(loc[0] + "," + loc[1]);
					}
					ret = true;
				} else if (currentPath
						.equals(DigestPath
								+ "Location.GeoLocation.CircleByCenterPoint.CircleByCenterPoint.pos")) {
					if (value.contains(",")) {
						setPosition(value);
					} else {
						String[] loc = value.split("[ ]+");
						setPosition(loc[0] + "," + loc[1]);
					}
					ret = true;
				} else if (currentPath
						.equals(DigestPath
								+ "Location.GeoLocation.CircleByCenterPoint.CircleByCenterPoint.radius")) {
					setRadius(value);
					ret = true;
				} else if (currentPath
						.contains(DigestPath
								+ "Location.GeoLocation.Polygon.Polygon.exterior.LinearRing.pos")) {
					if (currentPath.endsWith("#new#")) {
						this.gl = new GeoLocation();
						if (value.contains(",")) {
							this.gl.setPoints(value);
						} else {
							String[] loc = value.split("[ ]+");
							this.gl.setPoints(loc[0] + "," + loc[1]);
						}
					} else if (currentPath.endsWith("#add#")) {
						if (value.contains(",")) {
							this.gl.setPoints(value);
						} else {
							String[] loc = value.split("[ ]+");
							this.gl.setPoints(loc[0] + "," + loc[1]);
						}
					}
					ret = true;
				} else if (currentPath
						.equals(DigestPath
								+ "Location.GeoLocation.Polygon.Polygon.exterior.LinearRing")) {
					if (value.equals("#ENDRING#")) {
						setGeoLocation(this.gl);
					}
				}
			}
			return true;
		} catch (Exception ex) {
		}

		return ret;
	}

	public String toString() {
		return getWpID();
	}

	public String getStyle() {
		return this.Style;
	}

	public void setStyle(String style) {
		this.Style = style;
	}

	public class LatLong {
		public double latitude;
		public double longitude;

		public LatLong() {
		}
	}
}