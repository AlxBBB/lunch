package topjava.graduation.model;

//import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.SafeHtml;
//import org.springframework.util.CollectionUtils;
//import ru.javawebinar.topjava.View;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@NamedQueries({
//        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
//        @NamedQuery(name = User.BY_EMAIL, query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=?1"),
//        @NamedQuery(name = User.ALL_SORTED, query = "SELECT u FROM User u ORDER BY u.name, u.email"),
//})
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractNamedEntity {

    //public static final String DELETE = "User.delete";
    //public static final String BY_EMAIL = "User.getByEmail";
    //public static final String ALL_SORTED = "User.getAllSorted";

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
//    @SafeHtml(groups = {View.ValidatedRestUI.class})  // https://stackoverflow.com/questions/17480809
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5)
    // https://stackoverflow.com/a/12505165/548473
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public List<Vote> getVotes() {
        return votes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("date DESC")
    private List<Vote> votes;

    public User() {
    }

    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getRole());
    }

    public User(Integer id, String name, String email, String password,  Role role) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.role=role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setRoles(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", email=" + email +
                ", name=" + name +
                ", role=" + role +
                '}';
    }
}