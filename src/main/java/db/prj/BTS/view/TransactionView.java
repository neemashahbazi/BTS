package db.prj.BTS.view;

import db.prj.BTS.domain.*;
import db.prj.BTS.exception.InsufficientBAlanceException;
import db.prj.BTS.service.ClientService;
import db.prj.BTS.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
public class TransactionView {
    @Autowired
    ClientService clientService;
    @Autowired
    TransactionService transactionService;
    BuyTransaction buyTransaction;
    SellTransaction sellTransaction;
    PaymentTransaction paymentTransaction;
    private String commissionType;
    private Double amount;
    private Integer clientId;
    private Integer transactionId;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public BuyTransaction getBuyTransaction() {
        return buyTransaction;
    }

    public void setBuyTransaction(BuyTransaction buyTransaction) {
        this.buyTransaction = buyTransaction;
    }

    public SellTransaction getSellTransaction() {
        return sellTransaction;
    }

    public void setSellTransaction(SellTransaction sellTransaction) {
        this.sellTransaction = sellTransaction;
    }

    public PaymentTransaction getPaymentTransaction() {
        return paymentTransaction;
    }

    public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String buyBitcoin() {
        try {
            Client client = clientService.getClientByID(clientId).get(0);
            buyTransaction = transactionService.buyBitcoin(amount, commissionType, client);
            return "transaction_success_buy.xhtml?faces-redirect=true";
        } catch (InsufficientBAlanceException exp) {
            return "insufficient_amount.xhtml?faces-redirect=true";

        }
    }

    public String sellBitcoin() {
        try {
            Client client = clientService.getClientByID(clientId).get(0);
            sellTransaction = transactionService.sellBitcoin(amount, commissionType, client);
            return "transaction_success_sell.xhtml?faces-redirect=true";
        } catch (InsufficientBAlanceException exp) {
            return "insufficient_amount.xhtml?faces-redirect=true";

        }
    }

    public String pay() {
        Client client = clientService.getClientByID(clientId).get(0);
        paymentTransaction = transactionService.pay(amount, client, client.getTrader());
        return "transaction_success_pay.xhtml?faces-redirect=true";

    }

    public String cancel(Integer transactionId) {
        try {
            transactionService.cancel(transactionId);
            return "home.xhtml?faces-redirect=true";
        } catch (Exception e){
            return "home.xhtml?faces-redirect=true";

        }

    }

    public List<Transaction> getAllTransactionByClientId() {
        Client client = clientService.getClientByID(clientId).get(0);
        return client.getTransactionList();

    }

    public List<Transaction> getAllTransactionByClientId(Integer clientId) {
        Client client = clientService.getClientByID(clientId).get(0);
        return client.getTransactionList();

    }

    public List<AuditTransaction> getAllAuditTransactionByClientId() {
        Client client = clientService.getClientByID(clientId).get(0);
        return client.getAuditTransactionList();

    }
}
