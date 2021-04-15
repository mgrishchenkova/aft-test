package redmine.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RoleInfo {
    private Integer id;
    private String name;
    private Boolean assignable;
    private String issues_visibility;
    private String time_entries_visibility;
    private String User_visibility;
    private List<String> permissions;
}
