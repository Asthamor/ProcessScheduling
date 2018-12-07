/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author mauri
 */
public class CPUProcess {
    private int time;
    Proceso process;
    
    CPUProcess(int processTime, Proceso p){
        time = processTime;
        process = p;
    }
    
    public void executeProcess(ProgressBar bar) throws InterruptedException{        
        for (double i = 1.0; i <= time; i++){
            Thread.sleep(1000);
            final double step = i;
            final double size = (double) time;
            
            Platform.runLater(() -> bar.setProgress( step / size ));
            
        }
        bar.setProgress(0.0);
    }
    
    public Proceso executeQuantum(ProgressBar bar, int quantum) throws InterruptedException {
        int i = 0;
        int remainingTime = process.getProcessTime();
        while ( i < quantum && process.getProcessTime() > 0){
            Thread.sleep(1000);
            final double step = i;
            final double size = (double) quantum;
            Platform.runLater(() -> bar.setProgress(step / size));
            remainingTime--;
            process.setProcessTime(remainingTime);
            i++;
        }
        if (process.getProcessTime() > 0) {
            return process;
        } else {
            return null;
        }
    }
    
}
