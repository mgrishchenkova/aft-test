package redmine.model.rolee;

import java.util.Set;

public class Role {
    private String name;
    private Boolean assignable;
    private Integer position;
    private IssuesVisibility issuesVisibility;
    private UsersVisibility usersVisibility;
    private Set<RolePermission> rolePermissionSet;


}
