package redmine.model;

public interface Generatable<T> {
    T read();

    T update();

    T create();

    default T generate() {
        if (read() != null) {
            return update();
        } else return create();

    }

}
