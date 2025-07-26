package com.lucasangelo.todosimple.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = Task.TABLE_NAME)
@AllArgsConstructor // cria os construtores
@NoArgsConstructor // cria um construtor vazio
@Getter // cria os getters
@Setter // cria os setters
@EqualsAndHashCode // cria o Equals e o HasCode
public class Task {

    public static final String TABLE_NAME = "task";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // cria a tabela incrementado 1. ex: 1,2,3,4...
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "description", length = 100, nullable = false, unique = true)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 255)
    private String description;


}
