package redmine.model.dto;

import lombok.Data;

import java.util.List;
@Data
public class UserCreatingError {
    List<String> errors;

}
