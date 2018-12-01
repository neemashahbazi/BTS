package db.prj.BTS.service;

import db.prj.BTS.domain.Level;
import db.prj.BTS.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelService {

    @Autowired
    LevelRepository levelRepository;

    public Level getByName(String name){
        return levelRepository.findByName(name).get(0);
    }

}
