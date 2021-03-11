package app.dto;

import java.time.LocalTime;

public class Output {
    private LocalTime time;
    private String symbol;
    private int highPrice;
    private int lowPrice;

    public Output(LocalTime time, String symbol, int highPrice, int lowPrice) {
        this.time = time;
        this.symbol = symbol;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
    }

    public Output setTime(String time) {
        this.time = LocalTime.parse(time);

        return this;
    }

    public Output setTime(LocalTime time) {
        this.time = time;

        return this;
    }

    public Output setSymbol(String symbol) {
        this.symbol = symbol;

        return this;
    }

    public Output setHighPrice(int price) {
        this.highPrice = price;

        return this;
    }

    public Output setLowPrice(int price) {
        this.lowPrice = price;

        return this;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getHighPrice() {
        return this.highPrice;
    }

    public int getLowPrice() {
        return this.lowPrice;
    }
}
