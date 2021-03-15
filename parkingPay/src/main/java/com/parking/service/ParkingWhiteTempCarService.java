package com.parking.service;

import com.parking.domain.ParkingWhiteTempCar;

public interface ParkingWhiteTempCarService {

    public int insertParkingWhiteTempCar(ParkingWhiteTempCar parkingWhiteTempCar) throws Exception;

    public ParkingWhiteTempCar findParkingWhiteTempCarById(String carPlate) throws Exception;

    public int updateParkingWhiteTempCar(ParkingWhiteTempCar parkingWhiteTempCar) throws Exception;

    public int deleteParkingWhiteTempCar(ParkingWhiteTempCar parkingWhiteTempCar) throws Exception;
}
