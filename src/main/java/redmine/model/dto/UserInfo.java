package redmine.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserInfo {
    private Integer id;
    private String login;
    private Boolean admin;
    private String firstname;
    private String lastname;
    private String mail;
    private LocalDateTime created_on;
    private LocalDateTime last_login_on;
    private String apiKey;
    private Integer status;

}
