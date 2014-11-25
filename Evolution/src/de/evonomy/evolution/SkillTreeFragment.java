package de.evonomy.evolution;

import java.util.LinkedList;

import main.SkillElement;
import main.SpeciesSkillInformation;

import de.evonomy.evolution.FragmentSkillBody.Slots;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class SkillTreeFragment extends DialogFragment{
	private static final String SLOT="slot";
	private FragmentSkillBody.Slots slot;
	private LinkedList<SkillElement> skills;
	private LinkedList<SkillElement> rootSkills;
	private int height;
	private int width;
	private int treeHeight;
	private int treeWidth;
	private View root;
	public static SkillTreeFragment newInstance(FragmentSkillBody.Slots slot){
		SkillTreeFragment frag=new SkillTreeFragment();
		Bundle args=new Bundle();
		
		args.putSerializable(SLOT, slot);
		frag.setArguments(args);
		return frag;
	}
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		//Remove status bar
				
		getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
				
		//remove title
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		//inflate the layout for fragment
		//get the slot
		slot=(Slots) getArguments().getSerializable(SLOT);
		skills=new LinkedList<SkillElement>();
		rootSkills=new LinkedList<SkillElement>();
		root=inflater.inflate(R.layout.fragment_skill_tree, container,false);
		skills=SpeciesSkillInformation.getLegSkills();
		
		
	    
		
		
		return root;
	}
	@Override
	public void onResume(){
		super.onResume();
		//in onResume to always give a cool animation
		getView().post(new Runnable() {
			
			@Override
			public void run() {
				height=root.getWidth();
				width=root.getHeight();
				
			}
		});
		
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
		//TODO find height
		
		
	}
	int maxHeight(SkillElement node){
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
}
