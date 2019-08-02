package edu.cnm.deepdive.lordofthesticks.model;

/**
 * Arena is an Entity for database fulfillment. The needed information to store in firebase is
 * identified here and there are getters and setters to allow other classes to assign and
 * use the information.
 */
public class Arena {

  private String name;

  private String id;

  private int bottom;

  private int top;

  private int left;

  private int right;

  private int timeLeft;

  public Arena() {}

  public void setName(String name) {
    this.name = name;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setBottom(int bottom) {
    this.bottom = bottom;
  }

  public void setTop(int top) {
    this.top = top;
  }

  public void setLeft(int left) {
    this.left = left;
  }

  public void setRight(int right) {
    this.right = right;
  }

  public void setTimeLeft(int timeLeft) {
    this.timeLeft = timeLeft;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public int getBottom() {
    return bottom;
  }

  public int getTop() {
    return top;
  }

  public int getLeft() {
    return left;
  }

  public int getRight() {
    return right;
  }

  public int getTimeLeft() {
    return timeLeft;
  }
}
