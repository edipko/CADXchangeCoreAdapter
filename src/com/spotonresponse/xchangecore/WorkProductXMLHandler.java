package com.spotonresponse.xchangecore;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

public class WorkProductXMLHandler extends BaseTokenXMLHandler
{
  private List<WorkProduct> workproducts = new ArrayList<WorkProduct>();
  private boolean isDescStarted = false;
  private StringBuilder textDesc = null;

  private String MapLayerPath = "WorkProductList.WorkProduct.StructuredPayload.ViewContext.LayerList.Layer";

  static Logger logger = Logger.getLogger(WorkProductXMLHandler.class);

  public void startElement(String uri, String localName, String qName, Attributes attributes)
  {
    super.startElement(uri, localName, qName, attributes);

    if (this.currentPath.equals("WorkProductList.WorkProduct"))
    {
      this.currentResult = new WorkProduct();
      this.parsingResult = true;
      this.isDescStarted = false;
    }
    else if (this.currentPath
      .equals("WorkProductList.WorkProduct.Digest.Event.What")) {
      String val = attributes.getValue(attributes.getQName(0));
      this.currentResult.parse(this.currentElement, val, this.currentPath);
    }
    else if (this.currentPath.endsWith(this.MapLayerPath + ".Server")) {
      String val = attributes.getValue(attributes.getQName(1));
      this.currentResult.parse(this.currentElement, val, this.currentPath);
      this.gotDigest = false;
    }
    else if (this.currentPath
      .endsWith(this.MapLayerPath + ".Server.OnlineResource")) {
      String val = attributes.getValue(attributes.getQName(0));
      this.currentResult.parse(this.currentElement, val, this.currentPath);
    }

    if (this.currentPath.endsWith("Digest.Event.Descriptor")) {
      this.isDescStarted = true;
      this.textDesc = new StringBuilder();
    }
  }

  public void characters(char[] ch, int start, int length)
  {
    if ((this.isDescStarted) && 
      (this.currentPath.endsWith("Digest.Event.Descriptor"))) {
      this.textDesc.append(new String(ch, start, length));
    } else if (this.parsingResult) {
      String value = new String(ch, start, length);
      if (this.currentElement != null)
        this.currentResult.parse(this.currentElement, value, this.currentPath);
    }
  }

  public void endElement(String uri, String localName, String qName)
  {
    super.endElement(uri, localName, qName);

    if ((localName.equals("WorkProduct")) && (this.parsingResult) && 
      (this.gotDigest))
    {
      this.workproducts.add((WorkProduct)this.currentResult);

      this.currentResult = null;
      this.parsingResult = false;
      this.gotDigest = false;
    } else if ((localName.equals("Descriptor")) && (this.isDescStarted)) {
      this.isDescStarted = false;
      String fullTextDesc = this.textDesc.toString();
      this.currentResult.parse(this.currentElement, fullTextDesc, this.currentPath + ".Descriptor");

    }
  }

  public List<WorkProduct> getWorkProducts()
  {
    return this.workproducts;
  }
}