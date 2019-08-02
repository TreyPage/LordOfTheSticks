package edu.cnm.deepdive.lordofthesticks.model;

import com.google.firebase.firestore.DocumentReference;

public class Stickman {

  private String name;

  private float userId;

  private int xLocation;

  private int yLocation;

  private int health;

  private boolean alive;

  private int kills;

  private DocumentReference item;

  public Stickman() {}

  public Stickman(String name, float userId, int xLocation, int yLocation, int health,
      boolean alive, int kills, DocumentReference item) {
    this.name = name;
    this.userId = userId;
    this.xLocation = xLocation;
    this.yLocation = yLocation;
    this.health = health;
    this.alive = alive;
    this.kills = kills;
    this.item = item;
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
