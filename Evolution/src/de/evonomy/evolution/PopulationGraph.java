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
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class PopulationGraph extends LinearLayout {
	private LinearLayout container;
	private GraphLinearLayout graph;


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
		//sView = (SurfaceView) findViewById(R.id.surface_view_population_graph_draw);
		//sView.setBackgroundColor(getResources().getColor(R.color.white));
		container=(LinearLayout) findViewById(R.id.linear_layout_population_graph_to_add);
		graph=new GraphLinearLayout(context);
		graph.setLayoutParams(new LinearLayout.LayoutParams(
		        ViewGroup.LayoutParams.MATCH_PARENT,
		        ViewGroup.LayoutParams.MATCH_PARENT));
		container.addView(graph);
		//holder = sView.getHolder();
		
		
		
		
		
	}

	public void setData(SpeciesData dat) {
		graph.setData(dat);
		
	}

	public void drawPopulation() {
		graph.invalidate();
//		width = sView.getWidth();
//		height = sView.getHeight();
//
//		Canvas canvas = holder.lockCanvas();
//		canvas.drawColor(Color.parseColor("#FFFFFF"));
//		for (int i = 0; i < data.length; i++) {
//			long max = data[i].getMax();
//			float deltaX = ((float) width)
//					/ ((float) data[i].getPopulation().size());
//			Path path = new Path();
//			path.moveTo(width, height);
//			int x = 0;
//			for (long popValue : data[i].getPopulation()) {
//				path.lineTo(width- x * deltaX,((float)popValue / ((float) max)) * height);
//				x++;
//			}
//			//path.lineTo(deltaX, y)
//			canvas.drawPath(path, paint[data[i].getPlayerNumber()]);
//		}
//		holder.unlockCanvasAndPost(canvas);

	}

}
