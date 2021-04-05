package pl.sg.meetingorganiser.meetingorganiserservice.service.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static pl.sg.meetingorganiser.meetingorganiserservice.util.jpa.JpaConstants.COLUMN_CREATED_AT;
import static pl.sg.meetingorganiser.meetingorganiserservice.util.jpa.JpaConstants.TIMESTAMP_WITH_TZ;

@Getter
@Setter
@Entity
@Table(name = "app_user_role")
public class UserRoleEntity implements Serializable {
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_ROLE_ID = "role_id";

    @EmbeddedId
    private UserRoleEntityId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    private RoleEntity role;

    @CreatedDate
    @Column(name = COLUMN_CREATED_AT, nullable = false, updatable = false, columnDefinition = TIMESTAMP_WITH_TZ)
    private LocalDateTime createdAt;

    private UserRoleEntity() {
    }

    public UserRoleEntity(UserEntity user, RoleEntity role) {
        this.user = user;
        this.role = role;
        this.id = new UserRoleEntityId(user.getId(), role.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleEntity that = (UserRoleEntity) o;
        return user.equals(that.user) &&
                role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }

    @Getter
    @Setter
    @Embeddable
    public static class UserRoleEntityId implements Serializable {
        @Column(name = COLUMN_USER_ID)
        private Long userId;

        @Column(name = COLUMN_ROLE_ID)
        private Long roleId;

        private UserRoleEntityId() {
        }

        public UserRoleEntityId(Long userId, Long roleId) {
            this.userId = userId;
            this.roleId = roleId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserRoleEntityId that = (UserRoleEntityId) o;
            return userId.equals(that.userId) &&
                    roleId.equals(that.roleId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, roleId);
        }
    }
}
