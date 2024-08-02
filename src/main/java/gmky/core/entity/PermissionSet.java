package gmky.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gmky.core.enumeration.PermissionSetTypeEnum;
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
@Table(name = "permission_set")
public class PermissionSet extends AbstractAuditEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ps_type")
    @Enumerated(EnumType.STRING)
    private PermissionSetTypeEnum type;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "ps_item", joinColumns = @JoinColumn(name = "ps_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();

    @ToString.Exclude
    @JsonIgnoreProperties
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "permissionSets")
    private Set<Role> roles = new HashSet<>();
}
