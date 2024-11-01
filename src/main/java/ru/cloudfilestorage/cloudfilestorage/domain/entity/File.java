package ru.cloudfilestorage.cloudfilestorage.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "files")
@Getter
@Setter
public class File implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

//    @Column(name = "USER_ID")
//    private Long userId;

}
