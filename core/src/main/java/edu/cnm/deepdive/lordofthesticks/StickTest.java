package edu.cnm.deepdive.lordofthesticks;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StickTest extends ApplicationAdapter implements ApplicationListener {

  private static final int FRAME_COLS = 2, FRAME_ROWS = 1;
  private static final int FRAME_COLUMNS =14, FRAME_ACROSS = 2;
  private static final float SCALE = 1.5f;
  public static final float PIXEL_PER_METER = 28f;
  private static final float TIME_STEP = 1 / 60f;
  private static final int VELOCITY_ITERATIONS = 6;
  private static final int POSITION_ITERATIONS = 2;
  private static final float VELOCITY_Y = -9.85f;
  private static final float VELOCITY_X = 0;
  private static final String MAP_PATH = "map/GameMap.tmx";
  private OrthographicCamera orthographicCamera;
  private OrthogonalTiledMapRenderer tiledMapRenderer;
  private Viewport gamePort;
  private Box2DDebugRenderer box2DDebugRenderer;
  private World world;
  private TiledMap tiledMap;
  private Player player;
  private SpriteBatch spriteBatch;
  private Texture stickman;
  private Texture runRightSheet;
  private Texture runLeftSheet;
  private Texture jumpLeftSheet;
  private Texture jumpRightSheet;
  private Animation<TextureRegion> runRightAnimation;
  private Animation<TextureRegion> runLeftAnimation;
  private Animation<TextureRegion> jumpLeftAnimation;
  private Animation<TextureRegion> jumpRightAnimation;
  private float stateTime;
  private TextureRegion runningRight;
  private TextureRegion runningLeft;
  private TextureRegion jumpingLeft;
  private TextureRegion jumpingRight;
  public static final int V_Height = 500;
  public static final int V_WIDTH = 1000;
  @Override
  public void create() {
    orthographicCamera = new OrthographicCamera();
    gamePort = new FitViewport(StickTest.V_WIDTH,StickTest.V_Height,orthographicCamera);
    orthographicCamera.position.set(gamePort.getWorldWidth() / 2,gamePort.getWorldHeight() /2 , 0);
    orthographicCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    world = new World(new Vector2(VELOCITY_X, VELOCITY_Y), false);
    box2DDebugRenderer = new Box2DDebugRenderer();
    tiledMap = new TmxMapLoader().load(MAP_PATH);
    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    MapParser.parseMapLayers(world, tiledMap);
    player = new Player(world);
    spriteBatch = new SpriteBatch();

    stickman = new Texture(Gdx.files.internal("stickman.png"));
    runRightSheet = new Texture(Gdx.files.internal("animations/RunningRightAtlas.png"));
    runLeftSheet = new Texture(Gdx.files.internal("animations/RunningLeftAtlas.png"));
    jumpLeftSheet = new Texture(Gdx.files.internal("animations/JumpingLeftAtlas.png"));
    jumpRightSheet = new Texture(Gdx.files.internal("animations/JumpingRightAtlas.png"));

    TextureRegion[][] tmp = TextureRegion.split(runRightSheet,
            runRightSheet.getWidth() / FRAME_COLS,
            runRightSheet.getHeight() / FRAME_ROWS);
    TextureRegion[][] tmp2 = TextureRegion.split(runLeftSheet,
            runLeftSheet.getWidth() / FRAME_COLS,
            runLeftSheet.getHeight() / FRAME_ROWS);
    TextureRegion[][] tmp3 = TextureRegion.split(jumpLeftSheet,
            jumpLeftSheet.getWidth() / FRAME_COLUMNS,
            jumpLeftSheet.getHeight() / FRAME_ACROSS);
    TextureRegion[][] tmp4 = TextureRegion.split(jumpRightSheet,
            jumpRightSheet.getWidth() / FRAME_COLUMNS,
            jumpRightSheet.getHeight() / FRAME_ACROSS);

    TextureRegion[] runRightFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
    int indexRightRun = 0;
    for (int i = 0; i < FRAME_ROWS; i++) {
      for (int j = 0; j < FRAME_COLS; j++) {
        runRightFrames[indexRightRun++] = tmp[i][j];
      }
    }

    TextureRegion[]  runLeftFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
    int indexLeftRun = 0;
    for (int i = 0; i < FRAME_ROWS; i++) {
      for (int j = 0; j < FRAME_COLS; j++) {
        runLeftFrames[indexLeftRun++] = tmp2[i][j];
      }
    }

    TextureRegion[] jumpLeftFrames = new TextureRegion[(FRAME_COLUMNS) * FRAME_ACROSS];
    int indexJumpLeft = 0;
    for (int i = 0; i < FRAME_ACROSS; i++) {
      for (int j = 0; j <FRAME_COLUMNS; j++) {
        jumpLeftFrames[indexJumpLeft++] = tmp3[i][j];
      }
    }

    TextureRegion[] jumpRightFrames = new TextureRegion[(FRAME_COLUMNS) * FRAME_ACROSS];
    int indexJumpRight = 0;
    for (int i = 0; i < FRAME_ACROSS; i++) {
      for (int j = 0; j <FRAME_COLUMNS ; j++) {
        jumpRightFrames[indexJumpRight++] = tmp4[i][j];
      }
    }

    runRightAnimation = new Animation<TextureRegion>(0.28f,runRightFrames);
    runLeftAnimation = new Animation<TextureRegion>(0.28f, runLeftFrames);
    jumpLeftAnimation = new Animation<TextureRegion>( 0.28f, jumpLeftFrames);
    jumpRightAnimation = new Animation<TextureRegion>(0.28f, jumpRightFrames);

    stateTime = 0f;
    world.setContactListener(new WorldContactListener());

  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      tiledMapRenderer.render();
      spriteBatch.setProjectionMatrix(orthographicCamera.combined);
      stateTime += Gdx.graphics.getDeltaTime();
      update();

  }


  private void update () {
      world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
      inputUpdate();
      cameraUpdate();
      tiledMapRenderer.setView(orthographicCamera);

    }

    private void cameraUpdate () {
      Vector3 position = orthographicCamera.position;
      position.x = player.getBody().getPosition().x * PIXEL_PER_METER;
      position.y = player.getBody().getPosition().y * PIXEL_PER_METER;
      orthographicCamera.position.set(position);
      orthographicCamera.update();
    }

    @Override
    public void resize ( int width, int height){
      orthographicCamera.setToOrtho(false, width / SCALE, height / SCALE);
    }

    @Override
    public void dispose () {
      stickman.dispose();
      spriteBatch.dispose();
      box2DDebugRenderer.dispose();
      world.dispose();
      tiledMapRenderer.dispose();
      tiledMap.dispose();
      runLeftSheet.dispose();
      runRightSheet.dispose();
      jumpLeftSheet.dispose();
      jumpRightSheet.dispose();
    }

  private void inputUpdate () {
      int horizontalForce = 0;
      boolean isJumping = false;

      if (!Gdx.input.isTouched()){
        notMoving();
      }
        if (Gdx.input.isTouched()) {
          Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
          touchPos = orthographicCamera.unproject(touchPos);

          if (touchPos.x / PIXEL_PER_METER > player.getBody().getPosition().x
          ) {
            horizontalForce += 1;
            movingRight();

        } else if (touchPos.x / PIXEL_PER_METER < player.getBody().getPosition().x
          ) {
            horizontalForce -= 1;
            movingLeft();
          }

          if (touchPos.y / PIXEL_PER_METER > player.getBody().getPosition().y)
            isJumping = true;
          {
            horizontalForce += 1;
          }
          if (touchPos.y / PIXEL_PER_METER > player.getBody().getPosition().y &&
                  player.isJumping())
            isJumping = true;
          leftJump();
          {
            horizontalForce -= 1;
          }

        }

        playerUpdate(horizontalForce, isJumping);

  }


    private void playerUpdate ( int horizontalForce, boolean isJumping){
      if (player.isDead()) {
        world.destroyBody(player.getBody());
        player = new Player(world);
      }
      if (isJumping) {
        player.getBody().applyForceToCenter(0, Player.JUMP_FORCE, false);
        player.getBody().setLinearVelocity(horizontalForce * Player.RUN_FORCE, player.getBody().getLinearVelocity().y);

      } else {
        player.getBody().setLinearVelocity(horizontalForce * Player.RUN_FORCE, player.getBody().getLinearVelocity().y);
    }
  }


  private void notMoving() {
    spriteBatch.begin();
    spriteBatch.draw(stickman, player.getBody().getPosition().x * PIXEL_PER_METER - (stickman.getWidth() / 4),
            player.getBody().getPosition().y * PIXEL_PER_METER - (stickman.getHeight() / 4) + 120, 65f, 180f);
    spriteBatch.end();
  }

    private void movingRight() {
        spriteBatch.begin();
        runningRight = runRightAnimation.getKeyFrame(stateTime, true);
        spriteBatch.draw(runningRight, player.getBody().getPosition().x * PIXEL_PER_METER - (runningRight.getTexture().getWidth() / 4) + 190,
                player.getBody().getPosition().y * PIXEL_PER_METER - (runningRight.getTexture().getHeight() / 4) + 120, 150f, 250f);
        spriteBatch.end();
    }

  private void movingLeft() {
        spriteBatch.begin();
        runningLeft = runLeftAnimation.getKeyFrame(stateTime, true);
        spriteBatch.draw(runningLeft, player.getBody().getPosition().x * PIXEL_PER_METER - (runningLeft.getTexture().getWidth() / 4) + 190,
                player.getBody().getPosition().y * PIXEL_PER_METER - (runningLeft.getTexture().getHeight() / 4) + 120, 150f, 250f);
        spriteBatch.end();
  }

  private void rightJump(){
    spriteBatch.begin();
    jumpingRight = jumpRightAnimation.getKeyFrame(stateTime, false);
    spriteBatch.draw(jumpingRight, player.getBody().getPosition().x * PIXEL_PER_METER - (jumpingRight.getTexture().getWidth() / 4) + 190,
            player.getBody().getPosition().y * PIXEL_PER_METER - (jumpingRight.getTexture().getHeight() / 4), 150f, 250f);
    spriteBatch.end();
  }

  private void leftJump() {
    spriteBatch.begin();
    jumpingLeft= jumpLeftAnimation.getKeyFrame(stateTime, true);
    spriteBatch.draw(jumpingLeft, player.getBody().getPosition().x * PIXEL_PER_METER - (jumpingLeft.getTexture().getWidth() / 4) + 190,
            player.getBody().getPosition().y * PIXEL_PER_METER - (jumpingLeft.getTexture().getHeight() / 4), 150f, 250f);
    spriteBatch.end();
  }
}