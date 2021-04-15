package steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Пусть;
import redmine.Manager.Context;
import redmine.cucumber.ParametersValidator;
import redmine.db.ProjectRequests;
import redmine.model.project.Project;
import redmine.model.role.*;
import redmine.model.user.User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

public class GenerationSteps {

    @Пусть("В системе заведен пользователь {string} с параметрами:")
    public void createAndSaveUser(String stashId, Map<String, String> params) {
        User user = new User();
        if (params.containsKey("admin")) {
            user.setAdmin(Boolean.parseBoolean(params.get("admin")));
        }
        if (params.containsKey("status")) {
            user.setStatus(Integer.parseInt(params.get("status")));
        }
        user.generate();
        Context.getStash().put(stashId, user);
    }

    @Пусть("В системе заведен проект {string} с параметрами:")
    public void createAndSaveProject(String stashId, Map<String, String> params) {
        Project project = new Project();
        if (params.containsKey("is_public")) {
            project.setIsPublic(Boolean.parseBoolean(params.get("is_public")));
        }
        project.generate();
        Context.getStash().put(stashId, project);
    }

    @Пусть("В системе заведена роль {string} с параметрами по умолчанию:")
    public void createAndSaveRole(String stashId) {
        Role role = new Role();
        Context.getStash().put(stashId, role);
    }

    @Пусть("В системе заведена роль {string} с параметрами:")
    public void createAndSaveRoleParam(String stashId, Map<String, String> parameters) {
        ParametersValidator.validateRoleParameters(parameters);
        Role role = new Role();
        if (parameters.containsKey("Позиция")) {
            role.setPosition(parseInt(parameters.get("Позиция")));
        }
        if (parameters.containsKey("Встроенная")) {
            role.setBuiltin(parseInt(parameters.get("Встроенная")));
        }
        if (parameters.containsKey("Задача может быть назначена этой роли")) {
            role.setAssignable(parseBoolean(parameters.get("Задача может быть назначена этой роли")));
        }
        if (parameters.containsKey("Видимость задач")) {
            role.setIssuesVisibility(
                    IssuesVisibility.of(parameters.get("Видимость задач"))
            );
        }
        if (parameters.containsKey("Видимость пользователей")) {
            role.setUserVisibility(
                    UserVisibility.of(parameters.get("Видимость пользователей"))
            );
        }
        if (parameters.containsKey("Видимость трудозатрат")) {
            role.setTimeEntriesVisibility(
                    TimeEntriesVisibility.of(parameters.get("Видимость трудозатрат"))
            );
        }
        if (parameters.containsKey("Права")) {
            RolePermissions permissions = Context.get(parameters.get("Права"), RolePermissions.class);
            role.setRolePermissionSet(permissions);
        }
        role.generate();
        Context.put(stashId, role);
    }


    @И("У пользователя {string} есть доступ к проекту {string} c ролью {string}")
    public void createProjectUserRole(String stashIdUser, String stashIdProject, String stashIdRole) {
        User user = Context.getStash().get(stashIdUser, User.class);
        Project project = Context.getStash().get(stashIdProject, Project.class);
        Role role = Context.getStash().get(stashIdRole, Role.class);
        ProjectRequests.addUserAndRoleToProject(project, user, role);
    }

    @И("Я сохраняю список прав в переменную {string}:")
    public void createPermissions(String stashId, List<String> permissionsDescriptions) {
        Set<RolePermission> permissions = permissionsDescriptions.stream()
                .map(RolePermission::of)
                .collect(Collectors.toSet());
        RolePermissions rolePermissions = new RolePermissions(permissions);
        Context.getStash().put(stashId, rolePermissions);
    }
}
