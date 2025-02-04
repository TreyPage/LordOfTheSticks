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
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import edu.cnm.deepdive.lordofthesticks.R;
import edu.cnm.deepdive.lordofthesticks.google.GoogleSignInService;

/***
 * The Splash activity shows a picture with credits to each team member, title of the game,
 * and a number of sticks fighting. It also has a Google sign in button. This class will show
 * the first time the game is launched and the sign in is required. This Google sign in will
 * connect the user to Firebase as well. After signing in the app will continue to the next
 * activity.
 */
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
        firebaseAuthWithGoogle(account);
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

    SignInButton button = findViewById(R.id.sign_in);
    button.setColorScheme(0);
    button.setOnClickListener(
        (view) -> signIn());
  }

  private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String TAG = "GoogleActivity";
    Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
    // [START_EXCLUDE silent]
    // [END_EXCLUDE]

    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              // Sign in success, update UI with the signed-in user's information
              Log.d(TAG, "signInWithCredential:success");
              FirebaseUser user = mAuth.getCurrentUser();
            } else {
              // If sign in fails, display a message to the user.
              Log.w(TAG, "signInWithCredential:failure", task.getException());
              Snackbar.make(findViewById(R.id.splash_activity), "Authentication Failed.",
                  Snackbar.LENGTH_SHORT).show();
            }
          }
        });
  }
}

//    This code below is to make the activity sleep for 5 seconds and then do something.
//      may still be useful later for splash screen. Not deleting yet. TP 8/2/19
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
