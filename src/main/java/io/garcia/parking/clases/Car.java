package io.garcia.parking.clases;

import java.util.UUID;

import lombok.Data;

@Data
public class Car {
	int size;
	UUID name;

	public Car(int sizeCar) {
		size=sizeCar;
		name=UUID.randomUUID();
	}
}
