package db.prj.BTS.repository;

import db.prj.BTS.domain.Client;

import db.prj.BTS.domain.Trader;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository  extends CrudRepository<Client, String> ,
        JpaSpecificationExecutor {
   public List<Client> getClientByTrader(Trader trader);
   public List<Client> findByClientId(Integer id);
}
