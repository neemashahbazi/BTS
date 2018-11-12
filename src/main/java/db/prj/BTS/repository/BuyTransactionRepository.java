package db.prj.BTS.repository;

import db.prj.BTS.domain.BuyTransaction;
import org.springframework.data.repository.CrudRepository;

public interface BuyTransactionRepository extends CrudRepository<BuyTransaction, Integer> {
}
