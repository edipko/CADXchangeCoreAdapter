package com.spotonresponse.irwin;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "channel")
@XmlAccessorType (XmlAccessType.FIELD)
public class ChannelObject {
	@XmlElement(name = "item")
    private List<CADItem> items = null;

	public List<CADItem> getItems() {
		return items;
	}

	public void setItems(List<CADItem> items) {
		this.items = items;
	}
}