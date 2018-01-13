package topjava.graduation.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import org.hibernate.validator.constraints.SafeHtml;
import topjava.graduation.model.core.AbstractNamedEntity;
import topjava.graduation.model.core.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "users_unique_email_idx")})
public class User extends AbstractNamedEntity {

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @SafeHtml
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @NotNull
    private Role role;

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

    public void setRole(Role role) {
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