package de.evonomy.evolution;

import java.util.LinkedList;

import main.SkillElement;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SkillElementView extends LinearLayout {
	private Button button;
	private boolean isClicked=false;
	private SkillElement element;
	private LinkedList<SkillElementView> childs;
	public SkillElementView(Context context,SkillElement element) { 
		super(context);
		this.element=element;
//		((LayoutInflater)context.getSystemService(
//				Context.LAYOUT_INFLATER_SERVICE))
//				.inflate(R.layout.skill_element_view, null);
		inflate(context,R.layout.skill_element_view, this);

		childs=new LinkedList<SkillElementView>();
		button=(Button) findViewById(R.id.button_skill_element_view);
		button.setText(element.getUpdate().name());
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isClicked){
					isClicked=true;
					//setText
				}
				
			}
		});
	}
	public void setBack(){
		isClicked=false;
		//set texts back
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
		return getX()+(1/2)*getResources()
				.getDimension(R.dimen.skill_element_hw);
	}
	public float getAnchorBottomY(){
		return getY()+getResources()
				.getDimension(R.dimen.skill_element_hw);
	}
	public float getAnchorTopY(){
		return getY();
		
	}

}
