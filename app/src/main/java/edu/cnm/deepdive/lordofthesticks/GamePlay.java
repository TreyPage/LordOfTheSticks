package edu.cnm.deepdive.lordofthesticks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.ContentView;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import edu.cnm.deepdive.lordofthesticks.JoystickView.JoystickListener;

public class GamePlay extends AppCompatActivity implements JoystickView.JoystickListener {


  // The view of the game!
  GameView gameView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    gameView = new GameView(this);
    setContentView(gameView);
    gameView.bringToFront();
    gameView.setZOrderOnTop(false);



//    JoystickView joystick = new JoystickView(this)
    /*
    * These lines are used to create the joysticks.
    */

//    setContentView(R.layout.activity_game_play);
//    JoystickView joystickLeft = findViewById(R.id.joystickLeft);
//    JoystickView joystickRight = findViewById(R.id.joystickRight);
//    joystickLeft.setZOrderOnTop(true);
//    joystickRight.setZOrderOnTop(true);
//    SurfaceHolder surfaceRight = joystickRight.getHolder();
//    SurfaceHolder surfaceLeft = joystickLeft.getHolder();
//    surfaceRight.setFormat(PixelFormat.TRANSPARENT);
//    surfaceLeft.setFormat(PixelFormat.TRANSPARENT);
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

  class GameView extends SurfaceView implements Runnable {

    // Thread for the game.
    Thread gameThread = null;

    //Needed surface holder for paint and canvas to create the movement.
    SurfaceHolder ourHolder;

    // Boolean for running or not. For the game loop.
    volatile boolean playing;

    // Canvas and paint objects.
    Canvas canvas;
    Paint paint;

    // Frames per seconds for the game.
    long fps;

    // Helps calculate the fps
    private long timeThisFrame;


    // Declare an object of type Bitmap, this is our stickman.
    Bitmap bitmapStick;

    // Bob starts off not moving
    boolean isMoving = false;

    // He can walk at 150 pixels per second, we can change this to change the speed etc.
    float walkSpeedPerSecond = 150;

    // He starts 10 pixels from the left
    float stickXPosition = 10;

    public GameView(Context context) {
      // Sets up the Object
      super(context);

      // Initialize our holder and paint objects
      ourHolder = getHolder();
      paint = new Paint();

      // Load the stickman!
      bitmapStick = getBitmapFromDrawable(getContext(), R.drawable.stickman);

      //Game on!!!!
      playing = true;
    }

    @Override
    public void run() {
      while (playing) {

        // Capture the current time in milliseconds in startFrameTime
        long startFrameTime = System.currentTimeMillis();

        // Update the frame
        update();

        // Draw the frame
        draw();

        // Calculate the fps this frame
        // We can then use the result to
        // time animations and more.
        timeThisFrame = System.currentTimeMillis() - startFrameTime;
        if (timeThisFrame > 0) {
          fps = 1000 / timeThisFrame;
        }

      }
    }

    public void update() {

      // stick is moving in here to adjust to make the joysticks control it.
      if(isMoving){
        stickXPosition = stickXPosition + (walkSpeedPerSecond / fps);
      }

    }

    public void draw() {

      if (ourHolder.getSurface().isValid()) {
        // Lock the canvas.
        canvas = ourHolder.lockCanvas();

        // Draw back ground TODO see if this effects the joysticks.
        canvas.drawColor(Color.argb(255,255,255,255));

        // Brush color for drawing? TODO understand this better
        paint.setColor(Color.argb(255, 249, 129, 0));

        //TODO this will let us know the fps by displaying it if we need it.
//
//        paint.setTextSize(45);
//
//        canvas.drawText("FPS:" + fps, 20, 40, paint);

        // Draw our stick at 200 pixels.
        canvas.drawBitmap(bitmapStick, stickXPosition, 200,  paint);

        //After drawing everything to the screen then unlock the drawing surface.
        ourHolder.unlockCanvasAndPost(canvas);
      }
    }

    public void pause() {
      playing = false;
      try {
        gameThread.join();
      } catch (InterruptedException e) {
        Log.e("Error:", "joining thread");
      }
    }

    public void resume() {
      playing = true;
      gameThread = new Thread(this);
      gameThread.start();
    }

    // TODO implement movement based upon the joysticks

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

      switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

        // Player has touched the screen
        case MotionEvent.ACTION_DOWN:

          // Set isMoving so Bob is moved in the update method
          isMoving = true;

          break;

        // Player has removed finger from screen
        case MotionEvent.ACTION_UP:

          // Set isMoving so Bob does not move
          isMoving = false;

          break;
      }
      return true;
    }

  }

  @Override
  protected void onResume() {
    super.onResume();
    gameView.resume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    gameView.pause();
  }


  public static Bitmap getBitmapFromDrawable(Context context, @DrawableRes int drawableId) {
    Drawable drawable = AppCompatResources.getDrawable(context, drawableId);

    if (drawable instanceof BitmapDrawable) {
      return ((BitmapDrawable) drawable).getBitmap();
    } else if (drawable instanceof VectorDrawableCompat || drawable instanceof VectorDrawable) {
      Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
      drawable.draw(canvas);

      return bitmap;
    } else {
      throw new IllegalArgumentException("unsupported drawable type");
    }
  }
}
