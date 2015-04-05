package parsingXML;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class SkillTreeParser {
	
	private Node skillTreeNode;
	
	public SkillTreeParser(String filename) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File(filename));
		skillTreeNode = document.getDocumentElement();
	}
	
	public SkillTreeCollection parseSkillTreeCollection(){
		SkillTreeCollection collection = new SkillTreeCollection();
		Element element = (Element) skillTreeNode;
		for (int i = 0; i < element.getChildNodes().getLength(); i++) {
			SkillTree tree = parseSkillTree(element.getChildNodes().item(i));
			if(tree!=null)
				collection.addSkillTree(tree);
		}
		return collection;
	}
	
	private SkillTree parseSkillTree(Node aNode){
		short nodeType = aNode.getNodeType();
		if (nodeType == Node.ELEMENT_NODE) {
			//gtet the element
			Element element = (Element) aNode;
			
			//create the tree
			SkillTree tree = new SkillTree(element);
			
			//add the childs
			for (int i = 0; i < element.getChildNodes().getLength(); i++) {
				SkillTree child = parseSkillTree(element.getChildNodes().item(i));
				if(child!=null)
					tree.addChild(child);
			}
			return tree;
		} else if (nodeType == Node.TEXT_NODE) {
			//text between <bla> and </bla>
			return null;
		} else {
			//...
			return null;
		}
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException{
		SkillTreeParser parser = new SkillTreeParser("skills.xml");
		SkillTreeCollection collection = parser.parseSkillTreeCollection();
		System.out.println(collection);
	}
}
