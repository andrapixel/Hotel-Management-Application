package com.sd.app.hotel.presentation;

import com.sd.app.hotel.model.RoomEntity;
import com.sd.app.hotel.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/hotel")
public class RoomsController {
    @Autowired
    private RoomsService roomsService;

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomEntity>> getRooms() {
        List<RoomEntity> rooms = roomsService.findAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<List<RoomEntity>> getAvailableRooms() {
        List<RoomEntity> roomsList = roomsService.findAllAvailableRooms();
        return ResponseEntity.ok(roomsList);
    }

    @PostMapping("/add-room")
    public String addRoom(@RequestBody RoomEntity roomEntity) {
        return roomsService.addRoom(roomEntity);
    }

    @DeleteMapping("/delete-room/{id}")
    public String deleteRoom(@PathVariable int id) {
        return roomsService.deleteRoom(id);
    }

    @PutMapping("/update-room/{id}")
    public String updateRoom(@PathVariable int id, @RequestBody RoomEntity roomEntity) {
        return roomsService.updateRoom(id, roomEntity);
    }

    @PostMapping("/add-rooms-from-file")
    public String addRoomsFromJsonFile() {
        return roomsService.addRoomsFromFile();
    }
}