package io.garcia.parking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.garcia.parking.clases.Car;
import io.garcia.parking.clases.Line;
import io.garcia.parking.clases.Parking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages a parking lot and tries to send it to the nearest parking.
 */
@Component
public class OccupyNearest implements IParking{

    @Override
    public List<String> findParking(Parking p) {
        List<String>data= new ArrayList<>();
        for (Line l:p.getLineas()) {
            List<Car> auxLine = l.getCars();
            data.add(l.getName() +"   "+l.getFreeSpace());
        }
        return data;
    }
    @Override
    public String parkingCar(Parking p,Line L, Car car) {
        L.setCar(car);
        return "Parking on ="+L+"\n"+findParking(p);
    }
    public Line unParkingCar(Parking p,String name){
        Line aux=new Line("Car not Found");
        for (Line line :p.getLineas()
        ) {
            if(line.removeCar(name)){
                aux=line;
            }

        }
        return aux;
    }

    @Override
    public List<String>  takeOutCar(Parking p, Car c) {
        for (Line l:p.getLineas()) {
            l.UnsetCar(c);
        }

        return findParking(p);
    }

    @Override
    public ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper;
    }
}
