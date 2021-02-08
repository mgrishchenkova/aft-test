package redmine.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import redmine.util.StringGenerator;

import java.sql.Timestamp;
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
    private Timestamp created_on=Timestamp.valueOf(LocalDateTime.now());;
    private Timestamp last_login_on=Timestamp.valueOf(LocalDateTime.now());;
    private String apiKey=StringGenerator.stringRandom(31,"0123456789abcdef");
    private Integer status=2;
    private String password=StringGenerator.stringRandom(9,StringGenerator.ENGLISH);


}
