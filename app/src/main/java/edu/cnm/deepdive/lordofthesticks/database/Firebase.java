package edu.cnm.deepdive.lordofthesticks.database;

import static com.firebase.ui.auth.AuthUI.TAG;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.HashMap;
import java.util.Map;

public class Firebase extends LiveData<Task<DocumentSnapshot>> {

    private static final String Log_tag = "FirebaseQueryLiveData";
    private final DocumentReference documentReference;
    private final MyValueEventListner listner = new MyValueEventListner();

    public Firebase (DocumentReference ref){
      this.documentReference = ref;
    }


    @Override
    protected void onActive(){
      Log.d(Log_tag, "onActive");
      documentReference.get().addOnCompleteListener(listner);
    }

    @Override
    protected void onInactive(){
      Log.d(Log_tag, "onInactive");
      documentReference.get().addOnCompleteListener(listner);
    }

    private class MyValueEventListner implements OnCompleteListener<DocumentSnapshot>{

      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        setValue(task);
      }

    }

  }

