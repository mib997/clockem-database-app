package com.example.clockemapp;

import java.time.LocalDate;



import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Represents personal information about an individual.
 * This class stores the first name, last name, date of birth, country,
 * and calculates the age of the person based on the provided date of birth.
 */
public class PersonInfo {

    private final String firstName, lastName, DOB, country, age;

    public PersonInfo(String first, String last, String DOB, String country){
        this.firstName = first;
        this.lastName = last;
        this.DOB = DOB;
        this.country = country;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dob = LocalDate.parse(DOB, formatter);
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dob, currentDate);

        age = Integer.toString(period.getYears());



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

    public String getAge(){
        return age;
    }

    @Override
    public String toString(){
        return "Personal info [First name: " + firstName +", Last name:"+lastName+
                ", DOB: "+DOB+", Country: "+country+", Age: "+getAge()+"]";
    }

}
