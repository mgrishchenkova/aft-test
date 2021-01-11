package redmine.model.rolee;

import java.util.Set;

public class Role {
    private String name;
    private Boolean assignable;
    private Integer position;
    private IssuesVisibility issuesVisibility;
    private UsersVisibility usersVisibility;
    private Set<RolePermission> rolePermissionSet;
    private TimeEntriesVisibility timeEntriesVisibility;
    private Boolean allRolesManaged;
    private int builtin;
    private String settings;

    public Role() {

    }
}
