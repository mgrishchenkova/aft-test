package redmine.model;

public interface Generatable<T> {
    T read();

    T update();

    T create();

    default T generate() {
        return read() != null
                ? update()
                : create();
    }
}
