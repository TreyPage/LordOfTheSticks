package edu.cnm.deepdive.lordofthesticks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesCallbackStatusCodes;
import com.google.android.gms.games.RealTimeMultiplayerClient;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.cnm.deepdive.lordofthesticks.google.PlayServices;
import edu.cnm.deepdive.lordofthesticks.viewmodel.MenuViewModel;
import java.util.ArrayList;


public class MenuScreen extends AppCompatActivity {

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
    viewModel.getSnapshot()
        .observe(this, (snapshot) -> data.setText(snapshot.get("name").toString()));
    viewModel.setPath("3bG5uTKVRb8uh93IYOjr");

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

}
