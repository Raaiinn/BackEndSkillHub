package com.skillhub.skillhub.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private String token;
    private String role;
    private Boolean active;
}
