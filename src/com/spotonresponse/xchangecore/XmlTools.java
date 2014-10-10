package com.spotonresponse.xchangecore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.xpath.XPath;

public class XmlTools {
	public static Document stringToDoc(String xml) {
		DocumentBuilder db = null;
		Document doc = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			doc = db.parse(is);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

	public static void printDocument(Document doc, OutputStream out)
			throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty("omit-xml-declaration", "no");
		transformer.setOutputProperty("method", "xml");
		transformer.setOutputProperty("indent", "yes");
		transformer.setOutputProperty("encoding", "UTF-8");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(new DOMSource(doc), new StreamResult(
				new OutputStreamWriter(out, "UTF-8")));
	}

	public static ArrayList<String> getWorkproducts(Document doc) {
		XPath xPath = XPathFactory.newInstance().newXPath();
		ArrayList<String> workproductIds = new ArrayList<String>();
		NodeList workproducts;
		try {
			workproducts = (NodeList) xPath
					.compile(
							"//Envelope/Body/GetIncidentListResponse/WorkProductList/WorkProduct")
					.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < workproducts.getLength(); i++) {
				Node workproduct = workproducts.item(i);
				NodeList identifier = (NodeList) xPath.compile(
						"//AssociatedGroups/Identifier").evaluate(workproduct,
						XPathConstants.NODESET);
				workproductIds.add(identifier.item(i).getTextContent());
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workproductIds;
	}

	public static InputStream docToInputStream(Document doc) {
		InputStream is = null;
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			Source xmlSource = new DOMSource(doc);
			Result outputTarget = new StreamResult(outputStream);
			TransformerFactory.newInstance().newTransformer()
					.transform(xmlSource, outputTarget);
			is = new ByteArrayInputStream(outputStream.toByteArray());
		} catch (TransformerException | TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return is;
	}

	public static String replaceSoap(String resp) {
		String response = resp
				.replace(
						"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">",
						"")
				.replace("<SOAP-ENV:Header/>", "")
				.replace("<SOAP-ENV:Body>", "")
				.replace("</SOAP-ENV:Body>", "")
				.replace("</SOAP-ENV:Envelope>", "")
				.replace(
						"<inc:GetIncidentListResponse xmlns:inc=\"http://uicds.org/IncidentManagementService\">",
						"").replace("</inc:GetIncidentListResponse>", "");
		return response;
	}

}
