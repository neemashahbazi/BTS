package db.prj.BTS.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("SELL")
public class SellTransaction extends Transaction {


    public SellTransaction() {
        setTrxType("SELL");
    }
}
