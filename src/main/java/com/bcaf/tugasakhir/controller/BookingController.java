package com.bcaf.tugasakhir.controller;

import com.bcaf.tugasakhir.dto.ReqBookingExtensionDTO;
import com.bcaf.tugasakhir.model.BookingsExtensions;
import com.bcaf.tugasakhir.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllBookings(HttpServletRequest request){
        return bookingService.getAllBookings(request);
    }

    @PutMapping("/{bookingId}/generate-code")
    public ResponseEntity<Object> generateBookingCode(@PathVariable(value = "bookingId") Integer bookingId, HttpServletRequest request){
        return bookingService.generateBookingCode(bookingId, request);
    }

    @PostMapping("/{bookingId}/{confirmationCode}")
    public ResponseEntity<Object> enterBookingCode(@PathVariable(value = "bookingId") Integer bookingId, @PathVariable(value = "confirmationCode") Long confirmationCode, HttpServletRequest request){ //
        return bookingService.enterBookingCode(bookingId, confirmationCode, request);
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<Object> cancelBooking(@PathVariable(value = "bookingId") Integer bookingId, HttpServletRequest request){
        return bookingService.cancelBooking(bookingId, request);
    }

    @PutMapping("/{bookingId}/extend")
    public ResponseEntity<Object> extendBooking(@PathVariable(value = "bookingId") Integer bookingId, @RequestBody ReqBookingExtensionDTO reqBookingExtensionDTO, HttpServletRequest request){
        return bookingService.extendBooking(bookingId, reqBookingExtensionDTO, request);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getMyBookings(@PathVariable(value = "userId") Long userId, HttpServletRequest request){
        return bookingService.getMyBookings(userId, request);
    }



}
