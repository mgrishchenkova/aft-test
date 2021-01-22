package redmine.model.rolee;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import redmine.model.Generatable;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class Role implements Generatable<Role> {
    private String name="testInsertRole";
    private Integer id=4;
    private Boolean assignable=true;
    private Integer position=11;
    private IssuesVisibility issuesVisibility=IssuesVisibility.ALL;
    private UsersVisibility usersVisibility=UsersVisibility.ALL;
    private Set<RolePermission> rolePermissionSet=new RolePermissions(new HashSet<>());
    private TimeEntriesVisibility timeEntriesVisibility=TimeEntriesVisibility.ALL;
    private Boolean allRolesManaged=false;
    private int builtin=1;
    private String settings="--- !ruby/hash:ActiveSupport::HashWithIndifferentAccess\\n\" +\n" +
            "            \"permissions_all_trackers: !ruby/hash:ActiveSupport::HashWithIndifferentAccess\\n\" +\n" +
            "            \"  view_issues: '1'\\n\" +\n" +
            "            \"  add_issues: '1'\\n\" +\n" +
            "            \"  edit_issues: '1'\\n\" +\n" +
            "            \"  add_issue_notes: '1'\\n\" +\n" +
            "            \"  delete_issues: '1'\\n\" +\n" +
            "            \"permissions_tracker_ids: !ruby/hash:ActiveSupport::HashWithIndifferentAccess\\n\" +\n" +
            "            \"  view_issues: []\\n\" +\n" +
            "            \"  add_issues: []\\n\" +\n" +
            "            \"  edit_issues: []\\n\" +\n" +
            "            \"  add_issue_notes: []\\n\" +\n" +
            "            \"  delete_issues: []\\n\"";



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
