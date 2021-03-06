package by.misterlucky.liquibase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLUtils {

	private static final String CHANGESET = "changeSet";
	private static final String ID = "id";
	private static final String AUTHOR = "author";
	private static final String SQLSCRIPT = "script";
	private static final String REQUIRENMENT_LEVEL = "requirenment";

	protected static List<ChangeSet> changeSets(InputStream stream)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		List<ChangeSet> chList = new ArrayList<>();
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(stream);
			document.getDocumentElement().normalize();
			NodeList nodeList = document.getElementsByTagName(CHANGESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				ChangeSet ch = getChangeSet(nodeList.item(i));
				if (ch != null){
					Logger.log("read chaangeSet: "+ch.toString());
					chList.add(ch);}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		Logger.log("Have been read "+chList.size()+" "+chList.size()+" changeSets");
		return chList;
	}

	protected static List<ChangeSet> changeSets(File source)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		List<ChangeSet> chList = new ArrayList<>();
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(source);
			document.getDocumentElement().normalize();
			NodeList nodeList = document.getElementsByTagName(CHANGESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				ChangeSet ch = getChangeSet(nodeList.item(i));
				if (ch != null){
					Logger.log("read chaangeSet: "+ch.toString());
					chList.add(ch);}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		Logger.log("for file "+source.getAbsolutePath()+ " read "+chList.size()+" "+chList.size()+" changeSets");
		return chList;
	}

	private static ChangeSet getChangeSet(Node node) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			String id = getTagValue(ID, element);
			String author = getTagValue(AUTHOR, element);
			String sql = getTagValue(SQLSCRIPT, element);
			String requirenment = getTagValue(REQUIRENMENT_LEVEL, element);
			return new ChangeSet(id, author, sql, requirenment);
		} else{
			Logger.log("can`t read changeSet from Node: "+node.toString());
			return null;
		}
	}

	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}

}