package de.evonomy.evolution;
import de.evonomy.evolution.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class FragmentSkillBody extends Fragment {
	public enum Slots {LEGS};
	private LinearLayout lllegs;
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View root=inflater.inflate(R.layout.fragment_skill_body,
				container,false);
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
								.newInstance(Slots.LEGS, 1000, 1000);
						FragmentManager fm=getActivity().getSupportFragmentManager();
						frag.show(fm, "fragment_skill_legs");
						
					}
				});
				
			}
		});
		
	}
}
