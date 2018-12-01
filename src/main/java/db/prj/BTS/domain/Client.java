package db.prj.BTS.domain;

import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "u_clients")
public class Client extends User {


    @Column(name = "id")
    private Integer clientId;
    private String firstname;
    private String lastname;
    private String phone_num;
    private String cellphone_num;
    private String email;
    private String street;
    private String state;
    private String city;
    private String zipcode;
    private Double fiat_currency;
    private Double bitcoin_bal;


    @ManyToOne(optional = false)
    @JoinColumn(name = "level")
    private Level level;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TRADER_USERNAME")
    private Trader trader;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Transaction> transactionList;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<AuditTransaction> auditTransactionList;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getCellphone_num() {
        return cellphone_num;
    }

    public void setCellphone_num(String cellphone_num) {
        this.cellphone_num = cellphone_num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Double getFiat_currency() {
        return fiat_currency;
    }

    public void setFiat_currency(Double fiat_currency) {
        this.fiat_currency = fiat_currency;
    }

    public Double getBitcoin_bal() {
        return bitcoin_bal;
    }

    public void setBitcoin_bal(Double bitcoin_bal) {
        this.bitcoin_bal = bitcoin_bal;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public List<AuditTransaction> getAuditTransactionList() {
        return auditTransactionList;
    }

    public void setAuditTransactionList(List<AuditTransaction> auditTransactionList) {
        this.auditTransactionList = auditTransactionList;
    }

    public String getLevelName() {
        return  this.level.getName();
    }



    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
