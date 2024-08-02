package gmky.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity implements Serializable {
    @CreatedDate
    @Column(name = "created_at")
    protected Instant createdAt;

    @CreatedBy
    @Column(name = "created_by")
    protected String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected Instant updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    protected String updatedBy;
}
