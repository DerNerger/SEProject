package parsingXML;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import main.PossibleUpdates;
import main.SkillValue;

public class SkillTreeCollection implements Iterable<SkillTree>{
	private LinkedList<SkillTree> skillTrees;
	private HashMap<PossibleUpdates, SkillValue> values;
	
	public SkillTreeCollection(){
		skillTrees = new LinkedList<>();
		values = new HashMap<>();
	}
	
	public void addSkillTree(SkillTree tree){
		skillTrees.add(tree);
		
		Queue<SkillTree> trees = tree.getChilds();
		while(!trees.isEmpty()){
			SkillTree t = trees.poll();
			values.put(t.getSkillName(), t.getSkillValue());
			trees.addAll(t.getChilds());
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(SkillTree tree : skillTrees){
			sb.append("["+tree.toString()+"]\n");
		}
		return sb.toString();
	}

	@Override
	public Iterator<SkillTree> iterator() {
		return skillTrees.iterator();
	}
	
	public SkillValue getSkillValue(PossibleUpdates update){
		return values.get(update);
	}
}
