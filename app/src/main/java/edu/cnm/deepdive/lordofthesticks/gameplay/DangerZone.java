package edu.cnm.deepdive.lordofthesticks.gameplay;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Class for the {@link MapParser} to look for object layers called 'Danger Zone' and give them density for collision detection. Used for spikes or pits etc.
 */
public class DangerZone {
  private static final float DENSITY = 1.0f;
  public DangerZone(World world, Shape shape) {
    Body body;
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.StaticBody;
    body = world.createBody(bodyDef);
    body.createFixture(shape, DENSITY).setUserData(this);
    shape.dispose();
  }
}