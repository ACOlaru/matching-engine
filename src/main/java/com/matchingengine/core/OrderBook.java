package com.matchingengine.core;

import com.matchingengine.model.Order;
import com.matchingengine.model.Side;

import java.util.*;

public class OrderBook {
    // BUY side: highest price first
    private final NavigableMap<Double, Queue<Order>> bids = new TreeMap<>(Comparator.reverseOrder());

    // SELL side: lowest price first
    private final NavigableMap<Double, Queue<Order>> asks = new TreeMap<>();

    public void add(Order order) {
        NavigableMap<Double, Queue<Order>> bookSide =
                (order.getSide() == Side.BUY) ? bids : asks;

        Queue<Order> ordersAtPrice = bookSide.computeIfAbsent(order.getPrice(), p -> new LinkedList<>());
        ordersAtPrice.add(order);
    }

    public Optional<Double> getBestBid() {
        return bids.isEmpty() ? Optional.empty() : Optional.of(bids.firstKey());
    }

    public Optional<Double> getBestAsk() {
        return asks.isEmpty() ? Optional.empty() : Optional.of(asks.firstKey());
    }

    public NavigableMap<Double, Queue<Order>> getAsks() {
        return asks;
    }

    public NavigableMap<Double, Queue<Order>> getBids() {
        return bids;
    }

    @Override
    public String toString() {
        return "OrderBook{" +
                " bestBid=" + getBestBid().orElse(null) +
                ", bestAsk=" + getBestAsk().orElse(null) +
                '}';
    }
}
