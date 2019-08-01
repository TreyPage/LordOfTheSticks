package edu.cnm.deepdive.lordofthesticks.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.google.android.gms.games.stats.PlayerStats;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import edu.cnm.deepdive.lordofthesticks.database.Firebase;
import java.util.HashMap;
import java.util.Map;

public class MenuViewModel extends AndroidViewModel {

  private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
  private MutableLiveData<DocumentSnapshot> snapshot = new MutableLiveData<>();
  private MutableLiveData<String> path = new MutableLiveData<>();
  private DocumentReference documentReference = null;
  private DocumentReference doc = null;

  HashMap<String, Object> docDate = new HashMap<>();

  public MenuViewModel(Application application) {
    super(application);
  }

//  public void setupMap(){
//    docData.put("Arena", );
//  }

  public void setPath(String path) {
    documentReference = mDatabase.collection("arenas").document(path);
    new Firebase(documentReference, (documentSnapshot, e) ->
        snapshot.postValue(documentSnapshot)
    );
  }

  public LiveData<DocumentSnapshot> getSnapshot() {
    return snapshot;
  }




  public void setMap(Map<String, Object> map) {
    if (documentReference != null) {
      documentReference.set(map);
    }
  }




  public void setMap() {
    doc = mDatabase.collection("arena").document();
    doc.set(docDate);
  }






//  FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//
//  private static final String TAG = "Firebase";
//
//  public LiveData<String> name;
//
//  public MenuViewModel(@NonNull Application application) {
//    super(application);
//  }
//
//
//  FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//  private static final String TAG = "Firebase";

//  private String getArena() {
//    final StringBuilder sb = new StringBuilder();
//
//    DocumentReference docRef = db.collection("arenas").document("3bG5uTKVRb8uh93IYOjr");
//    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//      @Override
//      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//        Log.d(TAG, task.toString());
//        if (task.isSuccessful()) {
//          DocumentSnapshot document = task.getResult();
//          Log.d(TAG, document.toString());
//          if (document.exists()) {
//            sb.append(document.get("name"));
//            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//          } else {
//            Log.d(TAG, "No such document");
//          }
//        } else {
//          Log.d(TAG, "get failed with ", task.getException());
//        }
////      return sb.toString();
//      }
//    });
//
//    return sb.toString();
//  }

}
