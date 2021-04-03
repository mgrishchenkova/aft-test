package redmine.Manager;

import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import redmine.Property;
import redmine.dataBase.ConnectionDB;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Manager {
    public final static ConnectionDB dbConnection = new ConnectionDB();
    //TODO изменить на ThreadLocal когда будет многопточность
    private static WebDriver driver;
    private static WebDriverWait wait;


    /**
     * Получить экземпляр драйвера (ленивая инициализация)
     *
     * @return драйвер
     */

    public static WebDriver driver() {
        if (driver == null) {
            driver = getPropertyDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Property.getIntegerProperty("ui.implicitly.wait"), TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, Property.getIntegerProperty("ui.condition.wait"));
        }
        return driver;
    }

    /**
     * Закрыть браузер, очистить драйвер.
     */

    public static void driverQuit() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }

    public static WebDriverWait waiter() {
        return wait;
    }


    public static byte[] takeScreenshot() {
        return ((TakesScreenshot) driver()).getScreenshotAs(OutputType.BYTES);
    }

    public static JavascriptExecutor js() {
        return (JavascriptExecutor) driver();
    }


    /**
     * Открыть страницу Redmine
     */
    public static void openPage(String uri) {
        driver().get(Property.getStringProperties("ui.url") + uri);
    }

    /**
     * Возвращает экземпляр драйвера в зависимости от значения в properties-файле
     *
     * @return драйвер
     */
    @SneakyThrows
    private static WebDriver getPropertyDriver() {
        if (Property.getBooleanProperty("remote")) {
            // remote = true
            MutableCapabilities capabilities = new ChromeOptions();
            capabilities.setCapability("browserName", Property.getStringProperties("browser"));
            capabilities.setCapability("browserVersion", Property.getStringProperties("browser.version"));
            Map<String, Object> selenoidOptions = ImmutableMap.of(
                    "enableVNC", Property.getBooleanProperty("enable.vnc"),
                    "enableVideo", Property.getBooleanProperty("enable.video")
            );
            capabilities.setCapability("selenoid:options", selenoidOptions);
            return new RemoteWebDriver(
                    new URL(Property.getStringProperties("selenoid.hub.url")),
                    capabilities
            );
        } else {
            // remote = false
            switch (Property.getStringProperties("browser")) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", Property.getStringProperties("webdriver.chrome.driver"));
                    return new ChromeDriver();
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", Property.getStringProperties("webdriver.gecko.driver"));
                    return new FirefoxDriver();
                default:
                    throw new IllegalArgumentException("Неизвестный тип браузера");
            }
        }
    }
}
