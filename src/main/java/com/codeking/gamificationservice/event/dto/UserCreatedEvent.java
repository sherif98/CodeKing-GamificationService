package com.codeking.gamificationservice.event.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCreatedEvent {

    private String userId;
    private String userName;
}
