package edu.cnm.deepdive.lordofthesticks;

import android.graphics.PixelFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.cnm.deepdive.lordofthesticks.JoystickView.JoystickListener;

public class GamePlay extends AppCompatActivity implements JoystickView.JoystickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    JoystickView joystick = new JoystickView(this);
    setContentView(R.layout.activity_game_play);
    JoystickView joystickLeft = findViewById(R.id.joystickLeft);
    JoystickView joystickRight = findViewById(R.id.joystickRight);
    joystickLeft.setZOrderOnTop(true);
    joystickRight.setZOrderOnTop(true);
    SurfaceHolder surfaceRight = joystickRight.getHolder();
    SurfaceHolder surfaceLeft = joystickLeft.getHolder();
    surfaceRight.setFormat(PixelFormat.TRANSPARENT);
    surfaceLeft.setFormat(PixelFormat.TRANSPARENT);
  }

  @Override
  public void onJoystickMoved(float xPercent, float yPercent, int id) {
    switch (id)
    {
      case R.id.joystickRight:
        Log.d("Right Joystick", "X percent: " + xPercent + " Y percent: " + yPercent);
        break;
      case R.id.joystickLeft:
        Log.d("Left Joystick", "X percent: " + xPercent + " Y percent: " + yPercent);
        break;
    }
  }
}
