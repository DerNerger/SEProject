package de.evonomy.network;


import onlineProtocol.ChatMessageToServer;
import onlineProtocol.CreateStatusPacket;
import onlineProtocol.ImReadyPacket;
import onlineProtocol.InvitePacketToServer;
import onlineProtocol.SlotTypeChange;
import onlineProtocol.CreateStatusPacket.OpponentType;
import de.evonomy.evolution.R;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CreateGameFragment extends DialogFragment {
	
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
	
	private View root;
	
	//activity
	private OnlineActivity activity;
	
	//createGame
	private CreateStatusPacket gameStatus = null;
	
	//network
	private OnlineClient onlineClient;
	private SessionInformation sessionInformation;

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		
		//inflate the layout for fragment
		root=inflater.inflate(R.layout.activity_online_create_game, container,false);

		//Remove status bar
		if(Build.VERSION.SDK_INT<16){
			getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		else
			root.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		 //remove title
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		//init the activity shit
		activity = ((OnlineActivity) getActivity());
		gameStatus = activity.getGameStatusPacket();
		onlineClient = activity.getClient();
		sessionInformation = activity.getSessionInfo();
		
		initCreateComponents();
		int playerNumber = gameStatus.getPlayerNumber(sessionInformation.getUsername());
		initCreateListeners(playerNumber);
		
		if(playerNumber==1){
			for (int i = 0; i < spinnerPlayerI.length; i++) {
				spinnerPlayerI[i].setEnabled(true);
			}
		}
		else
			checkBoxPlayerIRdy[playerNumber-2].setEnabled(true);
		
		refreshCreateGame();
		return root;
	}
	
	//Create layout--------------------------------------------------------------------------
	private void initCreateComponents(){
		//init arrays
		spinnerPlayerI = new Spinner[3];
		textViewPlayerI = new TextView[4];
		checkBoxPlayerIRdy = new CheckBox[3];
		
		//init spinner
		spinnerPlayerI[0] = (Spinner) root.findViewById(R.id.spinnerPlayer2);
		spinnerPlayerI[1] = (Spinner) root.findViewById(R.id.spinnerPlayer3);
		spinnerPlayerI[2] = (Spinner) root.findViewById(R.id.spinnerPlayer4);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.PlayerType, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPlayerI[0].setEnabled(false);
		spinnerPlayerI[1].setEnabled(false);
		spinnerPlayerI[2].setEnabled(false);
		spinnerPlayerI[0].setAdapter(adapter);
		spinnerPlayerI[1].setAdapter(adapter);
		spinnerPlayerI[2].setAdapter(adapter);
		
		//init textviews
		textViewPlayerI[0] = (TextView) root.findViewById(R.id.textViewPlayer1);
		textViewPlayerI[1] = (TextView) root.findViewById(R.id.textViewPlayer2);
		textViewPlayerI[2] = (TextView) root.findViewById(R.id.textViewPlayer3);
		textViewPlayerI[3] = (TextView) root.findViewById(R.id.textViewPlayer4);
		
		//initCheckBoxes
		checkBoxPlayerIRdy[0] = (CheckBox) root.findViewById(R.id.checkBoxPlayer2Rdy);
		checkBoxPlayerIRdy[1] = (CheckBox) root.findViewById(R.id.checkBoxPlayer3Rdy);
		checkBoxPlayerIRdy[2] = (CheckBox) root.findViewById(R.id.checkBoxPlayer4Rdy);
		
		//invite friends
		editTextInvitePlayer = (EditText) root.findViewById(R.id.editTextInvitePlayer);
		buttonInvitePlayer = (Button) root.findViewById(R.id.buttonInvitePlayer);
		
		//start game
		buttonStartGameWithFriends = (Button) root.findViewById(R.id.buttonStartGameWithFriends);
		
		//chat
		textViewChat = (TextView) root.findViewById(R.id.textViewChat);
		editTextChatMsg = (EditText) root.findViewById(R.id.editTextChatMsg);
		buttonChat =(Button) root.findViewById(R.id.buttonChat);
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
					Toast.makeText(activity.getApplicationContext(), 
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
	
	//needs to call on UI thread!!!!!
	public void refreshCreateGame(){
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
	
	public void showChatMsg(final String msg){
		if(gameStatus!=null){ //in game lobby?
			String oldText = textViewChat.getText().toString();
			textViewChat.setText(oldText+"\n"+msg);
		}else{
			Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
		}
	}
		
	public void onStart() {
		  super.onStart();
		  
		// safety check
		if (getDialog() == null) {
			return;
		}

		getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}
}
