package gmky.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "permission")
public class Permission extends AbstractAuditEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission_code")
    private String permissionCode;

    @Column(name = "resource_code")
    private String resourceCode;

    @Column(name = "description")
    private String description;

    @ToString.Exclude
    @JsonIgnoreProperties
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "permissions")
    private Set<PermissionSet> permissionSets = new HashSet<>();
}
