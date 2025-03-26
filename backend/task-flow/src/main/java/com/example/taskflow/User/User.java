package com.example.taskflow.User;

import com.example.taskflow.Role.Role;
import com.example.taskflow.Room.Room;

import com.example.taskflow.Task.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    @NotBlank
    @Size(min=4, max=16)
    private String username;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","role_id"})}
    )
    private List<Role> roles;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean isAdmin;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean enabled;

    @PrePersist
    public void prePersist() {
        enabled = true;
    }

    @OneToMany(mappedBy = "assignedBy")
    private Set<Task> assignedTasks = new HashSet<>();

    @OneToMany(mappedBy = "assignedTo")
    private Set<Task> tasksAssignedToMe = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Room> rooms = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
