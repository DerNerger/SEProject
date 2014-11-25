package de.evonomy.evolution;

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
	public SkillElementView(Context context,SkillElement element) {
		super(context);
		this.element=element;
		((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.skill_element_view, null);
		button=(Button) findViewById(R.id.button_skill_element_view);
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

}
