package com.example;

import com.example.model.CsvReader;
import com.example.model.Message;
import com.example.model.User;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Runner {

    public static void main(String[] args) {
        List<User> userList = CsvReader.getUsers();
        List<Message> messageList = CsvReader.getMessages();
        Map<LocalDate, Map<Integer, Integer>> map = new HashMap<>();

        List<LocalDate> dates = messageList
                .stream()
                .map(Message::getDate)
                .distinct()
                .collect(Collectors.toList());

        for (LocalDate date : dates) {
            Map<Integer, Integer> nestedMap = new HashMap<>();

            for (User user : userList) {
                int count = (int) messageList
                        .stream()
                        .filter(message -> (message.getIdFrom() == user.getId() || message.getIdTo() == user.getId())
                                && message.getDate().equals(date))
                        .count();
                nestedMap.put(user.getId(), count);
            }
            map.put(date, nestedMap);
        }

        for (Map.Entry<LocalDate, Map<Integer, Integer>> mapLoop : map.entrySet()) {
            Map<Integer, Integer> integerMap = mapLoop.getValue();
            Map.Entry<Integer, Integer> integerEntry = integerMap.entrySet()
                    .stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .get();
            System.out.println(mapLoop.getKey() + " - " + userList.get(integerEntry.getKey()).getName());
        }
    }

}
