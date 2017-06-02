package com.zhihu.QAsite.async.handler;

import com.zhihu.QAsite.async.EventHandler;
import com.zhihu.QAsite.async.EventModel;
import com.zhihu.QAsite.async.EventType;
import com.zhihu.QAsite.model.Entity;
import com.zhihu.QAsite.model.Message;
import com.zhihu.QAsite.model.User;
import com.zhihu.QAsite.service.MessageService;
import com.zhihu.QAsite.service.QuestionService;
import com.zhihu.QAsite.service.UserService;
import com.zhihu.QAsite.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by machenike on 2017/5/31 0031.
 */
@Component
public class CommentHandler implements EventHandler{
    @Autowired
    MessageService messageService;

    @Autowired
    QuestionService questionService;

    @Override
    public void doHandler(EventModel model) {
        synchronized (new Object()){
            questionService.setCommentCount((questionService.getCommentCount(model.getEntityId())+1),model.getEntityId());
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.COMMENT);
    }
}