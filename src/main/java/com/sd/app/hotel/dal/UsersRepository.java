package com.sd.app.hotel.dal;

import com.sd.app.hotel.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
    List<UserEntity> findByRole(String userRole);
}
