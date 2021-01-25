package redmine.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import redmine.dataBase.UserRequest;
import redmine.model.Generatable;

import java.time.LocalDateTime;
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
    private Boolean admin=true;
    private Integer status=1;
    private String email="dvkkdfb";
    private String password="dfbdfn";
    private String hashed_password;
    private String salt="gbennnn";
    private LocalDateTime last_login_on;
    private String language="ru";
    private Integer auth_source_id=1;
    private LocalDateTime created_on;
    private LocalDateTime updated_on;
    private String type="User";
     private String  identity_url="636346343s";
    private  String mail_notification="dffd";
    private Boolean must_change_passwd=false;
    private LocalDateTime passwd_changed_on;



    @Override
    public Users read() {

        Users user = UserRequest.getUser(this);
        return user;
    }

    @Override
    public Users update() {

        return UserRequest.updateUser(this);
    }

    @Override
    public Users create() {
        return UserRequest.addUser(this);
    }
}
