package db.prj.BTS.repository;

import db.prj.BTS.domain.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {


    @Query(value = "Select count(id) from trx_transactions where  date= ?1", nativeQuery = true)
    public int  getDailyTransaction(Date date);


    @Query(value = "Select count(id) from trx_transactions where  date between  ?1 and ?2", nativeQuery = true)
    public int getTransactionBetweenRange(Date begin, Date end);
}
