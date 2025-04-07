
package com.example.taskflow.Task;

import com.example.taskflow.Room.Room;
import com.example.taskflow.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;

import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Date finishDate;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToOne
    @JoinColumn(name = "room_code",nullable = false)
    @JsonBackReference
    private Room room;
    @ManyToOne
    @JoinColumn(name = "assigned_by")
    @JsonIgnoreProperties({"roles", "authorities", "accountNonExpired", "credentialsNonExpired", "accountNonLocked", "admin"})
    private User assignedBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    @JsonIgnoreProperties({"roles", "authorities", "accountNonExpired", "credentialsNonExpired", "accountNonLocked", "admin"})
    private User assignedTo;


    public Task() {
    }

    public Task(String title, String description, Date finishDate, Difficulty difficulty, Room room, User assignedBy, User assignedTo) {
        this.title = title;
        this.description = description;
        this.finishDate = finishDate;
        this.difficulty = difficulty;
        this.room = room;
        this.assignedBy = assignedBy;
        this.assignedTo = assignedTo;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }
}
