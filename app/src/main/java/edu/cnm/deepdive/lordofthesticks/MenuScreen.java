package edu.cnm.deepdive.lordofthesticks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MenuScreen extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_screen);

    FloatingActionButton playButton = findViewById(R.id.menu_play);
    FloatingActionButton settingsButton = findViewById(R.id.menu_settings);
    FloatingActionButton shopButton = findViewById(R.id.menu_cart);
    FloatingActionButton helpButton = findViewById(R.id.menu_help);

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
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
      }
    });
  }
}
