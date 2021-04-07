package redmine.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.util.StringGenerator;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UserInfo {
    private Integer id;
    private String login=StringGenerator.randomString(8,StringGenerator.ENGLISH);
    private Boolean admin;
    private String firstname=StringGenerator.randomString(12,StringGenerator.ENGLISH);
    private String lastname=StringGenerator.randomString(12,StringGenerator.ENGLISH);
    private String mail=StringGenerator.randomEmail();
    private LocalDateTime created_on=LocalDateTime.now();
    private LocalDateTime last_login_on=LocalDateTime.now();
    private String api_key;
    private Integer status=2;
    private String password;


}
