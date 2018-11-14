package db.prj.BTS.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("BUY")
public class BuyTransaction extends Transaction {

    public BuyTransaction() {
    }

    public BuyTransaction(Date date, Date time, Date dateTime, Integer amount, Integer commission_amount, String commission_type, Client client) {
        super(date, time, dateTime, amount, commission_amount, commission_type, client);
    }
}
