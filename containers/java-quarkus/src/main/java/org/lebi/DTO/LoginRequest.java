package org.lebi.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
