package com.example.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader {

    private static final String csvUserPath = "D:\\NewJavaProjects\\IsSoft\\src\\main\\resources\\users.csv";
    private static final String csvMessagePath = "D:\\NewJavaProjects\\IsSoft\\src\\main\\resources\\messages.csv";
    private static final String csvSplit = ",";
    private static final List<User> users = new LinkedList<>();
    private static final List<Message> messages = new LinkedList<>();

    static {
        String user;
        try (BufferedReader br = new BufferedReader(new FileReader(csvUserPath));
             BufferedReader brMessage = new BufferedReader(new FileReader(csvMessagePath))
        ) {
            System.out.println("Files were connect");
            br.lines().skip(1).forEach(s -> users.add(
                    setUpUser(s)
            ));
            brMessage.lines().skip(1).forEach(s -> messages.add(
                    setUpMessage(s)
            ));
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    private static User setUpUser(String str) {
        String[] strings = str.replaceAll("\"", "").split(csvSplit);
        return new User(Integer.parseInt(strings[0]), strings[1]);
    }

    private static Message setUpMessage(String str) {
        String[] strings = str.replaceAll("\"", "").split(csvSplit);
        return new Message(LocalDate.parse(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Message> getMessages() {
        return messages.stream()
                .sorted(Comparator.comparing(Message::getDate))
                .collect(Collectors.toList());
    }

}


