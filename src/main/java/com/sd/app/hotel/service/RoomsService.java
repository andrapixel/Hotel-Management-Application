package com.sd.app.hotel.service;

import com.sd.app.hotel.model.RoomEntity;

import java.util.List;

public interface RoomsService {
    List<RoomEntity> findAllRooms();
    List<RoomEntity> findAllAvailableRooms();
    List<RoomEntity> findByType(String roomType);
    RoomEntity findByRoomNumber(int noRoom);
    void changeRoomAvailability(int id, boolean availability);
    String addRoom(RoomEntity newRoom);
    String deleteRoom(int noRoom);
    String updateRoom(int noRoom, RoomEntity givenRoomDetails);
    String addRoomsFromFile();
}
