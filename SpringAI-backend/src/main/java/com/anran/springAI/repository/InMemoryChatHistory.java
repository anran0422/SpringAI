package com.anran.springAI.repository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryChatHistory implements ChatHistoryRepository{

    /**
     * 这里是存储在本地，如果想要存储在数据库，那么就需要对应着去存储
     * todo 基于用户的会话查询
     */
    private final Map<String, List<String>> chatHistory = new HashMap<>();

    @Override
    public void save(String type, String chatId) {
//        List<String> chatIds = chatHistory.computeIfAbsent(type, k -> new ArrayList<>());
        if(!chatHistory.containsKey(type)) {
            chatHistory.put(type, new ArrayList<>());
        }
        List<String> chatIds = chatHistory.get(type);

        if(chatIds.contains(chatId)) {
            return;
        }
        chatIds.add(chatId);
    }

    @Override
    public List<String> getChatIds(String type) {
        List<String> chatIds = chatHistory.get(type);
        if(chatIds == null) {
            return new ArrayList<>();
        }
        return chatIds;
//        return chatHistory.getOrDefault(type, new ArrayList<>());
    }
}
