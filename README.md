# Matching Engine

A lightweight Java implementation of a **limit order book** and trade matching engine.  
The engine processes **limit orders**, maintains **bid/ask books**, and executes trades using **price-time (FIFO) priority**.  
It is designed to demonstrate core principles of **exchange-level matching**, **data structure design**, and **modular software architecture**.

---

## Overview

- Maintains an **in-memory order book** for multiple symbols.
- Matches incoming orders based on **configurable strategies**.
- Supports **asynchronous order submission** via a **multi-threaded dispatcher**, allowing concurrent intake while preserving deterministic matching.
- Separates core concerns:
    - Order management
    - Trade generation
    - Matching strategy execution

---

## Features

- **FIFO matching** based on price-time priority
- Independent **bid/ask books** with efficient lookups
- **Trade generation** and order book state updates
- **Pluggable matching strategies** via Strategy Pattern
- **Multi-threaded order intake** using a producer-consumer pattern
- **CSV-based simulation** for reproducible testing

---

## Design

- **Strategy Pattern** — encapsulates matching algorithms (e.g., FIFO, Pro-Rata)
- **Builder Pattern** — clean, immutable `Order` creation
- **TreeMap + Queue** — ordered price levels with FIFO queues per price
- **Separation of Concerns** — I/O, model, and matching logic decoupled
- **Producer–Consumer Dispatcher** — single-threaded engine processes orders asynchronously from multiple producer threads

---

## Technical Notes

- **Language:** Java
- **Build:** Maven
- **Complexity:** O(log n) per price-level access via `TreeMap`
- **Execution Order:** FIFO via `LinkedList` queues
- **Extensibility:** Strategy-driven and stateless core design
- **Concurrency:** Thread-safe intake using `BlockingQueue` and single-threaded processing for deterministic matching

---

## Future Work

- Market and cancel order support
- Additional matching algorithms
- Persistent order state and logging
- Enhanced multi-threading with per-symbol dispatchers for higher throughput

---

## Author

Alexandra Olaru – Software Engineer