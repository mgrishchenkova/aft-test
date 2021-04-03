package redmine.model.project;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import redmine.dataBase.ProjectRequest;
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
    private String name= "Mary"+ StringGenerator.stringRandom(6,StringGenerator.ENGLISH);
    private String description="description11";
    private String homepage;
    private Boolean is_public=false;
    private Integer parent_id;
    private Timestamp created_on=Timestamp.valueOf(LocalDateTime.now());;
    private Timestamp updated_on=Timestamp.valueOf(LocalDateTime.now());;
    private String identifier=StringGenerator.stringRandom(10,StringGenerator.ENGLISH);
    private Integer status=1;
    private Integer lft=1;
    private Integer rgt=1;
    private Boolean inherit_members=false;
    private Integer default_version_id;
    private Integer default_assigned_to_id;

    @Override
    public Project read() {
        Project project= ProjectRequest.getProject(this);
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
