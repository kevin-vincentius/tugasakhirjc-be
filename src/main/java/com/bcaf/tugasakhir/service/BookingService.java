package com.bcaf.tugasakhir.service;

import com.bcaf.tugasakhir.dto.ReqBookingExtensionDTO;
import com.bcaf.tugasakhir.dto.RespGetMyBookingsDTO;
import com.bcaf.tugasakhir.dto.RespListUserDTO;
import com.bcaf.tugasakhir.model.Bookings;
import com.bcaf.tugasakhir.model.BookingsExtensions;
import com.bcaf.tugasakhir.model.Rooms;
import com.bcaf.tugasakhir.model.Session;
import com.bcaf.tugasakhir.repo.BookingExtensionRepo;
import com.bcaf.tugasakhir.repo.BookingRepo;
import com.bcaf.tugasakhir.repo.RoomRepo;
import com.bcaf.tugasakhir.repo.SessionRepo;
import com.bcaf.tugasakhir.util.GlobalFunction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {
    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private BookingExtensionRepo bookingExtensionRepo;

    ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<Object> getAllBookings(HttpServletRequest request) {
        try {
            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session"): null;

            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);
            if (sessionId == null || existingSession.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
            }

            List<Bookings> allBookings = bookingRepo.findAll();

            List<RespGetMyBookingsDTO> bookingsDTO = allBookings.stream()
                    .map(booking -> {
                        RespGetMyBookingsDTO dto = modelMapper.map(booking, RespGetMyBookingsDTO.class);

                        RespListUserDTO userDTO = modelMapper.map(booking.getUser(), RespListUserDTO.class);
                        dto.setUserDTO(userDTO);

                        return dto;
                    })
                    .collect(Collectors.toList());

            return GlobalFunction.requestSuccess(bookingsDTO, null, HttpStatus.OK, "Get list booking berhasil");
        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Gagal mengambil list booking");
        }
    }

    public ResponseEntity<Object> getMyBookings(Long userId, HttpServletRequest request) {
        try {
            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session"): null;

            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);
            if (sessionId == null || existingSession.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
            }

            // get bookings by user id
            List<Bookings> myBookings = bookingRepo.findByUser_UserId(userId);

            List<RespGetMyBookingsDTO> myBookingsDTO = myBookings.stream()
                    .map(booking -> modelMapper.map(booking, RespGetMyBookingsDTO.class))
                    .collect(Collectors.toList());

            return GlobalFunction.requestSuccess(myBookingsDTO, null, HttpStatus.OK, "Get list booking user berhasil");
        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Gagal mengambil list booking user");
        }
    }

    public ResponseEntity<Object> generateBookingCode(Integer bookingId, HttpServletRequest request) {
        try {
//            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session"): null;
//
//            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);
//            if (sessionId == null || existingSession.isEmpty()) {
//                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
//            }

            // user datang ke resepsionis untuk ambil kunci -> resepsionis liat booking list -> generate code untuk start booking
            Optional<Bookings> optBooking = bookingRepo.findById(bookingId);

            if (optBooking.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Gagal mencari booking");
            }

            Bookings booking = optBooking.get();

            // generate 4-digit number (dari 1000 sampai 9999)
            Random random = new Random();
            long bookingCode = 1000L + random.nextInt(9000); // Ensure it’s between 1000 and 9999

            booking.setConfirmationCode(bookingCode);

            RespGetMyBookingsDTO myBookingDTO = modelMapper.map(booking, RespGetMyBookingsDTO.class);

            return GlobalFunction.requestSuccess(myBookingDTO, null, HttpStatus.CREATED, "Generate code berhasil");
        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Gagal generate code");
        }
    }

    public ResponseEntity<Object> enterBookingCode(Integer bookingId, Long confirmationCode, HttpServletRequest request) {
        try {
//            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session"): null;
//
//            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);
//            if (sessionId == null || existingSession.isEmpty()) {
//                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
//            }

            /* flow: user datang ke resepsionis untuk ambil kunci -> resepsionis liat booking list -> generate code untuk start booking */
            Optional<Bookings> optBooking = bookingRepo.findById(bookingId);

            if (optBooking.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Booking tidak ada");
            }

            Bookings booking = optBooking.get();

            Optional<Rooms> optRoom = roomRepo.findById(booking.getRoom().getRoomNumber());
            Rooms room = optRoom.get();

            switch (booking.getBookingStatus()) {
                case null:
                    return GlobalFunction.requestFailed(null, HttpStatus.NOT_FOUND, "Code belum digenerate");
                case 2: // --> status complete
                    return GlobalFunction.requestFailed(null, HttpStatus.CONFLICT, "Booking sudah tidak aktif");
                case 3: // --> status canceled
                    return GlobalFunction.requestFailed(null, HttpStatus.CONFLICT, "Booking sudah dicancel");
                default:
                    break;
            }

            if (!Objects.equals(booking.getConfirmationCode(), confirmationCode)) {
                // kalau code belum di generate atau code tidak sama
                return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Code tidak sesuai");
            }

            booking.setBookingStatus((short) (booking.getBookingStatus() + 1)); // --> update status booking

            if (booking.getBookingStatus() == 1) {
                // kalau status booking ongoing -> update status room jadi occupied
                room.setRoomStatus("Occupied");
            }
            if (booking.getBookingStatus() == 2) {
                // kalau status booking completed -> update status room jadi available
                room.setRoomStatus("Available");
            }

            return GlobalFunction.requestSuccess(null, null, HttpStatus.CREATED, "Code sesuai!");
        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Gagal mengkonfirmasi booking code");
        }
    }

    public ResponseEntity<Object> cancelBooking(Integer bookingId, HttpServletRequest request) {
        try {
//            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session"): null;
//
//            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);
//            if (sessionId == null || existingSession.isEmpty()) {
//                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
//            }

            Optional<Bookings> optBooking = bookingRepo.findById(bookingId);

            if (optBooking.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Booking tidak ada");
            }

            Bookings booking = optBooking.get();

            if (booking.getBookingStatus() != 0) {
                // status booking harus Booked
                return GlobalFunction.requestSuccess(null, null, HttpStatus.BAD_REQUEST, "Booking sudah ongoing/ completed/ canceled");
            }
            booking.setBookingStatus((short) 3);

            return GlobalFunction.requestSuccess(null, null, HttpStatus.OK, "Booking berhasil dicancel");

        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Gagal cancel booking");
        }
    }

    public ResponseEntity<Object> extendBooking(Integer bookingId, ReqBookingExtensionDTO reqBookingExtensionDTO, HttpServletRequest request) {
        try {
            String sessionId = request.getHeader("X-Session") != null ? request.getHeader("X-Session"): null;

            Optional<Session> existingSession = sessionRepo.findBySessionId(sessionId);
            if (sessionId == null || existingSession.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.FORBIDDEN, "Anda tidak terotorisasi");
            }

            Optional<Bookings> optBooking = bookingRepo.findById(bookingId);
            System.out.println(optBooking);
            if (optBooking.isEmpty()) {
                return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Booking tidak ada");
            }

            Bookings currentBooking = optBooking.get();

            if (currentBooking.getBookingsExtensions() != null) {
                return GlobalFunction.requestFailed(null, HttpStatus.CONFLICT, "Booking sudah pernah diextend");
            }

            switch (currentBooking.getBookingStatus()) {
                case 0:
                    return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Booking belum dimulai");
                case 2:
                case 3:
                    // kalau sudah completed atau canceled -> tidak bisa extend
                    return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Booking sudah tidak aktif");
            }
//            String previousEndTimeStr = "2024-11-15 20:00:00.0"; // This might be parsed from some source
//            Date previousEndTime = sdf1.parse(previousEndTimeStr);
//            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
//            String previousEndTimeUtcStr = outputFormat.format(previousEndTime);

            // convert previousEndTime dari yyyy-MM-dd HH:mm:ss.S ke EEE MMM dd HH:mm:ss z yyyy untuk samain format dengan newEndTime di req dto
            LocalDateTime previousEndTime = currentBooking.getEndTime();
            LocalDateTime newEndTime = reqBookingExtensionDTO.getNewEndTime();
            System.out.println(previousEndTime);
            System.out.println(newEndTime);

            if (newEndTime.isBefore(previousEndTime) || newEndTime.equals(previousEndTime)) {
                return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Jam ekstensi harus lebih dari end time sebelumnya");
            }

            List<Bookings> bookingsList = bookingRepo.findByRoom_RoomNumber(currentBooking.getRoom().getRoomNumber()); // --> list schedule untuk room tersebut

            for (Bookings booking : bookingsList) {
                if (booking.getBookingDate().equals(currentBooking.getBookingDate())) { // --> cek booking di tanggal tersebut
                    // loop existing schedule untuk cari apakah ada booking yang overlap
                    if (booking.getStartTime().isBefore(newEndTime) && booking.getEndTime().isAfter(previousEndTime)) {
                        return GlobalFunction.requestFailed(null, HttpStatus.CONFLICT, "Sudah ada booking pada jam yang dipilih");
                    }
                }
            }

            BookingsExtensions bookingsExtensions = modelMapper.map(reqBookingExtensionDTO, BookingsExtensions.class);
            bookingExtensionRepo.save(bookingsExtensions);

            currentBooking.setBookingsExtensions(bookingsExtensions);

            return GlobalFunction.requestSuccess(null, null, HttpStatus.CREATED, "Booking berhasil diextend");

        } catch (Exception e) {
            return GlobalFunction.requestFailed(null, HttpStatus.BAD_REQUEST, "Gagal extend booking");
        }
    }
}
