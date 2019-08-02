package edu.cnm.deepdive.lordofthesticks.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.cnm.deepdive.lordofthesticks.ProfileActivity;
import edu.cnm.deepdive.lordofthesticks.R;
import edu.cnm.deepdive.lordofthesticks.Settings;
import edu.cnm.deepdive.lordofthesticks.Shop;
import edu.cnm.deepdive.lordofthesticks.google.PlayServices;
import edu.cnm.deepdive.lordofthesticks.viewmodel.GameViewModel;

/**
 * The MenuScreen has 4 buttons on it to access the other activities in the app, the title of the
 * game, and a string of text being received from firebase.
 */
public class MenuScreen extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_screen);

    FloatingActionButton playButton = findViewById(R.id.menu_play);
    FloatingActionButton settingsButton = findViewById(R.id.menu_settings);
    FloatingActionButton shopButton = findViewById(R.id.menu_cart);
    FloatingActionButton helpButton = findViewById(R.id.menu_help);

    firebaseText();

    playButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), PlayServices.class);
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

  private void firebaseText() {
    TextView data = findViewById(R.id.data_text);
    GameViewModel viewModel = ViewModelProviders.of(this).get(GameViewModel.class);
    viewModel.getSnapshot()
        .observe(this, (snapshot) -> {
          if (snapshot != null) {
            data.setText(snapshot.get("name").toString());
          }
        });
    viewModel.setPath("3bG5uTKVRb8uh93IYOjr");
  }

}
