package steps;

import cucumber.api.java.ru.Пусть;
import redmine.Manager.Context;
import redmine.model.user.Users;

import java.util.Map;

public class GenerationSteps {

    @Пусть("В системе заведен пользователь {string} с параметрами:")
    public void createAndSaveUser(String stashId, Map<String,String> params){
        Users user = new Users();
        if (params.containsKey("admin")){
            user.setAdmin(Boolean.parseBoolean(params.get("admin")));
        }
        if (params.containsKey("status")){
            user.setStatus(Integer.parseInt(params.get("status")));
        }
        user.generate();
        Context.getStash().put(stashId,user);
    }
}
