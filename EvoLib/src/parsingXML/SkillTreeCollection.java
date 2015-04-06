package parsingXML;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import main.PossibleUpdates;
import main.SkillValue;

public class SkillTreeCollection{
	
	private LinkedList<SkillTree> preselection;
	private LinkedList<SkillTree> bodyextensions;
	private LinkedList<SkillTree> abilities;
	
	private HashMap<PossibleUpdates, SkillValue> values;
	
	public SkillTreeCollection(){
		preselection = new LinkedList<>();
		bodyextensions = new LinkedList<>();
		abilities = new LinkedList<>();
		values = new HashMap<>();
	}
	
	public void addPreselection(SkillTree tree){
		preselection.add(tree);
		putToHashMap(tree);
	}
	
	public void addBodyextensions(SkillTree tree){
		bodyextensions.add(tree);
		putToHashMap(tree);
	}
	
	public void addAbility(SkillTree tree){
		abilities.add(tree);
		putToHashMap(tree);
	}
	
	private void putToHashMap(SkillTree tree){
		Queue<SkillTree> trees = new LinkedList<SkillTree>();
		trees.add(tree);
		while(!trees.isEmpty()){
			SkillTree t = trees.poll();
			values.put(t.getSkillName(), t.getSkillValue());
			trees.addAll(t.getChilds());
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("HashMap:\n[");
		for(PossibleUpdates update : values.keySet()){
			sb.append(update+"->"+values.get(update)+", ");
		}
		sb.append("]\n");
		sb.append("Preselection:\n");
		for(SkillTree tree : preselection){
			sb.append("["+tree.toString()+"]\n");
		}
		
		sb.append("Bodyextensions\n");
		for(SkillTree tree : bodyextensions){
			sb.append("["+tree.toString()+"]\n");
		}
		
		sb.append("Abilities:\n");
		for(SkillTree tree : abilities){
			sb.append("["+tree.toString()+"]\n");
		}
		return sb.toString();
	}
	
	public SkillValue getSkillValue(PossibleUpdates update){
		return values.get(update);
	}
}
