package redmine.model.project;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import redmine.db.ProjectRequest;
import redmine.model.Generatable;
import redmine.util.StringGenerator;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class Project implements Generatable<Project> {

    private Integer id;
    private String name = "ololo" + StringGenerator.randomString(6, StringGenerator.ENGLISH);
    private String description = "description11";
    private String homepage;
    private Boolean isPublic = false;
    private Integer parentId;
    private Timestamp createdOn = Timestamp.valueOf(LocalDateTime.now());
    private Timestamp updatedOn = Timestamp.valueOf(LocalDateTime.now());
    private String identifier = StringGenerator.randomString(10, StringGenerator.ENGLISH);
    private Integer status = 1;
    private Integer lft = 1;
    private Integer rgt = 1;
    private Boolean inheritMembers = false;
    private Integer defaultVersionId;
    private Integer defaultAssignedToId;

    @Override
    public Project read() {
        Project project = ProjectRequest.getProject(this);
        return project;
    }

    @Override
    public Project update() {
        return ProjectRequest.updateProject(this);
    }

    @Override
    public Project create() {
        return ProjectRequest.createProject(this);
    }
}
