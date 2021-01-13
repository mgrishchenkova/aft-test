package redmine.model.rolee;

import redmine.util.Generatable;

import java.util.Set;

public class Role implements Generatable<Role> {
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

    @Override
    public Role read() {
        return null;
    }

    @Override
    public Role update() {
        return null;
    }

    @Override
    public Role create() {
        return null;
    }
}
