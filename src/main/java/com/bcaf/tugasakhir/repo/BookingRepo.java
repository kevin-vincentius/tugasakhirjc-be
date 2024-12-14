package com.bcaf.tugasakhir.repo;

import com.bcaf.tugasakhir.model.Bookings;
import com.bcaf.tugasakhir.model.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<Bookings, Integer > {
    List<Bookings> findByRoom_RoomNumber(String roomNumber);
    List<Bookings> findByUser_UserId(Long userId);
    Optional<Bookings> findById(Integer bookingId);


}
