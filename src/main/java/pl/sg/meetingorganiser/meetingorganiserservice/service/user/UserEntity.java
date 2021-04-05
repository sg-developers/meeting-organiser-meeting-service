package pl.sg.meetingorganiser.meetingorganiserservice.service.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sg.meetingorganiser.meetingorganiserservice.util.jpa.AbstractJpaEntity;
import pl.sg.meetingorganiser.meetingorganiserservice.util.jpa.JpaConstants;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(
        name = "app_user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "app_user_public_id_uk",
                        columnNames = JpaConstants.COLUMN_PUBLIC_ID
                )
        }
)
public class UserEntity extends AbstractJpaEntity {
    private static final String SEQUENCE = "app_user_id_seq";

    @Id
    @Column(name = JpaConstants.COLUMN_ID, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false)
    private AuthProvider provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserRoleEntity> roles = new HashSet<>();
}
