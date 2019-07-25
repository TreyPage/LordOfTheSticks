package edu.cnm.deepdive.lordofthesticks.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import edu.cnm.deepdive.lordofthesticks.database.Firebase;
import javax.annotation.Nullable;

public class MenuViewModel extends AndroidViewModel {

  private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
  private MutableLiveData<DocumentSnapshot> snapshot = new MutableLiveData<>();
  private MutableLiveData<String> path = new MutableLiveData<>();

  public MenuViewModel(Application application) {
    super(application);
  }

  public void setPath(String path) {
    new Firebase(mDatabase.collection("arenas").document(path), (documentSnapshot, e) ->
        snapshot.postValue(documentSnapshot)
    );
  }

  public LiveData<DocumentSnapshot> getSnapshot() {
    return snapshot;
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
