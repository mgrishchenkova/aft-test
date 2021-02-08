package API;

import org.testng.annotations.BeforeMethod;
import redmine.Manager.Manager;
import redmine.api.implementations.RestApiClient;
import redmine.api.interfaces.ApiClient;
import redmine.model.user.Users;

public class TestCase2 {
    private Users user;
    private ApiClient apiClient;

    @BeforeMethod
    public void addUser(){
        user=new Users();
        user.generate();
        apiClient = new RestApiClient(user);
        //ВЫНЕСТИ В МЕТОД!!!
        String addToken="INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?)RETURNING id;\n";

        Manager.dbConnection.executePreparedQuery(addToken,
                user.getId(),"api",user.getApikey(),user.getCreated_on(),user.getUpdated_on());
        String emailAdd="INSERT INTO public.email_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)RETURNING id;;\n";
        Manager.dbConnection.executePreparedQuery(emailAdd,
                user.getId(),user.getEmail(),true,true, user.getCreated_on(),user.getUpdated_on());

    }
}
