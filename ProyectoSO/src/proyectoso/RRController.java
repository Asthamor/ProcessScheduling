/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author mauri
 */
public class RRController implements Initializable {

    @FXML
    private ProgressBar pgrb_progress;
    @FXML
    private Label lbl_proex;
    @FXML
    private Label lbl_processid;
    @FXML
    private JFXButton btn_start;
    @FXML
    private JFXButton btn_stop;
    @FXML
    private TableView<Proceso> tbl_readyqueue;
    @FXML
    private TableColumn<Proceso, Integer> rq_process;
    @FXML
    private TableColumn<Proceso, Integer> rq_time;
    @FXML
    private TableColumn<Proceso, Integer> rq_size;
    @FXML
    private TableView<Proceso> tbl_donequeue;
    @FXML
    private TableColumn<Proceso, Integer> dq_process;
    @FXML
    private TableColumn<Proceso, Integer> dq_time;
    @FXML
    private TableColumn<Proceso, Integer> dq_size;
    @FXML
    private TableView<Proceso> tbl_waitingqueue;
    @FXML
    private TableColumn<Proceso, Integer> wq_process;
    @FXML
    private TableColumn<Proceso, Integer> wq_time;
    @FXML
    private TableColumn<Proceso, Integer> wq_size;
    @FXML
    private ProgressBar pgrb_ram_usage;
    
    LinkedList<Proceso> readyQueue;
    LinkedList<Proceso> doneQueue;
    LinkedList<Proceso> waitingQueue;
    
    CPUProcess processor;
    Thread thread;
    double totalRAM = 4096;
    double ramUsage = 0;
    int currentId;
    int quantum = 5;
    ArrayList<Proceso> listaProcesos;
    @FXML
    private Label mem_use;

    /**
     * Initializes the controller class.
     */
        @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public ArrayList generateProcess() {
        ArrayList<Proceso> processList = new ArrayList<>();
        Random r = new Random();
        int processCounter = r.nextInt(30);
        for (int i = 0; i < processCounter; i++) {
            processList.add(new Proceso(i));
            currentId = i;
        }
        return processList;
    }

    public void populateProcesses() {
        readyQueue = new LinkedList();
        waitingQueue = new LinkedList();
        doneQueue = new LinkedList();
        
        rq_process.setCellValueFactory(new PropertyValueFactory("id"));
        rq_time.setCellValueFactory(new PropertyValueFactory("processTime"));
        rq_size.setCellValueFactory(new PropertyValueFactory("processSize"));
        dq_process.setCellValueFactory(new PropertyValueFactory("id"));
        dq_time.setCellValueFactory(new PropertyValueFactory("processTime"));
        dq_size.setCellValueFactory(new PropertyValueFactory("processSize"));
        wq_process.setCellValueFactory(new PropertyValueFactory("id"));
        wq_time.setCellValueFactory(new PropertyValueFactory("processTime"));
        wq_size.setCellValueFactory(new PropertyValueFactory("processSize"));

        tbl_readyqueue.setItems(FXCollections.observableList(readyQueue));
        tbl_donequeue.setItems(FXCollections.observableList(doneQueue));
        tbl_waitingqueue.setItems(FXCollections.observableList(waitingQueue));
        
        listaProcesos = generateProcess();
        waitingQueue.addAll(listaProcesos);
        tbl_waitingqueue.refresh();
        pullFromWaiting();
        Thread addProcesses = new Thread() {
            @Override
            public void run() {
                Random rtime = new Random();
                while(true){
                    try {
                        Thread.sleep(Math.abs(rtime.nextInt(20000)));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FCFSController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    currentId++;
                    waitingQueue.add(new Proceso(currentId));
                    tbl_waitingqueue.refresh();
                }
            }
        };
        addProcesses.setDaemon(true);
        addProcesses.start();
        
    }
    
    public void pullFromWaiting(){
        while ((ramUsage < totalRAM) && (waitingQueue.peek() != null) &&
                ((waitingQueue.peek().getProcessSize() + ramUsage) < totalRAM)){
            Proceso p = waitingQueue.poll();
            readyQueue.add(p);
            ramUsage += p.getProcessSize();
            updateRAMUsage();
            tbl_waitingqueue.refresh();
            tbl_readyqueue.refresh();
            
        }
    }

    public void updateRAMUsage(){
        pgrb_ram_usage.setProgress(ramUsage / totalRAM);
        Platform.runLater(() -> mem_use.setText(ramUsage + " / " + totalRAM));
    }
    public void runSimulation() {
        thread = new Thread() {
            @Override
            public void run() {
                while (readyQueue.peek() != null) {
                    Proceso toExecute = (Proceso) readyQueue.poll();

                    processor = new CPUProcess(toExecute.getProcessTime(), toExecute);
                    try {
                        Platform.runLater(() -> lbl_processid.setText("" + toExecute.getId()));
                        Proceso result;
                        result = processor.executeQuantum(pgrb_progress, quantum);
                        if (result != null){
                            readyQueue.add(result);
                        } else {
                            ramUsage -= toExecute.getProcessSize();
                            updateRAMUsage();
                            doneQueue.add(toExecute);
                        }

                    } catch (InterruptedException ex) {
                        Logger.getLogger(FCFSController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    pullFromWaiting();
                    tbl_readyqueue.refresh();
                    tbl_donequeue.refresh();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void initSimulation() {
        ramUsage = 0;
        populateProcesses();
        runSimulation();
    }

    @FXML
    private void stopSimulation(ActionEvent event) {
        thread.stop();
    }
    
}
