package db.prj.BTS.service;

import db.prj.BTS.domain.User;
import db.prj.BTS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser(){
       return (List<User>) userRepository.findAll();
    }
}
