package com.local.userbase.service.user;

import java.time.LocalDate;

public record User(
        String id,
        String name,
        String phone,
        String email,
        LocalDate registerDate,
        LocalDate dateOfBirth
) {
}
