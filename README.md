# Matching engine

A lightweight Java implementation of a limit order book and trade matching engine.  
The system processes limit orders, maintains bid/ask books, and executes trades using price-time (FIFO) priority.  
It is designed to demonstrate the core principles behind exchange-level matching, data structure design, and modular software architecture.

---

## Overview

The engine maintains an in-memory order book and matches incoming orders based on configurable strategies.  
It separates core concerns: order management, trade generation, and strategy execution.  

---

## Features

- Matching based on price-time (FIFO) priority
- Independent bid/ask books with efficient lookups
- Trade generation and order book state updates
- Strategy interface for pluggable matching logic
- CSV-based order simulation

---

## Design

- **Strategy Pattern** — encapsulates matching algorithms and allows extension (e.g. Pro-Rata, Price-Time).
- **Builder Pattern** — clean and immutable `Order` creation.
- **TreeMap / Queue** — ordered price levels with FIFO queues per price.
- **Separation of Concerns** — I/O, model, and matching logic are fully decoupled.

---

## Input Format

The format of the input can be seen in `src/main/resources/orders.csv`

---
## Technical Notes

- Language: Java

- Build: Maven

- Complexity: O(log n) price-level access via TreeMap

- Execution Order: FIFO via LinkedList queues

- Extensibility: strategy-driven and stateless core design

---

## Future Work

- Market and cancel order support
- Additional matching algorithms
- Persistent order state and logging
- Thread-safe matching for concurrent order flow
