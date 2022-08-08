package io.garcia.parking;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.garcia.parking.clases.Car;
import io.garcia.parking.clases.Level;
import io.garcia.parking.clases.Line;
import io.garcia.parking.service.IParking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import io.garcia.parking.clases.Parking;
import org.springframework.shell.standard.ShellOption;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class Commands{

	Parking p;


    @Autowired
    List<IParking> iParkingList;
    IParking iParking;
    @ShellMethod(value = "Classification method",group = "Classification")
    public String classificationMethod() throws JsonProcessingException {
        String metodos= "";
        for (IParking metodo: iParkingList) {
            metodos+= metodo.getClass().getSimpleName()+"\n";
        }
        return metodos;
    }
    @ShellMethod(value = "Selected classification method",group = "Classification")
    public String getMethod() throws JsonProcessingException {
        if(iParking==null)
            return "Method not selected";
        return iParking.getClass().getSimpleName();
    }
    @ShellMethod(value = "Parking info")
    public String Info() throws JsonProcessingException {
        if(p==null){
        	p= new Parking();
            iParking=iParkingList.get(0);
            System.out.println("Select method: "+ iParking.getClass().getName());
        	p.setLevels(new ArrayList<>());
        }
        return iParking.getMapper().writeValueAsString(p);
    }

    @ShellMethod(value = "Parking add Lines",prefix = "2")
    public String parkingAddLines(int levels , int lines, int size, @ShellOption(defaultValue = "OccupyNearest") String metodo) throws JsonProcessingException {
        ArrayList<String> mensajes = new ArrayList<String>();
        if(p!=null && p.getLineas().size()>0)
            return "Parking it is initialized yet";
        iParking=getOrdeningMethod(metodo);

        if(levels<iParking.getMinimunLevels())
            mensajes.add("Minimun 2 levels");
        if(lines<iParking.getMinimunLines())
            mensajes.add("Minimun 4 lines");
        if(size<iParking.getMinimunMeters())
            mensajes.add("Minimun 29 meters");
        if(mensajes.isEmpty()) {
            if (p == null)
                p = new Parking();
            for (int i = 0; levels > i; i++) {
                Level level = new Level(i);
                for (int j = 0; lines > j; j++) {
                    Line auxLine = new Line(j, level, size);
                    level.getLines().add(auxLine);
                    p.getLineas().add(auxLine);

                }

                p.getLevels().add(level);

            }
            mensajes.add(p.toString());
        }

    	return iParking.getMapper().writeValueAsString(mensajes);
    }

    private String listMethod() {

        String metodos= "";
        for (IParking metodo: iParkingList) {
            metodos+= metodo.getClass().getSimpleName()+" | ";
        }
        return metodos;

    }

    private IParking getOrdeningMethod(String metodo) {
        IParking method=iParkingList.get(0);
        for (IParking p: iParkingList
             ) {
            if(p.getClass().getSimpleName().equals(metodo))
                method=p;

        }
        return method;
    }

    @ShellMethod(value = "Parking car",group = "Move Car")
    public String parkingCar(int sizeCar) {
        ArrayList<String> mensajes = new ArrayList<String>();
        if(p==null || p.getLineas().size()>0)
            return "Initialize Parking";
        if(iParking.getCarSize(sizeCar))
            return "Car size between (4,5) meters";
        List<Line> linesAux=findLinesLowOccupy(p,sizeCar);
        return iParking.parkingCar(p,linesAux.get(0),new Car(sizeCar));
    }
    @ShellMethod(value = "Unparking car",group = "Move Car")
    public String unParkingCar(String name) {
        if(p==null || p.getLineas().size()>0)
            return "Initialize Parking";

        return "Left car on Line: "+iParking.unParkingCar(p,name)+ "\n"+p;
    }

    private List<Line> findLinesLowOccupy(Parking p,int sizeCar) {

        return p.getLineas().stream().filter(line -> line.getFreeSpace()>=sizeCar).sorted(Comparator.comparing(Line::getFreeSpace)).collect(Collectors.toList());
    }


}