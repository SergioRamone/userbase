package com.local.userbase.api.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record UserRequestBody(
        String id,
        @NotNull String name,
        @NotNull String phone,
        @NotNull @Email String email,
        LocalDate registerDate,
        @NotNull LocalDate dateOfBirth
) {
}
