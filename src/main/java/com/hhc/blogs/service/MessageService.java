package com.hhc.blogs.service;

import com.hhc.blogs.domain.Message;
import com.hhc.blogs.mapper.MessageMapper;
import com.hhc.blogs.req.MessageSaveReq;
import com.hhc.blogs.util.CopyUtil;
import com.hhc.blogs.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private MessageMapper messageMapper;

    /**
     * 储存消息
     * */
    public void save(MessageSaveReq req){
        LOG.info("消息发布：{}", req);
        Message message = CopyUtil.copy(req, Message.class);
        long id = snowFlake.nextId();
        message.setId(id);
        message.setTime(new Date());
        messageMapper.save(message);
        LOG.info("消息发布成功");
    }

    /**
     * 删除消息
     * */
    public void delete(Long messageId){
        LOG.info("删除消息：{}", messageId);
        messageMapper.deleteById(messageId);
        LOG.info("消息删除成功");
    }

    /**
     * 获取聊天记录
     * */
    public List<Message> getMessageList(Long senderId, Long receiverId){
        LOG.info("{}曾经向{}发送消息", senderId, receiverId);
        List<Message> messageList = messageMapper.getMessageByPair(senderId, receiverId);
        LOG.info("消息查询成功");
        return messageList;
    }
}
