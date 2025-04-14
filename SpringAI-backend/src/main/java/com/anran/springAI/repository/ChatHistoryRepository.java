package com.anran.springAI.repository;

import java.util.List;

public interface ChatHistoryRepository {

    /**
     * 保存会话记录
     * @param type 业务类型：chat/service/pdf
     * @param chatId 会话Id
     */
    void save(String type, String chatId);

    /**
     * 获取会话 Id 列表
     * @param type 业务类型
     * @return 会话 Id 列表
     */
    List<String> getChatIds(String type);
}
