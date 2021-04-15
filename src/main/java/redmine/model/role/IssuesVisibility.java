package redmine.model.role;

import java.util.stream.Stream;

public enum IssuesVisibility {
    DEFAULT("только общие задачи"),
    ALL("все задачи"),
    OWN("задачи созданные или назначенные пользователю");

    private final String description;

    IssuesVisibility(String description) {
        this.description = description;
    }

    public static IssuesVisibility of(String description) {
        return Stream.of(values())
                .filter(it -> it.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден IssuesVisibility" + description));
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
