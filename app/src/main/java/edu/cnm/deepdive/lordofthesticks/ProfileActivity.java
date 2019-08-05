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
package edu.cnm.deepdive.lordofthesticks;
*/
package edu.cnm.deepdive.lordofthesticks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import edu.cnm.deepdive.lordofthesticks.database.Firebase;
import edu.cnm.deepdive.lordofthesticks.google.GoogleSignInService;
import edu.cnm.deepdive.lordofthesticks.google.PlayServices;
import edu.cnm.deepdive.lordofthesticks.view.Splash;

/**
 * The ProfileActivity is simply displaying the current signed in user and has a log out button
 * that will send the user back to the splash screen to log in.
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

  //firebase auth object
  private FirebaseAuth firebaseAuth;

  //view objects
  private TextView textViewUserEmail;
  private TextView lastTotalParticipants;
  private TextView lastRoom;

  private Button buttonLogout;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);

    //initializing firebase authentication object
    firebaseAuth = FirebaseAuth.getInstance();

    //if the user is not logged in
    //that means current user will return null
    if (firebaseAuth.getCurrentUser() == null) {
      //closing this activity
      finish();
      //starting login activity
      startActivity(new Intent(this, Splash.class));
    }

    //getting current user
    FirebaseUser user = firebaseAuth.getCurrentUser();

    //initializing views
    textViewUserEmail = findViewById(R.id.textViewUserEmail);
    lastRoom = findViewById(R.id.lastRoom);
    lastTotalParticipants = findViewById(R.id.lastTotalParticipants);
    buttonLogout = findViewById(R.id.buttonLogout);

    //displaying logged in user name
    textViewUserEmail.setText("Welcome " + user.getEmail());

    //adding listener to button
    buttonLogout.setOnClickListener(this);
    String lastRoomText = PlayServices.roomInfo();
    lastRoom.setText(lastRoomText);
  }

  @Override
  public void onClick(View view) {
    //if logout is pressed
    if (view == buttonLogout) {
      //logging out the user
      signOut();
      //closing activity
      finish();
      //starting login activity
      startActivity(new Intent(this, Splash.class)
          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
  }

  private void signOut() {
    GoogleSignInService service = GoogleSignInService.getInstance();
    firebaseAuth.signOut();
    service.getClient().signOut().addOnCompleteListener((task) -> {
      service.setAccount(null);
    });
  }
}