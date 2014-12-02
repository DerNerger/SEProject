package de.evonomy.evolution;

import java.util.LinkedList;

import main.SkillElement;
import main.SpeciesSkillInformation;

import de.evonomy.evolution.FragmentSkillBody.Slots;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SkillTreeFragment extends Fragment{
	private static final String SLOT="slot";
	private FragmentSkillBody.Slots slot;
	private RelativeLayout rlcontainer;
	private RelativeLayout rl;
	private LinkedList<SkillElement> skills;
	private LinkedList<SkillElement> rootSkills;
	private int height;
	private int width;
	private int treeHeight;
	private int treeWidth;
	private View root;
	private int[]widthsOfDepth;
	private int zahler=0;
	private int[] currentCounterOfDepth;
	private LinkedList<SkillElementView> skillViews;
	private LinkedList<SkillElementView> rootSkillViews;
	
	public static SkillTreeFragment newInstance(FragmentSkillBody.Slots slot
			,int width,int height){
		SkillTreeFragment frag=new SkillTreeFragment();
		Bundle args=new Bundle();
		
		args.putSerializable(SLOT, slot);
		args.putInt("width", width);
		args.putInt("height", height);
		frag.setArguments(args);
		return frag;
	}
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		//inflate the layout for fragment
		//get the slot
		slot=(Slots) getArguments().getSerializable(SLOT);
		width=getArguments().getInt("width");
		height=getArguments().getInt("height");
		//initiate the skilllists
		skills=new LinkedList<SkillElement>();
		rootSkills=new LinkedList<SkillElement>();
		root=inflater.inflate(R.layout.fragment_skill_tree, container,false);
		//getall the views
		rlcontainer=(RelativeLayout) 
			root.findViewById(R.id.linear_layout_fragment_skill_tree_container);
		rl=new SkillTreeRelativeLayout(getActivity());
		rlcontainer.addView(rl);
		
		//get Skills by slot
		switch(slot){
		case ARMS: skills=SpeciesSkillInformation.getArmSkills();
			break;
		case BODY:skills=SpeciesSkillInformation.getBodySkills();
			break;
		case HEAD:skills=SpeciesSkillInformation.getHeadSkills();
			break;
		case LEGS:skills=SpeciesSkillInformation.getLegSkills();
			break;
		case NEXTTOHEAD: skills=SpeciesSkillInformation.getNextToHeadSkills();
			break;
		case NEXTTOLEGS: skills=SpeciesSkillInformation.getNextToLegSkills();
			break;
		default: throw new RuntimeException("Bist du behindert?Warum gibst du"+
				" nen slot mit an den baum aufbauer den es nicht gibt?????");
		}
		
		
		
		findHeightAndMaxWidthOfTreeAndSetRootViews();
		createSkillViews();
		Log.e("SkillTreeFragment","trWidth"+treeWidth+" trHeight"
				+treeHeight+" #skillelem"+skills.size());
		//now create a skill view tree(for drawing) from the old tree
		createSkillViewTreeFromElementTree();
		
		return root;
	}
	private void findHeightAndMaxWidthOfTreeAndSetRootViews(){
		//set Root skills
		for(SkillElement sk:skills){
			if(sk.isRoot()) rootSkills.add(sk);
		}
		treeHeight=0;
		for(SkillElement sk:rootSkills){
			int tmp=maxHeight(sk);
			if(tmp>treeHeight) treeHeight=tmp;
		}
		widthsOfDepth=new int[treeHeight];
		currentCounterOfDepth=new int[treeHeight];
		for(int i=0;i<treeHeight;i++){
			currentCounterOfDepth[i]=1;
		}
		treeWidth=0;
		maxWidth(rootSkills);
		
		
	}
	private int maxHeight(SkillElement node){
		if(node.getChilds().size()==0) return 1;
		else{
			int max=0;
			for(SkillElement child:node.getChilds()){
				int h;
				if((h=maxHeight(child))>max) max=h;
			}
			return max+1;
		}
	}
	private void maxWidth(LinkedList<SkillElement> current){
		if(current.size()==0) return ;
		else{
			if(current.size()>treeWidth) treeWidth=current.size();
			widthsOfDepth[zahler]=current.size();
			LinkedList<SkillElement> next=new LinkedList<SkillElement>();
			for(SkillElement cu:current){
				next.addAll(cu.getChilds());
			}
			zahler++;
			maxWidth(next);
		}
	}
	public void createSkillViews(){
		skillViews=new LinkedList <SkillElementView>();
		rootSkillViews=new LinkedList <SkillElementView>();
		for(SkillElement sk:skills){
			skillViews.add(new SkillElementView(getActivity(), sk));
		}
		for(SkillElementView sk:skillViews){
			if(sk.getSkillElement().isRoot()){
				rootSkillViews.add(sk);
			}
		}
	}
	
	public void createSkillViewTreeFromElementTree(){
		for(SkillElementView toAddChildren:skillViews){
			for(SkillElementView toAdd:skillViews){
				Log.e("isChildren", toAddChildren.getSkillElement()
						.getChilds().contains(
						toAdd.getSkillElement())+"");
				if(toAddChildren.getSkillElement().getChilds().contains(
						toAdd.getSkillElement())){
					toAddChildren.addChildren(toAdd);
				}
			}
		}
	}
	

	//zum malen steigt man immer rekursiv in dem baum ab, gibt die aktuelle
	//höhe mit und lässt die position für diese ebene hochzählen
	//anschleißend fügt man an dieser stelle die entsprechende view hinzu
	private void drawTree(SkillElementView root,int depth){
		/*Never change a running system!!!!!*/
		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(
			getActivity().getResources()
			.getDimensionPixelSize(R.dimen.skill_element_hw), getActivity()
			.getResources().getDimensionPixelSize(R.dimen.skill_element_hw));

		params.setMargins(
				//left=position in der aufgeteilten ebene-
				//hälfte einer aufteilung(für zentriertere sicht)
				(width/widthsOfDepth[depth]) 
				*currentCounterOfDepth[depth]-(width/widthsOfDepth[depth])/2
				-(getActivity().getResources()
				.getDimensionPixelSize(R.dimen.skill_element_hw)/2)
				,
				
				//top =wie bottom, nur - die höhe des bildes
				height-(depth)*(height/treeHeight)
				-getActivity().getResources()
				.getDimensionPixelSize(R.dimen.skill_element_hw),
				//right=wie left,nur plus breite
				(width/widthsOfDepth[depth]) 
				*currentCounterOfDepth[depth]-width/widthsOfDepth[depth]/2
				+(getActivity().getResources()
				.getDimensionPixelSize(R.dimen.skill_element_hw)),
				//von ganz unten anfangen
				//bottom=pro ebene aufwärts ein Baumhöhtel der höhe abziehen
				height-(depth)*(height/treeHeight));
		Log.e("SkillViewParams", "top"+params.topMargin+" bottom"
				+params.bottomMargin+" left"+params.leftMargin+ 
				" right"+params.rightMargin);
		Log.e("rrot id:", root.getSkillElement().getUpdate().name());
		rl.addView(root, params);
		Log.e("draw", "should have drawn"+root.getSkillElement().getUpdate().name());
		//count position for the depth one up
		currentCounterOfDepth[depth]++;
		Log.e("rootChilds", root.getChilds().size()+"");
		if(root.getChilds().size()>0){
			for(SkillElementView child: root.getChilds()){
				drawTree(child, depth+1);
				
			}
		}
	}
	@Override
	public void onResume(){
		super.onResume();
		//in onResume to draw AFTER onStart when the width and height is set
		getView().post(new Runnable() {
			
			@Override
			public void run() {
				rl.removeAllViews();
				for(SkillElementView root:rootSkillViews){
					drawTree(root, 0);
			
				}
				rl.invalidate();
			}
		});
		
	}

	
}
