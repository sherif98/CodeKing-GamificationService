package com.codeking.gamificationservice.service.impl;

import com.codeking.gamificationservice.domain.Badge;
import com.codeking.gamificationservice.domain.ProgrammingLanguage;
import com.codeking.gamificationservice.domain.SubmissionStatus;
import com.codeking.gamificationservice.domain.User;
import com.codeking.gamificationservice.event.dto.UserStatusUpdatedEvent;
import com.codeking.gamificationservice.event.send.api.UserStatusUpdatedEventDispatcher;
import com.codeking.gamificationservice.repository.ProblemRepository;
import com.codeking.gamificationservice.repository.UserRepository;
import com.codeking.gamificationservice.service.api.GameService;
import com.codeking.gamificationservice.service.dto.UserAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private UserStatusUpdatedEventDispatcher userStatusUpdatedEventDispatcher;

    @Override
    public void processUserAttempt(UserAttempt userAttempt) {
        if (userAttempt.getSubmissionStatus() == SubmissionStatus.ACCEPTED) {
            processAcceptedUserAttempt(userAttempt);
        }
    }

    private void processAcceptedUserAttempt(UserAttempt userAttempt) {
        int scoreToBeAdded = getProblemScore(userAttempt.getProblemId());
        Optional<User> userEntity = userRepository.findById(userAttempt.getUserId());
        User user = userEntity.orElseGet(() -> createNewUser(userAttempt.getUserId()));
        applyNewProblemSolved(userAttempt.getProgrammingLanguage(), user, userAttempt.getProblemId(), scoreToBeAdded);
        user.setBadges(findBadgesToEarn(user));
        userRepository.save(user);
        dispatchUserStatusUpdatedEvent(user);
    }

    private void dispatchUserStatusUpdatedEvent(User user) {
        UserStatusUpdatedEvent userStatusUpdatedEvent = UserStatusUpdatedEvent.builder()
                .userId(user.getUserId())
                .score(user.getScore())
                .userBadges(user.getBadges()).build();
        userStatusUpdatedEventDispatcher.dispatchUserStatusUpdatedEvent(userStatusUpdatedEvent);
    }

    private void updateUserScore(int scoreToBeAdded, User user) {
        user.setScore(user.getScore() + scoreToBeAdded);
    }

    private void applyNewProblemSolved(ProgrammingLanguage languageUsedToSolveProblem,
                                       User user, String newSolvedProblemId, int scoreToBeAdded) {

        Set<String> problemsSolvedIds = user.getProgrammingLanguageToSolvedProblems().get(languageUsedToSolveProblem);
        if (!problemsSolvedIds.contains(newSolvedProblemId)) {
            updateUserScore(scoreToBeAdded, user);
            problemsSolvedIds.add(newSolvedProblemId);
        }
    }

    private Set<Badge> findBadgesToEarn(User user) {
        Map<ProgrammingLanguage, Set<String>> programmingLanguageToSolvedProblems = user.getProgrammingLanguageToSolvedProblems();
        Set<Badge> alreadyEarnedBadges = user.getBadges();
        Set<Badge> badgesThatCanBeEarned = EnumSet.allOf(Badge.class);
        badgesThatCanBeEarned.removeAll(alreadyEarnedBadges);
        return badgesThatCanBeEarned.stream()
                .filter(badge -> badge.shouldEarnBadge().test(programmingLanguageToSolvedProblems))
                .collect(Collectors.toSet());
    }


    private User createNewUser(String userId) {
        Map<ProgrammingLanguage, Set<String>> programmingLanguageToSolvedProblems =
                EnumSet.allOf(ProgrammingLanguage.class)
                        .stream()
                        .collect(Collectors.toMap(Function.identity(), language -> Collections.emptySet()));
        return User.builder()
                .userId(userId)
                .badges(Collections.emptySet())
                .programmingLanguageToSolvedProblems(programmingLanguageToSolvedProblems)
                .build();
    }

    private int getProblemScore(String problemId) {
        return problemRepository.findById(problemId)
                .flatMap(problem -> Optional.of(problem.getProblemScore()))
                .orElse(0);
    }
}
