package edu.cnm.deepdive.lordofthesticks.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import edu.cnm.deepdive.lordofthesticks.gameplay.StickTest;

public class Bow extends Sprite {
  protected StickTest game;
  protected World world;
  protected Vector2 velocity;
  protected Boolean stagnant;
  protected Boolean isPickedUp;
  protected Body body;

  public Item( StickTest game ,float x, float y){
    this.game = game;
    setPosition(x,);


  }
}
