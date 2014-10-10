package com.spotonresponse.xchangecorexml;

import java.io.Serializable;

public class UICDSCreateResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String Status;
	private String WpID;
	private String IgID;
	private String checksum;

	public String getStatus() {
		return this.Status;
	}

	public void setStatus(String status) {
		this.Status = status;
	}

	public String getWpID() {
		return this.WpID;
	}

	public void setWpID(String wpID) {
		this.WpID = wpID;
	}

	public String getIgID() {
		return this.IgID;
	}

	public void setIgID(String igID) {
		this.IgID = igID;
	}

	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
}