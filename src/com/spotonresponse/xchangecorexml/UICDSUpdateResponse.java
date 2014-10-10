package com.spotonresponse.xchangecorexml;

import java.io.Serializable;

public class UICDSUpdateResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status;
	private String message;
	private String WpID;
	private String IgID;
	private String version;
	private String checksum;

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getChecksum() {
		return this.checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the wpID
	 */
	public String getWpID() {
		return WpID;
	}

	/**
	 * @param wpID the wpID to set
	 */
	public void setWpID(String wpID) {
		WpID = wpID;
	}

	/**
	 * @return the igID
	 */
	public String getIgID() {
		return IgID;
	}

	/**
	 * @param igID the igID to set
	 */
	public void setIgID(String igID) {
		IgID = igID;
	}
}
