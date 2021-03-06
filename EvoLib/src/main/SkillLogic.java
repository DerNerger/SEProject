package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import parsingXML.SkillTreeCollection;
import parsingXML.SkillTreeParser;

public class SkillLogic {
	
	private static InputStream stream; //TODO: this only a hotfix for the xml-parser
	public static void setStream(InputStream s){stream = s;}
	
	//static instance
	private static SkillLogic instance = null;
	
	//object attributes
	private SkillTreeCollection skillTrees;
	
	//functions
	public static synchronized SkillLogic getSkillLogic(){
		if(instance==null){
			instance = new SkillLogic();
		}
		return instance;
	}
	
	public SkillValue getSkillValue(PossibleUpdates update,boolean reverse){
		SkillValue temp = skillTrees.getSkillValue(update);
		SkillValue s = new SkillValue(temp);
		if(reverse){
			s.setAgility(s.getAgility()*-1);
			s.setIntelligence(s.getIntelligence()*-1);
			s.setMaxTemp(s.getMaxTemp()*-1);
			s.setMinTemp(s.getMinTemp()*-1);
			s.setMovementChance(s.getMovementChance()*-1);
			s.setProcreation(s.getProcreation()*-1);
			s.setResourceDemand(s.getResourceDemand()*-1);
			s.setSocial(s.getSocial()*-1);
			s.setStrength(s.getStrength()*-1);
			s.setVisibillity(s.getVisibillity()*-1);
			if(s.isChangesWater())
				s.setWater(!s.isWater());
		}
		return s;
	}
	
	public int getSkillCosts(PossibleUpdates update,boolean reverse){
		SkillValue v=getSkillValue(update, reverse);
		/*
		 * Here is the formula to calculate the costs of a skill
		 * */
		int sum=0;
		sum+=Math.abs(v.getAgility());
		sum+=Math.abs(v.getIntelligence());
		sum+=Math.abs(v.getMaxTemp());
		sum+=Math.abs(v.getMinTemp());
		sum+=Math.abs(v.getMovementChance());
		sum+=Math.abs(v.getProcreation());
		sum+=Math.abs(v.getResourceDemand());
		sum+=Math.abs(v.getSocial());
		sum+=Math.abs(v.getStrength());
		sum+=Math.abs(v.getVisibillity());
		sum+=Math.abs(v.isChangesWater()?25:0);
		return (int)Math.pow(sum, 1.5);
	}
	
	public SkillValue getSkillValue(PossibleUpdates update){
		return skillTrees.getSkillValue(update);
	}
	
	public SkillLogic(){
		try {
			SkillTreeParser parser = new SkillTreeParser(stream);
			skillTrees = parser.parseSkillTreeCollection();
		} catch (ParserConfigurationException | SAXException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		SkillLogic.setStream(new FileInputStream("skills.xml"));
		SkillLogic l = SkillLogic.getSkillLogic();
		SkillValue val = l.getSkillValue(PossibleUpdates.CENTRALNERVSYSTEM,false);
		System.out.println(val);
		val = l.getSkillValue(PossibleUpdates.CENTRALNERVSYSTEM,true);
		System.out.println(val);
		val = l.getSkillValue(PossibleUpdates.CENTRALNERVSYSTEM,false);
		System.out.println(val);
		val = l.getSkillValue(PossibleUpdates.CENTRALNERVSYSTEM,true);
		System.out.println(val);
	}
}
