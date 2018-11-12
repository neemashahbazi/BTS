package db.prj.BTS.repository;

import db.prj.BTS.domain.Client;
import db.prj.BTS.domain.Trader;
import org.springframework.data.repository.CrudRepository;

public interface TraderRepository extends CrudRepository<Trader, String> {
}
