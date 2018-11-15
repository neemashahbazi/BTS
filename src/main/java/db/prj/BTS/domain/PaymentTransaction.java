package db.prj.BTS.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@DiscriminatorValue("PAYMENT")
public class PaymentTransaction extends Transaction {

    public PaymentTransaction() {
setTrxType("Pay");
    }
}
