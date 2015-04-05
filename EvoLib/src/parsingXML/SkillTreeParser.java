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
		short nodeType = skillTreeNode.getNodeType();
		SkillTreeCollection collection = new SkillTreeCollection();
		//procress the SkillCollection
		if(nodeType==Node.ELEMENT_NODE){
			Element element = (Element) skillTreeNode;
			if(!element.getNodeName().equals("SkillCollection"))
				throw new RuntimeException("XML-File is incorrect. Unknown Tag:"+element.getNodeName());
			
			//procress the children
			for (int i = 0; i < element.getChildNodes().getLength(); i++) {
				Node child = element.getChildNodes().item(i);
				short childNodeType = child.getNodeType();
				if(childNodeType==Node.ELEMENT_NODE){
					addSubTrees(child, collection);
				}
			}
		}
		return collection;
	}
	
	private void addSubTrees(Node child, SkillTreeCollection collection){
		//parse the children
		Element childElement = (Element) child;
		String childName = childElement.getNodeName();
		if(childName.equals("Preselection")){
			//add all Preselection
			for(int j=0; j<childElement.getChildNodes().getLength();j++){
				SkillTree tree = parseSkillTree(childElement.getChildNodes().item(j));
				if(tree!=null)
					collection.addPreselection(tree);
			}
		} else if(childName.equals("Bodyextensions")){
			//add all Bodyextensions
			for(int j=0; j<childElement.getChildNodes().getLength();j++){
				SkillTree tree = parseSkillTree(childElement.getChildNodes().item(j));
				if(tree!=null)
					collection.addBodyextensions(tree);
			}
		} else if(childName.equals("Ability")){
			//add all Abilities
			for(int j=0; j<childElement.getChildNodes().getLength();j++){
				SkillTree tree = parseSkillTree(childElement.getChildNodes().item(j));
				if(tree!=null)
					collection.addAbility(tree);
			}
		} else {
			throw new RuntimeException("XML-File is incorrect. Unknown Tag:"+childElement.getNodeName());
		}
	}
	
	private SkillTree parseSkillTree(Node aNode){
		short nodeType = aNode.getNodeType();
		if (nodeType == Node.ELEMENT_NODE) {
			//get the element
			Element element = (Element) aNode;
			
			//create the tree
			SkillTree tree = new SkillTree(element);
			
			//add the children
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
