package redmine.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleDTO {
    private RoleInfo role;
}
