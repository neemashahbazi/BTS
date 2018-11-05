package db.prj.BTS.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.prj.BTS.BtsApplication;
import db.prj.BTS.domain.*;
import db.prj.BTS.exception.ConncetionFailureExecption;
import db.prj.BTS.exception.InsufficientBAlanceException;
import db.prj.BTS.repository.*;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

/*
This Class handle all business related to transactions.
* */
@Service
public class TransactionService {

    private static final Logger logger = LogManager.getLogger(TransactionService.class);
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

    /*Buy bitcoin: calculate fee and update client valance and fiat currency accordingly
     * if amount is not sufficient or server connection is not possible throws appropriate exception to handle in view level*/
    @Transactional
    public BuyTransaction buyBitcoin(Double amount, String commission_type, Client client) throws InsufficientBAlanceException, ConncetionFailureExecption {

        logger.info("Start Buying Bitcoin for client with username: {} .", client.getUsername());

        Date now = new Date();

        double rate = getUpdatedBitCoinRate();
        double fiat_amount = rate * amount;
        double commission_amount = 0;
        if (FROM_BALANACE.equals(commission_type)) {
            commission_amount = amount * client.getLevel().getPercentage();

            if (client.getFiat_currency() < fiat_amount) {
                logger.warn("Client {} 's fiat currency is not sufficient .", client.getUsername());
                throw new InsufficientBAlanceException("Fiat Currency is insufficient");
            }

            if (client.getBitcoin_bal() < commission_amount) {
                logger.warn("Client {} 's balance is not sufficient .", client.getUsername());
                throw new InsufficientBAlanceException("Balance is insufficient");
            }

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

        logger.info(" Buying Bitcoin for client with username: {}  is successful with trx_id.", client.getUsername(),result.getId());
        return result;


    }

    /*Sell bitcoin: calculate fee and update client valance and fiat currency accordingly
     * if amount is not sufficient or server connection is not possible throws appropriate exception to handle in view level*/
    @Transactional
    public SellTransaction sellBitcoin(Double amount, String commission_type, Client client) throws InsufficientBAlanceException, ConncetionFailureExecption {

        logger.info("Start Selling Bitcoin for client with username: {} .", client.getUsername());
        Date now = new Date();

        double rate = getUpdatedBitCoinRate();
        double fiat_amount = rate * amount;
        double commission_amount = 0;
        if (FROM_BALANACE.equals(commission_type)) {
            commission_amount = amount * client.getLevel().getPercentage();

            if (client.getBitcoin_bal() < commission_amount + amount) {
                logger.warn("Client {} 's balance is not sufficient .", client.getUsername());
                throw new InsufficientBAlanceException("Balance is insufficient");
            }

            client.setFiat_currency(client.getFiat_currency() + fiat_amount);
            client.setBitcoin_bal((client.getBitcoin_bal() - commission_amount - amount));
        } else if (FROM_FIAT_CURRENCY.equals(commission_type)) {
            commission_amount = fiat_amount * client.getLevel().getPercentage();
            if (client.getFiat_currency() < commission_amount) {
                logger.warn("Client {} 's fiat currency is not sufficient. ", client.getUsername());
                throw new InsufficientBAlanceException("Fiat Currency is insufficient");
            }
            if (client.getBitcoin_bal() < amount) {
                logger.warn("Client {} 's balance is not sufficient .", client.getUsername());
                throw new InsufficientBAlanceException("Balance is insufficient");
            }

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
        sellTransaction.setFiat_amount(fiat_amount);
        sellTransaction.setTrader(client.getTrader());

        SellTransaction result = sellTransactionRepository.save(sellTransaction);
        ;
        if (result != null)

            clientRepository.save(client);
        logger.info(" Selling Bitcoin for client with username: {}  is successful with trx_id {}.", client.getUsername(),result.getId());


        return result;


    }

    /*
     * Add fiat currency to  client's amount if successful.*/
    @Transactional
    public PaymentTransaction pay(Double amount, Client client, Trader trader) {

        logger.info("Start Performing payment for client with username: {} .", client.getUsername());
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

        logger.info(" Performing payment for client with username: {}  is successful with trx_id.{}", client.getUsername(),result.getId());

        return result;

    }


    /*Delete transaction from transaction table and update client's fiat currency and balance accordingly*/
    @Transactional
    public String cancel(Integer trx_id) {
        Transaction trx = transactionRepository.findById(trx_id).get();
        logger.info("Start Deleting transaction with trx_id: {} bt trader with username {} .", trx.getId(), trx.getTrader().getUsername());
        if (trx.getCommission_type() == FROM_FIAT_CURRENCY) {
            if (trx.getTrxType() == "BUY") {
                trx.getClient().setFiat_currency(trx.getClient().getFiat_currency() + trx.getFiat_amount() + trx.getCommission_amount());
                trx.getClient().setBitcoin_bal(trx.getClient().getBitcoin_bal() - trx.getAmount());

            } else if (trx.getTrxType() == "SELL") {
                trx.getClient().setFiat_currency(trx.getClient().getFiat_currency() - trx.getFiat_amount() + trx.getCommission_amount());
                trx.getClient().setBitcoin_bal(trx.getClient().getBitcoin_bal() + trx.getAmount());
            } else
                trx.getClient().setFiat_currency(trx.getClient().getFiat_currency() - trx.getAmount());

        } else {
            if (trx.getTrxType() == "BUY") {
                trx.getClient().setFiat_currency(trx.getClient().getFiat_currency() + trx.getFiat_amount());
                trx.getClient().setBitcoin_bal(trx.getClient().getBitcoin_bal() - trx.getAmount() + trx.getCommission_amount());

            } else if (trx.getTrxType() == "SELL") {
                trx.getClient().setFiat_currency(trx.getClient().getFiat_currency() - trx.getFiat_amount());
                trx.getClient().setBitcoin_bal(trx.getClient().getBitcoin_bal() + trx.getAmount() + trx.getCommission_amount());
            } else
                trx.getClient().setFiat_currency(trx.getClient().getFiat_currency() - trx.getAmount());
        }

        transactionRepository.deleteById(trx_id);
        clientRepository.save(trx.getClient());

        logger.info(" Deleting transaction with trx_id {}  is successful with trx_id {}.", trx_id);
        return "home.xhtml?faces-redirect=true";


    }

    /*provide statistical report aa bout transaction amount based on the selected date.
     * output is 3 elements array where a[0], a[1] and a[2] ar daily, weekly and monthly total transaction amount respectively.*/
    public List<Integer> getReport(Date date) {
        logger.info(" Start Generating report for date : {}.", date);
        List<Integer> list = new ArrayList<Integer>();
        list.add(getDailyTransactionAmount(date));
        list.add(getWeeklyTransactionAmount(date));
        list.add(getMonthlyTransactionAmount(date));
        logger.info(" Finish Generating report for date : {}.", date);
        return list;
    }

    /*return total amount of transactions on specific date*/
    private int getDailyTransactionAmount(Date date) {

        return transactionRepository.getDailyTransaction(DateUtils.truncate(date, Calendar.DATE));
    }

    /*return total amount of transactions on week of the specific  date*/
    private int getMonthlyTransactionAmount(Date date) {

        Calendar gc = new GregorianCalendar();
        gc.set(Calendar.MONTH, date.getMonth());
        gc.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = gc.getTime();
        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        Date monthEnd = gc.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        return transactionRepository.getTransactionBetweenRange(DateUtils.truncate(monthStart, Calendar.DATE), DateUtils.truncate(monthEnd, Calendar.DATE));
    }

    /*return total amount of transactions on month of the specific  date*/
    private int getWeeklyTransactionAmount(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        Date weekStart = c.getTime();
        for (int i = 0; i < 6; i++) {
            c.add(Calendar.DATE, 1);
        }
        Date weekEnd = c.getTime();
        weekEnd.setHours(23);
        weekEnd.setMinutes(59);
        weekEnd.setSeconds(59);

        return transactionRepository.getTransactionBetweenRange(DateUtils.truncate(weekStart, Calendar.DATE), weekEnd);
    }

    /*return bitcoin rate . By calling bitcoindesk webservice
     * if connection is failed, throws ConncetionFailureExecption  */
    public double getUpdatedBitCoinRate() throws ConncetionFailureExecption {

        String rateString;
        double rate = 0;
        try {
            String url = "https://api.coindesk.com/v1/bpi/currentprice/USD.json";
            CloseableHttpClient httpClient
                    = HttpClients.custom()
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .build();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            ResponseEntity<String> response
                    = new RestTemplate(requestFactory).exchange(
                    url, HttpMethod.GET, null, String.class);
            response.getStatusCode().value();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.getBody());
            JsonNode rateNode = node.at("/bpi/USD/rate_float");
            rateString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rateNode);
            rate = Double.parseDouble(rateString);
        } catch (Exception e) {
            logger.error("Could not connect to Bitcoin desk");
            throw new ConncetionFailureExecption("Could not connect to BitCoinDesk to get rate");
        }
        return rate;


    }
}
