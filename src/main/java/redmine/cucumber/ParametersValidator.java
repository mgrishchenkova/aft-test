package redmine.cucumber;

import lombok.SneakyThrows;
import org.testng.Assert;
import redmine.Manager.Context;
import redmine.ui.pages.CucumberName;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
public class ParametersValidator {

    public static void validateRoleParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> Assert.assertTrue(
                AllowedParameters.ROLE_PARAMETERS.contains(key),
                "Список допустимых параметров при работе с ролями не содержит параметр " + key
        ));
    }
    public static void validateUserParameters(Map<String, String> parameters) {

    }
    @SneakyThrows
    public static String replaceCucumberVariables(String rawString) {
        while (rawString.contains("${")) {
            String replacement = rawString.substring(rawString.indexOf("${"), rawString.indexOf("}") + 1);
            String stashId = replacement.substring(2, replacement.indexOf("->"));
            String fieldDescription = replacement.substring(replacement.indexOf("->") + 2, replacement.length() - 1);

            Object stashObject = Context.get(stashId.trim());

            Field foundFiend = Arrays.stream(stashObject.getClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(CucumberName.class))
                    .filter(field -> field.getAnnotation(CucumberName.class).value().equals(fieldDescription.trim()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Не задана аннотация @CucumberName "+fieldDescription));
            foundFiend.setAccessible(true);

            String result = foundFiend.get(stashObject).toString();

            rawString = rawString.replace(replacement, result);
        }
        return rawString;
    }
}
