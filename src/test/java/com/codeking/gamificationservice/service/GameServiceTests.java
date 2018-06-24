package com.codeking.gamificationservice.service;


import com.codeking.gamificationservice.domain.*;
import com.codeking.gamificationservice.event.dto.UserStatusUpdatedEvent;
import com.codeking.gamificationservice.event.send.api.UserStatusUpdatedEventDispatcher;
import com.codeking.gamificationservice.repository.ProblemRepository;
import com.codeking.gamificationservice.repository.UserRepository;
import com.codeking.gamificationservice.service.api.GameService;
import com.codeking.gamificationservice.service.dto.UserAttempt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTests {


    @Autowired
    private GameService gameService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProblemRepository problemRepository;

    @MockBean
    private UserStatusUpdatedEventDispatcher userStatusUpdatedEventDispatcher;

    @Test
    public void shouldNotProcessAttemptWhenSubmissionStatusIsWrongAnswer() {
        //given
        UserAttempt userAttempt = UserAttempt.builder().submissionStatus(SubmissionStatus.WRONG_ANSWER).build();
        //when
        gameService.processUserAttempt(userAttempt);
        //then
        verify(problemRepository, never()).findById("");
    }

    @Test
    public void shouldNotProcessAttemptWhenSubmissionStatusIsTimeLimitExceeded() {
        //given
        UserAttempt userAttempt = UserAttempt.builder().submissionStatus(SubmissionStatus.TIME_LIMIT_EXCEEDED).build();
        //when
        gameService.processUserAttempt(userAttempt);
        //then
        verify(problemRepository, never()).findById("");
    }

    @Test
    public void shouldNotProcessAttemptWhenSubmissionStatusIsRunTimeError() {
        //given
        UserAttempt userAttempt = UserAttempt.builder().submissionStatus(SubmissionStatus.RUNTIME_ERROR).build();
        //when
        gameService.processUserAttempt(userAttempt);
        //then
        verify(problemRepository, never()).findById("");
    }

    @Test
    public void shouldNotProcessAttemptWhenSubmissionStatusIsCompilationError() {
        //given
        UserAttempt userAttempt = UserAttempt.builder().submissionStatus(SubmissionStatus.COMPILATION_ERROR).build();
        //when
        gameService.processUserAttempt(userAttempt);
        //then
        verify(problemRepository, never()).findById("");
    }

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    public void shouldProcessAttemptsCorrectlyWhenSubmissionStatusIsAccepted() {
        //given
        UserAttempt userAttempt = UserAttempt.builder().
                submissionStatus(SubmissionStatus.ACCEPTED)
                .problemId("1")
                .userId("2")
                .programmingLanguage(ProgrammingLanguage.JAVA)
                .build();
        Problem problem = Problem.builder().problemId("1").problemScore(100).build();
        User user = new User("2");
        when(problemRepository.findById("1")).thenReturn(Optional.of(problem));
        when(userRepository.findById("2")).thenReturn(Optional.of(user));
        //when
        gameService.processUserAttempt(userAttempt);
        //then
        verify(problemRepository, times(1)).findById("1");
        verify(userRepository, times(1)).findById("2");
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        assertEquals(userArgumentCaptor.getValue().getBadges().size(), 1);
        assertTrue(userArgumentCaptor.getValue().getBadges().contains(Badge.FIRST_PROBLEM_SOLVED));
        assertTrue(userArgumentCaptor.getValue().getProgrammingLanguageToSolvedProblems().get(ProgrammingLanguage.JAVA).contains("1"));
        assertEquals(userArgumentCaptor.getValue().getScore(), 100);
    }

    @Captor
    private ArgumentCaptor<UserStatusUpdatedEvent> statusUpdatedEventArgumentCaptor;

    @Test
    public void shouldDispatchUserStatusUpdatedEventAfterProcessNewAttempt() {
        //given
        UserAttempt userAttempt = UserAttempt.builder().
                submissionStatus(SubmissionStatus.ACCEPTED)
                .problemId("1")
                .userId("2")
                .programmingLanguage(ProgrammingLanguage.JAVA)
                .build();
        Problem problem = Problem.builder().problemId("1").problemScore(100).build();
        User user = new User("2");
        when(problemRepository.findById("1")).thenReturn(Optional.of(problem));
        when(userRepository.findById("2")).thenReturn(Optional.of(user));
        //when
        gameService.processUserAttempt(userAttempt);
        //then
        verify(userStatusUpdatedEventDispatcher, times(1))
                .dispatchUserStatusUpdatedEvent(statusUpdatedEventArgumentCaptor.capture());
        assertEquals(statusUpdatedEventArgumentCaptor.getValue().getUserId(), "2");
        assertEquals(statusUpdatedEventArgumentCaptor.getValue().getScore(), 100);
        assertTrue(statusUpdatedEventArgumentCaptor.getValue().getUserBadges().contains(Badge.FIRST_PROBLEM_SOLVED));
    }
}
