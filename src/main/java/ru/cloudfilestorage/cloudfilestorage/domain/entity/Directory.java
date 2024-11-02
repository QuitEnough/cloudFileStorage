package ru.cloudfilestorage.cloudfilestorage.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "directories")
@Getter
@Setter
public class Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "PARENT_ID")
    private Long parentId;

}
