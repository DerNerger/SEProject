package de.evonomy.evolution;

import de.evonomy.evolution.FragmentSkillBody.Slots;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class SkillAbilityFragment extends Fragment implements SlotSkillable {

	private LinearLayout fragmentContainer;
	private SlotSkillable toOvergive;
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View root=inflater.inflate(R.layout.fragment_skill_ability,
				container,false);
		fragmentContainer=(LinearLayout) root.findViewById(R.id.linear_layout_skill_ability);
		toOvergive=this;
		return root;
	}
	@Override
	public void onResume(){
		super.onResume();
		getView().post(new Runnable() {
			
			@Override
			public void run() {
				SkillTreeFragment frag=SkillTreeFragment
						.newInstance(Slots.ABILITY, fragmentContainer.getMeasuredWidth()
								,fragmentContainer.getMeasuredHeight(),toOvergive);
				FragmentManager fm=getChildFragmentManager();
				FragmentTransaction trans=fm.beginTransaction();
				trans.replace(fragmentContainer.getId(), frag,"tree_fragment");
				trans.commit();
				
			}
		});
		
	}
	@Override
	public void notifiyToDrawNew(Slots slot) {
		SkillTreeFragment frag=SkillTreeFragment
				.newInstance(slot, fragmentContainer.getMeasuredWidth()
						,fragmentContainer.getMeasuredHeight(),toOvergive);
		FragmentManager fm=getChildFragmentManager();
		FragmentTransaction trans=fm.beginTransaction();
		trans.replace(fragmentContainer.getId(), frag,"tree_fragment");
//		frag.show(fm, "fragment_skill_legs");
		trans.commit();
		
	}

}
