package com.codeking.gamificationservice.service.api;

import com.codeking.gamificationservice.service.dto.UserAttempt;

public interface GameService {

    void processUserAttempt(UserAttempt userAttempt);
}
