package db.prj.BTS.domain;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;


@Entity(name="trx_transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="trx_type",
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


    private Integer amount;
    private Integer commission_amount;
    private String commission_type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "id")
    private Client client;

    public Transaction() {

    }

    public Transaction(Date date, Date time, Date dateTime, Integer amount, Integer commission_amount, String commission_type, Client client) {
        this.date = date;
        this.time = time;
        this.dateTime = dateTime;
        this.amount = amount;
        this.commission_amount = commission_amount;
        this.commission_type = commission_type;
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCommission_amount() {
        return commission_amount;
    }

    public void setCommission_amount(Integer commission_amount) {
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
