package redmine.model.dto;

import redmine.util.StringGenerator;

import java.time.LocalDateTime;
import java.util.Objects;


public class UserInfo {
    private Integer id;
    private String login = StringGenerator.randomString(8, StringGenerator.ENGLISH);
    private Boolean admin;
    private String firstname = StringGenerator.randomString(12, StringGenerator.ENGLISH);
    private String lastname = StringGenerator.randomString(12, StringGenerator.ENGLISH);
    private String mail = StringGenerator.randomEmail();
    private LocalDateTime created_on = LocalDateTime.now();
    private LocalDateTime last_login_on = LocalDateTime.now();
    private String api_key;
    private Integer status = 2;
    private String password;

    public Integer getId() {
        return id;
    }

    public UserInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public UserInfo setLogin(String login) {
        this.login = login;
        return this;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public UserInfo setAdmin(Boolean admin) {
        this.admin = admin;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public UserInfo setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public UserInfo setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public UserInfo setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public LocalDateTime getCreated_on() {
        return created_on;
    }

    public UserInfo setCreated_on(LocalDateTime created_on) {
        this.created_on = created_on;
        return this;
    }

    public LocalDateTime getLast_login_on() {
        return last_login_on;
    }

    public UserInfo setLast_login_on(LocalDateTime last_login_on) {
        this.last_login_on = last_login_on;
        return this;
    }

    public String getApi_key() {
        return api_key;
    }

    public UserInfo setApi_key(String api_key) {
        this.api_key = api_key;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public UserInfo setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", admin=" + admin +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", mail='" + mail + '\'' +
                ", created_on=" + created_on +
                ", last_login_on=" + last_login_on +
                ", api_key='" + api_key + '\'' +
                ", status=" + status +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return id.equals(userInfo.id) &&
                login.equals(userInfo.login) &&
                admin.equals(userInfo.admin) &&
                firstname.equals(userInfo.firstname) &&
                lastname.equals(userInfo.lastname) &&
                mail.equals(userInfo.mail) &&
                created_on.equals(userInfo.created_on) &&
                last_login_on.equals(userInfo.last_login_on) &&
                api_key.equals(userInfo.api_key) &&
                status.equals(userInfo.status) &&
                password.equals(userInfo.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, admin, firstname, lastname, mail, created_on, last_login_on, api_key, status, password);
    }

    public UserInfo() {
    }
}
