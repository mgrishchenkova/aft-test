package redmine.db;

import redmine.Manager.Manager;
import redmine.model.project.Project;
import redmine.model.role.Role;
import redmine.model.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectRequest {
    public static List<Project> getAllProject() {
        String query = "select *from public.projects";
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        return result.stream()
                .map(map -> {
                    Project project = new Project();
                    project.setId((Integer) map.get("id"));
                    project.setName((String) map.get("name"));
                    project.setDescription((String) map.get("description"));
                    project.setHomepage((String) map.get("homepage"));
                    project.setIs_public((Boolean) map.get("is_public"));
                    project.setParent_id((Integer) map.get("parent_id"));
                    project.setIdentifier((String) map.get("identifier"));
                    project.setStatus((Integer) map.get("status"));
                    project.setLft((Integer) map.get("lft"));
                    project.setRgt((Integer) map.get("rgt"));
                    project.setInherit_members((Boolean) map.get("inherit_members"));
                    project.setDefault_version_id((Integer) map.get("default_version_id"));
                    project.setDefault_assigned_to_id((Integer) map.get("default_assigned_to_id"));
                    return project;
                }).collect(Collectors.toList());
    }

    public static Project createProject(Project project) {
        String query = "INSERT INTO public.projects\n" +
                "(id, \"name\", description, homepage, is_public, parent_id, created_on, updated_on, identifier, status, lft, rgt, inherit_members, default_version_id, default_assigned_to_id)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)RETURNING id;";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                project.getName(),
                project.getDescription(),
                project.getHomepage(),
                project.getIs_public(),
                project.getParent_id(),
                project.getCreated_on(),
                project.getUpdated_on(),
                project.getIdentifier(),
                project.getStatus(),
                project.getLft(),
                project.getRgt(),
                project.getInherit_members(),
                project.getDefault_assigned_to_id(),
                project.getDefault_version_id());
        project.setId((Integer) result.get(0).get("id"));
        return project;
    }

    public static Project updateProject(Project project) {
        String query = "\n" +
                "UPDATE public.projects\n" +
                "SET description='?', homepage=?, is_public=?, parent_id=?, created_on='?', updated_on='?', identifier='?', status=?, lft=?, rgt=?, inherit_members=?, default_version_id=?, default_assigned_to_id=?\n" +
                "WHERE name=? RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                project.getName(),
                project.getDescription(),
                project.getHomepage(),
                project.getIs_public(),
                project.getParent_id(),
                project.getCreated_on(),
                project.getUpdated_on(),
                project.getIdentifier(),
                project.getStatus(),
                project.getLft(),
                project.getRgt(),
                project.getInherit_members(),
                project.getDefault_assigned_to_id(),
                project.getDefault_version_id());
        project.setId((Integer) result.get(0).get("id"));
        return project;
    }

    public static Project getProject(Project objectProject) {
        return getAllProject().stream()
                .filter(project -> {
                    if (objectProject.getId() == null) {
                        return objectProject.getName().equals(project.getName());
                    } else {
                        return (objectProject.getId().equals(project.getId()));
                    }
                })
                .findFirst()
                .orElse(null);
    }

    public static Project addUserAndRoleToProject(Project project, User user, Role role) {
        String addUser = "insert into public.members\n" +
                "(id,user_id,project_id,created_on,mail_notification) values(default,?,?,?,false) RETURNING id;\n";

        List<Map<String, Object>> userMembers = Manager.dbConnection.executePreparedQuery(addUser,
                user.getId(), project.getId(), LocalDateTime.now());
        user.setId((Integer) userMembers.get(0).get("id"));
        Integer membersId = (Integer) userMembers.get(0).get("id");

        String addRole = "insert into public.member_roles\n" +
                "(id,member_id,role_id,inherited_from) values (default,?,?,NULL) returning id;\n";
        Manager.dbConnection.executePreparedQuery(addRole, membersId, role.getId());
        return project;
    }
}
