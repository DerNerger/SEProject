package de.evonomy.evolution;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.widget.LinearLayout;

public class GraphLinearLayout extends LinearLayout {
	private SpeciesData[] data;
	private Paint[] paint;
	private long max;
	
	public GraphLinearLayout(Context context) {
		super(context);
		setWillNotDraw(false);
		data= new SpeciesData[4];
		paint= new Paint[4];
		for(int i =0;i<data.length;i++){
			data[i]= new SpeciesData(i);
			paint[i]=new Paint();
			paint[i].setStyle(Style.STROKE);
		}
		paint[0].setColor(context.getResources().getColor(R.color.species1));
		paint[0].setStrokeWidth(5f);
		paint[1].setColor(context.getResources().getColor(R.color.species2));
		paint[1].setStrokeWidth(5f);
		paint[2].setColor(context.getResources().getColor(R.color.species3));
		paint[2].setStrokeWidth(5f);
		paint[3].setColor(context.getResources().getColor(R.color.species4));
		paint[3].setStrokeWidth(5f);
		
	}
	public void setData(SpeciesData dat) {
		data[dat.getPlayerNumber()] = dat;
	}
	@Override
	protected void onDraw(Canvas canvas){
		int width = getWidth();
		int height = getHeight();
		//find max of all
		for(int i =0;i<data.length;i++){
			if(data[i].getMax()>max){
				max=data[i].getMax();
			}
		}
		
		canvas.drawColor(Color.parseColor("#FFFFFF"));
		for (int i = 0; i < data.length; i++) {
			float deltaX = ((float) width)
					/ ((float) data[i].getPopulation().size());
			Path path = new Path();
			
			
			int x = 0;
			long popValue;
			int length=data[i].getPopulation().size();
			if(length>0){
				path.moveTo(0, height- ((float)data[i].getPopulation().get(0)
						/ ((float) max)) * height);
			}else{
				path.moveTo(0, height);
			}
			for (int p=0;p<length;p++) {
				popValue=data[i].getPopulation().get(p);
				path.lineTo(x * deltaX,height- ((float)popValue / ((float) max)) * height);
				x++;
			}
			canvas.drawPath(path, paint[data[i].getPlayerNumber()]);
		}
		
	}

}
