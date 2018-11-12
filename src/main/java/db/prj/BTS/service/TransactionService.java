package db.prj.BTS.service;

import db.prj.BTS.domain.*;
import db.prj.BTS.repository.BuyTransactionRepository;
import db.prj.BTS.repository.PaymentTransactionRepository;
import db.prj.BTS.repository.SellTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionService {
    @Autowired
    private BuyTransactionRepository buyTransactionRepository;

    @Autowired
    private SellTransactionRepository sellTransactionRepository;

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    public  BuyTransaction  buyBitcoin (Integer amount, String commission_type,Client client){

        Date now = new Date();


        BuyTransaction buyTransaction =new BuyTransaction();

        buyTransaction.setDate(now);
        buyTransaction.setTime(now);
        buyTransaction.setDateTime(now);
        buyTransaction.setAmount(amount);
        buyTransaction.setCommission_type(commission_type);
        buyTransaction.setCommission_amount(0);
        buyTransaction.setClient(client);


       return buyTransactionRepository.save(buyTransaction);


    }

    public SellTransaction sellBitcoin (Integer amount, String commission_type, Client client){

        Date now = new Date();


        SellTransaction sellTransaction =new SellTransaction();

        sellTransaction.setDate(now);
        sellTransaction.setTime(now);
        sellTransaction.setDateTime(now);
        sellTransaction.setAmount(amount);
        sellTransaction.setCommission_type(commission_type);
        sellTransaction.setCommission_amount(0);
        sellTransaction.setClient(client);


        return sellTransactionRepository.save(sellTransaction);


    }

    public PaymentTransaction pay (Integer amount, Client client, Trader trader){

        Date now = new Date();


        PaymentTransaction paymentTransaction =new PaymentTransaction();

        paymentTransaction.setDate(now);
        paymentTransaction.setTime(now);
        paymentTransaction.setDateTime(now);
        paymentTransaction.setAmount(amount);
        paymentTransaction.setClient(client);
        paymentTransaction.setTrader(trader);



        return paymentTransactionRepository.save(paymentTransaction);


    }
}
