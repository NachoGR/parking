package io.garcia.parking.clases;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Line {
    String name;
    List<Car> cars= new ArrayList<>();
    public Line (String name){
        this.name=name;
    }
    public Line(int line,Level level, int size){
        name= "Plant "+level.plant+ " - Line "+line;
        this.size=size;
    }
int size;

    public int getFreeSpace() {
        Integer occupacy=cars.stream().map(c->c.getSize()).collect(Collectors.summingInt(Integer::intValue));
        return size-occupacy;
    }

    public void setCar(Car car) {
        this.cars.add(car);
    }
    public void UnsetCar(Car car) {
        this.cars.remove(car);
    }

    public boolean removeCar(String name) {
        Car aux=findUsingEnhancedForLoop(name);
        return cars.remove(aux);
    }
    private Car findUsingEnhancedForLoop(
            String name) {

        for (Car car : cars) {
            if (car.getName().toString().equals(name)) {
                return car;
            }
        }
        return null;
    }
}
