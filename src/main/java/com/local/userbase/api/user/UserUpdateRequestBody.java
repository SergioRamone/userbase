package com.local.userbase.api.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record UserUpdateRequestBody(
        @NotNull String id,
        String name,
        String phone,
        @Email String email,
        LocalDate registerDate,
        LocalDate dateOfBirth
) {
}
