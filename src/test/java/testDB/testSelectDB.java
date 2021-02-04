package testDB;

import org.testng.annotations.Test;
import redmine.dataBase.RoleRequests;
import redmine.dataBase.UserRequest;
import redmine.model.rolee.*;
import redmine.model.user.Users;

import java.text.ParseException;
import java.time.LocalDateTime;
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

    @Test
    public void testAddUser() throws ParseException {
        LocalDateTime localDate = LocalDateTime.now();
        Users user=new Users();
        user.setLogin("testLogin5").setAdmin(false);
        UserRequest.addUser(user);
    }
    @Test
    public void testUserUpdate() throws ParseException{
        LocalDateTime localDate = LocalDateTime.now();
        Users users=new Users();
        users.setLogin("testLogin5").

                setAdmin(false);

               // setLast_login_on(localDate).
                //setCreated_on(localDate).
               // setUpdated_on(localDate);
        UserRequest.updateUser(users);
    }

    @Test
    public void testGenerateUser()throws ParseException{

        Users user = new Users();
        user.setLogin("testLogin2").setAdmin(true);
        user.generate();
    }
}
