package de.evonomy.evolution;

import java.util.LinkedList;

import main.SkillElement;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SkillElementView extends LinearLayout {
	private Button button;
	private SkillElement element;
	private LinkedList<SkillElementView> childs;
	private boolean clickable=true;
	private Context context;
	private SkillTreeFragment frago;
	//is set to false, if other zweig of parent is skilled
	private boolean isSkillable;
	public SkillElementView(final Context context,final SkillElement element
			,final SkillTreeFragment frago) { 
		super(context);
		this.element=element;
		this.context=context;
		this.frago=frago;
		isSkillable=true;
		inflate(context,R.layout.skill_element_view, this);
		
		childs=new LinkedList<SkillElementView>();
		button=(Button) findViewById(R.id.button_skill_element_view);
		if(!((GameActivity)context).isSkilled(element.getUpdate())
				){
					this.setAlpha(0.5f);
					if(element.getParent()!=null&&!((GameActivity)context)
					.isSkilled(element.getParent().getUpdate()))
						clickable=false;
					
		}
		setImage();
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(clickable&&isSkillable){
					//TODO start the Dialog to display all the information
					SkillDialogFragment frag=
							SkillDialogFragment
							.newInstance(element.getUpdate(),
									((GameActivity) context).getSpecies(),
									element.getPrice(),frago);
					
					
					FragmentManager fm=((GameActivity) context).getSupportFragmentManager();
					frag.show(fm, "fragment_skill_dialog");
					
				}
				
			}
		});
	}
	public SkillElement getSkillElement(){
		return element;
	}
	public void addChildren(SkillElementView sk){
		childs.add(sk);
	}
	public LinkedList<SkillElementView> getChilds(){
		return childs;
	}
	public float getAnchorX(){
		return getX()+(1f/2f)*getResources()
				.getDimension(R.dimen.skill_element_hw);
	}
	public float getAnchorBottomY(){
		return getY()+getResources()
				.getDimension(R.dimen.skill_element_hw);
	}
	public float getAnchorTopY(){
		return getY();
		
	}
	@SuppressWarnings("deprecation")
	private void setImage(){
		//switch by possibleupdate typ for getting da right picta
		//TODO irgendeiner muss hier noch die bimbo arbeit machen
		//Also die bilder setzen
		switch(element.getUpdate()){
		default:button.setBackgroundDrawable(getResources()
				.getDrawable(R.drawable.ic_launcher));
		}
	}
	public void branchUnskillable(){
		isSkillable=false;
		for(SkillElementView cu:childs){
			cu.branchUnskillable();
		}
	}
	public boolean isSkillable(){
		return isSkillable;
	}

}
