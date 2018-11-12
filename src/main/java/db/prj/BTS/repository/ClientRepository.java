package db.prj.BTS.repository;

import db.prj.BTS.domain.Client;

import db.prj.BTS.domain.Trader;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository  extends CrudRepository<Client, String> ,
        JpaSpecificationExecutor {
   public List<Client> getClientByTrader(Trader trader);
}
