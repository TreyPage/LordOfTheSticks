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
package edu.cnm.deepdive.lordofthesticks.model;

import com.google.firebase.firestore.DocumentReference;

/**
 * Stickman is an Entity for database fulfillment. The needed information to store in firebase is
 * identified here and there are getters and setters to allow other classes to assign and use the
 * information.
 */
public class Stickman {

  private String name;

  private float userId;

  private int xLocation;

  private int yLocation;

  private int health;

  private boolean alive;

  private int kills;

  private DocumentReference item;

  public Stickman() {
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUserId(float userId) {
    this.userId = userId;
  }

  public void setxLocation(int xLocation) {
    this.xLocation = xLocation;
  }

  public void setyLocation(int yLocation) {
    this.yLocation = yLocation;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  public void setKills(int kills) {
    this.kills = kills;
  }

  public void setItem(DocumentReference item) {
    this.item = item;
  }

  public String getName() {
    return name;
  }

  public float getUserId() {
    return userId;
  }

  public int getxLocation() {
    return xLocation;
  }

  public int getyLocation() {
    return yLocation;
  }

  public int getHealth() {
    return health;
  }

  public boolean isAlive() {
    return alive;
  }

  public int getKills() {
    return kills;
  }

  public DocumentReference getItem() {
    return item;
  }
}
