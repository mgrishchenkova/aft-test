package redmine.model.rolee;

import java.util.stream.Stream;

public enum IssuesVisibility {
    DEFAULT("только общие задачи"),
    ALL("все задачи"),
    OWN("задачи созданные или назначенные пользователю");

    private String description;

    IssuesVisibility(String description) {
        this.description = description;
    }

    public static IssuesVisibility of(final String description) {
       return Stream.of(values())
                .filter(it->it.description.equals(description))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Не найде IssuesVisibility"+description));
    }

}
