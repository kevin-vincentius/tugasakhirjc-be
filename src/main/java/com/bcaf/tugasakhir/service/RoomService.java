package com.bcaf.tugasakhir.service;

import com.bcaf.tugasakhir.dto.*;
import com.bcaf.tugasakhir.model.*;
import com.bcaf.tugasakhir.repo.BookingRepo;
import com.bcaf.tugasakhir.repo.RoomRepo;
import com.bcaf.tugasakhir.repo.SessionRepo;
import com.bcaf.tugasakhir.repo.UserRepo;
import com.bcaf.tugasakhir.util.GlobalFunction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomService {
    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private UserRepo userRepo;

    ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<Object> getFilteredRooms(String roomStatus, Boolean hasProjector, String capacityType, Short floorNumber,
                                                   HttpServletRequest request) {
        try {
            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session") : null;
            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);

            if (sessionId == null || existingSession.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
            }

            List<Rooms> rooms = roomRepo.findByFilters(roomStatus, hasProjector, capacityType, floorNumber);

            return GlobalFunction.requestSuccess(rooms, null, HttpStatus.OK, null);

        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Hasil pencarian gagal");
        }
    }

    public ResponseEntity<Object> getRoomDetail(String roomNumber, HttpServletRequest request) {
        try {
            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session") : null;
            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);

            if (sessionId == null || existingSession.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
            }

            Optional<Rooms> room = roomRepo.findById(roomNumber);

            return GlobalFunction.requestSuccess(room, null, HttpStatus.OK, null);

        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Hasil pencarian gagal");
        }
    }

    public ResponseEntity<Object> getSchedules(String roomNumber, HttpServletRequest request) {
        try {
            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session") : null;

            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);
            if (sessionId == null || existingSession.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
            }
            List<Bookings> bookingList = bookingRepo.findByRoom_RoomNumber(roomNumber);

            if (bookingList.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.NOT_FOUND, "Tidak ada booking untuk ruangan ini");
            }

            List<RespGetSchedulesDTO> schedulesDTO = bookingList.stream().map(booking -> {
                RespGetSchedulesDTO dto = modelMapper.map(booking, RespGetSchedulesDTO.class);
                RespListUserDTO userDTO = modelMapper.map(booking.getUser(), RespListUserDTO.class);
                dto.setUserDTO(userDTO);

                return dto;
            }).toList();
            return GlobalFunction.requestSuccess(schedulesDTO, null, HttpStatus.OK, "Get list booking berhasil");

        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Hasil query schedule gagal");
        }
    }

    public ResponseEntity<Object> bookRoom(String roomNumber, ReqBookDTO reqBookDTO, HttpServletRequest request) {
        try {
            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session") : null;

            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);
            if (sessionId == null || existingSession.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
            }

            Long userId = existingSession.get().getUserId(); // --> ambil current user id
            Optional<MstUser> optionalUser = userRepo.findById(userId); // --> cari user berdasarkan id
            MstUser user = optionalUser.get(); // --> masukin ke entity user

            List<Bookings> bookingsList = bookingRepo.findByRoom_RoomNumber(roomNumber); // --> list schedule untuk room tersebut

            for (Bookings booking : bookingsList) {

                if (booking.getBookingDate().equals(reqBookDTO.getBookingDate())) { // --> cek booking di tanggal tersebut
                    // loop existing schedule untuk cari apakah ada booking yang overlap
                    if (reqBookDTO.getStartTime().isAfter(reqBookDTO.getEndTime())) {
                        return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Start time must be before end time");
                    }
                    if (reqBookDTO.getStartTime().isBefore(booking.getEndTime()) && reqBookDTO.getEndTime().isAfter(booking.getStartTime())) {
                        return GlobalFunction.requestFailed(null, HttpStatus.CONFLICT, "Sudah ada booking pada jam yang dipilih");
                    }
                }
            }

            Optional<Rooms> optionalRoom = roomRepo.findById(roomNumber); // --> cari object room dari room id
            Rooms room = optionalRoom.get(); // masukin ke entity room

            Bookings booking = modelMapper.map(reqBookDTO, Bookings.class);
            booking.setRoom(room);
            booking.setUser(user);
            bookingRepo.save(booking);

            RespBookingDTO respBookingDTO = modelMapper.map(booking, RespBookingDTO.class);
            respBookingDTO.setRoom(room);

            // update status room
            room.setRoomStatus("Occupied");

            return GlobalFunction.requestSuccess(respBookingDTO, null, HttpStatus.CREATED, "Booking berhasil!");

        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Gagal booking ruangan");
        }
    }


}
