package redmine.model.user;

import lombok.*;
import lombok.experimental.Accessors;
import redmine.dataBase.UserRequest;
import redmine.model.Generatable;
import redmine.util.StringGenerator;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@Data
public class Users implements Generatable<Users> {
    private Integer id;
    private String login=StringGenerator.stringRandom(8,StringGenerator.ENGLISH);
    private String firstname=StringGenerator.stringRandom(12,StringGenerator.ENGLISH);;
    private String lastname=StringGenerator.stringRandom(12,StringGenerator.ENGLISH);;
    private Boolean admin;
    private Integer status=1;
    private String email=StringGenerator.email();
    private String password="dfbdfn";
    private String hashed_password;
    private String salt= StringGenerator.stringRandom(32,"0123456789abcdef");
    private LocalDateTime last_login_on=LocalDateTime.now();
    private String language="ru";
    private Integer auth_source_id=1;
    private LocalDateTime created_on=LocalDateTime.now();
    private LocalDateTime updated_on=LocalDateTime.now();
    private String type="User";
     private String  identity_url="636346343s";
    private  String mail_notification="dffd";
    private Boolean must_change_passwd=false;
    private LocalDateTime passwd_changed_on=LocalDateTime.now();

    public String getApiKey(){
        return StringGenerator.stringRandom(40,"0123456789abcdef");
    }


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
