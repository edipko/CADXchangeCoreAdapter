package com.spotonresponse.irwin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Item")
public class CADItem {

	private String status;
	private String location;
	private String landmark;
	private double latitude;
	private double longitude;
	private String county;
	private String initialreport;
	private String forestedacres;
	private String nonforestedacres;
	private String total;
	private String cause;

	public String getStatus() {
		return status;
	}

	@XmlElement(name="Status")
	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	@XmlElement(name="Location")
	public void setLocation(String location) {
		this.location = location;
	}

	public String getLandmark() {
		return landmark;
	}

	@XmlElement(name="Landmark")
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public double getLatitude() {
		return latitude;
	}

	@XmlElement(name="Latitude")
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	@XmlElement(name="Longitude")
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCounty() {
		return county;
	}

	@XmlElement(name="County")
	public void setCounty(String county) {
		this.county = county;
	}


	public String getInitialreport() {
		String fmtdate = null;
		try {
			Date date = new SimpleDateFormat("MM/dd/yy HH:mm:ss").parse(initialreport);
			fmtdate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fmtdate;
	}

	@XmlElement(name="InitialReport")
	public void setInitialreport(String initialreport) {
		this.initialreport = initialreport;
	}

	public String getForestedacres() {
		return forestedacres;
	}

	@XmlElement(name="ForestedAcres")
	public void setForestedacres(String forestedacres) {
		this.forestedacres = forestedacres;
	}

	public String getNonforestedacres() {
		return nonforestedacres;
	}

	@XmlElement(name="NonForestedAcres")
	public void setNonforestedacres(String nonforestedacres) {
		this.nonforestedacres = nonforestedacres;
	}

	public String getTotal() {
		return total;
	}

	@XmlElement(name="Total")
	public void setTotal(String total) {
		this.total = total;
	}

	public String getCause() {
		return cause;
	}

	@XmlElement(name="Cause")
	public void setCause(String cause) {
		this.cause = cause;
	}

}
