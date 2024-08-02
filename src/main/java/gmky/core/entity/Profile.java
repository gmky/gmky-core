package gmky.core.entity;

import gmky.core.enumeration.UserStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile extends AbstractAuditEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", updatable = false)
    private String username;

    @Column(name = "user_id", updatable = false)
    private String userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    @Column(name = "dob")
    private Instant dob;

    @Column(name = "email", updatable = false)
    private String email;

    @Column(name = "locked_at")
    private Instant lockedAt;

    @Column(name = "activated_at")
    private Instant activatedAt;
}
