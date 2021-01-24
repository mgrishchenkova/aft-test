package redmine.model.rolee;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import redmine.dataBase.RoleRequests;
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
    private IssuesVisibility issuesVisibility=IssuesVisibility.ALL;
    private UsersVisibility usersVisibility=UsersVisibility.MEMBERS_OF_VISIBLE_PROJECT;
    private Set<RolePermission> rolePermissionSet;
    private TimeEntriesVisibility timeEntriesVisibility=TimeEntriesVisibility.ALL;
    private Boolean allRolesManaged=true;
    private int builtin;
    private String settings="--- !ruby/hash:ActiveSupport::HashWithIndifferentAccess\n" +
            "permissions_all_trackers: !ruby/hash:ActiveSupport::HashWithIndifferentAccess\n" +
            "  view_issues: '1'\n" +
            "  add_issues: '1'\n" +
            "  edit_issues: '1'\n" +
            "  add_issue_notes: '1'\n" +
            "  delete_issues: '1'\n" +
            "permissions_tracker_ids: !ruby/hash:ActiveSupport::HashWithIndifferentAccess\n" +
            "  view_issues: []\n" +
            "  add_issues: []\n" +
            "  edit_issues: []\n" +
            "  add_issue_notes: []\n" +
            "  delete_issues: []\n";



    @Override
    public Role read() {
        Role role = RoleRequests.getRole(this);
        return role;
    }

    @Override
    public Role update() {
        return RoleRequests.updateRole(this);
    }

    @Override
    public Role create() {
        return RoleRequests.addRole(this);
    }
}
