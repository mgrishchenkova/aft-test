package redmine.Manager;

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

    // @Step("Сущности в контексте автотеста")
    //   public static void saveStashToAllure() {
    //  getStash().getEntities().forEach(
    //            (key, value) -> Allure.addAttachment(key, value.toString())
    //  );
    // }
}
