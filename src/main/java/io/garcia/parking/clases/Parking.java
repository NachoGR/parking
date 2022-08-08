package io.garcia.parking.clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import io.garcia.parking.service.IParking;
import lombok.Data;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.messaging.converter.GsonMessageConverter;

@Data
public class Parking  {
   private List<Line> lineas= new ArrayList<>();
List<Level> levels = new ArrayList<>();

    @Override
    public String toString() {
        return "Parking{\n" +
                "levels=" + levels +
                "\n}";
    }
}
