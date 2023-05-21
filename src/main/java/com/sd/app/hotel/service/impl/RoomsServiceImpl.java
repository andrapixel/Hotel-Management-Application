package com.sd.app.hotel.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.app.hotel.dal.RoomsRepository;
import com.sd.app.hotel.model.RoomEntity;
import com.sd.app.hotel.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomsServiceImpl implements RoomsService {
    @Autowired
    private RoomsRepository roomsRepository;

    @Override
    public List<RoomEntity> findAllRooms() {
        return roomsRepository.findAll();
    }

    @Override
    public List<RoomEntity> findAllAvailableRooms() {
        List<RoomEntity> availableRooms = new ArrayList<>();
        for (RoomEntity roomEntity: findAllRooms()) {
            if (roomEntity.isAvailable())
                availableRooms.add(roomEntity);
        }

        return availableRooms;
    }

    @Override
    public List<RoomEntity> findByType(String roomType) {
        return roomsRepository.findByType(roomType);
    }

    @Override
    public RoomEntity findByRoomNumber(int noRoom) {
        Optional<RoomEntity> foundRoom = roomsRepository.findById(noRoom);

        if (foundRoom.isEmpty()) {
            try {
                throw new Exception("Room number " + noRoom + " could not be found.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return foundRoom.get();
    }

    @Override
    public void changeRoomAvailability(int id, boolean availability) {
        RoomEntity room = findByRoomNumber(id);
        room.setAvailable(availability);
        roomsRepository.save(room);
    }

    @Override
    public String addRoom(RoomEntity newRoom) {
        roomsRepository.save(newRoom);
        return "New room added successfully.";
    }

    @Override
    public String deleteRoom(int noRoom) {
        if (noRoom == 0 || noRoom < 0) {
            throw new InvalidParameterException("Invalid room number.\n");
        }

        Optional<RoomEntity> optional = roomsRepository.findById(noRoom);
        if (optional.isEmpty()) {
            try {
                throw new Exception("Room number " + noRoom + " could not be found.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        roomsRepository.deleteById(noRoom);
        return "Room deleted successfully.";
    }

    @Override
    public String updateRoom(int noRoom, RoomEntity givenRoomDetails) {
        Optional<RoomEntity> optional = roomsRepository.findById(noRoom);

        if (optional.isEmpty()) {
            try {
                throw new Exception("Room number " + noRoom + " could not be found.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        RoomEntity updatedRoom = new RoomEntity(optional.get().getNoRoom(), givenRoomDetails.getCapacity(),
                givenRoomDetails.isAvailable(), givenRoomDetails.getType());

        roomsRepository.save(updatedRoom);
        return "Room details updated successfully.";
    }

    @Override
    public String addRoomsFromFile() {
        try {
            // Load JSON data from file
            InputStream inputStream = getClass().getResourceAsStream("/rooms.json");
            ObjectMapper objectMapper = new ObjectMapper();
            List<RoomEntity> rooms = objectMapper.readValue(inputStream, new TypeReference<>() {
            });

            // Add rooms to database
            for (RoomEntity room: rooms) {
                addRoom(room);
            }

            return "Rooms added successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error adding rooms from file";
        }
    }
}
