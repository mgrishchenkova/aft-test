package redmine.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.util.StringGenerator;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UserInfo {
    private Integer id;
    private String login=StringGenerator.stringRandom(8,StringGenerator.ENGLISH);
    private Boolean admin=false;
    private String firstname=StringGenerator.stringRandom(12,StringGenerator.ENGLISH);
    private String lastname=StringGenerator.stringRandom(12,StringGenerator.ENGLISH);
    private String mail=StringGenerator.email();
    private LocalDateTime created_on=LocalDateTime.now();
    private LocalDateTime last_login_on=LocalDateTime.now();
    private String api_key=StringGenerator.stringRandom(31,"0123456789abcdef");
    private Integer status=2;
    private String password=StringGenerator.stringRandom(9,StringGenerator.ENGLISH);


}
