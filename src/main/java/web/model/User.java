package web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "User_And_Role",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roleSet = new HashSet<>();

    private String[] role;

    public User() {
    }

    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleSet;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void userUpdateRole(String[] roleArrays){
        Role roleUser = new Role(2L, "ROLE_USER");
        Role roleAdmin = new Role(1L, "ROLE_ADMIN");
        Set<Role> newRoleList = new HashSet<>();
        for (String i : roleArrays) {
            if (i.equals("ROLE_ADMIN")) {
                newRoleList.add(roleAdmin);
            } else {
                newRoleList.add(roleUser);
            }
        }
        setRoleSet(newRoleList);
    }

    public static class Builder {
        User user;

        public Builder() {
            user = new User();
        }

        public Builder withId(int id) {
            user.id = id;
            return this;
        }

        public Builder withName(String name) {
            user.name = name;
            return this;
        }

        public Builder withAge(int age) {
            user.age = age;
            return this;
        }

        public Builder withPassword(String password) {
            user.password = password;
            return this;
        }

        public Builder withRole(String[] roleArrays) {
            Role roleUser = new Role(2L, "ROLE_USER");
            Role roleAdmin = new Role(1L, "ROLE_ADMIN");
            Set<Role> newRoleList = new HashSet<>();
            for (String i : roleArrays) {
                if (i.equals("ROLE_ADMIN")) {
                    newRoleList.add(roleAdmin);
                } else {
                    newRoleList.add(roleUser);
                }
            }
            user.setRoleSet(newRoleList);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
