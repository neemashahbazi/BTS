package db.prj.BTS.domain;

import javax.persistence.*;


@Entity
@Table(name = "u_user")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private  int role;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role.getValue();
    }
}