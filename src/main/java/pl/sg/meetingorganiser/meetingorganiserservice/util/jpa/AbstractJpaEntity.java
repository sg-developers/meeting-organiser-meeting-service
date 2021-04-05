package pl.sg.meetingorganiser.meetingorganiserservice.util.jpa;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static pl.sg.meetingorganiser.meetingorganiserservice.util.jpa.JpaConstants.COLUMN_CREATED_AT;
import static pl.sg.meetingorganiser.meetingorganiserservice.util.jpa.JpaConstants.TIMESTAMP_WITH_TZ;

@Getter
@Setter
@MappedSuperclass
@Access(AccessType.FIELD)
@EqualsAndHashCode(of = {"publicId"})
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractJpaEntity implements Serializable {

    @Column(name = "public_id", nullable = false, updatable = false, length = 36, columnDefinition = "VARCHAR(36)")
    private String publicId = UUID.randomUUID().toString();

    @CreatedDate
    @Column(name = COLUMN_CREATED_AT, nullable = false, updatable = false, columnDefinition = TIMESTAMP_WITH_TZ)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false, columnDefinition = TIMESTAMP_WITH_TZ)
    private LocalDateTime modifiedAt;

    @Version
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Long version;
}
