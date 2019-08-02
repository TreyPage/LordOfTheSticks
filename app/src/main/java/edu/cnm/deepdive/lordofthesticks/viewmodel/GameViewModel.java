package edu.cnm.deepdive.lordofthesticks.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

  public GameViewModel(@NonNull Application application) {
    super(application);
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

  public void postToArena() {
    mDatabase.collection("arenas").document().set(PlayServices.informationDrop());
  }

}
