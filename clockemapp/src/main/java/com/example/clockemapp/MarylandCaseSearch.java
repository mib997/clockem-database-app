package com.example.clockemapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


/**
 * The MarylandCaseSearch class provides functionality to automate access
 * to the Maryland Judiciary Case Search website.
 *
 * This class is primarily designed to initiate a web driver instance
 * and navigate to the Maryland Judiciary Case Search web page.
 *
 * This can be useful in scenarios where automated access to the site
 * is needed for scraping or performing automated tasks related to legal cases.
 */
public class MarylandCaseSearch {

    public void openSite(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://casesearch.courts.state.md.us/casesearch/");

    }

}
