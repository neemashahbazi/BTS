package db.prj.BTS.repository;

import db.prj.BTS.domain.AuditTransaction;
import org.springframework.data.repository.CrudRepository;

public interface AuditTransactionRepository extends CrudRepository<AuditTransaction, Integer> {
}
