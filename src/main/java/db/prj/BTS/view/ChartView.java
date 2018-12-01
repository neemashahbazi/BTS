package db.prj.BTS.view;

import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;

@Named
public class ChartView implements Serializable {
    private BarChartModel barModel;

    @PostConstruct
    public void init() {
        createBarModels();
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
        boys.set("Daily", 10);
        boys.set("Weekly", 100);
        boys.set("Monthly", 400);
        model.addSeries(boys);

        return model;
    }
}
