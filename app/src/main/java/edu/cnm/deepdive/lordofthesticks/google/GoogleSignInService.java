package edu.cnm.deepdive.lordofthesticks.google;

import android.app.Application;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.Games;

public class GoogleSignInService {

  private static Application context;

  private GoogleSignInAccount account;
  private GoogleSignInClient client;

  private GoogleSignInService() {
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail().requestId()
        .requestScopes(Games.SCOPE_GAMES_LITE)
        .requestIdToken("656839351493-aevuk7ekve8bjlek9dbga23lu478k3k1.apps.googleusercontent.com")
        .build();
    client = GoogleSignIn.getClient(context, options);
  }

  public static void setContext(Application context) {
    GoogleSignInService.context = context;
  }

  public static GoogleSignInService getInstance() {
    return instanceHolder.INSTANCE;
  }

  public GoogleSignInClient getClient() {
    return client;
  }

  public GoogleSignInAccount getAccount() {
    return account;
  }

  public void setAccount(GoogleSignInAccount account) {
    this.account = account;
  }

  private static class instanceHolder {

    private static final GoogleSignInService INSTANCE = new GoogleSignInService();
  }
}