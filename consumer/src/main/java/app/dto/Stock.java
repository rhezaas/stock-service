package app.dto;

import java.util.Date;

public class Stock {
    private Date date;
    private String symbol;
    private int prefix;
    private int price;

    public Stock(Date date, String symbol, int prefix, int price) {
        this.date = date;
        this.symbol = symbol;
        this.prefix = prefix;
        this.price = price;
    }

    public Stock setDate(Date date) {
        this.date = date;

        return this;
    }

    public Stock setSymbol(String symbol) {
        this.symbol = symbol;

        return this;
    }

    public Stock setPrefix(int prefix) {
        this.prefix = prefix;

        return this;
    }

    public Stock setPrice(int price) {
        this.price = price;

        return this;
    }

    public Date getDate() {
        return this.date;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getPrefix() {
        return this.prefix;
    }

    public int getPrice() {
        return this.price;
    }
}
