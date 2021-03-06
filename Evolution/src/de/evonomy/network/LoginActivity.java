package de.evonomy.network;

import java.util.Observable;
import java.util.Observer;

import simpleNet.Packet;
import simpleNet.PacketType;

import loginProtocol.LoginPacket;

import de.evonomy.evolution.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements Observer{
	
	//android GUI
		private EditText editText_LoginName;
		private EditText editText_PassWd;
		private Button button_Login;
		private Button button_register;
		private CheckBox checkBoxSaveName;
		
		//network communication
		private LoginClient client;
		
		protected void onCreate(Bundle savedInstanceState) {
			//Remove title bar
		    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		    //Remove notification bar
		    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_login);
			
			initComponents();
			initListeners();
		}
		
		private void initComponents() {
			editText_LoginName = (EditText) findViewById(R.id.editText_LoginName);
			editText_PassWd = (EditText) findViewById(R.id.editText_PassWd);
			button_Login = (Button) findViewById(R.id.button_Login);
			button_register  = (Button) findViewById(R.id.button_register);
			checkBoxSaveName = (CheckBox) findViewById(R.id.checkBoxSaveUsername);
			
			//readLoginName
		    SharedPreferences settings = getSharedPreferences("LOGINNAME", 0);
		    String name = settings.getString("LOGINNAME", null);
		    String pw = settings.getString("LOGINPW", null);
		    if(name!=null){
		    	editText_LoginName.setText(name);
		    	editText_PassWd.setText(pw);
		    	checkBoxSaveName.setChecked(true);
		    }
		}
		
		private void initListeners() {
			final LoginActivity usedInOnClickListener = this;
			button_Login.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String name = editText_LoginName.getText().toString();
					String passWd = editText_PassWd.getText().toString();
					if(name.contains(";") || passWd.contains(";") || name.equals("") || passWd.equals("")){
						Toast.makeText(getApplicationContext(), getString(R.string.name_not_allowed), Toast.LENGTH_LONG).show();
						return;
					}
					if(checkBoxSaveName.isChecked()){
						SharedPreferences settings = getSharedPreferences("LOGINNAME", 0);
					    SharedPreferences.Editor editor = settings.edit();
					    editor.putString("LOGINNAME", name);
					    editor.putString("LOGINPW", passWd);
					    editor.commit();
					}
					setContentView(R.layout.activity_wait);
					client = new LoginClient(getResources().getInteger(R.integer.LoginPort), getString(R.string.host));
					client.addObserver(usedInOnClickListener);
					new Thread(client).start();
					client.login(name, passWd);
				}
			});
			
			button_register.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent_register = new Intent(usedInOnClickListener, RegisterActivity.class);
					startActivity(intent_register);
				}
			});
		}

		@Override
		public void update(Observable observable, Object data) {
			Packet pack = (Packet)data;
			if(pack.getType()==PacketType.FailPacket){
				setContentView(R.layout.activity_login);
				initComponents();
				initListeners();
				Toast.makeText(getApplicationContext(), getString(R.string.noConnection), Toast.LENGTH_LONG).show();
				return;
			}
			LoginPacket p = (LoginPacket) data;
			if(p.getPassWd()==1){
				goOnline(p.getName());
			} else {
				handleLoginWrong(p.getName());
			}
		}
		
		private void goOnline(String name){
			final LoginActivity usedInOnClickListener = this;
			client.deleteObserver(this);
			//client.setRun(false);  TODO:warum ist der client hier bereits disconnected??
			
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					setContentView(R.layout.activity_login);
					initComponents();
					initListeners();
				}
			});
			// login here
			Intent intent_online = new Intent(usedInOnClickListener, OnlineActivity.class);
			intent_online.putExtra("Username", name);
			startActivity(intent_online);
		}
		
		private void handleLoginWrong(final String message){
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					setContentView(R.layout.activity_login);
					initComponents();
					initListeners();
					Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
				}
			});
		}
}
