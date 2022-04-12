package com.local.userbase.service.user;

import com.local.userbase.repository.user.UserRecord;
import com.local.userbase.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRecord create(UserRecord userRecord) {
        UserRecord createdUser = userRepository.save(userRecord);
        return createdUser;
    }

    public Optional<UserRecord> getById(String id) {
        Optional<UserRecord> curUser = userRepository.findById(id);
        return curUser;
    }

    public List<UserRecord> list() {
        return userRepository.findAll();
    }

    public Page<UserRecord> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public UserRecord update(UserRecord userRecord) {
        UserRecord updatedUser = userRepository.save(userRecord);
        return updatedUser;
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
