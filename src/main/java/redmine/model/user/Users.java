package redmine.model.user;

import lombok.*;
import lombok.experimental.Accessors;
import redmine.dataBase.UserRequest;
import redmine.model.Generatable;
import redmine.ui.pages.CucumberName;
import redmine.util.StringGenerator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;


@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@Data
public class Users implements Generatable<Users> {

    private Integer id;
    @CucumberName("логин")
    private String login=StringGenerator.stringRandom(8,StringGenerator.ENGLISH);
    private String firstname=StringGenerator.stringRandom(12,StringGenerator.ENGLISH);;
    private String lastname=StringGenerator.stringRandom(12,StringGenerator.ENGLISH);;
    private Boolean admin=false;
    private Integer status=1;
    private String mail=StringGenerator.email();
    private String password=StringGenerator.stringRandom(7,StringGenerator.ENGLISH);
    private String salt= StringGenerator.stringRandom(31,"0123456789abcdef");
    private String hashed_password=sha1Hex(salt + sha1Hex(password));
    private Timestamp last_login_on=Timestamp.valueOf(LocalDateTime.now());
    private String language="ru";
    private Integer auth_source_id;
    private Timestamp created_on=Timestamp.valueOf(LocalDateTime.now());
    private Timestamp updated_on=Timestamp.valueOf(LocalDateTime.now());
    private String type="User";
     private String  identity_url;
    private  String mail_notification="all";
    private Boolean must_change_passwd=false;
    private Timestamp passwd_changed_on=Timestamp.valueOf(LocalDateTime.now());
    private String api_key =StringGenerator.stringRandom(39,"0123456789abcdef");
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");




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
