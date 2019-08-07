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

/**
 * Arena is an Entity for database fulfillment. The needed information to store in firebase is
 * identified here and there are getters and setters to allow other classes to assign and use the
 * information.
 */
public class Arena {

  private String name;

  private String id;

  private int bottom;

  private int top;

  private int left;

  private int right;

  private int timeLeft;

  public Arena() {
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
