package com.parking.service.impl;

import com.parking.dao.ParkingWhiteTempCarDao;
import com.parking.domain.ParkingWhiteTempCar;
import com.parking.service.ParkingWhiteTempCarService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("parkingWhiteTempCarService")
public class ParkingWhiteTempCarServiceImpl implements ParkingWhiteTempCarService {

    @Inject
    private ParkingWhiteTempCarDao parkingWhiteTempCarDao;
    @Override
    public int insertParkingWhiteTempCar(ParkingWhiteTempCar parkingWhiteTempCar) throws Exception {
        
        return parkingWhiteTempCarDao.insertParkingWhiteTempCar(parkingWhiteTempCar);
    }

    @Override
    public ParkingWhiteTempCar findParkingWhiteTempCarById(String carPlate) throws Exception {
        try {
            ParkingWhiteTempCar parkingWhiteTempCar = new ParkingWhiteTempCar();
            parkingWhiteTempCar.setCarPlate(carPlate);
            return parkingWhiteTempCarDao.selectParkingWhiteTempCarById(parkingWhiteTempCar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateParkingWhiteTempCar(ParkingWhiteTempCar parkingWhiteTempCar) throws Exception {
        return parkingWhiteTempCarDao.updateParkingWhiteTempCar(parkingWhiteTempCar);
    }

    @Override
    public int deleteParkingWhiteTempCar(ParkingWhiteTempCar parkingWhiteTempCar) throws Exception {
        return parkingWhiteTempCarDao.deleteParkingWhiteTempCarById(parkingWhiteTempCar);
    }
}
