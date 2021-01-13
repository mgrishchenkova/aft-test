package redmine.model.rolee;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RolePermissions extends HashSet<RolePermission> {
    public RolePermissions(RolePermission... permissions) {

        this.addAll(Arrays.asList(permissions));
    }

    public RolePermissions(Set<RolePermission> permissions) {
        this.addAll(permissions);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("---\n");
        forEach(permission -> sb.append("- :").append(permission).append("\n"));
        return sb.toString();
    }
}
