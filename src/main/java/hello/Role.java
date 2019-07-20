package hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt, updatedAt"},
        allowGetters = true
)

//класс с другого проекта. для реализации Spring boot security
//достаточно id, role, Set<Account> accounts
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @EqualsAndHashCode.Exclude
    private Long id;

    @NonNull
    private String role;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "updated_at", nullable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private Date updatedAt = new Date();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE , CascadeType.PERSIST/*, CascadeType.DETACH, CascadeType.REFRESH*/})
    private Set<Account> accounts = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(id, role1.id) &&
                Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
