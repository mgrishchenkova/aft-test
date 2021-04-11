package redmine.ui.help;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.reflections.Reflections;
import redmine.ui.page.AbstractPage;
import redmine.ui.page.Page;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Stream;

public class CucumberPageObjectHelper {
    @SneakyThrows
    public static WebElement getElementBy(String cucumberPageName, String cucumberFieldName) {
        AbstractPage page = getPageBy(cucumberPageName);
        Field foundField = Stream.of(page.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CucumberName.class))
                .filter(field -> cucumberFieldName.equals(field.getAnnotation(CucumberName.class).value()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Нет аннотации @CucumberName(\"%s\") у поля", cucumberFieldName)));
        foundField.setAccessible(true);
        return (WebElement) foundField.get(page);
    }

    @SneakyThrows
    public static AbstractPage getPageBy(String cucumberPageName){
        Reflections reflections = new Reflections("redmine.ui.page");
        Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(CucumberName.class);
        Class<?> pageClass = allClasses.stream()
                .filter(clazz -> cucumberPageName.equals(clazz.getAnnotation(CucumberName.class).value()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Нет аннотации @CucumberName(\"%s\") у класса", cucumberPageName)
                ));
        return Page.getPage((Class<AbstractPage>) pageClass);
    }


}
