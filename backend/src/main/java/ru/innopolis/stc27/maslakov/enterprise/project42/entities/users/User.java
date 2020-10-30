package ru.innopolis.stc27.maslakov.enterprise.project42.entities.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(generator = "USER_ID_GENERATOR", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "USER_ID_GENERATOR", allocationSize = 1, sequenceName = "users_user_id_seq")
    private Long id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Convert(converter = RoleAttributeConverter.class)
    @Column(name = "user_role_id", nullable = false)
    private Role role;

}
