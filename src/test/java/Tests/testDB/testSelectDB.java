package Tests.testDB;

import org.testng.annotations.Test;
import redmine.db.RoleRequests;
import redmine.db.UserRequests;
import redmine.model.role.*;
import redmine.model.user.User;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static redmine.model.role.RolePermission.*;

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
                setUserVisibility(UserVisibility.ALL).
                setTimeEntriesVisibility(TimeEntriesVisibility.ALL)
                .setAllRolesManaged(false);

        RoleRequests.addRole(role);


    }

    @Test
    public void testUpdateDB() {
        Role role = new Role();
        role.setName("testUpdateDB");
        role.setRolePermissionSet(new RolePermissions(new HashSet<>(Arrays.asList(ADD_DOCUMENTS))));
        role.setUserVisibility(UserVisibility.ALL);
        RoleRequests.updateRole(role);
    }

    @Test
    public void testGeneratableAdd(){
        Role role = new Role();
        role.setName("Создание новой роли");
        role.setRolePermissionSet(new RolePermissions(new HashSet<>(Arrays.asList(ADD_DOCUMENTS))));
        role.setUserVisibility(UserVisibility.MEMBERS_OF_VISIBLE_PROJECT);
        role.setIssuesVisibility(IssuesVisibility.DEFAULT);
        role.setTimeEntriesVisibility(TimeEntriesVisibility.ALL);
        role.setAssignable(true);
        role.generate();
    }

    @Test
    public void testAllUser(){
        int count = UserRequests.getAllUser().size();
        System.out.println(count);
    }

    @Test
    public void testAddUser() throws ParseException {
        LocalDateTime localDate = LocalDateTime.now();
        User user=new User();
        user.setLogin("testLogin5").setAdmin(false);
        UserRequests.addUser(user);
    }
    @Test
    public void testUserUpdate() throws ParseException{
        LocalDateTime localDate = LocalDateTime.now();
        User User=new User();
        User.setLogin("testLogin5").

                setAdmin(false);

               // setLast_login_on(localDate).
                //setCreated_on(localDate).
               // setUpdated_on(localDate);
        UserRequests.updateUser(User);
    }

    @Test
    public void testGenerateUser()throws ParseException{

        User user = new User();
        user.setLogin("testLogin2").setAdmin(true);
        user.generate();
    }
    @Test
    public void testToken(){
        User user = new User();
        user.generate();
        UserRequests.addUser(user);
    }
}
