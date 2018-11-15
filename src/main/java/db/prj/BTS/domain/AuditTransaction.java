package db.prj.BTS.domain;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "trx_audit")
public class AuditTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Temporal(TemporalType.TIMESTAMP)
    private Date deleted_date;


    private Double amount;
    private Double fiat_amount;
    private Double commission_amount;
    private String commission_type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "id")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "deleted_by", referencedColumnName = "username")
    private Trader trader;


    public AuditTransaction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDeleted_date() {
        return deleted_date;
    }

    public void setDeleted_date(Date deleted_date) {
        this.deleted_date = deleted_date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFiat_amount() {
        return fiat_amount;
    }

    public void setFiat_amount(Double fiat_amount) {
        this.fiat_amount = fiat_amount;
    }

    public Double getCommission_amount() {
        return commission_amount;
    }

    public void setCommission_amount(Double commission_amount) {
        this.commission_amount = commission_amount;
    }

    public String getCommission_type() {
        return commission_type;
    }

    public void setCommission_type(String commission_type) {
        this.commission_type = commission_type;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }
}


