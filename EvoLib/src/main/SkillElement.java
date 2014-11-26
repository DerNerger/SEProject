package main;

import java.util.LinkedList;
/**
 * 
 * @author niklas
 * Holds requirements 
 */
public class SkillElement {
	private LinkedList<SkillElement> directRequiredFor;
	private LinkedList<SkillElement> indirectRequired;
	private SkillElement parent;
	private boolean isRoot=true;
	private PossibleUpdates update;
	public SkillElement(PossibleUpdates update){
		this.update=update;
		directRequiredFor=new LinkedList<SkillElement>();
		indirectRequired=new LinkedList<SkillElement>();
		
	}
	public void addChild(SkillElement skill){
		directRequiredFor.add(skill);
		skill.setParent(this);
	}
	private void setParent(SkillElement skill){
		if(parent!=null) throw new RuntimeException("A SkillElement can only" +
				" have one direct Parent!!" +
				" Try indirect parents, if implemented yet");
		parent=skill;
		isRoot=false;
	}
	private void addIndirectRequirement(SkillElement skill){
		indirectRequired.add(skill);
	}
	public boolean isRoot(){
		return isRoot;
	}
	public LinkedList<SkillElement> getChilds(){
		return directRequiredFor;
	}
	private LinkedList<SkillElement> getInDirectRequirements(){
		return indirectRequired;
	}
	public PossibleUpdates getUpdate(){
		return update;
	}
	public SkillElement getParent(){
		return parent;
	}
}
