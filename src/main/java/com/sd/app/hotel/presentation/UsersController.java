package com.sd.app.hotel.presentation;

import com.sd.app.hotel.dto.LoginRequestDto;
import com.sd.app.hotel.model.LoginMessage;
import com.sd.app.hotel.model.UserEntity;
import com.sd.app.hotel.model.UserRole;
import com.sd.app.hotel.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.function.LongFunction;

@RestController
@CrossOrigin
@RequestMapping("api/hotel")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> users = usersService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/guests")
    public ResponseEntity<List<UserEntity>> getGuests() {
        List<UserEntity> guestsList = usersService.findByRole(String.valueOf(UserRole.GUEST));
        return ResponseEntity.ok(guestsList);
    }

    @GetMapping(value = "/admins")
    public ResponseEntity<List<UserEntity>> getAdmins() {
        List<UserEntity> adminsList = usersService.findByRole(String.valueOf(UserRole.ADMIN));
        return ResponseEntity.ok(adminsList);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserEntity userEntity) {
        return usersService.saveUser(userEntity);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto) {
        LoginMessage loginMessage = usersService.login(loginDto);
        return ResponseEntity.ok(loginMessage);
    }

    @DeleteMapping("/delete-user/{id}")
    public String delete(@PathVariable long id) {
        return usersService.deleteUser(id);
    }

    @PutMapping("/update-user/{id}")
    public String update(@PathVariable long id, @RequestBody UserEntity userEntity) {
        return usersService.updateUser(id, userEntity);
    }
}
