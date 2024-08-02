package gmky.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gmky.core.enumeration.RoleTypeEnum;
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
@Table(name = "role")
public class Role extends AbstractAuditEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    private RoleTypeEnum type;

    @Column(name = "is_enable")
    private Boolean isEnable;

    @Column(name = "is_default")
    private Boolean isDefault;

    @ManyToMany
    @ToString.Exclude
    @JsonIgnoreProperties
    @EqualsAndHashCode.Exclude
    @JoinTable(name = "ps_role", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "ps_id"))
    private Set<PermissionSet> permissionSets = new HashSet<>();
}
