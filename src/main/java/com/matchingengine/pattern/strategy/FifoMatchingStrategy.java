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
        List<Trade> trades = new ArrayList<>();

       NavigableMap<Double, Queue<Order>> asks = orderBook.getAsks();
       Iterator<Map.Entry<Double, Queue<Order>>> it = asks.entrySet().iterator();

        while (it.hasNext() && incomingOrder.getQuantity() > 0) {
            Map.Entry<Double, Queue<Order>> entry = it.next();
            double askPrice = entry.getKey();

            if (askPrice > incomingOrder.getPrice()) {
                break; //Price larger than what it is willing to pay
            }

            Queue<Order> sellOrders = entry.getValue();

            while(!sellOrders.isEmpty() && incomingOrder.getQuantity() > 0) {
                Order sellOrder = sellOrders.peek();
                int tradeQuantity = Math.min(incomingOrder.getQuantity(), sellOrder.getQuantity());
                double tradePrice = sellOrder.getPrice(); // price = resting order’s price

                trades.add(new Trade(incomingOrder.getId(), sellOrder.getId(), incomingOrder.getSymbol(), tradePrice, tradeQuantity));

                incomingOrder.reduceQuantity(tradeQty);
                sellOrder.reduceQuantity(tradeQty);

                if (sellOrder.getQuantity() == 0) {
                    sellOrders.poll();
                }
            }
        }

        return trades;
    }

    private List<Trade> matchBuy(Order incomingOrder, OrderBook orderBook) {

    }
}
