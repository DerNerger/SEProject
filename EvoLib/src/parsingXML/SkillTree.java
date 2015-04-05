package parsingXML;
import java.util.LinkedList;

import main.PossibleUpdates;
import main.SkillValue;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class SkillTree {
	
	//node
	private PossibleUpdates skillName;
	private SkillValue skillValue;
	
	//childs
	private LinkedList<SkillTree> childs;
	
	/**
	 * Parset den Knoten aus dem Eintrag einer XML-Datei
	 * */
	public SkillTree(Element element){
		childs = new LinkedList<>();
		skillValue = new SkillValue();
		//add the attributes
		NamedNodeMap attributeMap = element.getAttributes();
		for (int i = 0; i < attributeMap.getLength(); i++) {
			Attr attr = (Attr) attributeMap.item(i);
			set(attr.getName(), attr.getValue());
		}
	}
	
	public void addChild(SkillTree tree){
		childs.add(tree);
	}
	
	private void set(String name, String value){
		if(name.equals("name")){
			skillName = PossibleUpdates.valueOf(value);
		} else if(name.equals("water")){
			skillValue.setWater(Boolean.parseBoolean(value));
		} else if(name.equals("intelligence")){
			skillValue.setIntelligence(Integer.parseInt(value));
		} else if(name.equals("strength")){
			skillValue.setStrength(Integer.parseInt(value));
		} else if(name.equals("resourceDemand")){
			skillValue.setResourceDemand(Integer.parseInt(value));
		} else if(name.equals("agility")){
			skillValue.setAgility(Integer.parseInt(value));
		} else if(name.equals("social")){
			skillValue.setSocial(Integer.parseInt(value));
		} else if(name.equals("procreation")){
			skillValue.setProcreation(Integer.parseInt(value));
		} else if(name.equals("minTemp")){
			skillValue.setMinTemp(Integer.parseInt(value));
		} else if(name.equals("maxTemp")){
			skillValue.setMaxTemp(Integer.parseInt(value));
		} else if(name.equals("movementChance")){
			skillValue.setMovementChance(Double.parseDouble(value));
		} else if(name.equals("visibillity")){
			skillValue.setVisibillity(Integer.parseInt(value));
		} else {
			throw new RuntimeException("unknown xml attribute:"+name);
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(skillName+": "+skillValue.toString());
		sb.append("  children:[");
		for(SkillTree t : childs){
			sb.append(t.toString());
		}
		sb.append("]");
		return sb.toString();
	}
	
	public LinkedList<SkillTree> getChilds(){
		return childs;
	}

	public PossibleUpdates getSkillName() {
		return skillName;
	}

	public SkillValue getSkillValue() {
		return skillValue;
	}
	
	
}
