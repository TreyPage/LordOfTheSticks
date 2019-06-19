package edu.cnm.deepdive.lordofthesticks;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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
          Intent intent = new Intent(getBaseContext(),LoginActivity.class);
          startActivity(intent);

          //Remove activity
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
