package db.prj.BTS.repository;

import db.prj.BTS.domain.Level;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LevelRepository extends CrudRepository<Level, Integer> {
    public List<Level> findByName(String name);
}
