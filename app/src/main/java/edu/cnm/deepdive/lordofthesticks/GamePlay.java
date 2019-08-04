package edu.cnm.deepdive.lordofthesticks;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * The GamePlay class is called after PlayServices completes the matchmaking process and puts the
 * users into a room. GamePlay creates a new instance of StickTest() inside the core directory.
 * The actually GamePlay is handled through that directory. This class is just doing the prep work.
 */
public class GamePlay extends AndroidApplication {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
    initialize(new StickTest(), config);
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
  }
}
