package edu.cnm.deepdive.lordofthesticks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import edu.cnm.deepdive.lordofthesticks.viewmodel.MenuViewModel;


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
    viewModel.getSnapshot().observe(this, (snapshot) -> data.setText(snapshot.get("name").toString()));
    viewModel.setPath("3bG5uTKVRb8uh93IYOjr");

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
