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

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Class to work with {@link MapParser} to set information according to the various boundaries: {@link Ground}
 * , {@link Bounds}, {@link DangerZone}
 */
public class WorldContactListener implements ContactListener {

  @Override
  public void beginContact(Contact cntct) {
    Fixture fa = cntct.getFixtureA();
    Fixture fb = cntct.getFixtureB();
    if (fa == null || fb == null)
      return;
    if (fa.getUserData() == null || fb.getUserData() == null)
      return;
    if (isGroundContact(fa, fb)) {
      Player player = (Player) fb.getUserData();
      player.setJumping(false);
    }
    if (isDangerContact(fa, fb)) {
      Player player = (Player) fb.getUserData();
      player.setJumping(false);
      player.hit();
    }
  }

  @Override
  public void endContact(Contact cntct) {
    Fixture fa = cntct.getFixtureA();
    Fixture fb = cntct.getFixtureB();
    if (fa == null || fb == null)
      return;
    if (fa.getUserData() == null || fb.getUserData() == null)
      return;
    if (isGroundContact(fa, fb)) {
      Player player = (Player) fb.getUserData();
      player.setJumping(true);
    }
    if (isDangerContact(fa, fb)) {
      Player player = (Player) fb.getUserData();
      player.setJumping(true);
    }
  }

  private boolean isDangerContact(Fixture a, Fixture b) {
    return (a.getUserData() instanceof DangerZone && b.getUserData() instanceof Player);
  }

  private boolean isGroundContact(Fixture a, Fixture b) {
    return (a.getUserData() instanceof Ground && b.getUserData() instanceof Player);
  }

  @Override
  public void preSolve(Contact cntct, Manifold mnfld) {
  }

  @Override
  public void postSolve(Contact cntct, ContactImpulse ci) {
  }
}