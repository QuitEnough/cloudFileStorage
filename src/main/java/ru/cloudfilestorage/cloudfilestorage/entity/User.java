package ru.cloudfilestorage.cloudfilestorage.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NamedQuery;

@NamedQuery(
        name = "User.findByUserNameAndId",
        query = "SELECT u from User u where u.userName = ?1 and u.id = ?2"
)
@Entity
@Table(
        name = "usr",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "USER_NAME_UNIQUE",
                        columnNames = "USER_NAME"
                )
        }
)
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

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

}
