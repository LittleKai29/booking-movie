package com.project.spring.service;

import com.project.spring.dto.RoomDTO;
import com.project.spring.entity.Room;
import com.project.spring.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public RoomDTO createRoom(RoomDTO roomDTO) {
        Room room = new Room();
        room.setName(roomDTO.getName());
        room.setTotalSeats(roomDTO.getTotalSeats());
        Room savedRoom = roomRepository.save(room);
        roomDTO.setId(savedRoom.getId());
        return roomDTO;
    }
}
