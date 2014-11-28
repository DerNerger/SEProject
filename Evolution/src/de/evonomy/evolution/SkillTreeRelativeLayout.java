package de.evonomy.evolution;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.RelativeLayout;

public class SkillTreeRelativeLayout extends RelativeLayout {
	private static float widthOfLine=12;
	private Paint paint;
	public SkillTreeRelativeLayout(Context context) {
		super(context);
		paint=new Paint();
		paint.setColor(Color.parseColor("#000000"));
		paint.setStrokeWidth(widthOfLine);
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
		float anchorXRoot=root.getAnchorX();
		float anchorYRoot=root.getAnchorTopY();
		for (SkillElementView child:root.getChilds()){
			float anchorXChild=child.getAnchorX();
			float anchorYChild=child.getAnchorBottomY();
			//vertically up from root to half of height
			canvas.drawLine(anchorXRoot, anchorYRoot, anchorXRoot
					,anchorYRoot-(anchorYRoot-anchorYChild)/2 -(widthOfLine/2)
					, paint);
			//horizontal from root to child
			canvas.drawLine(anchorXRoot
					,anchorYRoot-(anchorYRoot-anchorYChild)/2 ,
					anchorXChild, anchorYRoot-(anchorYRoot-anchorYChild)/2
					, paint);
			//vertical to child
			canvas.drawLine(anchorXChild,
					anchorYRoot-(anchorYRoot-anchorYChild)/2+(widthOfLine/2),
					anchorXChild, anchorYChild, paint);
		}
		
	}

}
