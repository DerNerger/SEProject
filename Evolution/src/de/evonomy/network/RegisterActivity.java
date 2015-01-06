package de.evonomy.network;
import java.util.Observable;
import java.util.Observer;

import javax.xml.datatype.Duration;

import loginProtocol.LoginPacket;

import register.RegisterResult;

import de.evonomy.evolution.R;

import de.evonomy.register.RegisterClient;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends Activity implements Observer{
	
	private EditText editText_register_username;
	private EditText editText_register_passWd;
	private EditText editText_register_passWd_confirm;
	private Button button_register;
	private RegisterClient registerConnection;
	private LoginClient loginConnection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		initComponents();
		initListeners();
	}
	
	private void initComponents() {
		editText_register_username = (EditText) findViewById(R.id.editText_register_username);
		editText_register_passWd = (EditText) findViewById(R.id.editText_register_passWd);
		editText_register_passWd_confirm = (EditText) findViewById(R.id.editText_register_passWd_confirm);
		button_register = (Button) findViewById(R.id.Button_register_now);
	}
	
	private void initListeners() {
		final RegisterActivity usedInOnClickListener = this;
		button_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = editText_register_username.getText().toString();
				String passWd = editText_register_passWd.getText().toString();
				String passWdConfirm = editText_register_passWd_confirm.getText().toString();
				if(!passWd.equals(passWdConfirm)){
					Toast.makeText(getApplicationContext(), getString(R.string.pwds_not_equal), Toast.LENGTH_LONG).show();
					return;
				}
				if(username.contains(";") || username.equals("")){
					Toast.makeText(getApplicationContext(), getString(R.string.name_not_allowed), Toast.LENGTH_LONG).show();
					return;
				}
				if(registerConnection==null){
					//connect
					registerConnection = new RegisterClient(getResources().getInteger(R.integer.RegisterPort), getString(R.string.host));
					new Thread(registerConnection).start();
				}
				registerConnection.addObserver(usedInOnClickListener);
				registerConnection.register(username, passWd);
				setContentView(R.layout.activity_wait);
			}
		});
	}

	@Override
	public void update(Observable observable, Object data) {
		if(observable==registerConnection)
			handleRegisterResult(data);
		else
			handleLoginResult(data);
	}
	
	private void handleRegisterResult(Object data){
		final RegisterResult result = (RegisterResult) data;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				String name = editText_register_username.getText().toString();
				String passWd = editText_register_passWd.getText().toString();
				setContentView(R.layout.activity_register);
				initComponents();
				initListeners();
				if(result.isRegisterOk())
					showRegisterOk(result.getMessage(),name,passWd);
				else
					Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
	}
	
	private void handleLoginResult(Object data){
		final LoginPacket result = (LoginPacket) data;
		if(result.getPassWd()==1)
			goOnline(result.getName());
		else
			throw new RuntimeException("Something went REALLY wrong");
	}
	
	private void showRegisterOk(final String message, final String name, final String passWd) {
		final RegisterActivity thisAct = this;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	registerConnection.setRunning(false);
				        switch (which) {
				        case DialogInterface.BUTTON_POSITIVE:
				        	login(name, passWd);
				            break;

				        case DialogInterface.BUTTON_NEGATIVE:
				        	finish();
				            break;
				        }
				    }
				};
				
				AlertDialog.Builder builder = new AlertDialog.Builder(thisAct);
				
				String goOnline = "Zum eingeloggten Bereich";
				String back = "zurück";
				//builder.
				builder.setMessage(message).setPositiveButton(goOnline, dialogClickListener)
				    .setNegativeButton(back, dialogClickListener).show();
			}
		});
	} // end register ok
	
	
	private void login(String name, String passWd) {
		loginConnection = new LoginClient(getResources().getInteger(R.integer.LoginPort), getString(R.string.host));
		new Thread(loginConnection).start();
		loginConnection.login(name, passWd);
		loginConnection.addObserver(this);
		setContentView(R.layout.activity_wait);
	}
	
	private void goOnline(String name){
		Intent intent_online = new Intent(this, OnlineActivity.class);
		intent_online.putExtra("Username", name);
		startActivity(intent_online);
		finish();
	}
}
