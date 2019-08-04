package edu.cnm.deepdive.lordofthesticks.model;

import java.util.UUID;

/**
 * User is an Entity for database fulfillment. The needed information to store in firebase is
 * identified here and there are getters and setters to allow other classes to assign and use the
 * information.
 */
public class User {

  private UUID id;

  private String name;

  public User() {
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
