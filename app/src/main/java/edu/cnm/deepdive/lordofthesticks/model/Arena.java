package edu.cnm.deepdive.lordofthesticks.model;

public class Arena {

  private String name;

  private String id;

  private int bottom;

  private int top;

  private int left;

  private int right;

  private int timeLeft;

  public Arena() {}

  public Arena(String name, String id, int bottom, int top, int left, int right, int timeLeft) {
    this.name = name;
    this.id = id;
    this.bottom = bottom;
    this.top = top;
    this.left = left;
    this.right = right;
    this.timeLeft = timeLeft;
  }

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
