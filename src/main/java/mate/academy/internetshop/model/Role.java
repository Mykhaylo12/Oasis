package mate.academy.internetshop.model;

import java.util.Objects;

public class Role {
    private Long id;
    private RoleName roleName;

    public Role() {
    }

    public Role(RoleName roleName) {
        this();
        this.roleName = roleName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }

    public enum RoleName {
        USER, ADMIN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(getId(), role.getId())
                && getRoleName() == role.getRoleName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoleName());
    }
}
