package redmine.model.user;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.db.UserRequests;
import redmine.model.Generatable;
import redmine.ui.help.CucumberName;
import redmine.util.StringGenerator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;


@Data
@Accessors(chain = true)
public class User implements Generatable<User> {

    private Integer id;
    @CucumberName("логин")
    private String login = StringGenerator.randomString(8, StringGenerator.ENGLISH);
    private String firstname = StringGenerator.randomString(12, StringGenerator.ENGLISH);
    private String lastname = StringGenerator.randomString(12, StringGenerator.ENGLISH);
    private Boolean admin = false;
    private Integer status = 1;
    private String mail = StringGenerator.randomEmail();
    private String password = StringGenerator.randomString(7, StringGenerator.ENGLISH);
    private String salt = StringGenerator.randomString(31, "0123456789abcdef");
    private String hashed_password = sha1Hex(salt + sha1Hex(password));
    private Timestamp last_login_on = Timestamp.valueOf(LocalDateTime.now());
    private String language = "ru";
    private Integer auth_source_id;
    private Timestamp created_on = Timestamp.valueOf(LocalDateTime.now());
    private Timestamp updated_on = Timestamp.valueOf(LocalDateTime.now());
    private String type = "User";
    private String identity_url;
    private String mail_notification = "all";
    private Boolean must_change_passwd = false;
    private Timestamp passwd_changed_on = Timestamp.valueOf(LocalDateTime.now());
    private String api_key = StringGenerator.randomString(39, "0123456789abcdef");
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


    @Override
    public User read() {

        return UserRequests.getUser(this);
    }

    @Override
    public User update() {

        return UserRequests.updateUser(this);
    }

    @Override
    public User create() {
        return UserRequests.addUser(this);

    }
}
