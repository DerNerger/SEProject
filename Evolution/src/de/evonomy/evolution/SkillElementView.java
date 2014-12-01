package de.evonomy.evolution;

import java.util.LinkedList;

import main.SkillElement;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SkillElementView extends LinearLayout {
	private Button button;
	private SkillElement element;
	private LinkedList<SkillElementView> childs;
	private boolean clickable=true;
	public SkillElementView(Context context,SkillElement element) { 
		super(context);
		this.element=element;
		inflate(context,R.layout.skill_element_view, this);
		
		childs=new LinkedList<SkillElementView>();
		button=(Button) findViewById(R.id.button_skill_element_view);
		if(!((GameActivity)context).isSkilled(element.getUpdate())
				&&element.getParent()!=null){
					this.setAlpha(0.5f);
					if(!((GameActivity)context)
					.isSkilled(element.getParent().getUpdate()))
						clickable=false;
					
		}
		setImage();
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(clickable){
					//TODO start the Dialog to display all the information
					
					//please delete
					Toast.makeText(getContext(), "hi", Toast.LENGTH_SHORT).show();
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

}
