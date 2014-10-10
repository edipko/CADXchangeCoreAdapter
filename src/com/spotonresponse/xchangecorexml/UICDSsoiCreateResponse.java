package com.spotonresponse.xchangecorexml;

import java.io.Serializable;

public class UICDSsoiCreateResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status;
	private String SOI;
	private String message;
	private String version;
	private String checksum;

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSOI() {
		return this.SOI;
	}

	public void setSOI(String sOI) {
		this.SOI = sOI;
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
}