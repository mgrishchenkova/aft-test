package redmine.model.rolee;

import java.util.stream.Stream;

public enum UsersVisibility {
    ALL("Все активные пользователи"),
    MEMBERS_OF_VISIBLE_PROJECT("Участники видимых проектов");

    private String description;

    UsersVisibility(String description) {
        this.description = description;
    }

    public static UsersVisibility of(String description){
        return Stream.of(values())
                .filter(it->it.description.equals(description))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Не найден UsersVisibility"+description));

    }


    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
