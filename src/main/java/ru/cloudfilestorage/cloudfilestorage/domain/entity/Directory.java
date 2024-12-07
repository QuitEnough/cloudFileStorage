package ru.cloudfilestorage.cloudfilestorage.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "directories")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
