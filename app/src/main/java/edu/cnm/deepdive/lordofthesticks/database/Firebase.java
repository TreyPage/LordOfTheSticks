package edu.cnm.deepdive.lordofthesticks.database;

import android.util.EventLog.Event;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import javax.annotation.Nullable;

public class Firebase extends LiveData<Task<DocumentSnapshot>> {

    private static final String Log_tag = "FirebaseQueryLiveData";
    private final DocumentReference documentReference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public Firebase (DocumentReference ref, EventListener<DocumentSnapshot> listener) {
      this.documentReference = ref;
      documentReference.addSnapshotListener(listener);
    }

    @Override
    protected void onActive(){
      Log.d(Log_tag, "onActive");
      documentReference.get().addOnCompleteListener(listener);
    }

    @Override
    protected void onInactive(){
      Log.d(Log_tag, "onInactive");
      documentReference.get().addOnCompleteListener(listener);
    }

    private class MyValueEventListener implements OnCompleteListener<DocumentSnapshot>{

      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        setValue(task);
      }

    }

  }

