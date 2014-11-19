package de.evonomy.evolution;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 
 * @author Niklas Adams
 * This View has the 5 attributes as column diagramm
 * just add <de.evolution.evolution.SpeciesAttributeView> and xmlns:custom="http://schemas.android.com/apk/res/de.evolution.evolution"
 * to your xml and specify the und custom given elements if you want(if not its 0)
 * in your activity you have to call initColumns in onWindowFocusChanged
 * use this methods to change the view directly
 */
public class SpeciesAttributeView extends LinearLayout {
	private static int ANIMTIME=300;
	private int intelligence;
	private int agility;
	private int strength;
	private int social;
	private int recreation;
	private View intelligenceView;
	private View agilityView;
	private View strengthView;
	private View socialView;
	private View recreationView;
	private float mHeight;
	private float onePercentFactor;
	private RelativeLayout rl;
	public SpeciesAttributeView(Context context,int intelligence,int agility,int strength,int social,int recreation) {
		super(context);
		this.intelligence=intelligence;
		this.agility=agility;
		this.recreation=recreation;
		this.social=social;
		this.strength=strength;
		init();
	}
	public SpeciesAttributeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a= context.getTheme().obtainStyledAttributes(attrs, R.styleable.SpeciesAttributeView, 0, 0);
		try{
			intelligence=a.getInteger(R.styleable.SpeciesAttributeView_intelligence, 1);
			agility=a.getInteger(R.styleable.SpeciesAttributeView_agility, 1);
			strength=a.getInteger(R.styleable.SpeciesAttributeView_strength, 1);
			social=a.getInteger(R.styleable.SpeciesAttributeView_social, 1);		
			recreation=a.getInteger(R.styleable.SpeciesAttributeView_recreation, 1);
	
		} finally{
			a.recycle();
		}
		init();
		
	}
	public int getIntelligence() {
		return intelligence;
	}
	private void setIntelligence(int intelligence) {
//		int oldIntelligence=this.intelligence;
		this.intelligence = intelligence;
//		float factor=(float)intelligence/(float)oldIntelligence;
//		
//		intelligenceView.animate().setInterpolator(new DecelerateInterpolator()).setDuration(500).scaleYBy(factor);
		RelativeLayout.LayoutParams params=(android.widget.RelativeLayout.LayoutParams) intelligenceView.getLayoutParams();
		params.height=(int)(intelligence*onePercentFactor);
		intelligenceView.setLayoutParams(params);
		invalidate();
		//requestLayout();

	}
	public int getAgility() {
		return agility;
	}
	private void setAgility(int agility) {
//		int oldAgility=this.agility;
		this.agility = agility;
//		float factor=(float)agility/(float)oldAgility;
//	
//		agilityView.animate().setInterpolator(new DecelerateInterpolator()).setDuration(500).scaleYBy(factor);
		RelativeLayout.LayoutParams params=(android.widget.RelativeLayout.LayoutParams) agilityView.getLayoutParams();
		params.height=(int)(agility*onePercentFactor);
		agilityView.setLayoutParams(params);
		invalidate();
		//requestLayout();

	}
	public int getStrength() {
		return strength;
	}
	private void setStrength(int strength) {
//		int oldStrength=this.strength;
		this.strength = strength;
//		float factor=(float)strength/(float)oldStrength;
//		strengthView.animate().setDuration(500).setInterpolator(new DecelerateInterpolator()).scaleYBy(factor);
		RelativeLayout.LayoutParams params=(android.widget.RelativeLayout.LayoutParams) strengthView.getLayoutParams();
		params.height=(int)(strength*onePercentFactor);
		strengthView.setLayoutParams(params);
		invalidate();
		//requestLayout();

	}
	public int getSocial() {
		return social;
	}
	private void setSocial(int social) {
//		int oldSocial=this.social;
		this.social = social;
//		float factor=(float)social/(float)oldSocial;
//		socialView.animate().setInterpolator(new DecelerateInterpolator()).setDuration(500).scaleYBy(factor);
		RelativeLayout.LayoutParams params=(android.widget.RelativeLayout.LayoutParams) socialView.getLayoutParams();
		params.height=(int)(social*onePercentFactor);
		socialView.setLayoutParams(params);
		invalidate();
		//requestLayout();

	}
	public int getRecreation() {
		return recreation;
	}
	private void setRecreation(int recreation) {
//		int oldRecreation=this.recreation;
		this.recreation = recreation;
//		float factor= (float)recreation/(float)oldRecreation;
//		recreationView.animate().setInterpolator(new DecelerateInterpolator()).setDuration(500).scaleYBy(factor);
		RelativeLayout.LayoutParams params=(android.widget.RelativeLayout.LayoutParams) recreationView.getLayoutParams();
		params.height=(int)(recreation*onePercentFactor);
		recreationView.setLayoutParams(params);
		invalidate();
		//requestLayout();

	}
	private void init(){
		inflate(getContext(),R.layout.species_attribute_view, this);
		intelligenceView=findViewById(R.id.intelligence_species_attribute_view);
		agilityView= findViewById(R.id.agility_species_attribute_view);
		strengthView=findViewById(R.id.strength_species_attribute_view);
		socialView=findViewById(R.id.social_species_attribute_view);
		recreationView=findViewById(R.id.recreation_species_attribute_view);
		
		
		
		
	}
	/**
	 * Only to be called after onCreate!
	 * For this, Overwrite
	 *  public void onWindowFocusChanged(boolean hasFocus) {
	 *      super.onWindowFocusChanged(hasFocus);
	 *      (yourObjectOfThisClass).initColums();
	 *      //your following code
	 *     
	 *	}
	 *  
	 */
	public void initColums(){
		rl=(RelativeLayout) findViewById(R.id.recreation_relative_layout_species_attribute_view);
		mHeight=rl.getHeight();
		//resize the blocks from 1dp to 1%of maximum height for easy resize
		onePercentFactor=(1f/100f*mHeight);
		ObjectAnimator anim=ObjectAnimator.ofInt(this, "intelligence",1, intelligence);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
		anim=ObjectAnimator.ofInt(this, "agility",1, agility);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
		anim=ObjectAnimator.ofInt(this, "strength",1, strength);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
		anim=ObjectAnimator.ofInt(this, "social",1, social);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
		anim=ObjectAnimator.ofInt(this, "recreation",1, recreation);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
		
	}
	public void changeIntelligence(int intelligence){
		ObjectAnimator anim=ObjectAnimator.ofInt(this, "intelligence", intelligence);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
	}
	public void changeAgility(int agility){
		ObjectAnimator anim=ObjectAnimator.ofInt(this, "agility", agility);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
	}
	public void changeStrength(int strength){
		ObjectAnimator anim=ObjectAnimator.ofInt(this, "strength", strength);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
	}
	public void changeSocial(int social){
		ObjectAnimator anim=ObjectAnimator.ofInt(this, "social", social);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
	}
	public void changeRecreation(int recreation){
		ObjectAnimator anim=ObjectAnimator.ofInt(this, "recreation", recreation);
		anim.setDuration(ANIMTIME);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();
	}
	

}
