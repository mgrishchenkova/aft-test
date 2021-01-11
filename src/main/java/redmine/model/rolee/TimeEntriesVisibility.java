package redmine.model.rolee;

import java.util.stream.Stream;

public enum TimeEntriesVisibility {
    ALL("Все трудозатраты"),
    OWN("Только собственные трудозатраты");

    private String description;

    TimeEntriesVisibility(String description) {
        this.description = description;
    }

    public static TimeEntriesVisibility of(String description) {
        return Stream.of(values())
                .filter(it -> it.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден TimeEntriesVisibility" + description));
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
