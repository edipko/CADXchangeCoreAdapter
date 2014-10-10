package com.spotonresponse.xchangecore;

import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class BaseTokenXMLHandler extends DefaultHandler
{
  String currentPath = new String();
  String currentElement;
  String attribute;
  Parseable currentResult;
  boolean parsingResult;
  boolean gotDigest;
  protected boolean gotMapContext;
  boolean parsingGeoLocation;
  boolean parsingMapContext;

  public boolean getSuccess()
  {
    return true;
  }

  public void startElement(String uri, String localName, String qName, Attributes attributes)
  {
    this.attribute = attributes.getValue(0);
    if (this.currentPath.length() != 0)
    {
      this.currentPath = this.currentPath.concat("." + localName);
    }
    else
    {
      this.currentPath = this.currentPath.concat(localName);
    }
    this.currentElement = localName;

    if ((this.attribute != null) && (this.currentPath.contains("Identifier")))
    {
      this.currentPath = this.currentPath.concat("#" + this.attribute + "#");
    }

    if ((this.currentPath.contains("Digest")) || (this.currentPath.contains("StructuredPayload.ViewContext"))) {
      this.gotDigest = true;
    }

    if (this.currentPath.endsWith("Location.GeoLocation.Polygon.Polygon.exterior.LinearRing.pos")) {
      if (this.parsingGeoLocation)
        this.currentPath = this.currentPath.concat("#add#");
      else {
        this.currentPath = this.currentPath.concat("#new#");
      }
      this.parsingGeoLocation = true;
    }

    if (this.currentPath.endsWith("WorkProductList.WorkProduct.StructuredPayload.ViewContext.LayerList.Layer")) {
      this.parsingMapContext = true;

      this.currentResult.parse(this.currentElement, "#STARTMAP_LAYER#", this.currentPath);
    }
  }

  public void characters(char[] ch, int start, int length)
  {
    if (this.parsingResult) {
      String value = new String(ch, start, length);
      if (this.currentElement != null)
      {
        this.currentResult.parse(this.currentElement, value, this.currentPath);
      }
    }
  }

  public void endElement(String uri, String localName, String qName)
  {
    int pos = this.currentPath.lastIndexOf('.');

    if ((this.currentPath.endsWith("Location.GeoLocation.Polygon.Polygon.exterior.LinearRing")) && 
      (this.parsingGeoLocation))
    {
      this.currentResult.parse(this.currentElement, "#ENDRING#", this.currentPath);
      this.parsingGeoLocation = false;
    }
    if ((this.currentPath.endsWith("WorkProductList.WorkProduct.StructuredPayload.ViewContext.LayerList.Layer")) && 
      (this.parsingMapContext)) {
      this.currentResult.parse(this.currentElement, "#ENDMAP_LAYER#", this.currentPath);
      this.parsingMapContext = false;
    }

    if (pos != -1)
    {
      this.currentPath = this.currentPath.substring(0, pos);
    }
    else {
      this.currentPath = new String();
    }

    this.currentElement = null;
  }

  public void error(SAXParseException exception)
  {
  }

  public void fatalError(SAXParseException exception)
  {
  }

  public void warning(SAXParseException exception)
  {
  }
}