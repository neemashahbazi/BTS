package db.prj.BTS.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@DiscriminatorValue("PAYMENT")
public class PaymentTransaction extends Transaction {

    @ManyToOne(optional = false)
    @JoinColumn(name = "TRADER_USERNAME")
    private Trader trader;

    public PaymentTransaction(Date date, Date time, Date dateTime, Integer amount, Integer commission_amount, String commission_type, Client client, Trader trader) {
        super(date, time, dateTime, amount, commission_amount, commission_type, client);
        this.trader = trader;
    }

    public PaymentTransaction() {

    }

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }
}
