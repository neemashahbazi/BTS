package db.prj.BTS.domain;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "trx_transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "trx_type",
        discriminatorType = DiscriminatorType.STRING)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.TIME)
    private Date time;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;


    private Double amount;
    private Double fiat_amount;
    private Double commission_amount;
    private String commission_type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "id")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TRADER_USERNAME")
    private Trader trader;

    @Transient
    private  String trxType;


    public Transaction() {

    }

    public Transaction(Date date, Date time, Date dateTime, Double amount, Double fiat_amount, Double commission_amount, String commission_type, Client client, Trader trader) {
        this.date = date;
        this.time = time;
        this.dateTime = dateTime;
        this.amount = amount;
        this.fiat_amount = fiat_amount;
        this.commission_amount = commission_amount;
        this.commission_type = commission_type;
        this.client = client;
        this.trader = trader;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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


    @Override
    public String toString() {
        return "DateAndTime{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", dateTime=" + dateTime +
                '}';
    }
}
