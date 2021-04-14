package redmine.db;

import redmine.Manager.Manager;
import redmine.model.user.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRequests {

    public static List<User> getAllUser() {
        String query = "select *from users;";
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        return result.stream()
                .map(map -> {
                    User user = new User();
                    user.setId((Integer) map.get("id"));
                    user.setLogin("");
                    user.setLogin((String) map.get("login"));
                    user.setFirstName((String) map.get("firstname"));
                    user.setLastName((String) map.get("lastname"));
                    user.setAdmin((Boolean) map.get("admin"));
                    user.setStatus((Integer) map.get("status"));
                    user.setHashedPassword((String) map.get("hashed_password"));
                    user.setSalt((String) map.get("salt"));
                    user.setLastLoginOn((Timestamp) map.get("last_login_on"));
                    user.setLanguage((String) map.get("language"));
                    user.setAuthSourceId((Integer) map.get("auth_source_id"));
                    user.setCreatedOn((Timestamp) map.get("created_on"));
                    user.setUpdatedOn((Timestamp) map.get("updated_on"));
                    user.setType((String) map.get("type"));
                    user.setIdentityUrl((String) map.get("identity_url"));
                    user.setMailNotification((String) map.get("mail_notification"));
                    user.setMustChangePasswd((Boolean) map.get("must_change_passwd"));
                    user.setPasswdChangedOn((Timestamp) map.get("passwd_changed_on"));
                    return user;
                }).collect(Collectors.toList());
    }

    public static User getUser(User objectUser) {
        return getAllUser().stream()
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

    public static User updateUser(User user) {
        String query = "update public.users\n" +
                "set hashed_password=?, firstname=?, lastname=?, \"admin\"=?, status=?, last_login_on=?, \"language\"=?, auth_source_id=?, created_on=?, updated_on=?, \"type\"=?, identity_url=?, mail_notification=?, salt=?, must_change_passwd=?, passwd_changed_on=?\n" +
                "where login=? RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,

                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAdmin(),
                user.getStatus(),
                user.getLastLoginOn(),
                user.getLanguage(),
                user.getAuthSourceId(),
                user.getCreatedOn(),
                user.getUpdatedOn(),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification(),
                user.getSalt(),
                user.getMustChangePasswd(),
                user.getPasswdChangedOn(),
                user.getLogin()

        );
        user.setId((Integer) result.get(0).get("id"));
        return user;

    }

    public static User addUser(User user) {
        String query = "insert into public.users\n" +
                "(id, login, hashed_password, firstname, lastname, \"admin\", status, last_login_on, \"language\", auth_source_id, created_on, updated_on, \"type\", identity_url, mail_notification, salt, must_change_passwd, passwd_changed_on)\n" +
                "values(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAdmin(),
                user.getStatus(),
                user.getLastLoginOn(),
                user.getLanguage(),
                user.getAuthSourceId(),
                user.getCreatedOn(),
                user.getUpdatedOn(),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification(),
                user.getSalt(),
                user.getMustChangePasswd(),
                user.getPasswdChangedOn(),
                user.getLogin()

        );
        user.setId((Integer) result.get(0).get("id"));

        return user;


    }
}
