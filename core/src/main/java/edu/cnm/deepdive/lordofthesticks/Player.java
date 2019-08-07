package edu.cnm.deepdive.lordofthesticks;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player {
  private static final int BOX_SIZE = 32;
  private static final float PLAYER_DENSITY = 1.0f;
  public static final float JUMP_FORCE = 800f;
  public static final float RUN_FORCE = 25f;
  private static final float PLAYER_START_X = 10f;
  private static final float PLAYER_START_Y = 20f;
  private Body body;
  public boolean isJumpingHigh = false;
  private boolean isDead = false;

  public void hit() {
    isDead = true;
  }
  public void setJumping(boolean jumping) {
    isJumpingHigh = jumping;
  }
  public boolean isJumping() {
    return isJumpingHigh;
  }

  public boolean isDead() {
    return isDead;
  }



  public Player(World world) {
    createBoxBody(world, PLAYER_START_X, PLAYER_START_Y);
  }

  private void createBoxBody(World world, float x, float y) {
    BodyDef bdef = new BodyDef();
    bdef.fixedRotation = true;
    bdef.type = BodyDef.BodyType.DynamicBody;
    bdef.position.set(x, y);
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(BOX_SIZE / StickTest.PIXEL_PER_METER / 2, BOX_SIZE / StickTest.PIXEL_PER_METER / 2);
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = PLAYER_DENSITY;
    body = world.createBody(bdef);
    body.createFixture(fixtureDef).setUserData(this);
  }
  public Body getBody() {
    return body;
  }
}
