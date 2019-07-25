package edu.cnm.deepdive.lordofthesticks.model;

public class Arena {

  private String name;

  private int id;

  private int bottom;

  private int top;

  private int left;

  private int right;

  private int timeLeft;



  public Arena(String name, int id, int bottom, int top, int left, int right, int timeLeft) {
    this.name = name;
    this.id = id;
    this.bottom = bottom;
    this.top = top;
    this.left = left;
    this.right = right;
    this.timeLeft = timeLeft;
  }

  public String getName() {
    return name;
  }

  public int getId() {
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
