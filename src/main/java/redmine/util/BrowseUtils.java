package redmine.util;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import redmine.Manager.Manager;
import redmine.Property;

import java.util.concurrent.TimeUnit;

public class BrowseUtils {
    public static Boolean elementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();

        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public static boolean isElementCurrentlyPresent(WebElement element) {
        try {
            Manager.driver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            return element.isDisplayed();
        } catch (NoSuchElementException exception) {
            return false;
        } finally {
            Manager.driver().manage().timeouts().implicitlyWait(Property.getIntegerProperty("ui.implicitly.wait"), TimeUnit.SECONDS);
        }
    }
}
