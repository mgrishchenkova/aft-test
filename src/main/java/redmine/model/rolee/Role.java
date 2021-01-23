package redmine.model.rolee;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import redmine.model.Generatable;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class Role implements Generatable<Role> {
    private String name;
    private Integer id;
    private Boolean assignable;
    private Integer position;
    private IssuesVisibility issuesVisibility;
    private UsersVisibility usersVisibility;
    private Set<RolePermission> rolePermissionSet;
    private TimeEntriesVisibility timeEntriesVisibility;
    private Boolean allRolesManaged;
    private int builtin;
    private String settings;



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
