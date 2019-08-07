/*
Copyright 2019 Brian Alexander, John Bailey, Austin DeWitt, Trey Page

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package edu.cnm.deepdive.lordofthesticks.gameplay;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Class to define the Player character that the {@link edu.cnm.deepdive.lordofthesticks.model.User}
 * controllers throughout the game.
 */
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