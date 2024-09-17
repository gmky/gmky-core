package gmky.core.entity;

import gmky.core.enumeration.FeatureFlagEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "feature_flag")
public class FeatureFlagEntity extends AbstractAuditEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private FeatureFlagEnum state;
}
