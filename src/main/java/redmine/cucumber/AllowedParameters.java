package redmine.cucumber;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class AllowedParameters {
    static final List<String> ROLE_PARAMETERS = ImmutableList.of(
            "Позиция",
            "Встроенная",
            "Задача может быть назначена этой роли",
            "Видимость задач",
            "Видимость пользователей",
            "Видимость трудозатрат",
            "Права"
    );
}
