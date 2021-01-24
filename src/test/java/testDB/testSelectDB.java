package testDB;

import org.testng.annotations.Test;
import redmine.dataBase.RoleRequests;
import redmine.dataBase.UserRequest;
import redmine.model.rolee.*;

import java.util.Arrays;
import java.util.HashSet;

import static redmine.model.rolee.RolePermission.*;

public class testSelectDB {
    @Test
    public void testAddDB() {
        Role role = new Role();
        role.setName("testUpdateDB2").
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
        Role role = new Role();
        role.setName("testUpdateDB");
        role.setRolePermissionSet(new RolePermissions(new HashSet<>(Arrays.asList(ADD_DOCUMENTS))));
        role.setUsersVisibility(UsersVisibility.ALL);
        RoleRequests.updateRole(role);
    }

    @Test
    public void testGeneratableAdd(){
        Role role = new Role();
        role.setName("Создание новой роли");
        role.setRolePermissionSet(new RolePermissions(new HashSet<>(Arrays.asList(ADD_DOCUMENTS))));
        role.setUsersVisibility(UsersVisibility.MEMBERS_OF_VISIBLE_PROJECT);
        role.setIssuesVisibility(IssuesVisibility.DEFAULT);
        role.setTimeEntriesVisibility(TimeEntriesVisibility.ALL);
        role.setAssignable(true);
        role.generate();
    }

    @Test
    public void testAllUsers(){
        int count = UserRequest.getAllUsers().size();
        System.out.println(count);
    }
}
