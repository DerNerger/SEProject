package de.evonomy.evolution;
import de.evonomy.evolution.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class FragmentSkillBody extends Fragment {
	public enum Slots {LEGS,ARMS,HEAD,BODY,NEXTTOHEAD,NEXTTOLEGS};
	private LinearLayout fragmentContainer;
	private LinearLayout lllegs;
	private LinearLayout llarmsl;
	private LinearLayout llarmsr;
	private LinearLayout llntheadl;
	private LinearLayout llntheadr;
	private LinearLayout llntlegsl;
	private LinearLayout llntlegsr;
	private LinearLayout llbody;
	private LinearLayout llhead;
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View root=inflater.inflate(R.layout.fragment_skill_body,
				container,false);
		fragmentContainer=(LinearLayout) root.findViewById(R.id.ll_fragment_skill_body_fragment_container);
		lllegs=(LinearLayout) root.findViewById(R.id.linear_layout_skill_legs);
		llarmsl=(LinearLayout) root.findViewById(R.id.linear_layout_skill_left_body);
		llarmsr=(LinearLayout) root.findViewById(R.id.linear_layout_skill_right_body);
		llbody=(LinearLayout) root.findViewById(R.id.linear_layout_skill_body);
		llhead=(LinearLayout) root.findViewById(R.id.linear_layout_skill_head);
		llntlegsl=(LinearLayout) root.findViewById(R.id.linear_layout_skill_left_legs);
		llntlegsr=(LinearLayout) root.findViewById(R.id.linear_layout_skill_right_legs);
		llntheadl=(LinearLayout) root.findViewById(R.id.linear_layout_skill_left_head);
		llntheadr=(LinearLayout) root.findViewById(R.id.linear_layout_skill_right_head);
		
		return root;
	}
	@Override
	public void onResume(){
		super.onResume();
		//in onResume to measure heights
		getView().post(new Runnable() {
			
			@Override
			public void run() {
				int width=fragmentContainer.getMeasuredWidth();
				int height=fragmentContainer.getMeasuredHeight();
				lllegs.setOnClickListener(new SkillListener(Slots.LEGS,
						width,height));
				llarmsl.setOnClickListener(new SkillListener(Slots.ARMS,
						width,height));
				llarmsr.setOnClickListener(new SkillListener(Slots.ARMS,
						width,height));
				llntheadl.setOnClickListener(new SkillListener(Slots.NEXTTOHEAD,
						width,height));
				llntheadr.setOnClickListener(new SkillListener(Slots.NEXTTOHEAD,
						width,height));
				llntlegsl.setOnClickListener(new SkillListener(Slots.NEXTTOLEGS,
						width,height));
				llntlegsr.setOnClickListener(new SkillListener(Slots.NEXTTOLEGS,
						width,height));
				llbody.setOnClickListener(new SkillListener(Slots.BODY,
						width,height));
				llhead.setOnClickListener(new SkillListener(Slots.HEAD,
						width,height));
				
			}
		});
		
	}
	
	private class SkillListener implements View.OnClickListener {
		private Slots s ;
		private int w;
		private int h;
		public SkillListener(Slots s, int width, int height) {
			this.s=s;
			h=height;
			w=width;
		}
		
		@Override
		public void onClick(View v) {
			SkillTreeFragment frag=SkillTreeFragment
					.newInstance(s, w,h);
			FragmentManager fm=getChildFragmentManager();
			FragmentTransaction trans=fm.beginTransaction();
			trans.replace(fragmentContainer.getId(), frag);
//			frag.show(fm, "fragment_skill_legs");
			trans.commit();
		}
		
	}
}
