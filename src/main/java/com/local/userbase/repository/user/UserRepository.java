package com.local.userbase.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserRecord, String> {
}
