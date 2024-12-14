package com.bcaf.tugasakhir.repo;

import com.bcaf.tugasakhir.model.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepo extends JpaRepository<Rooms, String> {
    @Query("SELECT r FROM Rooms r " +
            "WHERE (:roomStatus IS NULL OR r.roomStatus = :roomStatus) " +   // Use :roomStatus instead of :status
            "AND (:hasProjector IS NULL OR r.hasProjector = :hasProjector) " +
            "AND (:capacityType IS NULL OR r.capacityType = :capacityType) " +
            "AND (:floorNumber IS NULL OR r.floorNumber = :floorNumber)")
    List<Rooms> findByFilters(@Param("roomStatus") String roomStatus, @Param("hasProjector") Boolean hasProjector, @Param("capacityType") String capacityType, @Param("floorNumber") Short floorNumber);

}
