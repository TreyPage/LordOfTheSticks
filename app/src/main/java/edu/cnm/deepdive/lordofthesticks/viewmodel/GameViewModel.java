package edu.cnm.deepdive.lordofthesticks.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import edu.cnm.deepdive.lordofthesticks.database.Firebase;
import edu.cnm.deepdive.lordofthesticks.google.PlayServices;
import edu.cnm.deepdive.lordofthesticks.model.Arena;
import edu.cnm.deepdive.lordofthesticks.model.Item;
import edu.cnm.deepdive.lordofthesticks.model.Stickman;
import edu.cnm.deepdive.lordofthesticks.model.User;
import java.util.HashMap;

public class GameViewModel extends AndroidViewModel {

  private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
  private MutableLiveData<DocumentSnapshot> snapshot = new MutableLiveData<>();
  private DocumentReference documentReference = null;
  private HashMap<String, Object> docData = new HashMap<>();
  public String participantId;
  public GoogleSignInAccount player;
  public String roomId;

  private Item bow = new Item(20, 20, false, mDatabase.collection("itemTypes").document("Bow"));
  private Arena stickland = new Arena("Stickland", 1, 0, 1000, 0, 1000, 9000);

  public GameViewModel(@NonNull Application application) {
    super(application);
    PlayServices services = new PlayServices();
    participantId = services.getmMyParticipantId();
    player = services.getPlayer();
    roomId = services.getmRoomId();
  }

  public LiveData<DocumentSnapshot> getSnapshot() {
    return snapshot;
  }

  public void getArena() {
    documentReference = mDatabase.collection("arenas").document();
    new Firebase(documentReference, (documentSnapshot, e) ->
        snapshot.postValue(documentSnapshot)
    );
  }

  private HashMap setGame() {
    docData.put("Stickman", new Stickman());
    docData.put("Item1", bow);
    docData.put("Arena", stickland);
    docData.put("User", new User());
    docData.put("Player", player);
    docData.put("Room ID", roomId);
    docData.put("Player ID", participantId);
    return docData;
  }

  public void postToArena() {
    mDatabase.collection("arenas").document().set(setGame());
  }


}
