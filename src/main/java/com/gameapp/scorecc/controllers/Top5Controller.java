package com.gameapp.scorecc.controllers;

import com.gameapp.scorecc.model.UserScoreDTO;
import com.gameapp.scorecc.dbaccessors.UserScoreService;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller // This means that this class is a Controller
@RequestMapping(path="/scores") // This means URL's start with /demo (after Application path)
public class Top5Controller {

  @Autowired
  private UserScoreService userScoreService;

  /**
   * Gives the cached list of the top5 users in the scoreboard
   * May not be accurate but is fast API
   * @return list of to <=5 users
   */
  @GetMapping("/top5")
  public @ResponseBody List<UserScoreDTO> getTop5Scores() {
    try {
      return userScoreService.getTop5ScoresFromDB();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gives the list of the top5 users in the scoreboard
   * May be slow but is very accurate
   * @return list of to <=5 users
   */
  @GetMapping("/top5_accurate")
  public @ResponseBody List<UserScoreDTO> getTop5ScoresAccurate() {
    try {
      return userScoreService.getTop5ScoresFromDB_NoCache();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * If the given userID has a top score in top 5, call this method would ensure that
   * they won't see confusing value
   * @param userId
   * @return
   */
  @GetMapping("/top5/{userId}")
  public @ResponseBody List<UserScoreDTO> getTop5Scores(@PathVariable Integer userId) {
    try {
      List<UserScoreDTO> newList = ListUtils.union(userScoreService.getTop5ScoresFromDB(),
              userScoreService.findScoresByUserId(userId));
      newList.sort((o1, o2) -> o2.getScoreValue().compareTo(o1.getScoreValue()));
      if(newList.size() < 5){
        return newList;
      }
      return newList.subList(0, 5);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}