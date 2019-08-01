package edu.cnm.deepdive.lordofthesticks.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import edu.cnm.deepdive.lordofthesticks.R;

public class GameView extends SurfaceView implements Runnable {


  // Thread for the game.
  private Thread gameThread = null;

  //Needed surface holder for paint and canvas to create the movement.
  private SurfaceHolder ourHolder;

  // Boolean for running or not. For the game loop.
  private volatile boolean playing;

  // Canvas and paint objects.
  private Canvas canvas;
  private Paint paint;

  // Frames per seconds for the game.
  private long fps;

  // Helps calculate the fps
  private long timeThisFrame;


  // Declare an object of type Bitmap, this is our stickman.
  private Bitmap bitmapStick;
  // LevelList Drawable for animation
  private Drawable stick;


  private float lastXPercent;

  // Stick starts off not moving
  private boolean isMovingRight = false;
  private boolean isMovingLeft = false;
  private boolean isRunningRight = false;
  private boolean isRunningLeft = false;


  // He can walk at 250 pixels per second, we can change this to change the speed etc.
  private float walkSpeedPerSecond = 150;
  private float runSpeedPerSecond = 300;

  // He starts 10 pixels from the left
  private float stickXPosition = 300;

  // Ratio for the sprite.
  private int frameWidth = 150;
  private int frameHeight = 900;

  // Number of frames on sprite sheet.
  private int frameCount = 6;

  //Start at the first frame.
  private int currentFrame = 0;

  // Time we drew the last frame.
  private long lastFrameChangeTime = 0;

  // How long each draw frame lasts.
  private int frameLengthInMilliseconds = 100;

  // A rectangle that defines an area of the sprite sheet that represents one frame.
  private Rect frameToDraw = new Rect(0, 0, frameWidth, frameHeight);

  //A Rect defining where on the screen to draw.
  private RectF whereToDraw = new RectF(stickXPosition, 0, stickXPosition + frameWidth, frameHeight);


  {
    // Initialize our holder and paint objects
    ourHolder = getHolder();
    paint = new Paint();

    // TODO this is how I want to do it. start at 0. go 1-3 for right, and -1 to -3 for left
//    stick = getResources().getDrawable(R.drawable.movement);
//    stick.setLevel(0);


    // Load the stickman!       TODO this is an easy way to get the resource png and cycle through
//      bitmapStick = drawableToBitMap(drawable);
    bitmapStick = BitmapFactory.decodeResource(this.getResources(), R.drawable.movingright);

    // Scale the bitmap to the correct size, need to do because of Android auto scale to screen density.
    bitmapStick = Bitmap.createScaledBitmap(bitmapStick, frameWidth * frameCount, frameHeight, false);

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

  public float getLastXPercent() {
    return lastXPercent;
  }

  public void setLastXPercent(float lastXPercent) {
    this.lastXPercent = lastXPercent;
  }

  public void setCurrentFrame(int currentFrame) {
    this.currentFrame = currentFrame;
  }

  public void setMovingRight(boolean movingRight) {
    isMovingRight = movingRight;
  }

  public void setMovingLeft(boolean movingLeft) {
    isMovingLeft = movingLeft;
  }

  public void setRunningRight(boolean runningRight) {
    isRunningRight = runningRight;
  }

  public void setRunningLeft(boolean runningLeft) {
    isRunningLeft = runningLeft;
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
    if (isRunningRight) {
      stickXPosition = stickXPosition + (runSpeedPerSecond / fps);
    }
    if (isRunningLeft) {
      stickXPosition = stickXPosition - (runSpeedPerSecond / fps);
    }

  }

  public void getCurrentFrame() {

    //TODO after getting the LevelListDrawable to work implement it here cycling between 1-3 for right and -1 to -3 for left.

    long time = System.currentTimeMillis();
    if (isMovingRight || isRunningRight) {
      if ( time > lastFrameChangeTime + frameLengthInMilliseconds) {
        lastFrameChangeTime = time;
        currentFrame++;
        if (currentFrame >= frameCount) {
          currentFrame = 2;
        }
      }
    }

//    if (isMovingLeft || isRunningLeft) {
//      bitmapStick = BitmapFactory.decodeResource(this.getResources(), );
//
//      // Scale the bitmap to the correct size, need to do because of Android auto scale to screen density.
//      bitmapStick = Bitmap.createScaledBitmap(bitmapStick, frameWidth * frameCount, frameHeight, false);
//    }


    frameToDraw.left = currentFrame * frameWidth;
    frameToDraw.right = frameToDraw.left + frameWidth;

  }

  public void draw() {

    if (ourHolder.getSurface().isValid()) {
      // Lock the canvas.
      canvas = ourHolder.lockCanvas();

      // Draw back ground TODO see if this effects the joysticks.
      canvas.drawColor(Color.argb(255, 255, 255, 255));

      // Brush color for drawing? TODO understand this better
      paint.setColor(Color.argb(255, 249, 129, 0));

      //TODO this will let us know the fps by displaying it if we need it.
//
//        paint.setTextSize(45);
//
//        canvas.drawText("FPS:" + fps, 20, 40, paint);



      whereToDraw.set((int)stickXPosition, 0, (int)stickXPosition + frameWidth, frameHeight);

      getCurrentFrame();

      canvas.drawBitmap(bitmapStick, frameToDraw, whereToDraw, paint);

      // Draw our stick at 200 pixels.
//      canvas.drawBitmap(bitmapStick, stickXPosition, 200, paint);

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


}
