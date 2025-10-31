package com.matchingengine.model;

import java.time.Instant;
import java.util.UUID;

public class Order {
    private final String id;
    private final String symbol;
    private final Side side;
    private final double price;
    private int quantity;
    private final Instant timestamp;


    private Order(Builder builder) {
        this.id = builder.id;
        this.symbol = builder.symbol;
        this.side = builder.side;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.timestamp = builder.timestamp;
    }


    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public Side getSide() {
        return side;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void reduceQuantity(int tradeQuantity) {
        if (tradeQuantity > 0 && tradeQuantity <= quantity) {
            quantity -= tradeQuantity;
        }
    }

    public boolean isFilled() {
        return quantity == 0;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", symbol='" + symbol + '\'' +
                ", side=" + side +
                ", price=" + price +
                ", quantity=" + quantity +
                ", timestamp=" + timestamp +
                '}';
    }

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String symbol;
        private Side side;
        private double price;
        private int quantity;
        private Instant timestamp = Instant.now();

        public Builder symbol(String symbol) { this.symbol = symbol; return this; }
        public Builder side(Side side) { this.side = side; return this; }
        public Builder price(double price) { this.price = price; return this; }
        public Builder quantity(int quantity) { this.quantity = quantity; return this; }
        public Builder timestamp(Instant timestamp) { this.timestamp = timestamp; return this; }

        public Order build() {
            return new Order(this);
        }
    }
}
