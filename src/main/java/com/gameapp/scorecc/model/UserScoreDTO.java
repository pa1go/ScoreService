package com.gameapp.scorecc.model;

import lombok.Getter;

import java.sql.Date;

/**
 * Used by the DB accessors to combine the data from User table and UserScore Table
 */
@Getter
public class UserScoreDTO {

  private Integer scoreValue;
  private Date scoreDate;
  private String name;
  private String email;
  private String userName;

  public UserScoreDTO(Integer scoreValue, Date scoreDate, String name, String email, String userName) {
    this.scoreValue = scoreValue;
    this.scoreDate = scoreDate;
    this.name = name;
    this.email = email;
    this.userName = userName;
  }
}
