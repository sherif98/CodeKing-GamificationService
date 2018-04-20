package com.codeking.gamificationservice.event.dto;

import com.codeking.gamificationservice.domain.Badge;
import lombok.*;

import java.util.Set;

/**
 * dto represents event when the user status is updated with new badges or new score.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserStatusUpdatedEvent {

    private String userId;
    private Set<Badge> userBadges;
    private int score;
}
