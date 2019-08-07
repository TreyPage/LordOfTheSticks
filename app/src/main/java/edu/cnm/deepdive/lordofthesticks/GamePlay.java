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
package edu.cnm.deepdive.lordofthesticks;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import edu.cnm.deepdive.lordofthesticks.gameplay.StickTest;

/**
 * The GamePlay class is called after PlayServices completes the matchmaking process and puts the
 * users into a room. GamePlay creates a new instance of StickTest() inside the core directory. The
 * actually GamePlay is handled through that directory. This class is just doing the prep work.
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
