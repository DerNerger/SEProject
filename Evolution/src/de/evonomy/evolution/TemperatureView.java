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
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TemperatureView extends LinearLayout {
	private float minTemp;
	private float maxTemp;
	private float minTempArea;
	private float maxTempArea;
	private Paint paint;
	private Paint areaPaint;
	private Rect r;
	private Rect rArea;
	private static final long ANIMTIME=1000;
	public TemperatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		TypedArray a= context.getTheme().obtainStyledAttributes(attrs, R.styleable.TemperatureView, 0, 0);
		try{
			minTemp=a.getInteger(R.styleable.TemperatureView_min_temp, 1);
			maxTemp=a.getInteger(R.styleable.TemperatureView_max_temp, 1);
			minTempArea=a.getInteger(R.styleable.TemperatureView_min_temp_area, 1);
			maxTempArea=a.getInteger(R.styleable.TemperatureView_max_temp_area, 1);
			
	
		} finally{
			a.recycle();
		}
		init();
	}
	private void init(){
		inflate(getContext(),R.layout.temperature_view,this);
		areaPaint=new Paint();
		areaPaint.setColor(Color.parseColor("#FF0000"));
		paint=new Paint();
		paint.setColor(Color.parseColor("#FF8D00"));
		r= new Rect(0,0,0,0);
		rArea=new Rect(0,0,0,0);
		
	}
	public void setMaxTemp(float maxTemp){
		this.maxTemp=maxTemp;
		invalidate();
	}
	public void setMinTemp(float minTemp){
		this.minTemp=minTemp;
		invalidate();
	}
	public float getMinTemp() {
		return minTemp;
	}
	public float getMaxTemp() {
		return maxTemp;
	}
	public void changeMinTemp(float minTemp){
		ObjectAnimator anim=ObjectAnimator.ofFloat(this, "minTemp", minTemp);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
	}
	public void changeMaxTemp(float maxTemp){
		ObjectAnimator anim=ObjectAnimator.ofFloat(this, "maxTemp", maxTemp);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
		
	}
	public void setMaxTempArea(float maxTempArea){
		this.maxTempArea=maxTempArea;
		invalidate();
	}
	public void setMinTempArea(float minTempArea){
		this.minTempArea=minTempArea;
		invalidate();
	}
	public float getMinTempArea() {
		return minTempArea;
	}
	public float getMaxTempArea() {
		return maxTempArea;
	}
	public void changeMinTempArea(float minTempArea){
		ObjectAnimator anim=ObjectAnimator.ofFloat(this, "minTempArea", minTempArea);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
	}
	public void changeMaxTempArea(float maxTempArea){
		ObjectAnimator anim=ObjectAnimator.ofFloat(this, "maxTempArea", maxTempArea);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
		
	}
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		//canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight()/2, canvas.getWidth()/2, paint);
		//prozentsatz der minTemp an der generellen maximal erreichbaren Temperatur ausrechnen
		//und das mal die hälfte der anziege nehmen um auf den anteil von maxTemp an 100 grad zu bekommen
		//und dann mal 10/11 nehmen, da 1/11 der breite der anzeige nicht mit der leiste bedeckt  sit
		r.left=canvas.getWidth()/2+(int)((minTemp/100.)*(getWidth()/2.)*(10./11.));
		r.top=(int)(canvas.getHeight()*0.40);
		//genauso wie bei der linken seite
		r.right=canvas.getWidth()/2+(int)((maxTemp/100.)*(getWidth()/2.)*(10./11.));
		r.bottom=(int)(canvas.getHeight()*0.50);
		canvas.drawRect(r, paint);
		
		rArea.left=canvas.getWidth()/2+(int)((minTempArea/100.)*(getWidth()/2.)*(10./11.));
		rArea.top=(int)(canvas.getHeight()*0.50);
		//genauso wie bei der linken seite
		rArea.right=canvas.getWidth()/2+(int)((maxTempArea/100.)*(getWidth()/2.)*(10./11.));
		rArea.bottom=(int)(canvas.getHeight()*0.60);
		canvas.drawRect(rArea, areaPaint);
		
	}
	@Override
	public void onWindowFocusChanged(boolean isFocused){
		super.onWindowFocusChanged(isFocused);
//		Log.e("Temperatureview", "windowfocus");

		
	}
	

}
