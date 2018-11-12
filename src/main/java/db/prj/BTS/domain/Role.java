package db.prj.BTS.domain;

import javax.persistence.*;

@Entity
@Table(name = "u_roles")
public class Role {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "username")
    private User username;
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
