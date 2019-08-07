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

public class GameViewModel extends AndroidViewModel {

  private final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
  private MutableLiveData<DocumentSnapshot> snapshot = new MutableLiveData<>();
  private DocumentReference documentReference = null;

  public void setPath(String path) {
    documentReference = mDatabase.collection("arenas").document(path);
    new Firebase(documentReference, (documentSnapshot, e) ->
        snapshot.postValue(documentSnapshot)
    );
  }

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
