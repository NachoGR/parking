package io.garcia.parking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.garcia.parking.clases.Car;
import io.garcia.parking.clases.Line;
import io.garcia.parking.clases.Parking;

import java.util.List;

/**
 * This interface allows to implement different management policies
 */
public interface IParking {
 List<String> findParking(Parking p);
    String parkingCar(Parking p, Line L, Car car);
    public Line unParkingCar(Parking p,String name);

    List<String>  takeOutCar(Parking p, Car c);

    ObjectMapper getMapper();
    default int getMinimunLines(){
        return 4;
    }

    default int getMinimunLevels(){
        return 2;
    }

    default int getMinimunMeters(){
        return 29;
    }
    default boolean getCarSize(int sizeCar){
        return !(sizeCar>3 && sizeCar<6);
    }
}
