package DynamicTraceability.Toolkit.Tool;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

;
/**
 * ��Xml���л����
 * 
 * @author xOiuNt
 * 
 */
public class XmlOperator {
	public String prefixXmlPath = "F:/My Projects/DynamicTraceability/RM1.0 Data/code/";

	public String xmlName = "reqwss.xml";

	public String xmlPath = prefixXmlPath + xmlName;

	Document document = null;

	public Document initDoc(String packName, String saveXmlName) {
		this.xmlName = saveXmlName;
		document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("package");
		rootElement.addAttribute("namespace", packName);

		return document;
	}

	public void addElement(Element parentElement, Element childElement) {

	}

	public void addElement(Element parentElement, Element[] childElementList) {

	}

	public void openDoc(String fileName) {
		try {
			FileReader xmlReader = new FileReader(new File(fileName));

			SAXReader reader = new SAXReader(false);
			document = reader.read(xmlReader);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public List<String> getAttributeByName(String attributeName) {
		try {
			 List<Element> nodeList = findNode(document, attributeName);//document.selectNodes(attributeName);
			 List<String> attrList = new LinkedList<String>();
			 for(Element ele :  nodeList)  {
				 attrList.add(ele.getText().trim());
			 }

			return attrList;
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return null;
	}

	/**
	 * �����ĵ���Ѱָ�����ֵĽڵ�
	 * @param doc
	 * @param nodeName
	 * @return
	 */
	public List<Element> findNode(Document doc, String nodeName) {
		List<Element> nodeList = new LinkedList<Element>();
		findNode(doc.getRootElement(), nodeName, nodeList);
		
		return nodeList;
	}

	public void findNode(Element element, String nodeName, List<Element> nodeList) {
		for (int i = 0, size = element.nodeCount(); i < size; i++) {
			Node node = element.node(i);
			if (node instanceof Element) {
				Element ele = (Element)node;
				if (ele.getName() == nodeName) {
					nodeList.add(ele);
				}
				findNode((Element) node, nodeName, nodeList);
			} else {
				// do something....
			}
		}
	}

	public Document createDoc() {
		document = DocumentHelper.createDocument();
		return document;
	}

	public void saveDoc(String xmlPathName) {
		try {
			XMLWriter output = new XMLWriter(new FileWriter(new File(
					xmlPathName)));
			if (document != null) {
				output.write(document);
				output.flush();
				output.close();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void generateDocument() {
		Document document = DocumentHelper.createDocument();
		Element catalogElement = document.addElement("catalog");
		catalogElement.addComment("An XML Catalog");
		catalogElement.addProcessingInstruction("target", "text");
		Element journalElement = catalogElement.addElement("journal");
		journalElement.addAttribute("title", "XML Zone");
		journalElement.addAttribute("publisher", "IBM developerWorks");

		Element articleElement = journalElement.addElement("article");
		articleElement.addAttribute("level", "Intermediate");
		articleElement.addAttribute("date", "December-2001");
		Element titleElement = articleElement.addElement("title");
		titleElement.setText("Java configuration with XML Schema");
		Element authorElement = articleElement.addElement("author");
		Element firstNameElement = authorElement.addElement("firstname");
		firstNameElement.setText("Marcello");
		Element lastNameElement = authorElement.addElement("lastname");
		lastNameElement.setText("Vitaletti");

	}

	public static void main(String[] argv) {
		// XmlOperator dom4j = new XmlOperator();
		// dom4j.generateDocument();
	}
}
