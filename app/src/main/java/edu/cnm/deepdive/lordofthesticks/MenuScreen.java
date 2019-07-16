package edu.cnm.deepdive.lordofthesticks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import edu.cnm.deepdive.lordofthesticks.viewmodel.MenuViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.util.Map;


public class MenuScreen extends AppCompatActivity {

//  FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//  private static final String TAG = "Firebase";



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_screen);



    FloatingActionButton playButton = findViewById(R.id.menu_play);
    FloatingActionButton settingsButton = findViewById(R.id.menu_settings);
    FloatingActionButton shopButton = findViewById(R.id.menu_cart);
    FloatingActionButton helpButton = findViewById(R.id.menu_help);

    TextView data = findViewById(R.id.data_text);
    MenuViewModel viewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
//    MenuViewModel viewModel = ViewModelProviders
    LiveData<Task<DocumentSnapshot>> liveData = viewModel.getdataSnapshotLiveData();
    liveData.observe(this, new Observer<Task<DocumentSnapshot>>() {
      @Override
      public void onChanged(Task<DocumentSnapshot> task) {
        if(task.isSuccessful()){
          DocumentSnapshot documentSnapshot = task.getResult();
          data.setText(documentSnapshot.get("name").toString());

        }
      }
    });



    playButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), GamePlay.class);
        startActivity(intent);
      }
    });
    settingsButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), Settings.class);
        startActivity(intent);
      }
    });
    shopButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), Shop.class);
        startActivity(intent);
      }
    });
    helpButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // TODO should give a pop up or snackbar of some kind with how to play.
        Intent intent = new Intent(view.getContext(), ProfileActivity.class);
        startActivity(intent);
      }
    });

  }


}
