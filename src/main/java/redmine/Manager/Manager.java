package redmine.Manager;

import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import redmine.Property;
import redmine.db.ConnectionDB;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Manager {
    public final static ConnectionDB dbConnection = new ConnectionDB();
    //TODO изменить на ThreadLocal когда будет многопточность
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();


    /**
     * Получить экземпляр драйвера (ленивая инициализация)
     *
     * @return драйвер
     */

    public static WebDriver driver() {
        if (driver.get() == null) {
            driver.set(getPropertyDriver());
            driver.get().manage().window().maximize();
            driver.get().manage().timeouts().implicitlyWait(Property.getIntegerProperty("ui.implicitly.wait"), TimeUnit.SECONDS);
            wait.set(new WebDriverWait(driver.get(), Property.getIntegerProperty("ui.condition.wait")));
        }
        return driver.get();
    }

    /**
     * Закрыть браузер, очистить драйвер.
     */

    public static void driverQuit() {
        if (driver.get() != null) {
            driver.get().quit();
        }
        driver.set(null);
    }

    public static WebDriverWait waiter() {
        return wait.get();
    }

    @Step("Сделать скриншот")
    public static byte[] takeScreenshot() {

        return ((TakesScreenshot) driver()).getScreenshotAs(OutputType.BYTES);
    }

    public static JavascriptExecutor js() {
        return (JavascriptExecutor) driver();
    }


    /**
     * Открыть страницу Redmine
     */
    @Step("Открыть страницу {0}")
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
                default:
                    throw new IllegalArgumentException("Неизвестный тип браузера");
            }
        }
    }
}
