package redmine.Manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import redmine.Property;
import redmine.dataBase.ConnectionDB;

public class Manager {
    public final static ConnectionDB dbConnection = new ConnectionDB();
    private static WebDriver driver;

    public static WebDriver driver() {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void quitDriver() {
        driver().quit();
        driver = null;
    }
    public static void openPage(){
        driver().get(Property.getStringProperties("uiHost" ));
    }

}
