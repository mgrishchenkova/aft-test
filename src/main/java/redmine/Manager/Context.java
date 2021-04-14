package redmine.Manager;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

/**
 * Context, где будут содержаться сущности, сохраненные в ходе выполнения тестоы
 */
public class Context {
    private static Stash stash;

    public static void put(String stashId, Object entity) {
        getStash().put(stashId, entity);
    }

    public static <T> T get(String stashId, Class<T> clazz) {
        return clazz.cast(get(stashId));
    }

    public static Object get(String stashId) {
        return getStash().get(stashId);
    }

    public static Stash getStash() {
        if (stash == null) {
            stash = new Stash();
        }
        return stash;
    }

    public static void clearStash() {
        if (stash != null) {
            stash = null;
        }
    }


    @Step("Сущности сохраненные в Context в ходе выполнения тестов")
    public static void saveStashToAllure() {
        getStash().getEntities().forEach(
                (key, value) -> Allure.addAttachment(key, value.toString())
        );
    }
}
