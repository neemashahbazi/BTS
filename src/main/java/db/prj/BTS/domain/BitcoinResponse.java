package db.prj.BTS.domain;

public class BitcoinResponse {

    private String code;
    private String symbol;
    private float rate;
    private float rate_float;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Float getRate_float() {
        return rate_float;
    }

    public void setRate_float(Float rate_float) {
        this.rate_float = rate_float;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
