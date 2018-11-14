package db.prj.BTS.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("BUY")
public class BuyTransaction extends Transaction {

    public BuyTransaction() {
    }


}
