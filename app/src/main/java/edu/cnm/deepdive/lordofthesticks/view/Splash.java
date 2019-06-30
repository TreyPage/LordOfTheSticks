package edu.cnm.deepdive.lordofthesticks.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import edu.cnm.deepdive.lordofthesticks.LoginActivity;
import edu.cnm.deepdive.lordofthesticks.R;

public class Splash extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    Thread background = new Thread() {
      public void run() {
        try {
          // Thread will sleep for 5 seconds
          sleep(5*1000);
          // After 5 seconds redirect to another intent
          Intent intent = new Intent(getBaseContext(), LoginActivity.class);
          startActivity(intent);
          //Switch activity
          finish();
        } catch (Exception e) {
          // Do nothing?? maybe
        }
      }
    };
    // start thread
    background.start();
  }
}
