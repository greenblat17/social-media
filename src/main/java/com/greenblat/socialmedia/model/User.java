package com.greenblat.socialmedia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @OneToMany(
            mappedBy = "relatingUser",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<UserRelation> userRelations;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(
            mappedBy = "sender",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Message> messages;
}
