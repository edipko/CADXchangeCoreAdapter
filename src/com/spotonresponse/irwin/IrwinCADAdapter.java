package com.spotonresponse.irwin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.spotonresonse.httputils.FTPUtils;
import com.spotonresonse.httputils.HttpServices;
import com.spotonresonse.httputils.UserAuthenticationObject;
import com.spotonresponse.util.DoubleToString;
import com.spotonresponse.util.LatLonConvert;
import com.spotonresponse.xchangecore.UICDStools;
import com.spotonresponse.xchangecore.WorkProduct;
import com.spotonresponse.xchangecore.XmlFeedParser;
import com.spotonresponse.xchangecore.XmlTools;
import org.eclipse.jface.text.TextViewer;

public class IrwinCADAdapter {
	/* FTP Site Data */
	static String url = "gis.sc.gov";
	static String file = "/scgisdata/forestry/public_wild_out.xml";
	static int port = 21;
	static String user = "anonymous";
	static String pass = "ernie.dipko@gmail.com";

	/* XChange Core data */
	static String uicdsURL = "https://test3.xchangecore.leidos.com";
	static String uicdsUser = "spoton";
	static String uicdsPass = "spoton2013";
	static int UICDStimeout = 60;

	static List<WorkProduct> wplist = new ArrayList<WorkProduct>();

	public static ChannelObject getFtpData() {
		ChannelObject channels = null;
		return channels;
	}
	
	public static ChannelObject getFtpData(String url, TextViewer tv) {
		//String ftpurl = "ftp://" + url + file;
		String xml = FTPUtils.ftpFetchFile(url);
		
		String xml2 = xml
				.replace("geo:lat", "Latitude")
				.replace("geo:long", "Longitude")
				.replace(
						"<rss version=\"2.0\" xmlns:geo=\"http://www.w3.org/2003/01/geo/wgs84_pos#\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">",
						"")
				.replace("<rss xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:geo=\"http://www.w3.org/2003/01/geo/wgs84_pos#\" version=\"2.0\">", "")
				.replace("</rss>", "")
				.replace("<?xml version=\"1.0\"?>", "");

		org.eclipse.jface.text.Document doc = new org.eclipse.jface.text.Document(xml2);
		tv.setDocument(doc);
		ChannelObject channels = null;
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ChannelObject.class);
			XMLInputFactory xif = XMLInputFactory.newInstance();
			XMLStreamReader xsr = xif.createXMLStreamReader(new ByteArrayInputStream(
					xml2.getBytes(StandardCharsets.UTF_8)));
	System.out.println(xml2);		
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			channels = (ChannelObject) jaxbUnmarshaller.unmarshal(new ToLowerCaseDelegate(xsr));
		} catch (JAXBException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return channels;
	}

	public static String populateXchangeCore(CADItem item, String xc_url, String xc_user, String xc_pass) {
		String siteaddress = xc_url
				+ "/uicds/core/ws/services/WorkProductService";
		String createIncidentXml = null;
		String status = "";
		try {
			createIncidentXml = IOUtils
					.toString(
							IrwinCADAdapter.class
									.getResourceAsStream("/com/spotonresponse/xchangecorexml/CreateIncident.xml"),
							"UTF-8");
			LatLonConvert dms_latitude = new LatLonConvert(item.getLatitude());
			LatLonConvert dms_longitude = new LatLonConvert(item.getLongitude());
			String description = "<![CDATA[<b>Status: </b> " + item.getStatus() + "<br/>" 
					+ "<b>Location: </b>" + item.getLocation() + "<br/>" 
					+ "<b>Landmark: </b>" + item.getLandmark() + "<br/>" 
					+ "<b>County: </b>" + item.getCounty() + "<br/>"
					+ "<b>ForestedArea: </b>" + item.getForestedacres() + "<br/>" 
					+ "<b>NonForestedArea:</b>" + item.getNonforestedacres() + "<br/>" 
					+ "<b>Total: </b>" + item.getTotal() + "<br/>" 
					+ "<b>Cause: </b>" + item.getCause() + "<br/>"
				// Hard coded values necessary for IRWIN
					+ "<b>DispatchCenterId: </b>" + "SCSCSC" + "<br/>"
					+ "<b>pooResponsibleUnit: </b>" + "SCSCSC" + "<br/>"
					+ "<b>DiscoveryAcres: </b>" + "10" + "<br/>"
					+ "<b>FireCause: </b>" + "Undetermined" + "<br/>"
					+ "<b>IncidentTypeCategory: </b>" + "DF" + "<br/>"
					+ "<b>IncidentTypeKind: </b>" + "FI" + "<br/>"
					+ "]]>";
			String _createIncidentXml = createIncidentXml
					.replace("{DATETIME}", item.getInitialreport())
					.replace("{CATEGORY}", "Fire")
					.replace("{DESCRIPTION}", description)
					.replace(
							"{ACTIVITY}",
							item.getLocation() + " : " + item.getLandmark()
									+ " : " + item.getCounty() + " County")
					.replace("{ADDRESS}", "")
					.replace("{LATDEG}",
							DoubleToString.convert(dms_latitude.getDegree()))
					.replace("{LATMIN}",
							DoubleToString.convert(dms_latitude.getMinute()))
					.replace("{LATSEC}",
							DoubleToString.convert(dms_latitude.getSecond()))
					.replace("{LONDEG}",
							DoubleToString.convert(dms_longitude.getDegree()))
					.replace("{LONMIN}",
							DoubleToString.convert(dms_longitude.getMinute()))
					.replace("{LONSEC}",
							DoubleToString.convert(dms_longitude.getSecond()))
					.replace("{RADIUS}", "1")
					.replace("{ORGNAME}", "SC Fire Commission")
					.replace("{FULLNAME}", "");
			// System.out.println(_createIncidentXml);
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder;
			try {
				builder = factory.newDocumentBuilder();
				Document doc = builder.parse(new InputSource(new StringReader(
						_createIncidentXml)));
				UICDStools ut = new UICDStools();
				status = ut.sendRequest(doc, siteaddress, xc_user, xc_pass,
						UICDStimeout);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return status;
	}

	
	public static List<WorkProduct> getXchangeCoreData(String url,
			UserAuthenticationObject ua) {
		String xml = "";
		try {
			xml = IOUtils
					.toString(
							IrwinCADAdapter.class
									.getResourceAsStream("/com/spotonresponse/xchangecorexml/GetIncidentList.xml"),
							"UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String resp = HttpServices.postXML(xml, url, ua);

		/* Remove the SOAP Nodes from the response */
		String response = XmlTools.replaceSoap(resp);
		Document xmldocument = XmlTools.stringToDoc(response);

		/* Parse the Document and get the Workproduct */
		XmlFeedParser parser = new XmlFeedParser(
				XmlTools.docToInputStream(xmldocument));
		parser.parseStream();
		wplist = parser.getWorkProducts();
		
		return wplist;
	}

	
	
	
	public static void main(String[] args) {
		// Get the FTP Data
		ChannelObject channels = getFtpData();

		// Get the XChangeCore data
		UserAuthenticationObject ua = new UserAuthenticationObject(uicdsUser,
				uicdsPass);

		// If there are incidents in the RSS feed - proceed.
		if (channels.getItems() != null) {
			// Get the XChangeCore Data
			getXchangeCoreData(uicdsURL
					+ "/uicds/core/ws/services/IncidentManagementService", ua);

			// Loop through the FTP Data
			DecimalFormat df = new DecimalFormat("00.00000");

			for (CADItem item : channels.getItems()) {
				// Determine if we already have this in the XChangeCore
				String item_lat = df.format(item.getLatitude());
				String item_lng = df.format(item.getLongitude());

				String cadstring = item_lat + "|" + item_lng;
				// Loop through the incidents and determine if we already have a
				// matching
				// latitude/longitude
				boolean found = false;
				for (WorkProduct wp : wplist) {
					String[] pos = wp.getPosition().replace(" ", "").split(",");
					String lat = df.format(Double.parseDouble(pos[0]));
					String lon = df.format(Double.parseDouble(pos[1]));
					String xcorestring = lat + "|" + lon;
					if (cadstring.equals(xcorestring)) {
						found = true;
					}
				}

				// If not found - add it to the XchangeCore
				if (!found) {
					//populateXchangeCore(item);
				} else {
					System.out.println("Already have");
				}
			}

		} else {
			System.out.println("No FTP data");
		}

	}
	
	
	 private static class ToLowerCaseDelegate extends StreamReaderDelegate {
		 
	        public ToLowerCaseDelegate(XMLStreamReader xsr) {
	            super(xsr);
	        }
	 
	  /*      @Override
	        public String getAttributeLocalName(int index) {
	            return super.getAttributeLocalName(index).toLowerCase();
	        }
	 
	        @Override
	        public String getLocalName() {
	            return super.getLocalName().toLowerCase();
	        }
	 */
	    }
	 

}
