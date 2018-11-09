package db.prj.BTS.repository;

import db.prj.BTS.domain.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {

}