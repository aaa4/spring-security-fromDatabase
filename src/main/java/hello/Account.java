package hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
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

/*
Класс с другого проекта
для работы в этом примере достаточно использовать
id, username, email, Set<Role> roles;
аннотации валидации так же излишни
*/

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //  @EqualsAndHashCode.Exclude
    private Long id;

    @NonNull
    @Column(unique = true)
    @Size(min = 2, max = 30, message = " количество символов должно быть: 2..30")
    private String name;

    private String email;

    @NonNull
    @EqualsAndHashCode.Exclude
    private String encodedPassword;

    @Column
    private boolean enabled = false;

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


    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "account_role",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();


    @Override
    public String toString() {
        String token = "";

        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", encodedPassword='" + encodedPassword + '\'' +
                ", enabled=" + enabled +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", roles=" + roles +
                ", verificationToken=" + token +
                '}';
    }

}
