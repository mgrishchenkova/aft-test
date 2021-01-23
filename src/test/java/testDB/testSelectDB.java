package testDB;

import org.testng.annotations.Test;
import redmine.dataBase.RoleRequests;
import redmine.model.rolee.*;

import java.util.Arrays;
import java.util.HashSet;

import static redmine.model.rolee.RolePermission.MANAGE_MEMBERS;
import static redmine.model.rolee.RolePermission.MANAGE_VERSIONS;

public class testSelectDB {
    @Test
    public void testAddDB() {
        Role role = new Role();
        role.setName("testUpdateDB").
                setPosition(1).
                setAssignable(true).
                setBuiltin(1).
                setRolePermissionSet(new RolePermissions(new HashSet<>(Arrays.asList(MANAGE_MEMBERS, MANAGE_VERSIONS)))).
                setIssuesVisibility(IssuesVisibility.ALL).
                setUsersVisibility(UsersVisibility.ALL).
                setTimeEntriesVisibility(TimeEntriesVisibility.ALL)
                .setAllRolesManaged(false);

        RoleRequests.addRole(role);


    }

    @Test
    public void testUpdateDB() {


    }
}
