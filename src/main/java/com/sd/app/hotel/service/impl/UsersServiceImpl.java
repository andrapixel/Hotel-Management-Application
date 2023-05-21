package com.sd.app.hotel.service.impl;

import com.sd.app.hotel.dal.UsersRepository;
import com.sd.app.hotel.dto.LoginRequestDto;
import com.sd.app.hotel.model.LoginMessage;
import com.sd.app.hotel.model.UserEntity;
import com.sd.app.hotel.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> findAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public UserEntity findById(long id) {
        Optional<UserEntity> foundUser = usersRepository.findById(id);

        if (foundUser.isEmpty()) {
            try {
                throw new Exception("User with ID " + id + " could not be found.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return foundUser.get();
    }

    @Override
    public List<UserEntity> findByRole(String userRole) {
         return usersRepository.findByRole(userRole);
    }

    @Override
    public String saveUser(UserEntity newUserEntity) {
        newUserEntity.setPassword(passwordEncoder.encode(newUserEntity.getPassword()));
        usersRepository.save(newUserEntity);
        return "New user registered successfully.";
    }

    @Override
    public LoginMessage login(LoginRequestDto loginDto) {
        String msg = "";
        UserEntity user = usersRepository.findByUsername(loginDto.getUsername());
        if (user != null) {
            String password = loginDto.getPassword();
            String encodedPassword = user.getPassword();
            Boolean pwdCorrect = passwordEncoder.matches(password, encodedPassword);

            if (pwdCorrect) {
                return new LoginMessage("Login successful.", true, user.getUserId(), user.getRole());
            }
            else {
                return new LoginMessage("Incorrect password.", false, null, "");
            }
        }
        else {
            return new LoginMessage("User could not be found.", false, null, "");
        }
    }

    @Override
    public String deleteUser(long id) {
        if (id == 0 || id < 0) {
            throw new InvalidParameterException("Invalid ID.\n");
        }

        Optional<UserEntity> optional = usersRepository.findById(id);

        if (optional.isEmpty()) {
            try {
                throw new Exception("User with ID " + id + " could not be found.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        usersRepository.deleteById(id);
        return "User deleted successfully.";
    }

    @Override
    public String updateUser(long id, UserEntity givenUserEntity) {
        Optional<UserEntity> optional = usersRepository.findById(id);

        if (optional.isEmpty()) {
            try {
                throw new Exception("User with ID " + id + " could not be found.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        UserEntity updatedUserEntity = new UserEntity(optional.get().getUserId(), givenUserEntity.getFirstName(),
                givenUserEntity.getLastName(), givenUserEntity.getEmail(), givenUserEntity.getUsername(),
                passwordEncoder.encode(givenUserEntity.getPassword()), givenUserEntity.getRole());

        usersRepository.save(updatedUserEntity);
        return "User updated successfully.";
    }
}
