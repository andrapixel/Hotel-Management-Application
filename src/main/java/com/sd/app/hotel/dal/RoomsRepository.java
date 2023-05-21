package com.sd.app.hotel.dal;

import com.sd.app.hotel.model.RoomEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomsRepository extends JpaRepository<RoomEntity, Integer> {
    List<RoomEntity> findByType(String roomType);
}
