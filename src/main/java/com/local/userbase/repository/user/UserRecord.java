package com.local.userbase.repository.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "usersTable")
public record UserRecord(
        @Id String id,
        String name,
        String phone,
        String email,
        LocalDate registerDate,
        LocalDate dateOfBirth
) {
}
