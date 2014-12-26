package de.evonomy.evolution;

import java.util.LinkedList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

public class PopulationGraph extends LinearLayout {
	private SurfaceView sView;
	private SurfaceHolder holder;
	private SpeciesData[] data;
	private int width;
	private int height;
	private Paint[] paint;


	public PopulationGraph(Context context, AttributeSet attrs) {
		super(context, attrs);
		// setWillNotDraw(false);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.PopulationGraph, 0, 0);
		try {

		} finally {
			a.recycle();
		}
		inflate(getContext(),R.layout.population_graph,this);
		sView = (SurfaceView) findViewById(R.id.surface_view_population_graph_draw);
		//sView.setBackgroundColor(getResources().getColor(R.color.white));
		holder = sView.getHolder();
		data = new SpeciesData[4];
		paint = new Paint[4];
		for (int i = 0; i < data.length; i++) {
			data[i] = new SpeciesData(i);
			paint[i]=new Paint();
		}
		
		paint[0].setColor(context.getResources().getColor(R.color.species1));
		paint[0].setStrokeWidth(2f);
		paint[1].setColor(context.getResources().getColor(R.color.species2));
		paint[1].setStrokeWidth(2f);
		paint[2].setColor(context.getResources().getColor(R.color.species3));
		paint[2].setStrokeWidth(2f);
		paint[3].setColor(context.getResources().getColor(R.color.species4));
		paint[3].setStrokeWidth(2f);
		
	}

	public void setData(SpeciesData dat) {
		data[dat.getPlayerNumber()] = dat;
	}

	public void drawPopulation() {
		width = sView.getWidth();
		height = sView.getHeight();

		Canvas canvas = holder.lockCanvas();
		canvas.drawColor(Color.parseColor("#FFFFFF"));
		for (int i = 0; i < data.length; i++) {
			long max = data[i].getMax();
			float deltaX = ((float) width)
					/ ((float) data[i].getPopulation().size());
			Path path = new Path();
			path.moveTo(width, height);
			int x = 0;
			for (long popValue : data[i].getPopulation()) {
				path.lineTo(width- x * deltaX,((float)popValue / ((float) max)) * height);
				x++;
			}
			//path.lineTo(deltaX, y)
			canvas.drawPath(path, paint[data[i].getPlayerNumber()]);
		}
		holder.unlockCanvasAndPost(canvas);

	}

}
