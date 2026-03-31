package com.example.clockemapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * This class uses Selenium functions to connect to the Maryland Case Search website
 */
public class MarylandCaseSearch {

    public void openSite(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://casesearch.courts.state.md.us/casesearch/");

    }

}
