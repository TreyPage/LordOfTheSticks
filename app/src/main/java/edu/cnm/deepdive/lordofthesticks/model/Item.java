package edu.cnm.deepdive.lordofthesticks.model;

import com.google.firebase.firestore.DocumentReference;

public class Item {

  private float xLocation;

  private float yLocation;

  private boolean owned;

  private DocumentReference itemType;

  public Item(){}

  public Item(float xLocation, float yLocation, boolean owned,
      DocumentReference itemType) {
    this.xLocation = xLocation;
    this.yLocation = yLocation;
    this.owned = owned;
    this.itemType = itemType;
  }

  public void setxLocation(float xLocation) {
    this.xLocation = xLocation;
  }

  public void setyLocation(float yLocation) {
    this.yLocation = yLocation;
  }

  public void setOwned(boolean owned) {
    this.owned = owned;
  }

  public void setItemType(DocumentReference itemType) {
    this.itemType = itemType;
  }

  public DocumentReference getItemType() {
    return itemType;
  }

  public float getxLocation() {
    return xLocation;
  }

  public float getyLocation() {
    return yLocation;
  }

  public boolean isOwned() {
    return owned;
  }
}
