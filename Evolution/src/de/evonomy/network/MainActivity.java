package de.evonomy.network;
import de.evonomy.evolution.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity{
	
	private Button button_go_online;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initComponents();
		initListeners();
	}
	
	private void initComponents() {
		button_go_online = (Button) findViewById(R.id.button_go_online);
	}
	
	private void initListeners() {
		final MainActivity usedInOnClickListener = this;
		button_go_online.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent_login = new Intent(usedInOnClickListener, LoginActivity.class);
				startActivity(intent_login);
			}
		});
	}
}
