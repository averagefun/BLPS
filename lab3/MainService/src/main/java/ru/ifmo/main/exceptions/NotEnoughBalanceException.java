package ru.ifmo.main.exceptions;

public class NotEnoughBalanceException extends RuntimeException {
    public NotEnoughBalanceException() {
        super("Не достаточно средств");
    }
}
