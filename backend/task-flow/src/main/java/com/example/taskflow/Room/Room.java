package com.example.taskflow.Room;


import com.example.taskflow.Task.Task;
import com.example.taskflow.User.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="rooms")
public class Room {

    private String name;
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private String roomCode;

    @ManyToMany
    @JoinTable(
            name = "rooms_users",
            joinColumns = @JoinColumn(name = "room_code"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Task> tasks = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"roles", "authorities", "accountNonExpired", "credentialsNonExpired", "accountNonLocked", "admin"})
    private User owner;

    public Room( String name, String password, User owner) {
        this.name = name;
        this.password = password;
        this.owner = owner;
        this.users.add(owner);
    }

    public Room() {

    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    public void addTask(Task task) {
        tasks.add(task);
        task.setRoom(this);
    }
    public void addUser(User user) {
        this.users.add(user);
    }
}
