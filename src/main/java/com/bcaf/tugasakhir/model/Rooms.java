package com.bcaf.tugasakhir.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Rooms")
public class Rooms {
    @Id
    @Column(name = "RoomNumber", nullable = false, length = 20)
    private String roomNumber;

    @Column(name = "FloorNumber", nullable = false, columnDefinition = "smallint")
    private Short floorNumber;

    @Column(name = "CapacityType", nullable = false, length = 20)
    private String capacityType;  // --> Small, Medium, Large

    @Column(name = "Status", nullable = false)
    private String roomStatus; // -> Available, Occupied

    @Column(name = "HasProjector", nullable = false)
    private Boolean hasProjector;

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Short getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Short floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Boolean getHasProjector() {
        return hasProjector;
    }

    public void setHasProjector(Boolean hasProjector) {
        this.hasProjector = hasProjector;
    }
}
