package com.matchingengine.pattern.strategy;

import com.matchingengine.core.OrderBook;
import com.matchingengine.model.Order;
import com.matchingengine.model.Side;
import com.matchingengine.model.Trade;

import java.util.*;

public class FifoMatchingStrategy implements MatchingStrategy {

    @Override
    public List<Trade> match(Order incomingOrder, OrderBook orderBook) {
        if (incomingOrder.getSide() == Side.BUY) {
            return matchBuy(incomingOrder, orderBook);
        } else {
            return matchSell(incomingOrder, orderBook);
        }
    }

    private List<Trade> matchSell(Order incomingOrder, OrderBook orderBook) {
        NavigableMap<Double, Queue<Order>> bids = orderBook.getBids();
        return matchOrder(incomingOrder, bids, incomingOrder.getSide());
    }

    private List<Trade> matchBuy(Order incomingOrder, OrderBook orderBook) {
        NavigableMap<Double, Queue<Order>> asks = orderBook.getAsks();
        return matchOrder(incomingOrder, asks, incomingOrder.getSide());
    }

    private static List<Trade> matchOrder(Order incomingOrder, NavigableMap<Double, Queue<Order>> entries,  Side side) {
        List<Trade> trades = new ArrayList<>();
        Iterator<Map.Entry<Double, Queue<Order>>> it = entries.entrySet().iterator();

        while (it.hasNext() && incomingOrder.getQuantity() > 0) {
            Map.Entry<Double, Queue<Order>> entry = it.next();
            double priceLevelPrice = entry.getKey();

            if (side == Side.BUY && priceLevelPrice > incomingOrder.getPrice()) break;
            if (side == Side.SELL && priceLevelPrice < incomingOrder.getPrice()) break;

            Queue<Order> orders = entry.getValue();

            while(!orders.isEmpty() && incomingOrder.getQuantity() > 0) {
                Order restingOrder = orders.peek();
                int tradeQuantity = Math.min(incomingOrder.getQuantity(), restingOrder.getQuantity());
                double tradePrice = restingOrder.getPrice();

                String buyerId = (side == Side.BUY) ? incomingOrder.getId() : restingOrder.getId();
                String sellerId = (side == Side.SELL) ? incomingOrder.getId() : restingOrder.getId();
                trades.add(new Trade(buyerId, sellerId, incomingOrder.getSymbol(), tradePrice, tradeQuantity));

                incomingOrder.reduceQuantity(tradeQuantity);
                restingOrder.reduceQuantity(tradeQuantity);

                if (restingOrder.isFilled()) {
                    orders.poll();
                }
            }

            if (orders.isEmpty()) {
                it.remove();
            }
        }

        return trades;
    }
}
