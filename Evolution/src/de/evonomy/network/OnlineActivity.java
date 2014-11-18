package de.evonomy.network;


import de.evonomy.evolution.R;
import onlineProtocol.AuthenticationPacket;
import onlineProtocol.ChatMessageToServer;
import onlineProtocol.CreateGamePacket;
import onlineProtocol.CreateStatusPacket;
import onlineProtocol.ImReadyPacket;
import onlineProtocol.InviteResult;
import onlineProtocol.CreateStatusPacket.OpponentType;
import onlineProtocol.IClient;
import onlineProtocol.InvitePacketToServer;
import onlineProtocol.SlotTypeChange;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OnlineActivity extends Activity implements IClient{
	
	//online Layout
	private Button button_play_online;
	private Button button_logout;
	private Button button_create_Onlinegame;
	private TextView textView_search_opponent;
	private TextView textView_username;
	
	//createLayout
	private EditText editTextInvitePlayer;
	private Button buttonInvitePlayer;
	private Button buttonStartGameWithFriends;
	private TextView[] textViewPlayerI;
	private CheckBox[] checkBoxPlayerIRdy;
	private Spinner[] spinnerPlayerI;
	private TextView textViewChat;
	private EditText editTextChatMsg;
	private Button buttonChat;
	
	//createGame
	private CreateStatusPacket gameStatus = null;
	
	//network
	private OnlineClient onlineClient;
	private SessionInformation sessionInformation;
	//private boolean online;
	
	//other
	private boolean searchGame;

	
	protected void onCreate(Bundle savedInstanceState) {
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
				finish();
			}
		});
		
		button_create_Onlinegame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onlineClient.sendPacket(new CreateGamePacket(sessionInformation.getSessionId(), "server"));
			}
		});
	}
	
	//Create layout--------------------------------------------------------------------------
	private void initCreateComponents(){
		//init arrays
		spinnerPlayerI = new Spinner[3];
		textViewPlayerI = new TextView[4];
		checkBoxPlayerIRdy = new CheckBox[3];
		
		//init spinner
		spinnerPlayerI[0] = (Spinner) findViewById(R.id.spinnerPlayer2);
		spinnerPlayerI[1] = (Spinner) findViewById(R.id.spinnerPlayer3);
		spinnerPlayerI[2] = (Spinner) findViewById(R.id.spinnerPlayer4);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.PlayerType, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPlayerI[0].setEnabled(false);
		spinnerPlayerI[1].setEnabled(false);
		spinnerPlayerI[2].setEnabled(false);
		spinnerPlayerI[0].setAdapter(adapter);
		spinnerPlayerI[1].setAdapter(adapter);
		spinnerPlayerI[2].setAdapter(adapter);
		
		//init textviews
		textViewPlayerI[0] = (TextView) findViewById(R.id.textViewPlayer1);
		textViewPlayerI[1] = (TextView) findViewById(R.id.textViewPlayer2);
		textViewPlayerI[2] = (TextView) findViewById(R.id.textViewPlayer3);
		textViewPlayerI[3] = (TextView) findViewById(R.id.textViewPlayer4);
		
		//initCheckBoxes
		checkBoxPlayerIRdy[0] = (CheckBox) findViewById(R.id.checkBoxPlayer2Rdy);
		checkBoxPlayerIRdy[1] = (CheckBox) findViewById(R.id.checkBoxPlayer3Rdy);
		checkBoxPlayerIRdy[2] = (CheckBox) findViewById(R.id.checkBoxPlayer4Rdy);
		
		//invite friends
		editTextInvitePlayer = (EditText) findViewById(R.id.editTextInvitePlayer);
		buttonInvitePlayer = (Button) findViewById(R.id.buttonInvitePlayer);
		
		//start game
		buttonStartGameWithFriends = (Button) findViewById(R.id.buttonStartGameWithFriends);
		
		//chat
		textViewChat = (TextView) findViewById(R.id.textViewChat);
		editTextChatMsg = (EditText) findViewById(R.id.editTextChatMsg);
		buttonChat =(Button) findViewById(R.id.buttonChat);
	}
	
	private void initCreateListeners(final int playerNumber){
		//invite player
		buttonInvitePlayer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = editTextInvitePlayer.getText().toString();
				String username = sessionInformation.getUsername();
				editTextInvitePlayer.setText("");
				if(gameStatus.isPlayerInGame(name)){
					Toast.makeText(getApplicationContext(), 
							name+" ist bereits im Spiel.", Toast.LENGTH_SHORT).show();
					return;
				}
				InvitePacketToServer p = new InvitePacketToServer(username, "server", name);
				onlineClient.sendPacket(p);
			}
		});
		
		//button chat
		buttonChat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String msg = editTextChatMsg.getText().toString();
				msg = sessionInformation.getUsername()+": "+msg; //add name
				ChatMessageToServer p = new ChatMessageToServer(sessionInformation.getSessionId(), "server");
				p.setInformations(gameStatus.getHostname(), msg);
				editTextChatMsg.setText("");
				onlineClient.sendPacket(p);
			}
		});
		
		if(playerNumber==1){
			//init spinner listener
			spinnerPlayerI[0].setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					SlotTypeChange s = new SlotTypeChange(sessionInformation.getSessionId(), "server");
					int selectedIndex = spinnerPlayerI[0].getSelectedItemPosition();
					s.setInformations(0, OpponentType.values()[selectedIndex]);
					onlineClient.sendPacket(s);
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {return;}
			});
			spinnerPlayerI[1].setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					SlotTypeChange s = new SlotTypeChange(sessionInformation.getSessionId(), "server");
					int selectedIndex = spinnerPlayerI[1].getSelectedItemPosition();
					s.setInformations(1, OpponentType.values()[selectedIndex]);
					onlineClient.sendPacket(s);
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {return;}
			});
			
			spinnerPlayerI[2].setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					SlotTypeChange s = new SlotTypeChange(sessionInformation.getSessionId(), "server");
					int selectedIndex = spinnerPlayerI[2].getSelectedItemPosition();
					s.setInformations(2, OpponentType.values()[selectedIndex]);
					onlineClient.sendPacket(s);
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {return;}
			});//end init spinners
			
		} else{
			checkBoxPlayerIRdy[playerNumber-2].setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					String hostname = gameStatus.getHostname();
					boolean ready = checkBoxPlayerIRdy[playerNumber-2].isChecked();
					ImReadyPacket p = new ImReadyPacket(sessionInformation.getSessionId(), "server", hostname, ready);
					onlineClient.sendPacket(p);
				}
			});
		}
	}

	//the interface IClient------------------------------------------------------------
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
					refreshCreateGame();
				}
			});
		}
	}

	private void openCreateGame() {
		final int playerNumber = gameStatus.getPlayerNumber(sessionInformation.getUsername());
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				setContentView(R.layout.activity_online_create_game);
				initCreateComponents();
				initCreateListeners(gameStatus.getPlayerNumber(sessionInformation.getUsername()));
				if(playerNumber==1){
					for (int i = 0; i < spinnerPlayerI.length; i++) {
						spinnerPlayerI[i].setEnabled(true);
					}
				}
				else
					checkBoxPlayerIRdy[playerNumber-2].setEnabled(true);
				
				refreshCreateGame();
			}
		});
	}
	
	//needs to call on UI thread!!!!!
	private void refreshCreateGame(){
		//get values
		String names[] = gameStatus.getNames();
		boolean[] rdy = gameStatus.getReady();
		OpponentType[] types = gameStatus.getTypes();
		
		//set names
		textViewPlayerI[0].setText(gameStatus.getHostname());
		for (int i = 1; i < textViewPlayerI.length; i++) {
			if(!names[i-1].equals("null"))
				textViewPlayerI[i].setText(names[i-1]);
			else
				textViewPlayerI[i].setText(types[i-1].toString());
		}
		
		//setReady
		for (int i = 0; i < checkBoxPlayerIRdy.length; i++) {
			checkBoxPlayerIRdy[i].setChecked(rdy[i]);
		}
		
		//setOpponent
		for (int i = 0; i < spinnerPlayerI.length; i++) {
			spinnerPlayerI[i].setSelection(types[i].ordinal());
		}
		
		//can the hoststart the game?
		if(gameStatus.getPlayerNumber(sessionInformation.getUsername())==1 && gameStatus.allReady())
			buttonStartGameWithFriends.setEnabled(true);
		else
			buttonStartGameWithFriends.setEnabled(false);
	}

	@Override
	public void showChatMsg(final String msg) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(gameStatus!=null){ //in game lobby?
					String oldText = textViewChat.getText().toString();
					textViewChat.setText(oldText+"\n"+msg);
				}else{
					Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
				}
			}
		});
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
			setContentView(R.layout.activity_online);
			initComponents();
			initListeners();
		} else {
		    moveTaskToBack(true);
		}
	}

}
