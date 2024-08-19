package com.gameapp.scorecc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.sql.Date;

@Data
@Entity // This tells Hibernate to make a table out of this class
public class UserScore {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer scoreId;

  @ManyToOne
  @JoinColumn(name = "id")
  private User user;

  private Integer scoreValue;

  private Date scoreDate;
}