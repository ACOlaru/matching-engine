package com.matchingengine.core;

import com.matchingengine.model.Order;
import com.matchingengine.model.Trade;

import java.util.List;
import java.util.concurrent.*;

public class OrderDispatcher {
    private final BlockingQueue<Order> queue = new LinkedBlockingQueue<>();
    private final MatchingEngine engine;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean running = true;

    public OrderDispatcher(MatchingEngine engine) {
        this.engine = engine;
    }

    public void start() {
        executor.submit(() -> {
            while (running) {
                try {
                    Order order = queue.take(); // wait for an order
                    List<Trade> trades = engine.processOrder(order);

                    System.out.println("Processing order: " + order);

                    if (!trades.isEmpty()) {
                        System.out.println("Trades executed:");
                        trades.forEach(System.out::println);
                    } else {
                        System.out.println("No trades found");
                    }

                    System.out.println(engine.getOrderBook());
                    System.out.println("-----------------------------------------");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
    public void submitOrder(Order order) {
        queue.offer(order);
    }

    public void shutdown() {
        running = false;
        executor.shutdownNow();
    }

    public boolean isIdle() {
        return queue.isEmpty();
    }
}
