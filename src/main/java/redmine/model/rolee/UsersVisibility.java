package redmine.model.rolee;

public enum UsersVisibility {
    ALL("Все активные пользователи"),
    Members_of_visible_projects("Участники видимых проектов");

    private String description;

    UsersVisibility(String description) {
        this.description = description;
    }
}
