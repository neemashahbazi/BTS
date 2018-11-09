package db.prj.BTS.view;


import db.prj.BTS.domain.User;
import db.prj.BTS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named
public class UserView implements Serializable {

    @Autowired
    UserService userService;

    private List<User> users;


    public List<User> getUsers() {
        return userService.getAllUser();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
