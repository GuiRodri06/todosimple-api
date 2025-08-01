package com.lucasangelo.todosimple.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucasangelo.todosimple.models.enums.ProfileEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = User.TABLE_NAME) // cria a tabela user
@AllArgsConstructor // cria os construtores
@NoArgsConstructor // cria um construtor vazio
@Getter // cria os getters
@Setter // cria os setters
@EqualsAndHashCode // cria o Equals e o HasCode
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
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 60, nullable = false)
    @NotNull(groups = {CreatUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreatUser.class, UpdateUser.class})
    @Size(groups = {CreatUser.class, UpdateUser.class}, min = 8, max = 60)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Task> tasks = new ArrayList<Task>() {};

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile")
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnum> getProfiles() {
        return this.profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profileEnum) {
        this.profiles.add(profileEnum.getCode());
    }

}
