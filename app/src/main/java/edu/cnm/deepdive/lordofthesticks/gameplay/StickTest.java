package edu.cnm.deepdive.lordofthesticks.gameplay;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StickTest extends ApplicationAdapter {

  private static final float SCALE = 1.5f;
  public static final float PIXEL_PER_METER = 32f;
  private static final float TIME_STEP = 1 / 60f;
  private static final int VELOCITY_ITERATIONS = 6;
  private static final int POSITION_ITERATIONS = 2;
  private static final float VELOCITY_Y = -9.85f;
  private static final float VELOCITY_X = 0f;
  private static final String MAP_PATH = "map/GameMap.tmx";
  private OrthographicCamera orthographicCamera;
  private Box2DDebugRenderer box2DDebugRenderer;
  private Viewport gamePort;
  private World world;
  private Player player;
  public static SpriteBatch batch;
  private Controller controller;
  private Texture texture;
  private OrthogonalTiledMapRenderer tiledMapRenderer;
  private TiledMap tiledMap;

  public static final int V_Height = 500;
  public static final int V_WIDTH = 1000;

  @Override
  public void create() {
    orthographicCamera = new OrthographicCamera();
    gamePort = new FitViewport(StickTest.V_WIDTH,StickTest.V_Height,orthographicCamera);
    orthographicCamera.position.set(gamePort.getWorldWidth() / 2,gamePort.getWorldHeight() /2 , 0);
//    orthographicCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    world = new World(new Vector2(VELOCITY_X, VELOCITY_Y), false);
    batch = new SpriteBatch();
    texture = new Texture(Player.PLAYER_IMG_PATH);
    controller = new Controller();
    box2DDebugRenderer = new Box2DDebugRenderer();
    tiledMap = new TmxMapLoader().load(MAP_PATH);
    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    MapParser.parseMapLayers(world, tiledMap);
    player = new Player(world);
    world.setContactListener(new WorldContactListener());
    
  }

  @Override
  public void render() {
    update();
    Gdx.gl.glClearColor(0.0f, 0.0f, 0f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    tiledMapRenderer.render();
    controller.draw();
    batch.setProjectionMatrix(orthographicCamera.combined);

    batch.begin();

    // All drawing goes in here! Will have to add in the crap here. The texture comes first, then x and y coordinates, then width then height.

    batch.draw(texture, player.getBody().getPosition().x * PIXEL_PER_METER - (texture.getWidth() / 4),
        player.getBody().getPosition().y * PIXEL_PER_METER - (texture.getHeight() / 4), 60f, 150f);
    batch.end();
  }

  private void update() {
    world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

    inputUpdate();

    cameraUpdate();
    tiledMapRenderer.setView(orthographicCamera);

  }

  private void cameraUpdate() {
    Vector3 position = orthographicCamera.position;
    position.x = player.getBody().getPosition().x * PIXEL_PER_METER;
    position.y = player.getBody().getPosition().y * PIXEL_PER_METER;
    orthographicCamera.position.set(position);
    orthographicCamera.update();
  }

  @Override
  public void resize(int width, int height) {
    orthographicCamera.setToOrtho(false, width / SCALE, height / SCALE);
    controller.resize(width, height);
  }

  @Override
  public void dispose() {
    texture.dispose();
    batch.dispose();
    box2DDebugRenderer.dispose();
    world.dispose();
    tiledMapRenderer.dispose();
    tiledMap.dispose();
  }
  private void inputUpdate() {
//    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//      if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
//        player.getBody()
//            .applyLinearImpulse(new Vector2(-200f, 0), player.getBody().getWorldCenter(), true);
//      if (Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
//        player.getBody()
//            .applyLinearImpulse(new Vector2(200f, 0), player.getBody().getWorldCenter(), true);


//    if (Gdx.input.isKeyJustPressed(Keys.RIGHT)&& player.getBody().getLinearVelocity().x<= 7)
//      player.getBody().applyLinearImpulse(new Vector2(0.1f, 0),player.getBody().getWorldCenter(),true);
//    if (Gdx.input.isKeyJustPressed(Keys.UP))
//      player.getBody().applyLinearImpulse(new Vector2(0,4f), player.getBody().getWorldCenter(),true);
//    if (Gdx.input.isKeyJustPressed(Keys.RIGHT)&& player.getBody().getLinearVelocity().x<= 7)
//      player.getBody().applyLinearImpulse(new Vector2(0.1f, 0),player.getBody().getWorldCenter(),true);
//    if (Gdx.input.isKeyJustPressed(Keys.LEFT)&& player.getBody().getLinearVelocity().x<= -7)
//      player.getBody().applyLinearImpulse(new Vector2(-0.1f, 0),player.getBody().getWorldCenter(),true);
//  }


    if (controller.isRightPressed()){
      player.getBody()
          .applyLinearImpulse(new Vector2(10, 0), player.getBody().getWorldCenter(), true);
    }
    else if (controller.isLeftPressed())
      player.getBody()
          .applyLinearImpulse(new Vector2(-20f, 0), player.getBody().getWorldCenter(), true);
    else
      player.getBody().setLinearVelocity(new Vector2(0, player.getBody().getLinearVelocity().y));
    if (controller.isUpPressed() && player.getBody().getLinearVelocity().y == 0)
      player.getBody()
          .applyLinearImpulse(new Vector2(0, 100f), player.getBody().getWorldCenter(), true);
    }
//  private void inputUpdate() {
//    int horizontalForce = 0;
//    boolean isJumping = false;
//    if (Gdx.input.isTouched()) {
//      Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
//      touchPos = orthographicCamera.unproject(touchPos);
//      if (touchPos.x / PIXEL_PER_METER > player.getBody().getPosition().x)
//        horizontalForce += 1;
//      if (touchPos.x / PIXEL_PER_METER < player.getBody().getPosition().x)
//        horizontalForce -= 1;
//      if (touchPos.y / PIXEL_PER_METER > player.getBody().getPosition().y && !player.isJumping())
//        isJumping = true;
//    }
//    playerUpdate(horizontalForce, isJumping);
//  }

    private void playerUpdate ( int horizontalForce, boolean isJumping){
      if (player.isDead()) {
        world.destroyBody(player.getBody());
        player = new Player(world);
      }
      if (isJumping)
        player.getBody().applyForceToCenter(0, Player.JUMP_FORCE, false);
      player.getBody().setLinearVelocity(horizontalForce * Player.RUN_FORCE,
          player.getBody().getLinearVelocity().y);
    }

  }