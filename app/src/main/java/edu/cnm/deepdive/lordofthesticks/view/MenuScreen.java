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
    FloatingActionButton profileButton = findViewById(R.id.menu_profile);

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

    profileButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
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
