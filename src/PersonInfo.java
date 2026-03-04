import java.time.LocalDate;
/**
 * This file contains the Class file and methods that handle the
 * user data input.
 */


import java.time.Period;
import java.time.format.DateTimeFormatter;

public class PersonInfo {

    private final String firstName, lastName, DOB, country;

    public PersonInfo(String first, String last, String DOB, String country){
        this.firstName = first;
        this.lastName = last;
        this.DOB = DOB;
        this.country = country;

    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getDOB(){
        return DOB;
    }

    public String getCountry(){
        return country;
    }

    public String calculateAge(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dob = LocalDate.parse(DOB, formatter);
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dob, currentDate);

        return Integer.toString(period.getYears());
    }

    @Override
    public String toString(){
        return "Personal info [First name: " + firstName +", Last name:"+lastName+
                ", DOB: "+DOB+", Country: "+country+", Age: "+calculateAge()+"]";
    }

}
