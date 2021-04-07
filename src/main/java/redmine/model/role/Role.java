package redmine.model.role;

import lombok.*;
import lombok.experimental.Accessors;
import redmine.db.RoleRequests;
import redmine.model.Generatable;
import redmine.ui.help.CucumberName;
import redmine.util.StringGenerator;

import java.util.HashSet;

@Data
@Accessors(chain = true)
public class Role implements Generatable<Role> {
    @CucumberName("Наименование")
    private String name = StringGenerator.randomString(16, StringGenerator.ENGLISH);

    private Integer id;


    @CucumberName("Задача может быть назначена этой роли")
    private Boolean assignable = false;

    @CucumberName("Позиция")
    private Integer position = 1;

    @CucumberName("Видимость задач")
    private IssuesVisibility issuesVisibility = IssuesVisibility.ALL;

    @CucumberName("Видимость пользователей")
    private UserVisibility UserVisibility = UserVisibility.MEMBERS_OF_VISIBLE_PROJECT;

    @CucumberName("Права")
    private RolePermissions rolePermissionSet = new RolePermissions(new HashSet<>());
    ;

    @CucumberName("Видимость трудозатрат")
    private TimeEntriesVisibility timeEntriesVisibility = TimeEntriesVisibility.ALL;

    private Boolean allRolesManaged = true;

    @CucumberName("Встроенная")
    private int builtin;

    private String settings = "--- !ruby/hash:ActiveSupport::HashWithIndifferentAccess\n" +
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
        return RoleRequests.getRole(this);

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
