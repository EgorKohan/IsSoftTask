package com.example;

import com.example.model.CsvReader;
import com.example.model.Message;
import com.example.model.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Runner {

    public static void main(String[] args) {
        List<User> userList = CsvReader.getUsers();
        List<Message> messageList = CsvReader.getMessages();
        Map<LocalDate, Map<Integer, Integer>> map = new HashMap<>();

        long start = System.currentTimeMillis();

        final Set<LocalDate> dates = messageList.parallelStream().map(Message::getDate).collect(Collectors.toSet());

        for (LocalDate localDate : dates) {
            Map<Integer, Integer> userMap = new HashMap<>();
            for (User user : userList) {
                int userId = user.getId();
                userMap.put(userId, getCountOfUsersByDate(messageList, localDate, userId));
            }
            map.put(localDate, userMap);
        }

        for (Map.Entry<LocalDate, Map<Integer, Integer>> mapLoop : map.entrySet()) {
            Map<Integer, Integer> integerMap = mapLoop.getValue();
            Map.Entry<Integer, Integer> integerEntry = integerMap.entrySet()
                    .parallelStream()//here was stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .get();
            System.out.println(mapLoop.getKey() + " - " + userList.get(integerEntry.getKey()).getName());
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    private static int getCountOfUsersByDate(List<Message> messageList, LocalDate localDate, int userId) {
        return (int) messageList.parallelStream()
                .filter(message -> (
                                (message.getIdFrom() == userId || message.getIdTo() == userId) &&
                                        localDate.equals(message.getDate())
                        )
                ).count();
    }


}
