package de.evonomy.evolution;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TemperatureView extends LinearLayout {
	private int minTemp;
	private int maxTemp;
	private TextView minTempView;
	private TextView maxTempView;
	private Paint paint;
	private Rect r;
	public TemperatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
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
//		minTempView=(TextView) findViewById(R.id.text_view_min_temp_temperature_view);
//		maxTempView=(TextView) findViewById(R.id.text_view_max_temp_temperature_view);
		changeMinTemp(minTemp);
		changeMaxTemp(maxTemp);
		paint=new Paint();
		paint.setColor(Color.parseColor("#000000"));
		r= new Rect(0,0,0,0);;
		
	}
	public void changeMinTemp(int minTemp){
		
		invalidate();
	}
	public void changeMaxTemp(int maxTemp){
		
		invalidate();
	}
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		//canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight()/2, canvas.getWidth()/2, paint);
		r.left=canvas.getWidth()/2;
		r.top=canvas.getHeight()/2;
		r.right=(int)(canvas.getWidth()*0.9);
		r.bottom=(int)(canvas.getHeight()*0.55);
		canvas.drawRect(r, paint);
		
	}
	

}
