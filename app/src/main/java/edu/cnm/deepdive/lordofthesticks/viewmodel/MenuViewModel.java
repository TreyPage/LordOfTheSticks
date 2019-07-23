package edu.cnm.deepdive.lordofthesticks.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import edu.cnm.deepdive.lordofthesticks.database.Firebase;

public class MenuViewModel extends ViewModel {

  private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
  private final Firebase liveData = new Firebase(mDatabase.collection("arenas").document("3bG5uTKVRb8uh93IYOjr"));


  @NonNull
  public LiveData<Task<DocumentSnapshot>> getdataSnapshotLiveData(){
    return liveData;
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
