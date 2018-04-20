package com.codeking.gamificationservice.event.send.api;

import com.codeking.gamificationservice.event.dto.UserStatusUpdatedEvent;

public interface UserStatusUpdatedEventDispatcher {

    void dispatchUserStatusUpdatedEvent(UserStatusUpdatedEvent userStatusUpdatedEvent);
}
