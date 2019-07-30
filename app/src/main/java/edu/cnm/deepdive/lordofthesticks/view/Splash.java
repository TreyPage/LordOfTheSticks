package edu.cnm.deepdive.lordofthesticks.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import edu.cnm.deepdive.lordofthesticks.MenuScreen;
import edu.cnm.deepdive.lordofthesticks.R;
import edu.cnm.deepdive.lordofthesticks.google.GoogleSignInService;

public class Splash extends AppCompatActivity {

  private static final int LOGIN_REQUEST_CODE = 1001;

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == LOGIN_REQUEST_CODE) {
      try {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        GoogleSignInAccount account = task.getResult(ApiException.class);
        GoogleSignInService.getInstance().setAccount(account);
        switchToNext();
      } catch (ApiException e) {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
      }
    }
  }

  private void switchToNext() {
    Intent intent = new Intent(this, MenuScreen.class);
    startActivity(intent);
  }

  private void signIn() {
    Intent intent = GoogleSignInService.getInstance().getClient().getSignInIntent();
    startActivityForResult(intent, LOGIN_REQUEST_CODE);
  }

  @Override
  protected void onStart() {
    super.onStart();
    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    if (account != null) {
      GoogleSignInService.getInstance().setAccount(account);
      switchToNext();
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    findViewById(R.id.sign_in).setOnClickListener(
        (view) -> signIn());
//
//    Thread background = new Thread() {
//      public void run() {
//        try {
//          // Thread will sleep for 5 seconds
//          sleep(5 * 1000);
//          // After 5 seconds redirect to another intent
//          Intent intent = new Intent(getBaseContext(), MenuScreen.class);
//          startActivity(intent);
//          //Switch activity
//          finish();
//        } catch (Exception e) {
//          // Do nothing?? maybe
//        }
//      }
//    };
//    // start thread
//    background.start();
  }
}
