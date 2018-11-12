package db.prj.BTS.view;

import db.prj.BTS.domain.*;
import db.prj.BTS.service.ClientService;
import db.prj.BTS.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;

@Named
public class TransactionView {

    @Autowired
    ClientService clientService;

    @Autowired
    TransactionService transactionService;


    public BuyTransaction buyBitcoin(Integer amount, String commission_type, Integer client_id){
        Client client= clientService.getClientByID(client_id).get(0);
       return transactionService.buyBitcoin(amount,commission_type,client);
    }

    public SellTransaction sellBitcoin(Integer amount, String commission_type, Integer client_id){
        Client client= clientService.getClientByID(client_id).get(0);
        return transactionService.sellBitcoin(amount,commission_type,client);
    }

    public PaymentTransaction pay(Integer amount, Integer client_id){
        Client client= clientService.getClientByID(client_id).get(0);
        return transactionService.pay(amount,client,client.getTrader());
    }
}
