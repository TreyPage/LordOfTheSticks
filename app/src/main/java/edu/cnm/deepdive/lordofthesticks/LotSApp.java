package edu.cnm.deepdive.lordofthesticks;

import android.app.Application;
import edu.cnm.deepdive.lordofthesticks.google.GoogleSignInService;

public class LotSApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setContext(this);

  }
}