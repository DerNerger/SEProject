package de.evonomy.evolution;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResourceDemandView extends LinearLayout{
	private int resourceDemand;
	private ImageView im;
	private TextView te;
	public ResourceDemandView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a= context.getTheme().obtainStyledAttributes(attrs, R.styleable.ResourceDemandView, 0, 0);
		try{
			resourceDemand=a.getInteger(R.styleable.ResourceDemandView_demand, 1);
			
	
		} finally{
			a.recycle();
		}
		init();
	}
	private void init(){
		inflate(getContext(),R.layout.resource_demand_view,this);
		im=(ImageView) findViewById(R.id.image_view_resource_demand_view);
		te=(TextView) findViewById(R.id.text_view_resource_demand_view);
		setResourceDemand(resourceDemand);
	}
	public void setResourceDemand(int resourceDemand){
		this.resourceDemand=resourceDemand;
		if(resourceDemand>83){
			im.setBackgroundResource(R.drawable.res_dem_state_6);
			te.setText(R.string.res_dem_stat_6_str);
		}else if(resourceDemand>66){
			im.setBackgroundResource(R.drawable.res_dem_state_5);
			te.setText(R.string.res_dem_stat_5_str);
		
		}else if(resourceDemand>50){
			im.setBackgroundResource(R.drawable.res_dem_state_4);
			te.setText(R.string.res_dem_stat_4_str);
			
		}else if(resourceDemand>33){
			im.setBackgroundResource(R.drawable.res_dem_state_3);
			te.setText(R.string.res_dem_stat_3_str);
			
		}else if(resourceDemand>18){
			im.setBackgroundResource(R.drawable.res_dem_state_2);
			te.setText(R.string.res_dem_stat_2_str);
			
		}else{
			im.setBackgroundResource(R.drawable.res_dem_state_1);
			te.setText(R.string.res_dem_stat_1_str);
			
		}
	}
}
