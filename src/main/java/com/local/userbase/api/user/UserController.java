package com.local.userbase.api.user;

import com.local.userbase.repository.user.UserRecord;
import com.local.userbase.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;
    private @Valid UserUpdateRequestBody requestUpdateBody;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    // pagination+, sorting and filtering
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<Object> readUser(@RequestParam(value = "id", required = false) String id,
                                    @RequestParam(value = "filter", required = false) String filter,
                                    @RequestParam(value = "sort", defaultValue = "-registerDate") String sort,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "2") int size) {
        if (id != null) {
            // Это херня, нужно возвращать в таком же стиле как и список
            // Список с фильтром по id
            // А лучше вообще нормальный фильтр сделать
            Optional<UserRecord> userRecord = userService.getById(id);
            return ResponseEntity.ok().body(userRecord);
        } else {
//            String direction = String.valueOf(sort.charAt(0));
            String direction = sort.substring(0, 1);
            String sortField = sort.substring(1);

            Sort.Direction sortDirection;
            if (direction.equals("-")) {
                sortDirection = Sort.Direction.DESC;
            } else {
                sortDirection = Sort.Direction.ASC;
            }

            Sort sorting = Sort.by(sortDirection, sortField);
            Pageable paging = PageRequest.of(page, size, sorting);
            Page<UserRecord> result = userService.list(paging);
            return ResponseEntity.ok().body(result);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity<Object> createUser(@Valid @RequestBody UserRequestBody requestBody) {
        // Добавить валидацию полей? email, date, phone?

        UserRecord createdUser = new UserRecord(
                UUID.randomUUID().toString(),
                requestBody.name(),
                requestBody.phone(),
                requestBody.email(),
                (requestBody.registerDate() != null) ? requestBody.registerDate() : LocalDate.now(),
                requestBody.dateOfBirth()
        );

        userService.create(createdUser);

//        return ResponseEntity.ok().body(createdUser);
        // HttpStatus.CREATED = 201 status
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PATCH)
    @ResponseBody
    ResponseEntity<Object> updateUser(@Valid @RequestBody UserUpdateRequestBody requestUpdateBody) {
        Optional<UserRecord> userRecord = userService.getById(requestUpdateBody.id());
        if(userRecord.isEmpty()) {
            // Возвращаю 404 статус. Нужно ли requestUpdateBody делать для этой ошибки?
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(requestUpdateBody);
        }

        UserRecord updatedUser = new UserRecord(
                requestUpdateBody.id(),
                requestUpdateBody.name() != null ? requestUpdateBody.name() : userRecord.get().name(),
                requestUpdateBody.phone() != null ? requestUpdateBody.phone() : userRecord.get().phone(),
                requestUpdateBody.email() != null ? requestUpdateBody.email() : userRecord.get().email(),
                requestUpdateBody.registerDate() != null ? requestUpdateBody.registerDate() : userRecord.get().registerDate(),
                requestUpdateBody.dateOfBirth() != null ? requestUpdateBody.dateOfBirth() : userRecord.get().dateOfBirth()
        );

        userService.update(updatedUser);
        return ResponseEntity.ok().body(updatedUser);
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    @ResponseBody
    ResponseEntity<Object> deleteUser(@Valid @RequestBody UserUpdateRequestBody requestDeleteBody) {
        Optional<UserRecord> userRecord = userService.getById(requestDeleteBody.id());
        if(userRecord.isEmpty()) {
            // Возвращаю 404 статус. Нужно ли requestUpdateBody делать для этой ошибки?
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(requestDeleteBody);
        }
        userService.delete(requestDeleteBody.id());
        return ResponseEntity.ok().body(requestDeleteBody);
    }
}
