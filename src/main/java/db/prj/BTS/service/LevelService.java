package db.prj.BTS.service;

import db.prj.BTS.domain.Level;
import db.prj.BTS.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LevelService {

    @Autowired
    LevelRepository levelRepository;

    public Level getByName(String name){
      List<Level> level = levelRepository.findByName(name);
      if (level.isEmpty())
          return  null;
      else  return level.get(0);

    }

}
