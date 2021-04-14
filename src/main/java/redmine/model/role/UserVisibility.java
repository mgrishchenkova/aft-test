package redmine.model.role;

import java.util.stream.Stream;

public enum UserVisibility {
    ALL("Все активные пользователи"),
    MEMBERS_OF_VISIBLE_PROJECT("Участники видимых проектов");

    private String description;

    UserVisibility(String description) {
        this.description = description;
    }

    public static UserVisibility of(String description) {
        return Stream.of(values())
                .filter(it -> it.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден UserVisibility" + description));

    }


    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
