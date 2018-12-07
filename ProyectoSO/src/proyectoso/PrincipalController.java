/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author mauri
 */
public class PrincipalController implements Initializable {

    @FXML
    private Label lbl_proex;
    @FXML
    private JFXButton btn_fcfs;
    @FXML
    private JFXButton btn_rr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void goFCFS(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FCFS.fxml"));
        Parent root1;
        try {
            root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Simulador FCFS");
            stage.setScene(new Scene(root1));  
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @FXML
    private void goRR(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RR.fxml"));
        Parent root1;
        try {
            root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Simulador RoundRobin");
            stage.setScene(new Scene(root1));  
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
