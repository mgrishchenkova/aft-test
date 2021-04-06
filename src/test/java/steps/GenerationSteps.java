package steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Пусть;
import redmine.Manager.Context;
import redmine.dataBase.ProjectRequest;
import redmine.model.project.Project;
import redmine.model.rolee.Role;
import redmine.model.user.Users;

import java.util.Map;

public class GenerationSteps {

    @Пусть("В системе заведен пользователь {string} с параметрами:")
    public void createAndSaveUser(String stashId, Map<String, String> params) {
        Users user = new Users();
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
            project.setIs_public(Boolean.parseBoolean(params.get("is_public")));
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
    public void createAndSaveRoleParam(String stashId) {
        Role role = new Role();
        Context.getStash().put(stashId, role);
    }


    @И("У пользователя {string} есть доступ к проекту {string} c ролью {string}")
    public void createProjectUserRole(String stashIdUser, String stashIdProject, String stashIdRole) {
        Users user = Context.getStash().get(stashIdUser, Users.class);
        Project project = Context.getStash().get(stashIdProject, Project.class);
        Role role = Context.getStash().get(stashIdRole, Role.class);
        Project updateProject = ProjectRequest.addUserAndRoleToProject(project, user, role);

    }
}
