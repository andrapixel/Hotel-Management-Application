package com.sd.app.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginMessage {
    String message;
    Boolean status;
    Long userId;
    String role;
}
