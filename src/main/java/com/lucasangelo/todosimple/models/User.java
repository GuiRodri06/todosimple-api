package com.lucasangelo.todosimple.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = User.TABLE_NAME) // cria a tabela user
public class User {

    public interface CreatUser {}
    public interface UpdateUser {}

    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // cria a tabela incrementado 1. ex: 1,2,3,4...
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotNull(groups = CreatUser.class)
    @NotEmpty(groups = CreatUser.class)
    @Size(groups = CreatUser.class, min = 2, max = 100)
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 60, nullable = false)
    @NotNull(groups = {CreatUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreatUser.class, UpdateUser.class})
    @Size(groups = {CreatUser.class, UpdateUser.class}, min = 8, max = 60)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks = new ArrayList<Task>() {};

    public User() {
    }

    public User(Long id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof User))
            return false;

        User other = (User) obj;
        if (this.id == null)
            if (other.id != null)
                    return false;
            else if (!this.id.equals(other.id))
                return false;

        return Objects.equals(this.id, other.id) && Objects.equals(this.userName, other.userName)
                && Objects.equals(this.password, other.password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
