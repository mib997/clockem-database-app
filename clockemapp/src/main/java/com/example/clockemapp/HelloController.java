package com.example.clockemapp;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.SQLException;

public class HelloController {

    @FXML
    private TextField firstNameField, lastNameField, dobField,
            countryField, searchFirstNameField, searchLastNameField;
    @FXML
    private TableView<PersonInfo> personTable;

    @FXML
    private TableColumn<PersonInfo, String> firstNameColumn, lastNameColumn,
    dobColumn, countryColumn;

    @FXML
    private Label messageLabel;

    private DatabaseConnector connector;

    private ObservableList<PersonInfo> people = FXCollections.observableArrayList();

    String username = "";
    String password = "";

    @FXML
    public void initialize(){

        try{
            connector = new DatabaseConnector(username, password);
            messageLabel.setText("Connected to database.");
            /* MarylandCaseSearch search = new MarylandCaseSearch();
            search.openSite();*/
        } catch (Exception e){
            messageLabel.setText("Database connection failed.");
            e.printStackTrace();
        }

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        personTable.setItems(people);
        loadPeopleFromDatabase();

    }


    @FXML
    private void AddPerson(){

        String firstName, lastName, dob, country;

        firstName = firstNameField.getText().trim();
        lastName = lastNameField.getText().trim();
        dob = dobField.getText().trim();
        country = countryField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || country.isEmpty()){
            messageLabel.setText("Please fill in all fields.");
            return;
        }

        PersonInfo person = new PersonInfo(firstName, lastName, dob, country);
        people.add(person);

        try{
            connector.insertData(person);

            firstNameField.clear();
            lastNameField.clear();
            dobField.clear();
            countryField.clear();

            messageLabel.setText("Person added to the database.");
        } catch (SQLException e) {
            messageLabel.setText("Database error.");
            e.printStackTrace();
        }

        messageLabel.setText(
                "Added: " + person.getFirstName() + " " + person.getLastName()
        );
    }

    private void loadPeopleFromDatabase(){
        try{
            people.clear();
            people.addAll(connector.retrieveData());
        } catch (Exception e) {
            messageLabel.setText("Error");
            e.printStackTrace();
        }
    }

    @FXML
    private void tableClick(){
        PersonInfo p = personTable.getSelectionModel().getSelectedItem();

        if (p != null){
            firstNameField.setText(p.getFirstName());
            lastNameField.setText(p.getLastName());
            dobField.setText(p.getDOB());
            countryField.setText(p.getCountry());
        }
    }

    @FXML
    private void DeletePerson(){
        PersonInfo person = personTable.getSelectionModel().getSelectedItem();

        if (person == null){
            messageLabel.setText("Select a person.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete person");
        alert.setContentText("Are you sure you want to delete this person?");

        if (alert.showAndWait().get() == ButtonType.OK){
            try{
                connector.deletePerson(person);
                people.remove(person);
                messageLabel.setText("Person deleted");

            } catch (Exception e) {
                messageLabel.setText("Error deleting person");
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void UpdatePerson(){

        String firstName, lastName, dob, country;
        PersonInfo person = personTable.getSelectionModel().getSelectedItem();

        if (person == null){
            messageLabel.setText("Please select a person to update");
            return;
        }

        firstName = firstNameField.getText().trim();
        lastName = lastNameField.getText().trim();
        dob = dobField.getText().trim();
        country = countryField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || country.isEmpty()){
            messageLabel.setText("Please fill in all the fields.");
            return;

        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Update person");
        alert.setContentText("Are you sure you want to update this person?");

        if (alert.showAndWait().get() == ButtonType.OK){
            try {
                PersonInfo updatedPerson = new PersonInfo(firstName, lastName, dob, country);
                connector.updatePerson(person, updatedPerson);

                int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
                people.set(selectedIndex, updatedPerson);

                messageLabel.setText("Person updated");
            } catch (Exception e){
                messageLabel.setText("Error updating person.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void searchButton() {

        String searchFirstName, searchLastName;

        searchFirstName = searchFirstNameField.getText().trim().toLowerCase();
        searchLastName = searchLastNameField.getText().trim().toLowerCase();

        ObservableList<PersonInfo> list = FXCollections.observableArrayList();

        for (PersonInfo p : people){
            boolean firstNameMatch = searchFirstName.isEmpty() ||
                    p.getFirstName().toLowerCase().contains(searchFirstName);

            boolean lastNameMatch = searchLastName.isEmpty() ||
                    p.getLastName().toLowerCase().contains(searchLastName);

            if (firstNameMatch && lastNameMatch) {
                list.add(p);
            }
        }
        personTable.setItems(list);
    }

    @FXML
    public void showAllButton() {
        personTable.setItems(people);
        searchFirstNameField.clear();
        searchLastNameField.clear();

        messageLabel.setText("Showing all records");
    }

    @FXML
    public void ExportCSV(){
        try(FileWriter file = new FileWriter("clockem_export.csv")){

            file.append("First Name,Last Name,DOB,Country");

            for (PersonInfo p : people){
                file.append(p.getFirstName()).append(",");
                file.append(p.getLastName()).append(",");
                file.append(p.getDOB()).append(",");
                file.append(p.getCountry()).append("\n");
            }

            messageLabel.setText("Data exported successfully");
        } catch (IOException e) {
            messageLabel.setText("Export unsuccessful");
            e.printStackTrace();
        }

    }

    @FXML
    public void MDCaseSearch(){

        String firstName, lastName;

        firstName = firstNameField.getText().trim();
        lastName = lastNameField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty()){
            messageLabel.setText("The fields are empty");
            return;
        }

        try{
            MarylandCaseSearch search = new MarylandCaseSearch();
            search.openSite();
        } catch (Exception e){
            messageLabel.setText("Case search did not open");
            e.printStackTrace();
        }
    }
}
