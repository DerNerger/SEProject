package de.evonomy.evolution;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TemperatureView extends LinearLayout {
	private int minTemp;
	private int maxTemp;
	private TextView minTempView;
	private TextView maxTempView;
	public TemperatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a= context.getTheme().obtainStyledAttributes(attrs, R.styleable.TemperatureView, 0, 0);
		try{
			minTemp=a.getInteger(R.styleable.TemperatureView_min_temp, 1);
			maxTemp=a.getInteger(R.styleable.TemperatureView_max_temp, 1);
			
	
		} finally{
			a.recycle();
		}
		init();
	}
	private void init(){
		inflate(getContext(),R.layout.temperature_view,this);
		minTempView=(TextView) findViewById(R.id.text_view_min_temp_temperature_view);
		maxTempView=(TextView) findViewById(R.id.text_view_max_temp_temperature_view);
		changeMinTemp(minTemp);
		changeMaxTemp(maxTemp);
	}
	public void changeMinTemp(int minTemp){
		minTempView.setText(minTemp+" °C");
	}
	public void changeMaxTemp(int maxTemp){
		maxTempView.setText(maxTemp+ " °C");
	}
	

}
