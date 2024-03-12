package ru.ifmo.blps.exceptions;

public class NotEnoughBalanceException extends RuntimeException{
    public NotEnoughBalanceException() {
        super("Не достаточно средств");
    }
}
