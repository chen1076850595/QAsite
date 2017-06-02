package com.zhihu.QAsite.service;

import com.zhihu.QAsite.dao.MessageDAO;
import com.zhihu.QAsite.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by machenike on 2017/5/15 0015.
 */
@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;
    public int addMessage(Message message){
        return messageDAO.addMessage(message);
    }
    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDAO.getConversationList(userId, offset, limit);
    }

    public int getConvesationUnreadCount(int userId, String conversationId) {
        return messageDAO.getConvesationUnreadCount(userId, conversationId);
    }
    public int updateHasRead(String conversationId){
        return messageDAO.updateHasRead(conversationId);
    }
}
