package com.anran.springAI.controller;

import com.anran.springAI.model.VO.MessageVO;
import com.anran.springAI.repository.ChatHistoryRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/history")
public class ChatHistoryController {

    @Resource
    private ChatHistoryRepository chatHistoryRepository;

    private final ChatMemory chatMemory;

    @GetMapping("/{type}")
    public List<String> getChatIds(@PathVariable("type") String type) {
        return chatHistoryRepository.getChatIds(type);
    }

    /**
     * 这里其实不需要 {type}，但是如果只有 {chatId}，区分不清楚是上面那个接口
     * 还是下面这个接口了
     */
    @GetMapping("/{type}/{chatId}")
    public List<MessageVO> getChatHistory(@PathVariable("type") String type, @PathVariable("chatId") String chatId) {
        // 根据里面的方法是 lastN 条 直接给个最大的，将全部的查出来
        List<Message> messages = chatMemory.get(chatId, Integer.MAX_VALUE);
        if(messages == null) {
            return new ArrayList<>();
        }
        return messages.stream().map(MessageVO::new).toList();
    }
}
