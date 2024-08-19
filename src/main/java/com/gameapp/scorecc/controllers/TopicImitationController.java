package com.gameapp.scorecc.controllers;

import com.gameapp.scorecc.model.User;
import com.gameapp.scorecc.dbaccessors.UserRepository;
import com.gameapp.scorecc.model.UserScore;
import com.gameapp.scorecc.dbaccessors.UserScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Immitates users traffic on the service by adding user or adding scores to the user
 * Note: These APIs are not optimised because these are used for debugging purposes.
 */
@Controller // This means that this class is a Controller
@RequestMapping(path="/game") // This means URL's start with /demo (after Application path)
public class TopicImitationController {
  @Autowired // This means to get the bean called userRepository
         // Which is auto-generated by Spring, we will use it to handle the data
  private UserRepository userRepository;

  @Autowired
  private UserScoreRepository userScoreRepository;

  private Random random = new Random();

  private ScheduledExecutorService scheduler;

  /**
   * Adds given number of random players to the db with given count
   * @return string indicating that the process has been done
   */
  @PostMapping(path="/addPlayers") // Map ONLY POST Requests
  public @ResponseBody String startPlay (@RequestParam int count) {

    List<String> firstNames = Arrays.asList(
        "Liam", "Emma", "Noah", "Olivia", "William", "Ava", "James", "Isabella", "Oliver", "Sophia"
    );
    List<String> lastNames = Arrays.asList(
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"
    );

    IntStream.range(0, count).forEach(i -> {
      String firstName = firstNames.get(random.nextInt(firstNames.size()));
      String lastName = lastNames.get(random.nextInt(lastNames.size()));
      String fullName = firstName + " " + lastName;

      User user = new User();
      user.setName(fullName);
      user.setEmail(firstName.toLowerCase() + "." + lastName.toLowerCase() + i + "@example.com");
      user.setUserName(firstName.toLowerCase() + lastName.toLowerCase() + random.nextInt(1000));
      userRepository.save(user);
    });

    return "Done";
  }

  /**
   * Starts publishing random scores to random users in the DB to imitate
   * data coming from a topic.
   * @return string indicating that the process has been started
   */
  @GetMapping(path="/publishScores") // Map ONLY POST Requests
  public @ResponseBody String publishScores () {
    scheduleScoreAssignment();
    return "Started";
  }

  /**
   * Stops publishing random scores to random users in the DB to imitate
   * data coming from a topic.
   * @return string indicating that the process has been stopped
   */
  @GetMapping(path="/stopPublishScores") // Map ONLY POST Requests
  public @ResponseBody String stopPublishScores () {
    if(Objects.nonNull(scheduler)){
      scheduler.shutdown();
    }
    return "Stopped";
  }

  /**
   * Creates a threadpool and assigns tasks to the threadpool randomly
   * task being to assign a selected user score.
   */
  private void scheduleScoreAssignment() {
    scheduler = Executors.newScheduledThreadPool(1);

    Runnable task = () -> {
      for (int i = 0; i < 5; i++) {
        long delay = random.nextInt(30); // Random delay between 0 and 29 seconds
        scheduler.schedule(this::assignRandomScore, delay, TimeUnit.SECONDS);
      }
    };

    scheduler.scheduleAtFixedRate(task, 0, 30, TimeUnit.SECONDS);
  }

  /**
   * Fetches the current list of users selects a random user
   * and assigns a random score to the user
   */
  private void assignRandomScore() {
    List<User> users = (List<User>) userRepository.findAll();
    User randomPlayer = users.get(random.nextInt(users.size()));

    UserScore score = new UserScore();
    score.setUser(randomPlayer);
    score.setScoreValue(random.nextInt(20000));
    score.setScoreDate(new Date(1724009317374L));

    userScoreRepository.save(score);
    System.out.println("Assigned score: " + score.getScoreValue() + " to player ID: " + randomPlayer.getId());
  }
}