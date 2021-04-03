package redmine.ui.pages;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class CucumberPageObjectHelper {
    @SneakyThrows
    public static WebElement getElementBy(String cucumberFieldName) {
        HeaderPage page = Pages.getPage(HeaderPage.class);
        Field foundField = Stream.of(page.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CucumberName.class))
                .filter(field -> cucumberFieldName.equals(field.getAnnotation(CucumberName.class).value()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Нет аннотации @CucumberName(\"%s\") у поля", cucumberFieldName)));
        foundField.setAccessible(true);
        return (WebElement) foundField.get(page);
    }


}
