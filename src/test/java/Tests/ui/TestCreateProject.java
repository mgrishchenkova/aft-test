package Tests.ui;

import org.testng.annotations.Test;
import redmine.model.project.Project;

public class TestCreateProject {

    @Test(description = "СОздание проекта")
    public void createProject(){

        Project project= new Project().setIs_public(false).generate();

    }
}
