package de.evonomy.network;


import gameProtocol.PlayerInformation;
import de.evonomy.evolution.CreateSpeciesActivity;
import de.evonomy.evolution.R;
import onlineProtocol.AuthenticationPacket;
import onlineProtocol.CreateGamePacket;
import onlineProtocol.CreateStatusPacket;
import onlineProtocol.InviteResult;
import onlineProtocol.IClient;
import onlineProtocol.LeaveGamePacket;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Looper;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class OnlineActivity extends FragmentActivity implements IClient{
	
	//online Layout
	private Button button_play_online;
	private Button button_logout;
	private Button button_create_Onlinegame;
	private TextView textView_search_opponent;
	private TextView textView_username;
	
	//createGame
	private CreateStatusPacket gameStatus = null;
	
	//network
	private OnlineClient onlineClient;
	private SessionInformation sessionInformation;
	//private boolean online;
	
	//other
	private boolean searchGame;

	//the fragment
	private CreateGameFragment frag;
	
	protected void onCreate(Bundle savedInstanceState) {
		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wait);
		searchGame = false;
		String username = (String) getIntent().getExtras().get("Username");
	
		//connection
		sessionInformation = new SessionInformation(username);
		onlineClient = new OnlineClient(getResources().getInteger(R.integer.OnlinePort), getString(R.string.host), this);
		new Thread(onlineClient).start();
	}
	
	//Online layout--------------------------------------------------------------------------
	private void initComponents() {
		button_play_online = (Button) findViewById(R.id.button_play_online);
		button_logout = (Button) findViewById(R.id.button_logout);
		button_create_Onlinegame = (Button) findViewById(R.id.button_create_Onlinegame);
		textView_search_opponent = (TextView) findViewById(R.id.textView_search_opponent);
		textView_username = (TextView) findViewById(R.id.textViewUsername);
	}
	
	private void initListeners() {
		button_play_online.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (searchGame) {
					textView_search_opponent.setVisibility(View.INVISIBLE);
					button_play_online.setText("Spiel suchen");
				} else {
					button_play_online.setText("Suche abbrechen");
					textView_search_opponent.setVisibility(View.VISIBLE);
				}
				searchGame = !searchGame;
			}
		});
		
		button_logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				logout();
			}
		});
		
		button_create_Onlinegame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onlineClient.sendPacket(new CreateGamePacket(sessionInformation.getSessionId(), "server"));
			}
		});
	}

	@Override
	public void connected() {
		AuthenticationPacket p = new AuthenticationPacket("new client", "server", sessionInformation.getUsername());
		onlineClient.sendPacket(p);
	}

	@Override
	public void sessionOnline(String sessionID) {
		sessionInformation.setSessionId(sessionID);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				setContentView(R.layout.activity_online);
				initComponents();
				initListeners();
				textView_username.setText("Sie sind eingeloggt als " +sessionInformation.getUsername()+".");
			}
		});
	}

	@Override
	public void processCreateGame(CreateStatusPacket p) {
		if(gameStatus==null){
			gameStatus=p;
			openCreateGame();
		} else {
			gameStatus=p;
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					frag.refreshCreateGame(gameStatus);
				}
			});
		}
	}

	private void openCreateGame() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				frag = new CreateGameFragment();
				FragmentManager fm = getSupportFragmentManager();
				frag.show(fm, "CreateGameFragment");
			}
		});
	}

	@Override
	public void showChatMsg(final String msg) {
		if(gameStatus!=null){ //in game lobby?
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					frag.showChatMsg(msg);
				}
			});
		}else{
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
				}
			});
		}
	}

	@Override
	public void showInvite(final String hostname) {
		final OnlineActivity thisAct = this;
		runOnUiThread(new Runnable() {		
			@Override
			public void run() {
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	InviteResult result = new InviteResult(sessionInformation.getSessionId(), "server");
				        switch (which) {
				        case DialogInterface.BUTTON_POSITIVE:
				        	result.setData(hostname, true);
				            break;
				        case DialogInterface.BUTTON_NEGATIVE:
				        	result.setData(hostname, false);
				            break;
				        }
				        onlineClient.sendPacket(result);
				    }
				};
				
				AlertDialog.Builder builder = new AlertDialog.Builder(thisAct);
				
				String message = hostname+" hat Sie zu einem Spiel eingeladen.";
				String accept = "Einladung annehmen";
				String no = "Einladung ablehnen";
				builder.setMessage(message).setPositiveButton(accept, dialogClickListener)
				    .setNegativeButton(no, dialogClickListener).show();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		
		if(gameStatus!=null){
			frag = null;
			gameStatus = null;
		} else {
			logout();
		}
	}
	
	public OnlineClient getClient(){
		return onlineClient;
	}
	
	public SessionInformation getSessionInfo(){
		return sessionInformation;
	}
	
	public CreateStatusPacket getGameStatusPacket(){
		return gameStatus;
	}

	@Override
	public void kickPlayer() {
		frag.willBeKicked = true;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				getSupportFragmentManager().beginTransaction().remove(frag).commit();
				String msg = gameStatus.getHostname()+" hat Sie aus dem Spiel geworfen.";
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
			}
		});
	}
		
	private void logout(){
		if(onlineClient!=null)
			onlineClient.setRunning(false);
		finish();
	}

	@Override
	public void connectionLost() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				String msg = "Verbindung zum Server verloren.";
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
				logout(); //stop network thread and finish
			}
		});
	}

	@Override
	public void startTheGame() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//end the start game popup
				frag.willBeKicked = true;
				getSupportFragmentManager().beginTransaction().remove(frag).commit();
				//start the create species activity
				Intent intent=new Intent(getApplicationContext(),CreateSpeciesActivity.class);
				PlayerInformation info = gameStatus.getPlayerInformation(sessionInformation.getUsername());
				intent.putExtra("info", info);
				startActivity(intent);
			}
		});
	}
}
