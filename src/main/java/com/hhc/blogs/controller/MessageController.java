package com.hhc.blogs.controller;

import com.hhc.blogs.domain.Message;
import com.hhc.blogs.req.MessageSaveReq;
import com.hhc.blogs.resp.CommonResp;
import com.hhc.blogs.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 储存消息
     * */
    @PostMapping("/save")
    public CommonResp<Object> messageSave(@RequestBody MessageSaveReq req) {
        CommonResp<Object> resp = new CommonResp<>();
        messageService.save(req);
        return resp;
    }

    /**
     * 删除聊天记录
     * */
    @GetMapping("/delete/{messageId}")
    public CommonResp<Object> deleteById(@PathVariable Long messageId){
        CommonResp<Object> commonResp = new CommonResp<>();
        messageService.delete(messageId);
        return commonResp;
    }

    /**
     * 获取聊天记录
     * */
    @GetMapping("/getMessage/{senderId}/{receiverId}")
    public CommonResp<List<Message>> getMessageList(@PathVariable Long senderId, @PathVariable Long receiverId){
        CommonResp<List<Message>> resp = new CommonResp<>();
        List<Message> messageList = messageService.getMessageList(senderId, receiverId);
        resp.setContent(messageList);
        return resp;
    }
}