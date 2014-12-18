package de.evonomy.evolution;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.view.View;
import android.widget.RelativeLayout;

public class SkillTreeRelativeLayout extends RelativeLayout {
	private static float widthOfLine=12;
	private Paint paint;
	private Paint greyPaint;
	private Context context;
	private boolean drawExtra=false;
	private float px1,px2,py1,py2;
	public SkillTreeRelativeLayout(Context context) {
		super(context);
		this.context=context;
		paint=new Paint();
		paint.setColor(Color.parseColor("#000000"));
		paint.setStrokeWidth(widthOfLine);
		greyPaint=new Paint();
		greyPaint.setColor(Color.parseColor("#BCBCBC"));
		greyPaint.setStrokeWidth(widthOfLine);
		setWillNotDraw(false);
	}
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		for(int i =0;i<getChildCount();i++){
			View probView=getChildAt(i);
			if(probView instanceof SkillElementView){
				drawToChildren(canvas,(SkillElementView)probView);
			}
		}
		
	}
	private void drawToChildren(Canvas canvas,SkillElementView root){
		Paint toPaint;
		toPaint=greyPaint;
		if(((GameActivity)context).isSkilled(root
				.getSkillElement().getUpdate())) toPaint=paint;
		float anchorXRoot=root.getAnchorX();
		float anchorYRoot=root.getAnchorTopY();
		boolean oneChildIsSkilled=false;
		for (SkillElementView child:root.getChilds()){
			//if child is not skillable because of already skilled neighbour, 
			//set paint to grey again
			if(!child.isSkillable()) toPaint=greyPaint;
			else if(((GameActivity)context).isSkilled(root
					.getSkillElement().getUpdate()))toPaint=paint;
			//test if one child is Skilled, then the vertical line should be
			//drawn black
			if(((GameActivity)context).isSkilled(child
					.getSkillElement().getUpdate())){
				oneChildIsSkilled=true;
			}
			float anchorXChild=child.getAnchorX();
			float anchorYChild=child.getAnchorBottomY();
			
			//horizontal from root to child
			canvas.drawLine(anchorXRoot
					,anchorYRoot-(anchorYRoot-anchorYChild)/2 ,
					anchorXChild, anchorYRoot-(anchorYRoot-anchorYChild)/2
					, toPaint);
			if(toPaint==paint && oneChildIsSkilled){
				drawExtra=true;
				px1=anchorXRoot;
				py1=anchorYRoot-(anchorYRoot-anchorYChild)/2 ;
				px2=anchorXChild;
				py2=anchorYRoot-(anchorYRoot-anchorYChild)/2;
			}
			//vertical to child
			canvas.drawLine(anchorXChild,
					anchorYRoot-(anchorYRoot-anchorYChild)/2+(widthOfLine/2),
					anchorXChild, anchorYChild, toPaint);
			//vertically up from root to half of height
			//should be always drawn black if on child is Skilled
			//last drawn,so it can't be overdrawn
			canvas.drawLine(anchorXRoot, anchorYRoot, anchorXRoot
					,anchorYRoot-(anchorYRoot-anchorYChild)/2 -(widthOfLine/2)
					, oneChildIsSkilled ? paint : toPaint);
		}
		if(drawExtra){
			canvas.drawLine(px1, py1, px2, py2, paint);
		}
	}

}
