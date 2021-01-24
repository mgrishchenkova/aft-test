package redmine.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import redmine.model.Generatable;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class Users implements Generatable<Users> {
    private Integer id;
    private String login;
    private String firstname;
    private String lastname;
    private Boolean admin;
    private Status status;
    private String email;
    private String password;
    private String hashed_password;
    private String salt;
    private Date last_login_on;
    private String language;
    private Integer auth_source_id;
    private Date created_on;
    private Date updated_on;
    private String type;
     private String  identity_url;
    private  String mail_notification;
    private Boolean must_change_passwd;
    private Date passwd_changed_on;


    @Override
    public Users read() {
        return null;
    }

    @Override
    public Users update() {
        return null;
    }

    @Override
    public Users create() {
        return null;
    }
}
