package com.codeking.gamificationservice.controller;

import com.codeking.gamificationservice.domain.User;
import com.codeking.gamificationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
public class LeaderBoardController {

  @Autowired
  private UserRepository userRepository;


  @GetMapping
  public List<User> getLeaderBoard() {
    return userRepository.findTop10UsersByOrderByScoreDesc();
  }
}
