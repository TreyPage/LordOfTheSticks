package edu.cnm.deepdive.lordofthesticks;

import android.app.Application;
import edu.cnm.deepdive.lordofthesticks.google.GoogleSignInService;

/**
 * LotSApp is giving the GoogleSignInService a context to run its UI in.
 */
public class LotSApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setContext(this);
  }
}