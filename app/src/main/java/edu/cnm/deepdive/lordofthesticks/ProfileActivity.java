package edu.cnm.deepdive.lordofthesticks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import edu.cnm.deepdive.lordofthesticks.google.GoogleSignInService;
import edu.cnm.deepdive.lordofthesticks.view.Splash;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

  //firebase auth object
  private FirebaseAuth firebaseAuth;

  //view objects
  private TextView textViewUserEmail;
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
    buttonLogout = findViewById(R.id.buttonLogout);

    //displaying logged in user name
    textViewUserEmail.setText("Welcome " + user.getEmail());

    //adding listener to button
    buttonLogout.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    //if logout is pressed
    if (view == buttonLogout) {
      //logging out the user
      firebaseAuth.signOut();
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
    service.getClient().signOut().addOnCompleteListener((task) -> {
      service.setAccount(null);
      Intent intent = new Intent(this, Splash.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    });
  }
}