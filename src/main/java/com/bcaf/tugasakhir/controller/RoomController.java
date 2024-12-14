package com.bcaf.tugasakhir.controller;

import com.bcaf.tugasakhir.dto.ReqBookDTO;
import com.bcaf.tugasakhir.service.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<Object> getFilteredRooms(@RequestParam(required = false) String roomStatus,
                                                   @RequestParam(required = false) Boolean hasProjector,
                                                   @RequestParam(required = false) String capacityType,
                                                   @RequestParam(required = false) Short floorNumber,
                                                   HttpServletRequest request){
        return roomService.getFilteredRooms(roomStatus, hasProjector, capacityType, floorNumber, request);
    }

    @GetMapping("/{roomNumber}")
    public ResponseEntity<Object> getRoomDetail(@PathVariable(value = "roomNumber") String roomNumber, HttpServletRequest request){
        return roomService.getRoomDetail(roomNumber, request);
    }

    @GetMapping("/{roomNumber}/schedule")
    public ResponseEntity<Object> getSchedules(@PathVariable(value = "roomNumber") String roomNumber, HttpServletRequest request){
        return roomService.getSchedules(roomNumber, request);
    }

    @PostMapping("/{roomNumber}/book")
    public ResponseEntity<Object> bookRoom(@PathVariable(value = "roomNumber") String roomNumber, @RequestBody ReqBookDTO reqBookDTO, HttpServletRequest request){
        return roomService.bookRoom(roomNumber, reqBookDTO, request);
    }


}
