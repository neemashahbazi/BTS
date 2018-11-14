package db.prj.BTS.repository;

import db.prj.BTS.domain.PaymentTransaction;
import org.springframework.data.repository.CrudRepository;

public interface PaymentTransactionRepository extends CrudRepository<PaymentTransaction, Integer> {
}
