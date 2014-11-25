package de.evonomy.evolution;
import de.evonomy.evolution.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentSkillBody extends Fragment {
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View root=inflater.inflate(R.layout.fragment_skill_body, container,false);
		return root;
	}

}
