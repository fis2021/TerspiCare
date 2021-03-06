package org.fis2021.terpsicare.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DoctorHomePageController {

    @FXML
    public void handleLogout(ActionEvent event) throws Exception {
        try {
            Node node = (Node) event.getSource();
            Stage CurrentStage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomePage.fxml"));
            CurrentStage.setTitle("HomePage");
            CurrentStage.setScene(new Scene(root, 500, 275));
            CurrentStage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void handleViewAppointments(ActionEvent event) throws Exception {
        try {
            Node node = (Node) event.getSource();
            Stage CurrentStage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ViewAppointmentsDoctor.fxml"));
            CurrentStage.setTitle("Appointments");
            CurrentStage.setScene(new Scene(root, 600, 450));
            CurrentStage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void handleViewPatients(ActionEvent event) throws Exception {
        try {
            Node node = (Node) event.getSource();
            Stage CurrentStage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ViewPatients.fxml"));
            CurrentStage.setTitle("Patients");
            CurrentStage.setScene(new Scene(root, 600, 450));
            CurrentStage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void handleNotifications(ActionEvent event) throws Exception {
        try {
            Node node = (Node) event.getSource();
            Stage CurrentStage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("DoctorNotifications.fxml"));
            CurrentStage.setTitle("Notifications");
            CurrentStage.setScene(new Scene(root, 600, 340));
            CurrentStage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
