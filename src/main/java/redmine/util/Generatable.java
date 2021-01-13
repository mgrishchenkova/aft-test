package redmine.util;

public interface Generatable<T> {
    T read();

    T update();

    T create();

    default T genarate() {
        if (read() != null) {
            return update();
        } else return create();

    }

}
