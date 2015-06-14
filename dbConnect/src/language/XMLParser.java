package language;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	private Document document;
	
	public XMLParser(String path) throws IOException, SAXException, ParserConfigurationException {
		document = retrieveDocument(path);
	}
	
	public XMLParser(File file) throws IOException, SAXException, ParserConfigurationException {
		document = retrieveDocument(file.getAbsolutePath());
	}
	
	private Document retrieveDocument(String path) throws IOException, SAXException, ParserConfigurationException{
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder b = f.newDocumentBuilder();
		return b.parse(ClassLoader.getSystemResourceAsStream(path));
	}
	
	public NodeList getNodeListByTagName(String tag) {
		return document.getElementsByTagName(tag);
	}
	
	public ListIterator<Node> getElementsByTagName(String tag) {
		return getElementsFromNodeList(getNodeListByTagName(tag));		
	}
	
	public ListIterator<Node> getElementsFromNodeList(NodeList nodeList) {
		ArrayList<Node> list = new ArrayList<>();
		for (int i=0; i<nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof Element) list.add(nodeList.item(i));
		}
		return list.listIterator();
	}
	
	public String getNodeAttribute(Node node, String att) {
		return node.getAttributes().getNamedItem(att).getNodeValue();
	}
	
	public ListIterator<Node> getTagWithAttribute(String tag, String att, String value) {
		NodeList nodeList = document.getElementsByTagName(tag);
		ArrayList<Node> list = new ArrayList<>();
		for (int i=0; i<nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element && node.getAttributes().getNamedItem(att).getNodeValue().equals(value)) {
				list.add(node);
			}
		}
		return list.listIterator();
	}
	
	public String getNodeText(Node node) {
		return (node instanceof Element)?node.getTextContent().trim():null;
	}
	
	public static void getLanguages() {
		XMLParser parser = null;
		try {
			parser = new XMLParser("xml.xml");
			ListIterator<Node> nodeList = parser.getElementsByTagName("idioma");
			while (nodeList.hasNext()) {
				LanguageList lang = new LanguageList();
				Node node = nodeList.next();
				lang.setIdioma(parser.getNodeAttribute(node, "name"));
				ListIterator<Node> children = parser.getElementsFromNodeList(node.getChildNodes());
				while (children.hasNext()) {
					Node aux = children.next();
					lang.put(parser.getNodeAttribute(aux, "name"), parser.getNodeText(aux));
				}
				Strings.lista.add(lang);
			}
		} catch (ParserConfigurationException e) {
			System.out.println("Ha habido un error con la configuración del traductor XML");
		} catch (IOException e) {
			System.out.println("Ha habido un error con el archivo de idiomas XML");
		} catch (SAXException e) {
			System.out.println("Ha habido un error de SAX");
		}
	}
	
}
