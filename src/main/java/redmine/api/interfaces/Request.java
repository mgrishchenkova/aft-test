package redmine.api.interfaces;

import java.util.Map;

public interface Request {
    String getUri();
    Methods getMethods();
    Map<String,String> getParameters();
    Object getBody();
    Map<String,String> getHeaders();

}
