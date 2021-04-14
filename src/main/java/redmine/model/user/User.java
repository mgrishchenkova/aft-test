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
    private String firstName = StringGenerator.randomString(12, StringGenerator.ENGLISH);
    private String lastName = StringGenerator.randomString(12, StringGenerator.ENGLISH);
    private Boolean admin = false;
    private Integer status = 1;
    private String mail = StringGenerator.randomEmail();
    private String password = StringGenerator.randomString(7, StringGenerator.ENGLISH);
    private String salt = StringGenerator.randomString(31, "0123456789abcdef");
    private String hashedPassword = sha1Hex(salt + sha1Hex(password));
    private Timestamp lastLoginOn = Timestamp.valueOf(LocalDateTime.now());
    private String language = "ru";
    private Integer authSourceId;
    private Timestamp createdOn = Timestamp.valueOf(LocalDateTime.now());
    private Timestamp updatedOn = Timestamp.valueOf(LocalDateTime.now());
    private String type = "User";
    private String identityUrl;
    private String mailNotification = "all";
    private Boolean mustChangePasswd = false;
    private Timestamp passwdChangedOn = Timestamp.valueOf(LocalDateTime.now());
    private String apiKey = StringGenerator.randomString(39, "0123456789abcdef");
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
