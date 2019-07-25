package edu.cnm.deepdive.lordofthesticks.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.cnm.deepdive.lordofthesticks.GamePlay;
import edu.cnm.deepdive.lordofthesticks.R;

public class GameView extends SurfaceView implements Runnable {

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
  boolean isMovingRight = false;
  boolean isMovingLeft = false;

  // He can walk at 150 pixels per second, we can change this to change the speed etc.
  float walkSpeedPerSecond = 150;

  // He starts 10 pixels from the left
  float stickXPosition = 10;

  {
    // Initialize our holder and paint objects
    ourHolder = getHolder();
    paint = new Paint();

    // Load the stickman!
    bitmapStick = GamePlay.getBitmapFromDrawable(getContext(), R.drawable.stickman);

    //Game on!!!!
    playing = true;

  }

  public GameView(Context context) {
    // Sets up the Object
    super(context);

  }

  public GameView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public GameView(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public void setMovingRight(boolean movingRight) {
    isMovingRight = movingRight;
  }

  public void setMovingLeft(boolean movingLeft) {
    isMovingLeft = movingLeft;
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
    if (isMovingRight) {
      stickXPosition = stickXPosition + (walkSpeedPerSecond / fps);
    }
    if (isMovingLeft) {
      stickXPosition = stickXPosition - (walkSpeedPerSecond / fps);
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

//  @Override
//  public boolean onTouchEvent(MotionEvent motionEvent) {
//
//    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
//
//      // Player has touched the screen
//      case MotionEvent.ACTION_DOWN:
//
//        // Set isMovingRight so Bob is moved in the update method
//        isMovingRight = true;
//
//        break;
//
//      // Player has removed finger from screen
//      case MotionEvent.ACTION_UP:
//
//        // Set isMovingRight so Bob does not move
//        isMovingRight = false;
//
//        break;
//    }
//    return true;
//  }

}
