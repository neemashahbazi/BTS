package db.prj.BTS.view;

import db.prj.BTS.domain.*;
import db.prj.BTS.exception.InsufficientBAlanceException;
import db.prj.BTS.service.ClientService;
import db.prj.BTS.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;

@Named
public class TransactionView {

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

    @Autowired
    ClientService clientService;

    @Autowired
    TransactionService transactionService;

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


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String buyBitcoin() {
        try {

            Client client = clientService.getClientByID(clientId).get(0);
            transactionService.buyBitcoin(amount, commissionType, client);
            return "transaction_success.xhtml?faces-redirect=true";
        }catch (InsufficientBAlanceException exp){
            return null;
        }
    }

    public String sellBitcoin() {
        Client client = clientService.getClientByID(clientId).get(0);
        transactionService.sellBitcoin(amount, commissionType, client);
        return "transaction_success.xhtml?faces-redirect=true";
    }

    public String pay() {
        Client client = clientService.getClientByID(clientId).get(0);
        transactionService.pay(amount, client, client.getTrader());
        return "transaction_success.xhtml?faces-redirect=true";

    }

    public String cancel(Integer transactionId) {
        transactionService.cancel(transactionId);
        return "transaction_cancel.xhtml?faces-redirect=true";

    }

    public List<Transaction> getAllTransactionByClientId() {
        Client client = clientService.getClientByID(clientId).get(0);
        return client.getTransactionList();

    }

    public List<Transaction> getAllTransactionByClientId(Integer clientId) {
        Client client = clientService.getClientByID(clientId).get(0);
        return client.getTransactionList();

    }
}
