package de.evonomy.evolution;

import main.FieldType;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Shader;

public class PaintHandler {
	
	public static int RADIUSMASKFILTER=6;
	public static Paint getShaderPaint(FieldType t,Resources res){
		Paint p = new Paint();
		Bitmap fillBMP;
		BitmapShader fillBMPShader;
		
		switch(t){
		case DESERT:
			fillBMP=BitmapFactory.decodeResource(res, R.drawable.tilesand);
			break;
		case ICE: 
			fillBMP=BitmapFactory.decodeResource(res, R.drawable.tileice);
			break;
		case JUNGLE:
			fillBMP=BitmapFactory.decodeResource(res, R.drawable.tilejungle);
			break;
		case LAND: 
			fillBMP=BitmapFactory.decodeResource(res, R.drawable.tileland);
			break;
		case WATER:
			fillBMP=BitmapFactory.decodeResource(res, R.drawable.tilewater);
			break;
		default:
			fillBMP=BitmapFactory.decodeResource(res, R.drawable.tileland);
			break;
				
		}
		fillBMPShader=new BitmapShader(fillBMP, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);
		p.setColor(0xFFFFFFFF);
		p.setStyle(Paint.Style.FILL);
		
		p.setDither(true);
		p.setStrokeWidth(12);
		p.setShader(fillBMPShader);
		p.setAntiAlias(true);
//		p.setPathEffect(new CornerPathEffect(10));
		p.setMaskFilter(new BlurMaskFilter(RADIUSMASKFILTER, BlurMaskFilter.Blur.NORMAL ));
		
		p.setStrokeJoin(Paint.Join.BEVEL);
		p.setStrokeCap(Paint.Cap.ROUND);
		return p;
	} 
}
