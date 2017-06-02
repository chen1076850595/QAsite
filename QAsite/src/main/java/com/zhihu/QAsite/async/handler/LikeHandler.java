package com.zhihu.QAsite.async.handler;

import com.zhihu.QAsite.async.EventHandler;
import com.zhihu.QAsite.async.EventModel;
import com.zhihu.QAsite.async.EventType;
import com.zhihu.QAsite.model.Message;
import com.zhihu.QAsite.model.User;
import com.zhihu.QAsite.service.MessageService;
import com.zhihu.QAsite.service.UserService;
import com.zhihu.QAsite.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by machenike on 2017/5/25 0025.
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @Override
    public void doHandler(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        message.setConversationId(String.valueOf(WendaUtil.SYSTEM_USERID+"_"+model.getEntityOwnerId()));
        User user = userService.getUser(model.getActorId());
        message.setContent("用户 "+user.getName()+" 赞了你的评论,http://127.0.0.1:8080/question/"+model.getExt("questionId"));

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
