package de.evonomy.evolution;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MovementView extends LinearLayout {
	private float movement;
	private ImageView im;
	public MovementView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a= context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.MovementView, 0, 0);
		try{
			movement=a.getFloat(R.styleable.MovementView_movement, 0.1f);
			
	
		} finally{
			a.recycle();
		}
		init();
	}
	private void init(){
		inflate(getContext(),R.layout.movement_view,this);
		im=(ImageView) findViewById(R.id.image_view_movement_view);
		setMovementChance(movement);
		
	}
	public void setMovementChance(double movement){
		this.movement=(float) movement;
		if(this.movement>0.98f){
			im.setImageResource(R.drawable.migration_state_6);
		}else if(this.movement>0.83f){
			im.setImageResource(R.drawable.migration_state_5);
		}else if(this.movement>0.66f){
			im.setImageResource(R.drawable.migration_state_4);
		}else if(this.movement>0.5f){
			im.setImageResource(R.drawable.migration_state_3);
		}else if(this.movement>0.33f){
			im.setImageResource(R.drawable.migration_state_2);
		}else if(this.movement>0.18f){
			im.setImageResource(R.drawable.migration_state_1);
		}else{
			im.setImageResource(R.drawable.migration_state_0);
		}
	}


}
