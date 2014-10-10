package com.spotonresponse.xchangecore;

import com.spotonresponse.xchangecorexml.UICDSCreateResponse;
import com.spotonresponse.xchangecorexml.UICDSsoiCreateResponse;
import com.spotonresponse.xchangecorexml.UICDSUpdateResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtils
{
	static Logger logger = Logger.getLogger(XMLUtils.class);

  public static Document newDocumentFromInputStream(InputStream in)
  {
    DocumentBuilderFactory factory = null;
    DocumentBuilder builder = null;
    Document ret = null;
    try
    {
      factory = DocumentBuilderFactory.newInstance();
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    try
    {
      ret = builder.parse(new InputSource(in));
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    //logger.debug("Returning document: " + xmlToString(ret));
    
    return ret;
  }

  public static String xmlToString(Document doc) {
    try {
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      StreamResult result = new StreamResult(new StringWriter());
      DOMSource source = new DOMSource(doc);
      transformer.transform(source, result);
      return result.getWriter().toString();
    } catch (TransformerException ex) {
      ex.printStackTrace();
    }return null;
  }

  public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException
  {
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.setOutputProperty("omit-xml-declaration", "no");
    transformer.setOutputProperty("method", "xml");
    transformer.setOutputProperty("indent", "yes");
    transformer.setOutputProperty("encoding", "UTF-8");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

    transformer.transform(new DOMSource(doc), 
      new StreamResult(new OutputStreamWriter(out, "UTF-8")));
  }

  public static OutputStream streamDocument(Document doc) throws IOException, TransformerException
  {
    OutputStream out = null;
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.setOutputProperty("omit-xml-declaration", "no");
    transformer.setOutputProperty("method", "xml");
    transformer.setOutputProperty("indent", "yes");
    transformer.setOutputProperty("encoding", "UTF-8");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

    transformer.transform(new DOMSource(doc), 
      new StreamResult(new OutputStreamWriter(out, "UTF-8")));

    return out;
  }

  public static UICDSCreateResponse parseResponse(Document doc) {
    XPath xpath = XPathFactory.newInstance().newXPath();

    //logger.debug(xmlToString(doc));
    UICDSCreateResponse ucr = new UICDSCreateResponse();
    try
    {
      Node status = (Node)xpath.evaluate("//Envelope/Body/CreateIncidentResponse/WorkProductPublicationResponse/WorkProductProcessingStatus/Status", doc, XPathConstants.NODE);
      if (status != null) {
        ucr.setStatus(status.getTextContent());
      }

      Node wpid = (Node)xpath.evaluate("//Envelope/Body/CreateIncidentResponse/WorkProductPublicationResponse/WorkProduct/PackageMetadata/DataItemID", doc, XPathConstants.NODE);
      if (wpid != null) {
        ucr.setWpID(wpid.getTextContent());
      }

      Node igid = (Node)xpath.evaluate("//Envelope/Body/CreateIncidentResponse/WorkProductPublicationResponse/WorkProduct/PackageMetadata/WorkProductProperties/AssociatedGroups/Identifier", doc, XPathConstants.NODE);
      if (igid != null)
        ucr.setIgID(igid.getTextContent());
      
      Node checksum = (Node)xpath.evaluate("//Envelope/Body/CreateIncidentResponse/WorkProductPublicationResponse/WorkProduct/PackageMetadata/WorkProductIdentification/Checksum", doc, XPathConstants.NODE);
      if (checksum != null)
    	  ucr.setChecksum(checksum.getTextContent());
    }
    catch (Exception ex) {
      System.err.println("Error: " + ex);
    }

    return ucr;
  }

  public static UICDSsoiCreateResponse parseCreateSOIResponse(Document doc)
  {
    XPath xpath = XPathFactory.newInstance().newXPath();
    UICDSsoiCreateResponse uscr = new UICDSsoiCreateResponse();
    try {
      Node status = (Node)xpath.evaluate("//Envelope/Body/CreateSOIResponse/WorkProductPublicationResponse/WorkProductProcessingStatus/Status", doc, XPathConstants.NODE);
      if (status != null) {
        uscr.setStatus(status.getTextContent());
      }
      Node soi = (Node)xpath.evaluate("//Envelope/Body/CreateSOIResponse/WorkProductPublicationResponse/WorkProduct/PackageMetadata/WorkProductIdentification/Identifier", doc, XPathConstants.NODE);
      if (soi != null) {
        uscr.setSOI(soi.getTextContent());
      }
      Node version = (Node)xpath.evaluate("//Envelope/Body/CreateSOIResponse/WorkProductPublicationResponse/WorkProduct/PackageMetadata/WorkProductIdentification/Version", doc, XPathConstants.NODE);
      if (version != null) {
        uscr.setVersion(version.getTextContent());
      }

      Node checksum = (Node)xpath.evaluate("//Envelope/Body/CreateSOIResponse/WorkProductPublicationResponse/WorkProduct/PackageMetadata/WorkProductIdentification/Checksum", doc, XPathConstants.NODE);
      if (checksum != null) {
        uscr.setChecksum(checksum.getTextContent());
      }

      Node message = (Node)xpath.evaluate("//Envelope/Body/CreateSOIResponse/WorkProductPublicationResponse/WorkProductProcessingStatus/Message", doc, XPathConstants.NODE);
      if (message != null)
        uscr.setMessage(message.getTextContent());
      else
        uscr.setMessage("");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

    return uscr;
  }
  
  
  public static UICDSUpdateResponse parseUpdateIncident(Document doc)
  {
    XPath xpath = XPathFactory.newInstance().newXPath();
    UICDSUpdateResponse uur = new UICDSUpdateResponse();
    try {
      
      Node status = (Node)xpath.evaluate("//Envelope/Body/UpdateIncidentResponse/WorkProductPublicationResponse/WorkProductProcessingStatus/Status", doc, XPathConstants.NODE);
      if (status != null) {
        uur.setStatus(status.getTextContent());
      }
      Node WpID = (Node)xpath.evaluate("//Envelope/Body/UpdateIncidentResponse/WorkProductPublicationResponse/WorkProduct/PackageMetadata/WorkProductIdentification/Identifier", doc, XPathConstants.NODE);
      if (WpID != null) {
        uur.setWpID(WpID.getTextContent());
      }
      Node version = (Node)xpath.evaluate("//Envelope/Body/UpdateIncidentResponse/WorkProductPublicationResponse/WorkProduct/PackageMetadata/WorkProductIdentification/Version", doc, XPathConstants.NODE);
      if (version != null) {
        uur.setVersion(version.getTextContent());
      }

      Node checksum = (Node)xpath.evaluate("//Envelope/Body/UpdateIncidentResponse/WorkProductPublicationResponse/WorkProduct/PackageMetadata/WorkProductIdentification/Checksum", doc, XPathConstants.NODE);
      if (checksum != null) {
        uur.setChecksum(checksum.getTextContent());
      }
        
      Node IgID = (Node)xpath.evaluate("//Envelope/Body/UpdateIncidentResponse/WorkProductPublicationResponse/WorkProduct/PackageMetadata/WorkProductProperties/AssociatedGroups/Identifier", doc, XPathConstants.NODE);
      if (IgID != null) {
        uur.setIgID(IgID.getTextContent());
      }
      
      Node message = (Node)xpath.evaluate("//Envelope/Body/UpdateIncidentResponse/WorkProductPublicationResponse/WorkProductProcessingStatus/Message", doc, XPathConstants.NODE);
      if (message != null)
        uur.setMessage(message.getTextContent());
      else
        uur.setMessage("");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

    return uur;
  }
  
  
}