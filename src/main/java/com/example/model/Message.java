package com.example.model;

import java.time.LocalDate;
import java.util.Objects;

public class Message {

    private final LocalDate date;
    private final int idFrom;
    private final int idTo;

    public Message(LocalDate date , int idFrom, int idTo) {
        this.date = date;
        this.idFrom = idFrom;
        this.idTo = idTo;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getIdFrom() {
        return idFrom;
    }

    public int getIdTo() {
        return idTo;
    }

    @Override
    public String toString() {
        return "Message{" +
                "date=" + date +
                ", idFrom=" + idFrom +
                ", idTo=" + idTo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (idFrom != message.idFrom) return false;
        if (idTo != message.idTo) return false;
        return Objects.equals(date, message.date);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + idFrom;
        result = 31 * result + idTo;
        return result;
    }
}
