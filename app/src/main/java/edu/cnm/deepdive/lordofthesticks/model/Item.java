package edu.cnm.deepdive.lordofthesticks.model;

import com.google.firebase.firestore.DocumentReference;

/**
 * Item is an Entity for database fulfillment. The needed information to store in firebase is
 * identified here and there are getters and setters to allow other classes to assign and
 * use the information.
 */
public class Item {

  private float xLocation;

  private float yLocation;

  private boolean owned;

  private DocumentReference itemType;

  public Item() {
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
