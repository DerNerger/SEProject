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
	public enum Slots {LEGS};
	private LinearLayout fragmentContainer;
	
	
	private LinearLayout lllegs;
	
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View root=inflater.inflate(R.layout.fragment_skill_body,
				container,false);
		fragmentContainer=(LinearLayout) root.findViewById(R.id.ll_fragment_skill_body_fragment_container);
		lllegs=(LinearLayout) root.findViewById(R.id.linear_layout_skill_legs);
		
		return root;
	}
	@Override
	public void onResume(){
		super.onResume();
		//in onResume to measure heights
		getView().post(new Runnable() {
			
			@Override
			public void run() {
				lllegs.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						SkillTreeFragment frag=SkillTreeFragment
								.newInstance(Slots.LEGS, fragmentContainer.getMeasuredWidth(), fragmentContainer.getMeasuredHeight());
						FragmentManager fm=getChildFragmentManager();
						FragmentTransaction trans=fm.beginTransaction();
						trans.replace(fragmentContainer.getId(), frag);
//						frag.show(fm, "fragment_skill_legs");
						trans.commit();
						
					}
				});
				
			}
		});
		
	}
}
