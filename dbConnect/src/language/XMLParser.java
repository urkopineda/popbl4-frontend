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

/**
 * Clase que interpreta XML.
 * @author unaipme
 *
 */
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
	
	/**
	 * Obtener lista de nodos según el nombre de la etiqueta.
	 * @param tag: Nombre de la etiqueta
	 * @return Un NodeList con los resultados
	 */
	public NodeList getNodeListByTagName(String tag) {
		return document.getElementsByTagName(tag);
	}
	
	/**
	 * Filtrar elementos por el nombre de la etiqueta
	 * @param tag: Nombre de la etiqueta
	 * @return Lista de elementos que coinciden con el criterio.
	 */
	public ListIterator<Node> getElementsByTagName(String tag) {
		return getElementsFromNodeList(getNodeListByTagName(tag));		
	}
	
	/**
	 * Extraer todos los elementos de una lista de nodos.
	 * @param nodeList: Lista de nodos.
	 * @return La lista de lementos
	 */
	public ListIterator<Node> getElementsFromNodeList(NodeList nodeList) {
		ArrayList<Node> list = new ArrayList<>();
		for (int i=0; i<nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof Element) list.add(nodeList.item(i));
		}
		return list.listIterator();
	}
	
	/**
	 * Conseguir valor de un atributo de un nodo concreto.
	 * @param node: Nodo
	 * @param att: Atributo
	 * @return Valor del atributo en el nodo.
	 */
	public String getNodeAttribute(Node node, String att) {
		return node.getAttributes().getNamedItem(att).getNodeValue();
	}
	
	/**
	 * Buscar una etiqueta XML dependiendo del valor de cierto atributo.
	 * @param tag: Nombre de la etiqueta entre las que buscar.
	 * @param att: Atributo que hay que buscar entre las etiquetas.
	 * @param value: Valor del atributo a buscar
	 * @return Devuelve una lista con todos los nodos que cumplen los requisitos.
	 */
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
	
	/**
	 * Consigue el texto trimeado (Sin espacios ni al principio ni al final) de dentro de un nodo XML.
	 * @param node: Nodo en el que buscar.
	 * @return Texto trimeado.
	 */
	public String getNodeText(Node node) {
		return (node instanceof Element)?node.getTextContent().trim():null;
	}
	
	/**
	 * Método estático que convierte el xml en un objeto utilizable.
	 */
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
