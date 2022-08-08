package io.garcia.parking.clases;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Level  {
 public Level(int plant){
  this.plant=plant;
  lines= new ArrayList<>();
 }

 List<Line> lines;
 int plant;
}
