package pl.sg.meetingorganiser.meetingorganiserservice.service.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sg.meetingorganiser.meetingorganiserservice.infrastructure.security.RoleName;
import pl.sg.meetingorganiser.meetingorganiserservice.util.jpa.AbstractJpaEntity;
import pl.sg.meetingorganiser.meetingorganiserservice.util.jpa.JpaConstants;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(
        name = "app_role",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "app_role_public_id_uk",
                        columnNames = JpaConstants.COLUMN_PUBLIC_ID
                ),
                @UniqueConstraint(
                        name = "app_role_name_uk",
                        columnNames = JpaConstants.COLUMN_PUBLIC_ID
                )
        }
)
public class RoleEntity extends AbstractJpaEntity implements Serializable {
    private static final String SEQUENCE = "app_role_id_seq";

    @Id
    @Column(name = JpaConstants.COLUMN_ID, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", updatable = false, nullable = false)
    private RoleName name;

}
