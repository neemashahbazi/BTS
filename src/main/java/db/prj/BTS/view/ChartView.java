package db.prj.BTS.view;

import db.prj.BTS.service.TransactionService;
import org.primefaces.model.chart.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
public class ChartView implements Serializable {
    private BarChartModel barModel;
    @Autowired
    TransactionService transactionService;
    private Date date;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    private String day;
    private String month;
    private String year;

    public void searchDate() throws ParseException {
        date= new SimpleDateFormat( "yyyyMMdd" ).parse( year+month+day );
        setDate((date));
        System.out.println("Date Selected Is ::"+date);
        createBarModels();
    }

    @PostConstruct
    public void init(){
        date=new Date();
        createBarModels();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    private void createBarModels() {
        createBarModel();
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setTitle("Bar Chart");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Date");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total Transactions");
        yAxis.setMin(0);
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("Transactions");
        boys.set("Daily", transactionService.getReport(date).get(0));
        boys.set("Weekly", transactionService.getReport(date).get(1));
        boys.set("Monthly", transactionService.getReport(date).get(2));
        model.addSeries(boys);

        System.out.println("Date Selected Is ::"+date);
        System.out.println("Daily ::"+transactionService.getReport(date).get(0));
        System.out.println("Weekly ::"+transactionService.getReport(date).get(1));
        System.out.println("Monthly ::"+transactionService.getReport(date).get(2));

        return model;
    }
}
