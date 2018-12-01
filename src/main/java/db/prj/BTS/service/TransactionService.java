package db.prj.BTS.service;

import db.prj.BTS.domain.*;
import db.prj.BTS.exception.InsufficientBAlanceException;
import db.prj.BTS.repository.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionService {


    public static final String FROM_BALANACE = "fromBalance";
    public static final String FROM_FIAT_CURRENCY = "fromCurrency";

    @Autowired
    private BuyTransactionRepository buyTransactionRepository;

    @Autowired
    private SellTransactionRepository sellTransactionRepository;

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    public BuyTransaction buyBitcoin(Double amount, String commission_type, Client client) throws InsufficientBAlanceException {

//     System.out.println("TRX count is "+ getMonthlyTransactionAmount());

        Date now = new Date();

        double rate = getUpdatedBitCoinPrice();
        double fiat_amount = rate * amount;
        double commission_amount = 0;
        if (FROM_BALANACE.equals(commission_type)) {
                commission_amount = amount * client.getLevel().getPercentage();

            if (client.getFiat_currency() < fiat_amount)
                throw new InsufficientBAlanceException("Fiat Currency is insufficient");

            if (client.getBitcoin_bal() < commission_amount)
                throw new InsufficientBAlanceException("Balance is insufficient");

            client.setFiat_currency(client.getFiat_currency() - fiat_amount);
            client.setBitcoin_bal((client.getBitcoin_bal() - commission_amount + amount));
        } else if (FROM_FIAT_CURRENCY.equals(commission_type)) {
                commission_amount = fiat_amount * client.getLevel().getPercentage();

            if (client.getFiat_currency() < fiat_amount + commission_amount)
                throw new InsufficientBAlanceException("Fiat Currency is insufficient");

            client.setFiat_currency(client.getFiat_currency() - fiat_amount - commission_amount);
            client.setBitcoin_bal((client.getBitcoin_bal() + amount));
        }


        BuyTransaction buyTransaction = new BuyTransaction();

        buyTransaction.setDate(now);
        buyTransaction.setTime(now);
        buyTransaction.setDateTime(now);
        buyTransaction.setAmount(amount);
        buyTransaction.setCommission_type(commission_type);
        buyTransaction.setCommission_amount(commission_amount);
        buyTransaction.setClient(client);
        buyTransaction.setFiat_amount(fiat_amount);
        buyTransaction.setTrader(client.getTrader());
        BuyTransaction result = buyTransactionRepository.save(buyTransaction);
        if (result != null)

            clientRepository.save(client);


        return result;


    }

    public SellTransaction sellBitcoin(Double amount, String commission_type, Client client) {

        Date now = new Date();

        double rate = getUpdatedBitCoinPrice();
        double fiat_amount = rate * amount;
        double commission_amount=0 ;
        if (FROM_BALANACE.equals(commission_type)) {
                commission_amount = amount * client.getLevel().getPercentage();

            if (client.getBitcoin_bal() < commission_amount + amount)
                throw new InsufficientBAlanceException("Balance is insufficient");

            client.setFiat_currency(client.getFiat_currency() + fiat_amount);
            client.setBitcoin_bal((client.getBitcoin_bal() - commission_amount - amount));
        } else if (FROM_FIAT_CURRENCY.equals(commission_type)) {
                commission_amount = fiat_amount * client.getLevel().getPercentage();
            if (client.getFiat_currency() < commission_amount)
                throw new InsufficientBAlanceException("Fiat Currency is insufficient");
            if (client.getBitcoin_bal() < amount)
                throw new InsufficientBAlanceException("Balance is insufficient");

            client.setFiat_currency(client.getFiat_currency() + fiat_amount - commission_amount);
            client.setBitcoin_bal(client.getBitcoin_bal() - amount);
        }

        SellTransaction sellTransaction = new SellTransaction();

        sellTransaction.setDate(now);
        sellTransaction.setTime(now);
        sellTransaction.setDateTime(now);
        sellTransaction.setAmount(amount);
        sellTransaction.setCommission_type(commission_type);
        sellTransaction.setCommission_amount(commission_amount);
        sellTransaction.setClient(client);

        SellTransaction result = sellTransactionRepository.save(sellTransaction);
        ;
        if (result != null)

            clientRepository.save(client);


        return result;


    }

    public PaymentTransaction pay(Double amount, Client client, Trader trader) {

        Date now = new Date();


        PaymentTransaction paymentTransaction = new PaymentTransaction();

        paymentTransaction.setDate(now);
        paymentTransaction.setTime(now);
        paymentTransaction.setDateTime(now);
        paymentTransaction.setAmount(amount);
        paymentTransaction.setClient(client);
        paymentTransaction.setTrader(trader);

        PaymentTransaction result = paymentTransactionRepository.save(paymentTransaction);
        if (result != null)
            client.setFiat_currency(client.getFiat_currency() + amount);
            clientRepository.save(client);

        return result;

    }

    public void cancel(Integer trx_id) {
       Transaction trx= transactionRepository.findById(trx_id).get();
       if(trx.getCommission_type() == FROM_FIAT_CURRENCY){
           if(trx.getTrxType() == "BUY") {
               trx.getClient().setFiat_currency(trx.getClient().getFiat_currency() + trx.getFiat_amount() + trx.getCommission_amount());
               trx.getClient().setBitcoin_bal( trx.getClient().getBitcoin_bal() - trx.getAmount());

           } else if(trx.getTrxType() == "SELL") {
               trx.getClient().setFiat_currency(trx.getClient().getFiat_currency() - trx.getFiat_amount() + trx.getCommission_amount());
               trx.getClient().setBitcoin_bal( trx.getClient().getBitcoin_bal() + trx.getAmount());
           }
              else
                  trx.getClient().setFiat_currency( trx.getClient().getFiat_currency() - trx.getAmount());

       } else{
           if(trx.getTrxType() == "BUY") {
               trx.getClient().setFiat_currency(trx.getClient().getFiat_currency() + trx.getFiat_amount() );
               trx.getClient().setBitcoin_bal( trx.getClient().getBitcoin_bal() - trx.getAmount() + trx.getCommission_amount());

           } else if(trx.getTrxType() == "SELL") {
               trx.getClient().setFiat_currency(trx.getClient().getFiat_currency() - trx.getFiat_amount() );
               trx.getClient().setBitcoin_bal( trx.getClient().getBitcoin_bal() + trx.getAmount() + trx.getCommission_amount());
           }
           else
               trx.getClient().setFiat_currency( trx.getClient().getFiat_currency() - trx.getAmount());
        }

        transactionRepository.deleteById(trx_id);

    }

    public List<Integer> getReport(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(20);
        list.add(30);
        return  list;
    }


    public int getDailyTransactionAmount(){
//        Date now =new Date();

       return   transactionRepository.getDailyTransactionAmount(DateUtils.truncate(new Date(), Calendar.DATE));
    }


    public int getMonthlyTransactionAmount(){
        Date now =new Date();


        Calendar gc = new GregorianCalendar();
        gc.set(Calendar.MONTH, now.getMonth());
        gc.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = gc.getTime();
        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        Date monthEnd = gc.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        System.out.println("Calculated month start date : " + format.format(monthStart));
        System.out.println("Calculated month end date : " + format.format(monthEnd));

        return   transactionRepository.getMonthlyTransactionAmount(monthStart,monthEnd);
    }

    public float getUpdatedBitCoinPrice() {


        float rate = 5649.82f;

        return rate;


    }
}
