import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static boolean validateLetters(String string){
        if (string == null || string.isEmpty()){
            return false;
        }
        for (char c : string.toCharArray()){
            if (Character.isLetter(c)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkDOB(String dob){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            LocalDate.parse(dob, formatter);
            return true;
        } catch (DateTimeParseException e){
            return false;
        }
    }
    public static void main(String[] args) {

        PersonInfo info;
        ArrayList<PersonInfo> infoList = new ArrayList<>();
        Scanner scanString = new Scanner(System.in);
        System.out.println("_____________________________________________________________");
        System.out.println("Welcome to the Person ID Database Management System!");
        System.out.println("_____________________________________________________________");

        Scanner scanner = new Scanner(System.in);

        System.out.println("How many people would you like to add to the database?: ");
        int numPeople = scanner.nextInt();

        for (int i = 0; i < numPeople; i++){
            System.out.println("Enter the first and last name: ");
            System.out.println("First name: ");
            String FIRST_NAME = scanString.nextLine();
            if (!validateLetters(FIRST_NAME)){
                while (!validateLetters(FIRST_NAME)){
                    System.out.println("Input must be letters.");
                    System.out.println("First name: ");
                    FIRST_NAME = scanString.nextLine();
                }
            }
            System.out.println("Last name: ");
            String LAST_NAME = scanString.nextLine();
            if (!validateLetters(LAST_NAME)){
                while (!validateLetters(LAST_NAME)){
                    System.out.println("Input must be letters. ");
                    System.out.println("Last name: ");
                    LAST_NAME = scanString.nextLine();
                }
            }

            System.out.println("Enter the data of birth (mm/dd/yyyy): ");
            String DOB_INFO = scanString.nextLine();
            if (!checkDOB(DOB_INFO)){
                while (!checkDOB(DOB_INFO)) {
                    System.out.println("Date of birth not in right format. ");
                    System.out.println("Enter the data of birth (mm/dd/yyyy): ");
                    DOB_INFO = scanString.nextLine();
                }
            }

            System.out.println("Enter country of origin: ");
            String country = scanString.nextLine();
            if (!validateLetters(country)){
                while (!validateLetters(country)){
                    System.out.println("Sorry, country must be all letters.");
                    System.out.println("Enter country of origin: ");
                    country = scanString.nextLine();
                }
            }
            info = new PersonInfo(FIRST_NAME, LAST_NAME, DOB_INFO, country);
            infoList.add(info);

            for (PersonInfo p : infoList){
                System.out.println(p);
            }
        }

        scanString.close();
        scanner.close();

    }
}