package edu.cnm.deepdive.lordofthesticks.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

}
