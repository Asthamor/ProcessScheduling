

package proyectoso;

import java.util.Random;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;



public class Proceso {
    public int id;
    private int processTime;
    private int arrivalTime;
    private int remainingTime;
    private int waitingTime; // ArrivalTime<---ATime , RemainingTime<---RTime
    private int processSize;
    private  Color color;
    public int pb=0; // for progress bar;
    
    public Proceso(int id,int atime,int processTime,Color c){
    	this.id=id;
    	arrivalTime=atime;
    	color=c;
    	remainingTime = processTime;
    }
    
    public Proceso(int id){
        this.id = id;
        Random rand = new Random();
        int time = rand.nextInt(20);
        if (time != 0){
            processTime = time; 
        } else {
            processTime = 1;
        }
        double r = rand.nextFloat();
        double g = rand.nextFloat();
        double b = rand.nextFloat();
        double x = rand.nextDouble();
        color = new Color(r, g, b, x);
        
        processSize = rand.nextInt(1024);
    }

    public int getProcessSize() {
        return processSize;
    }
    

    public void setProcessSize(int processSize) {
        this.processSize = processSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public int getProcessTime() {
        return processTime;
    }

    public void setProcessTime(int processTime) {
        this.processTime = processTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getPb() {
        return pb;
    }

    public void setPb(int pb) {
        this.pb = pb;
    }
    
    public SimpleIntegerProperty idProperty(){
        return new SimpleIntegerProperty(this.id);
    }
    public SimpleIntegerProperty processTimeProperty(){
        return new SimpleIntegerProperty(this.processTime);
    }
    public SimpleIntegerProperty processSizeProperty(){
        return new SimpleIntegerProperty(this.processSize);
    }
        
}
