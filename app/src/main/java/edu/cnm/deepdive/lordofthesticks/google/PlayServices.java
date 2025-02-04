/*
Copyright 2019 Brian Alexander, John Bailey, Austin DeWitt, Trey Page

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package edu.cnm.deepdive.lordofthesticks.google;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesCallbackStatusCodes;
import com.google.android.gms.games.InvitationsClient;
import com.google.android.gms.games.RealTimeMultiplayerClient;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.OnRealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateCallback;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import edu.cnm.deepdive.lordofthesticks.GamePlay;
import edu.cnm.deepdive.lordofthesticks.model.Arena;
import edu.cnm.deepdive.lordofthesticks.model.Stickman;
import edu.cnm.deepdive.lordofthesticks.model.User;
import edu.cnm.deepdive.lordofthesticks.view.MenuScreen;
import edu.cnm.deepdive.lordofthesticks.viewmodel.GameViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/***
 * PlayServices is doing the implementation and connection to Google Play Games. When this class
 * is created it will immediately begin making the connection to Play Games and if the connection
 * is successful it will launch the Google provided waiting room UI. Once the Auto-match criteria
 * is met, a new activity is started. This activity will be the game.
 */
public class PlayServices extends AppCompatActivity {

  /*
   * API INTEGRATION SECTION. This section contains the code that integrates
   * the game with the Google Play game services API.
   */
  private final static String TAG = "LordoftheSticks";

  // Request codes for the UIs that we show with startActivityForResult:
  private final static int RC_WAITING_ROOM = 10002;

  // My participant ID in the currently active game
  private static String mMyParticipantId;

  private static GoogleSignInAccount player = null;

  private RealTimeMultiplayerClient mRealTimeMultiplayerClient = null;

  // Client used to interact with the Invitation system.
  private InvitationsClient mInvitationsClient = null;

  // Room ID where the currently active game is taking place; null if we're
  // not playing.
  private static String mRoomId = null;

  // Holds the configuration of the current room.
  private RoomConfig mJoinedRoomConfig;

  // The participants in the currently active game
  private static ArrayList<Participant> mParticipants = null;

  // are we already playing?
  private boolean mPlaying = false;

  // at least 2 players required for our game
  private final static int MIN_PLAYERS = 2;

  private Room mRoom;

  private GameViewModel gameViewModel;

  private static String playersEmail;

  /**
   * onCreate is going to do the setup for the entire class. It assigns the needed information to
   * gameViewModel, player, and mRealTimeMultiplayerClient. These 3 variables are necessary in a
   * number of places throughout the class. It then calls the method startQuickGame() in order to
   * start the process of creating and joining a game via google play games.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    gameViewModel = ViewModelProviders.of(Objects.requireNonNull(this))
        .get(GameViewModel.class);
    player = GoogleSignInService.getInstance().getAccount();
    playersEmail = GoogleSignInService.getInstance().getAccount().getEmail();
    // Client used to interact with the real time multiplayer system.
    mRealTimeMultiplayerClient = Games
        .getRealTimeMultiplayerClient(this, player);

    startQuickGame();
  }

  private void startQuickGame() {
    // auto-match criteria to invite one random automatch opponent.
    // You can also specify more opponents (up to 3).
    Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(1, 7, 0);

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

  // returns whether there are enough players to start the game
  private boolean shouldStartGame(Room room) {
    int connectedPlayers = 0;
    for (Participant p : room.getParticipants()) {
      if (p.isConnectedToRoom()) {
        ++connectedPlayers;
      }
    }
    return connectedPlayers >= MIN_PLAYERS;
  }

  // Returns whether the room is in a state where the game should be canceled.
  private boolean shouldCancelGame(Room room) {
    // TODO: Your game-specific cancellation logic here. For example, you might decide to
    // cancel the game if enough people have declined the invitation or left the room.
    // You can check a participant's status with Participant.getStatus().
    // (Also, your UI should have a Cancel button that cancels the game too)
    return false;
  }

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
            .leave(mJoinedRoomConfig, mRoomId);
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
            .leave(mJoinedRoomConfig, mRoomId);
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
          .leave(mJoinedRoomConfig, mRoomId);
      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      // show error message and return to main screen
      mRoom = null;
      mJoinedRoomConfig = null;
      goToAnotherScreen(MenuScreen.class);
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
            .leave(mJoinedRoomConfig, mRoomId);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        goToAnotherScreen(MenuScreen.class);
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
        Log.d(TAG, "Room " + mRoomId + " created.");
        mRoomId = room.getRoomId();
        informationDrop();
        showWaitingRoom(room);
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
        Log.d(TAG, "Room " + mRoomId + " joined.");
      } else {
        Log.w(TAG, "Error joining room: " + code);
        // let screen go to sleep
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

      }
    }

    @Override
    public void onLeftRoom(int code, @NonNull String roomId) {
      goToAnotherScreen(MenuScreen.class);
      Log.d(TAG, "Left room" + roomId);
    }

    @Override
    public void onRoomConnected(int code, @Nullable Room room) {
      if (code == GamesCallbackStatusCodes.OK && room != null) {
        Log.d(TAG, "Room " + mRoomId + " connected.");
        mParticipants = room.getParticipants();
        gameViewModel.postToArena();
      } else {
        Log.w(TAG, "Error connecting to room: " + code);
        // let screen go to sleep
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

      }
    }
  };

  private void showWaitingRoom(Room room) {
    Games.getRealTimeMultiplayerClient(this, player)
        .getWaitingRoomIntent(room, 3)
        .addOnSuccessListener(new OnSuccessListener<Intent>() {
          @Override
          public void onSuccess(Intent intent) {
            startActivityForResult(intent, RC_WAITING_ROOM);
          }
        });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == RC_WAITING_ROOM) {

      if (resultCode == Activity.RESULT_OK) {
        goToAnotherScreen(GamePlay.class);
        Thread background = new Thread() {
          public void run() {
            try {
              // Thread will sleep for 5 seconds
              sleep(30000 * 1000);
              // After 5 seconds redirect to another intent
              Intent intent = new Intent(getBaseContext(), MenuScreen.class);
              startActivity(intent);
              //Switch activity
              finish();
            } catch (Exception e) {
              // Do nothing?? maybe
            }
          }
        };
        // start thread
        background.start();
      } else if (resultCode == Activity.RESULT_CANCELED
          || resultCode == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
        // Waiting room was dismissed with the back button. The meaning of this
        // action is up to the game. You may choose to leave the room and cancel the
        // match, or do something else like minimize the waiting room and
        // continue to connect in the background.

        // This just leaves the room:
        Games.getRealTimeMultiplayerClient(this, player)
            .leave(mJoinedRoomConfig, mRoomId);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        goToAnotherScreen(MenuScreen.class);
      }
    }
  }

  private void goToAnotherScreen(Class desire) {
    Intent intent = new Intent(getApplicationContext(), desire);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  /***
   * informationDump is setting the user and room information to a hashmap that can be sent to
   * firebase. It is called in this class when the room is created to set the info and then
   * it is called in the GameViewModel to send the info.
   * @return is a Hashmap for firebase purposes.
   */
  public static HashMap informationDrop() {
    HashMap hashMap = new HashMap();
    Arena arena = new Arena();
    Stickman stickman = new Stickman();
    User user = new User();
    arena.setId(mRoomId);
    stickman.setName(mMyParticipantId);
    user.setName(playersEmail);
    hashMap.put("arena", arena);
    hashMap.put("stickman", stickman);
    hashMap.put("user", user);
    return hashMap;
  }

  /**
   * roomInfo displays the information for the current room and the amount of participants.
   */
  public static String roomInfo() {
    if (mParticipants == null) {
      return "";
    } else {
      return String.format("The last room ID was: %s\nThe amount of players was: %o", mRoomId,
          mParticipants.size());
    }
  }
}