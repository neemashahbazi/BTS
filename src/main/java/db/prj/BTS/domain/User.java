package db.prj.BTS.domain;

import javax.persistence.*;


@Entity
@Table(name = "u_users")
public class User {

    @Id
    private String username;

    private String password;

    private  boolean enabled;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}