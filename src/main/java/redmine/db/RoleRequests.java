package redmine.db;

import redmine.Manager.Manager;
import redmine.model.role.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoleRequests {
    public static List<Role> getAllRoles() {
        String query = "select *from roles";
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        return result.stream()
                .map(map -> {
                    Role role = new Role();
                    role.setId((Integer) map.get("id"));
                    role.setName((String) map.get("name"));
                    role.setPosition((Integer) map.get("position"));
                    role.setAssignable((Boolean) map.get("assignable"));
                    role.setRolePermissionSet(RolePermissions.of((String) map.get("permissions")));
                    role.setIssuesVisibility(
                            IssuesVisibility.valueOf(((String) map.get("issues_visibility")).toUpperCase()));
                    role.setTimeEntriesVisibility(
                            TimeEntriesVisibility.valueOf(((String) map.get("time_entries_visibility")).toUpperCase())
                    );
                    role.setUserVisibility(UserVisibility.valueOf(((String) map.get("users_visibility")).toUpperCase()));
                    role.setAllRolesManaged((Boolean) map.get("all_roles_managed"));
                    role.setSettings((String) map.get("settings"));
                    role.setBuiltin((Integer) map.get("builtin"));
                    return role;
                })
                .collect(Collectors.toList());
    }

    public static Role addRole(Role role) {
        String query = "insert into public.roles\n" +
                "(id, \"name\", \"position\", assignable, builtin, permissions, issues_visibility, users_visibility, time_entries_visibility, all_roles_managed, settings)\n" +
                "values(default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                role.getName(),
                role.getPosition(),
                role.getAssignable(),
                role.getBuiltin(),
                role.getRolePermissionSet().toString(),
                role.getIssuesVisibility().toString(),
                role.getUserVisibility().toString(),
                role.getTimeEntriesVisibility().toString(),
                role.getAllRolesManaged(),
                role.getSettings()
        );
        role.setId((Integer) result.get(0).get("id"));
        return role;

    }

    public static Role updateRole(Role role) {
        String query = "update public.roles\n" +
                "set \"position\"=?, assignable=?, builtin=?, " +
                "permissions=?, issues_visibility=?, users_visibility=?, time_entries_visibility=?, all_roles_managed=?, settings=?\n" +
                "where name=? returning id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                role.getPosition(),
                role.getAssignable(),
                role.getBuiltin(),
                role.getRolePermissionSet().toString(),
                role.getIssuesVisibility().toString(),
                role.getUserVisibility().toString(),
                role.getTimeEntriesVisibility().toString(),
                role.getAllRolesManaged(),
                role.getSettings(),
                role.getName());

        role.setId((Integer) result.get(0).get("id"));
        return role;

    }


    public static Role getRoleId(Integer id) {
        String query = String.format("select *from roles where id=%d", id);
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        return result.stream()
                .map(map -> {
                    Role role = new Role();
                    role.setId((Integer) map.get("id"));
                    role.setName((String) map.get("name"));
                    role.setPosition((Integer) map.get("position"));
                    role.setAssignable((Boolean) map.get("assignable"));
                    role.setRolePermissionSet(RolePermissions.of((String) map.get("permissions")));
                    role.setIssuesVisibility(
                            IssuesVisibility.valueOf(((String) map.get("issues_visibility")).toUpperCase()));
                    role.setTimeEntriesVisibility(
                            TimeEntriesVisibility.valueOf(((String) map.get("time_entries_visibility")).toUpperCase())
                    );
                    role.setUserVisibility(UserVisibility.valueOf(((String) map.get("users_visibility")).toUpperCase()));
                    role.setAllRolesManaged((Boolean) map.get("all_roles_managed"));
                    role.setSettings((String) map.get("settings"));
                    role.setBuiltin((Integer) map.get("builtin"));
                    return role;
                })
                .findFirst()
                .orElse(null);

    }
    public static Role getRoleName(String name) {
        String query = String.format("select *from roles where name='%s'",name);
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        return result.stream()
                .map(map -> {
                    Role role = new Role();
                    role.setId((Integer) map.get("id"));
                    role.setName((String) map.get("name"));
                    role.setPosition((Integer) map.get("position"));
                    role.setAssignable((Boolean) map.get("assignable"));
                    role.setRolePermissionSet(RolePermissions.of((String) map.get("permissions")));
                    role.setIssuesVisibility(
                            IssuesVisibility.valueOf(((String) map.get("issues_visibility")).toUpperCase()));
                    role.setTimeEntriesVisibility(
                            TimeEntriesVisibility.valueOf(((String) map.get("time_entries_visibility")).toUpperCase())
                    );
                    role.setUserVisibility(UserVisibility.valueOf(((String) map.get("users_visibility")).toUpperCase()));
                    role.setAllRolesManaged((Boolean) map.get("all_roles_managed"));
                    role.setSettings((String) map.get("settings"));
                    role.setBuiltin((Integer) map.get("builtin"));
                    return role;
                })
                .findFirst()
                .orElse(null);

    }
}
