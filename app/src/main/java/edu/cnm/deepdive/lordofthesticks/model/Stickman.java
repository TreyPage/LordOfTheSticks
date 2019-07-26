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
      boolean alive,
      int kills, DocumentReference item) {
    this.name = name;
    this.userId = userId;
    this.xLocation = xLocation;
    this.yLocation = yLocation;
    this.health = health;
    this.alive = alive;
    this.kills = kills;
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
