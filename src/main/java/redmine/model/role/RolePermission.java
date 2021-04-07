package redmine.model.role;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;
@AllArgsConstructor
public enum RolePermission {
    ADD_PROJECT("Создание проекта"),
    EDIT_PROJECT("Редактирование проектов"),
    CLOSE_PROJECT("Закрывать / открывать проекты"),
    SELECT_PROJECT_MODULES("Выбор модулей проекта"),
    MANAGE_MEMBERS("Управление участниками"),
    MANAGE_VERSIONS("Управление версиями"),
    ADD_SUBPROJECTS("Создание подпроектов"),
    MANAGE_PUBLIC_QUERIES("Управление общими запросами"),
    SAVE_QUERIES("Сохранение запросов"),
    VIEW_MESSAGES("Просмотр сообщений"),
    ADD_MESSAGES("Отправка сообщений"),
    EDIT_MESSAGES("Редактирование сообщений"),
    EDIT_OWN_MESSAGES("Редактирование собственных сообщений"),
    DELETE_MESSAGES("Удаление сообщений"),
    DELETE_OWN_MESSAGES("Удаление собственных сообщений"),
    MANAGE_BOARDS("Управление форумами"),
    VIEW_CALENDAR("Просмотр календаря"),
    VIEW_DOCUMENTS("Просмотр документов"),
    ADD_DOCUMENTS("Добавить документы"),
    EDIT_DOCUMENTS("Редактировать документы"),
    DELETE_DOCUMENTS("Удалить документы"),
    VIEW_FILES("Просмотр файлов"),
    MANAGE_FILES("Управление файлами"),
    VIEW_GANTT("Просмотр диаграммы Ганта"),
    VIEW_ISSUES("Просмотр задач"),
    ADD_ISSUES("Добавление задач"),
    EDIT_ISSUES("Редактирование задач"),
    EDIT_OWN_ISSUES("Редактировать свои задачи"),
    COPY_ISSUES("Копирование задач"),
    MANAGE_ISSUE_RELATIONS("Управление связыванием задач"),
    MANAGE_SUBTASKS("Управление подзадачами"),
    SET_ISSUES_PRIVATE("Установление видимости (общая/частная) для задач"),
    SET_OWN_ISSUES_PRIVATE("Установление видимости (общая/частная) для собственных задач"),
    ADD_ISSUE_NOTES("Добавление примечаний"),
    EDIT_ISSUE_NOTES("Редактирование примечаний"),
    EDIT_OWN_ISSUE_NOTES("Редактирование собственных примечаний"),
    VIEW_PRIVATE_NOTES("Просмотр приватных комментариев"),
    SET_NOTES_PRIVATE("Размещение приватных комментариев"),
    DELETE_ISSUES("Удаление задач"),
    VIEW_ISSUE_WATCHERS("Просмотр списка наблюдателей"),
    ADD_ISSUE_WATCHERS("Добавление наблюдателей"),
    DELETE_ISSUE_WATCHERS("Удаление наблюдателей"),
    IMPORT_ISSUES("Импорт задач"),
    MANAGE_CATEGORIES("Управление категориями задач"),
    VIEW_NEWS("Просмотр новостей"),
    MANAGE_NEWS("Управление новостями"),
    COMMENT_NEWS("Комментирование новостей"),
    VIEW_CHANGESETS("Просмотр изменений хранилища"),
    BROWSE_REPOSITORY("Просмотр хранилища"),
    COMMIT_ACCESS("Изменение файлов в хранилище"),
    MANAGE_RELATED_ISSUES("Управление связанными задачами"),
    MANAGE_REPOSITORY("Управление хранилищем"),
    VIEW_TIME_ENTRIES("Просмотр трудозатрат"),
    LOG_TIME("Учёт трудозатрат"),
    EDIT_TIME_ENTRIES("Редактирование учёта времени"),
    EDIT_OWN_TIME_ENTRIES("Редактирование собственного учёта времени"),
    MANAGE_PROJECT_ACTIVITIES("Управление типами действий для проекта"),
    LOG_TIME_FOR_OTHER_User("Учитывать время других пользователей"),
    IMPORT_TIME_ENTRIES("Импорт трудозатрат"),
    VIEW_WIKI_Page("Просмотр Wiki"),
    VIEW_WIKI_EDITS("Просмотр истории Wiki"),
    EXPORT_WIKI_Page("Экспорт wiki-страниц"),
    EDIT_WIKI_Page("Редактирование wiki-страниц"),
    RENAME_WIKI_Page("Переименование wiki-страниц"),
    DELETE_WIKI_Page("Удаление wiki-страниц"),
    DELETE_WIKI_Page_ATTACHMENTS("Удаление прикреплённых файлов"),
    PROTECT_WIKI_Page("Блокирование wiki-страниц"),
    MANAGE_WIKI("Управление Wiki");

    public  final String description;


    public static RolePermission of(String description) {
        return Stream.of(values())
                .filter(it -> it.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден RolePermission по описанию " + description));
    }


    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
