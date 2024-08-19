package com.gameapp.scorecc.controllers;

import com.gameapp.scorecc.model.User;
import com.gameapp.scorecc.dbaccessors.UserRepository;
import com.gameapp.scorecc.model.UserScore;
import com.gameapp.scorecc.dbaccessors.UserScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * These APIs are being used for testing and debugging purpose and temporary
 * Should not be exposed / pushed as they would cause data leak
 */
@Controller // This means that this class is a Controller
@RequestMapping(path="/db") // This means URL's start with /demo (after Application path)
public class TestingController {

  @Autowired // Auto generated repository bean
  private UserRepository userRepository;

  @Autowired // Auto generated repository bean
  private UserScoreRepository userScoreRepository;

  /**
   * Shows all the players stored in the DB
   * @return a list of players stored in the db
   */
  @GetMapping(path="/allUsers")
  public @ResponseBody Iterable<User> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }

  /**
   * Shows all the scores stored in the DB
   * @return a list of scores stored in the db
   */
  @GetMapping(path="/allScores")
  public @ResponseBody Iterable<UserScore> getAllScores() {
    // This returns a JSON or XML with the users
    return userScoreRepository.findAll();
  }

  /**
   * Cleans all the data present in the db
   * @return a string indicating that the process has been completed
   */
  @GetMapping(path="/reset")
  public @ResponseBody String cleanTables() {
    // This cleans the tables
    userRepository.deleteAll();
    userScoreRepository.deleteAll();
    return "Cleaned";
  }
}