package edu.cnm.deepdive.lordofthesticks.model;

import java.util.UUID;

public class User {

  private UUID id;

  private String name;

  public User() {}

  public User(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
