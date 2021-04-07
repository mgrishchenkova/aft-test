package redmine.model.role;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static RolePermissions of(String stringValue) {
        Set<RolePermission> permissions = Stream.of(stringValue.split("\n"))
                .filter(str -> str.startsWith("- :"))
                .map(str -> str.substring(3).toUpperCase())
                .map(RolePermission::valueOf)
                .collect(Collectors.toSet());
        return new RolePermissions(permissions);
    }
}
