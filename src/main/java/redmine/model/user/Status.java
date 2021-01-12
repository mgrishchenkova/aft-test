package redmine.model.user;

public enum Status {
    ACTIVE("1"),
    WAS("2"),
    BLOCKED("3");

    private String description;

    Status (String description){
        this.description=description;
    }

}
