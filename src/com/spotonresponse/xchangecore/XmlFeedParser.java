package com.spotonresponse.xchangecore;


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlFeedParser
{
  static Logger logger = Logger.getLogger(WorkProduct.class);

  private static SAXParserFactory sParserFactory = SAXParserFactory.newInstance();
  private String mFileName;
  private InputStream mStream;
  private WorkProductXMLHandler mWorkProductHandler;

  static
  {
    sParserFactory.setNamespaceAware(true);
    sParserFactory.setXIncludeAware(true);
  }

  public XmlFeedParser(InputStream stream)
  {
    this.mStream = stream;
  }

  public List<WorkProduct> getWorkProducts()
  {
    return this.mWorkProductHandler.getWorkProducts();
  }

  public boolean parse()
  {
    try
    {
      SAXParser parser = sParserFactory.newSAXParser();

      this.mWorkProductHandler = new WorkProductXMLHandler();

      parser.parse(new InputSource(new FileReader(this.mFileName)), 
        this.mWorkProductHandler);

      return this.mWorkProductHandler.getSuccess();
    } catch (ParserConfigurationException localParserConfigurationException) {
    } catch (SAXException e) {
      e.getMessage();
    }
    catch (IOException localIOException)
    {
    }
    return false;
  }

  public boolean parseStream()
  {
    try
    {
      SAXParser parser = sParserFactory.newSAXParser();
      this.mWorkProductHandler = new WorkProductXMLHandler();
      parser.parse(this.mStream, 
        this.mWorkProductHandler);

      logger.debug("Returning mWorkProductHandler");
      return this.mWorkProductHandler.getSuccess();
    } catch (ParserConfigurationException e) {
      logger.debug("Exception: " + e);
    } catch (SAXException e) {
      e.getMessage();
      logger.debug("Exception: " + e);
    } catch (IOException e) {
      logger.debug("Exception: " + e);
    }

    return false;
  }
}