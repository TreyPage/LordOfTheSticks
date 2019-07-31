package edu.cnm.deepdive.lordofthesticks.google;

import android.accounts.Account;
//import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.util.Log;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesCallbackStatusCodes;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.GamesClientStatusCodes;
import com.google.android.gms.games.InvitationsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.games.RealTimeMultiplayerClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.OnRealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateCallback;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import edu.cnm.deepdive.lordofthesticks.MenuScreen;
import edu.cnm.deepdive.lordofthesticks.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayServices extends AppCompatActivity {
  /*
   * API INTEGRATION SECTION. This section contains the code that integrates
   * the game with the Google Play game services API.
   */

  final static String TAG = "LordoftheSticks";

  // Request codes for the UIs that we show with startActivityForResult:
  final static int RC_SELECT_PLAYERS = 10000;
  final static int RC_INVITATION_INBOX = 10001;
  final static int RC_WAITING_ROOM = 10002;

  // Request code used to invoke sign in user interactions.
  private static final int RC_SIGN_IN = 9001;

  String mMyParticipantId;

  private GoogleSignInAccount player = null;

  private RealTimeMultiplayerClient mRealTimeMultiplayerClient = null;

  // Client used to interact with the Invitation system.
  private InvitationsClient mInvitationsClient = null;

  // Room ID where the currently active game is taking place; null if we're
  // not playing.
  String mRoomId = null;

  // Holds the configuration of the current room.
  RoomConfig mRoomConfig;

  // Are we playing in multiplayer mode?
  boolean mMultiplayer = false;

  RoomConfig mJoinedRoomConfig;
  // The participants in the currently active game
  ArrayList<Participant> mParticipants = null;

  // My participant ID in the currently active game
  String mMyId = null;

  // If non-null, this is the id of the invitation we received via the
  // invitation listener
  String mIncomingInvitationId = null;

  // Message buffer for sending messages
  byte[] mMsgBuf = new byte[2];

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_screen);

    player = GoogleSignInService.getInstance().getAccount();

    // Client used to interact with the real time multiplayer system.
    mRealTimeMultiplayerClient = Games
        .getRealTimeMultiplayerClient(this, player);

    //TODO switch to correct screen
    startQuickGame();
  }

  private void startQuickGame() {
    // auto-match criteria to invite one random automatch opponent.
    // You can also specify more opponents (up to 3).
    Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(1, 1, 0);

    // build the room config:
    RoomConfig roomConfig =
        RoomConfig.builder(mRoomUpdateCallback)
            .setOnMessageReceivedListener(mMessageReceivedHandler)
            .setRoomStatusUpdateCallback(mRoomStatusCallbackHandler)
            .setAutoMatchCriteria(autoMatchCriteria)
            .build();

    // prevent screen from sleeping during handshake
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    // Save the roomConfig so we can use it if we call leave().
    mJoinedRoomConfig = roomConfig;

    // create room:
    mRealTimeMultiplayerClient.create(roomConfig);
  }

  // are we already playing?
  boolean mPlaying = false;

  // at least 2 players required for our game
  final static int MIN_PLAYERS = 2;

  // returns whether there are enough players to start the game
  boolean shouldStartGame(Room room) {
    int connectedPlayers = 0;
    for (Participant p : room.getParticipants()) {
      if (p.isConnectedToRoom()) {
        ++connectedPlayers;
      }
    }
    return connectedPlayers >= MIN_PLAYERS;
  }

  // Returns whether the room is in a state where the game should be canceled.
  boolean shouldCancelGame(Room room) {
    // TODO: Your game-specific cancellation logic here. For example, you might decide to
    // cancel the game if enough people have declined the invitation or left the room.
    // You can check a participant's status with Participant.getStatus().
    // (Also, your UI should have a Cancel button that cancels the game too)
    return false;
  }

  private Room mRoom;
  private RoomStatusUpdateCallback mRoomStatusCallbackHandler = new RoomStatusUpdateCallback() {
    @Override
    public void onRoomConnecting(@Nullable Room room) {
      // Update the UI status since we are in the process of connecting to a specific room.
    }

    @Override
    public void onRoomAutoMatching(@Nullable Room room) {
      // Update the UI status since we are in the process of matching other players.
    }

    @Override
    public void onPeerInvitedToRoom(@Nullable Room room, @NonNull List<String> list) {
      // Update the UI status since we are in the process of matching other players.
    }

    @Override
    public void onPeerDeclined(@Nullable Room room, @NonNull List<String> list) {
      // Peer declined invitation, see if game should be canceled
      if (!mPlaying && shouldCancelGame(room)) {
        Games.getRealTimeMultiplayerClient(PlayServices.this, player)
            .leave(mJoinedRoomConfig, room.getRoomId());
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      }
    }

    @Override
    public void onPeerJoined(@Nullable Room room, @NonNull List<String> list) {
      // Update UI status indicating new players have joined!
    }

    @Override
    public void onPeerLeft(@Nullable Room room, @NonNull List<String> list) {
      // Peer left, see if game should be canceled.
      if (!mPlaying && shouldCancelGame(room)) {
        Games.getRealTimeMultiplayerClient(PlayServices.this, player)
            .leave(mJoinedRoomConfig, room.getRoomId());
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      }
    }

    @Override
    public void onConnectedToRoom(@Nullable Room room) {
      // Connected to room, record the room Id.
      mRoom = room;
      Games.getPlayersClient(PlayServices.this, player)
          .getCurrentPlayerId().addOnSuccessListener(new OnSuccessListener<String>() {
        @Override
        public void onSuccess(String playerId) {
          mMyParticipantId = mRoom.getParticipantId(playerId);
        }
      });
    }

    @Override
    public void onDisconnectedFromRoom(@Nullable Room room) {
      // This usually happens due to a network error, leave the game.
      Games.getRealTimeMultiplayerClient(PlayServices.this, player)
          .leave(mJoinedRoomConfig, room.getRoomId());
      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      // show error message and return to main screen
      mRoom = null;
      mJoinedRoomConfig = null;
    }

    @Override
    public void onPeersConnected(@Nullable Room room, @NonNull List<String> list) {
      if (mPlaying) {
        // add new player to an ongoing game
      } else if (shouldStartGame(room)) {
        // start game!
      }
    }

    @Override
    public void onPeersDisconnected(@Nullable Room room, @NonNull List<String> list) {
      if (mPlaying) {
        // do game-specific handling of this -- remove player's avatar
        // from the screen, etc. If not enough players are left for
        // the game to go on, end the game and leave the room.
      } else if (shouldCancelGame(room)) {
        // cancel the game
        Games.getRealTimeMultiplayerClient(PlayServices.this, player)
            .leave(mJoinedRoomConfig, room.getRoomId());
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      }
    }

    @Override
    public void onP2PConnected(@NonNull String participantId) {
      // Update status due to new peer to peer connection.
    }

    @Override
    public void onP2PDisconnected(@NonNull String participantId) {
      // Update status due to  peer to peer connection being disconnected.
    }
  };

  private OnRealTimeMessageReceivedListener mMessageReceivedHandler =
      new OnRealTimeMessageReceivedListener() {
        @Override
        public void onRealTimeMessageReceived(@NonNull RealTimeMessage realTimeMessage) {
          // Handle messages received here.
          byte[] message = realTimeMessage.getMessageData();
          // process message contents...
        }
      };

  private RoomUpdateCallback mRoomUpdateCallback = new RoomUpdateCallback() {
    @Override
    public void onRoomCreated(int code, @Nullable Room room) {
      // Update UI and internal state based on room updates.
      if (code == GamesCallbackStatusCodes.OK && room != null) {
        Log.d(TAG, "Room " + room.getRoomId() + " created.");
      } else {
        Log.w(TAG, "Error creating room: " + code);
        // let screen go to sleep
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

      }
    }

    @Override
    public void onJoinedRoom(int code, @Nullable Room room) {
      // Update UI and internal state based on room updates.
      if (code == GamesCallbackStatusCodes.OK && room != null) {
        Log.d(TAG, "Room " + room.getRoomId() + " joined.");
      } else {
        Log.w(TAG, "Error joining room: " + code);
        // let screen go to sleep
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

      }
    }

    @Override
    public void onLeftRoom(int code, @NonNull String roomId) {
      Log.d(TAG, "Left room" + roomId);
    }

    @Override
    public void onRoomConnected(int code, @Nullable Room room) {
      if (code == GamesCallbackStatusCodes.OK && room != null) {
        Log.d(TAG, "Room " + room.getRoomId() + " connected.");
      } else {
        Log.w(TAG, "Error connecting to room: " + code);
        // let screen go to sleep
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

      }
    }
  };
}