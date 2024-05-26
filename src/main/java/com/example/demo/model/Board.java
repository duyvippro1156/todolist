package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "boards")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    @JsonIgnore
    private int is_delete;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    Set<TaskList> tasksList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    Users author;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "guessId", referencedColumnName = "id")
    Users guess;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "inviteId", referencedColumnName = "id")
    Invite invite;
}
