package redmine.dataBase;

import redmine.Manager.Manager;
import redmine.model.rolee.Role;
import redmine.model.user.Users;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRequest {

    public static List<Users> getAllUsers(){
        String query="select *from users;";
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        return result.stream()
                .map(map -> {
                    Users user = new Users();
                    user.setId((Integer)map.get("id")); user.setLogin("");
                    user.setLogin((String) map.get("login"));
                    user.setFirstname((String) map.get("firstname"));
                    user.setLastname((String) map.get("lastname"));
                    user.setAdmin((Boolean) map.get("admin"));
                    //user.setStatus(Status.valueOf((Integer) map.get("status")) );
                    user.setEmail((String)map.get("email"));
                    user.setPassword((String) map.get("password"));
                    user.setHashed_password((String)map.get("hashed_password"));
                    user.setSalt((String) map.get("salt"));
                    user.setLast_login_on((Date) map.get("last_login_on"));
                    user.setLanguage((String) map.get("language"));
                    user.setAuth_source_id((Integer) map.get("auth_source_id"));
                    user.setCreated_on((Date) map.get("created_on"));
                    user.setUpdated_on((Date) map.get("updated_on"));
                    user.setType((String) map.get("type"));
                    user.setIdentity_url((String) map.get("identity_url"));
                    user.setMail_notification((String) map.get("mail_notification"));
                    user.setMust_change_passwd((Boolean) map.get("must_change_passwd"));
                    user.setPasswd_changed_on((Date) map.get("passwd_changed_on"));
                    return user;
                }).collect(Collectors.toList());
    }
    public static Users getRole(Users objectUser) {
        return getAllUsers().stream()
                .filter(user -> {
                    if (objectUser.getId() == null) {
                        return objectUser.getLogin().equals(user.getLogin());
                    } else {
                        return (objectUser.getId().equals(user.getId()));
                    }
                })
                .findFirst()
                .orElse(null);
    }

    public static Users updateRole(){
        String query="UPDATE public.users\n" +
                "SET hashed_password=?, firstname=?, lastname=?, \"admin\"=?, status=?, last_login_on=?, \"language\"=?, auth_source_id=?, created_on=?, updated_on=?, \"type\"=?, identity_url=?, mail_notification=?, salt=?, must_change_passwd=?, passwd_changed_on=?\n" +
                "WHERE login=? RETURNING id;\n";


    }

    public static Users addRole(Users user){
        String query="INSERT INTO public.users\n" +
                "(id, login, hashed_password, firstname, lastname, \"admin\", status, last_login_on, \"language\", auth_source_id, created_on, updated_on, \"type\", identity_url, mail_notification, salt, must_change_passwd, passwd_changed_on)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '?, ?, ?, ?, ?, ?, ?) RETURNING id;\n";
        List<Map<String,Object>> result = Manager.dbConnection.executePreparedQuery(query,
                user.getLogin(),
                user.getHashed_password(),
                user.getFirstname(),
                user.getLastname(),
                user.getAdmin(),
                user.getStatus(),
                user.getLast_login_on(),
                user.getLanguage(),

                )

    }
}
