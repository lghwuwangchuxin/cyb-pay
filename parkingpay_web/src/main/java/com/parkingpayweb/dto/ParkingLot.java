package com.parkingpayweb.dto;

public class ParkingLot extends BaseDTO {

    private String parkId;
    private String parkName;

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    @Override
    public String toString() {
        return "ParkingLot{" +
                "parkId='" + parkId + '\'' +
                ", parkName='" + parkName + '\'' +
                "} " + super.toString();
    }
}
