package ru.cloudfilestorage.cloudfilestorage.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "FILE_ID"))
    private List<File> files;

}
