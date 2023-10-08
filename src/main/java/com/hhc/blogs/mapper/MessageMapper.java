package com.hhc.blogs.mapper;

import com.hhc.blogs.domain.Message;

import java.util.List;

public interface MessageMapper {
    public void save(Message message);

    public void deleteById(Long messageId);

    public List<Message> getMessageByPair(Long senderId, Long receiverId);
}
