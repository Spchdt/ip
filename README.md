# 🥐 Kroissant - Your Personal Task Assistant

> "The croissant is a reminder that even the most ordinary things can be made extraordinary if you are willing to put in the layers" – Ruth Reichl

## What is Kroissant?

**Kroissant** is a lightweight, CLI-based task manager designed for busy people who want to stay organized *without the bloat*. Built with Java, it's:

* Simple and **intuitive**
* Lightning-fast ⚡
* Perfect for keyboard warriors
* ~~Overly complicated~~ Refreshingly straightforward

## Quick Start Guide

Getting started with Kroissant is a piece of bread 🍞:

1. Clone this repository to your local machine
2. Compile the Java files using `javac`
3. Run the application with `java Kroissant`
4. Start adding your tasks and watch your productivity soar! 🚀

## Core Features

Here's what Kroissant can do for you right now:

- [x] Add and manage **Todo** tasks
- [x] Track **Deadlines** with specific dates
- [x] Schedule **Events** with start and end times
- [x] Mark tasks as complete
- [x] Delete tasks you no longer need
- [x] Find tasks using keywords
- [ ] Set recurring tasks (coming in v2.0)
- [ ] Priority levels (planned)

## How It Works

Kroissant uses a clean command-line interface. Here's a peek at the core task handling logic:

```java
public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }
}
```

### Example Usage

Try these commands to get started:

```
todo Buy groceries
deadline CS2103T iP /by 2026-02-14
event Team meeting /from 2026-01-30 14:00 /to 2026-01-30 16:00
list
mark 1
find meeting
```

## Why Choose Kroissant? 🤔

Unlike bloated task management apps that require internet connection, subscriptions, and endless clicking, Kroissant keeps things **simple**:

- **No distractions** - just you and your terminal
- **Fast as lightning** - commands execute instantly
- **Your data stays local** - privacy guaranteed
- **Free forever** - no premium tiers or paywalls

Plus, if you're learning Java, Kroissant serves as an excellent example of:
- Object-oriented programming principles
- Exception handling with custom `KroissantException`
- File I/O for data persistence
- Command parsing and validation

## Technical Highlights

Built with modern Java practices including:

* **OOP Design** - Clean separation of concerns with `Task`, `Deadline`, `Event`, `Todo` classes
* **Exception Handling** - Custom exceptions for better error messages
* **Data Persistence** - Automatic saving and loading of tasks
* **Extensible Architecture** - Easy to add new task types and commands

## AI Usage

For information about how AI tools were used in the development of this project, please refer to [AI.md](AI.md).

---