package com.zhihu.QAsite.async.handler;

import com.zhihu.QAsite.async.EventHandler;
import com.zhihu.QAsite.async.EventModel;
import com.zhihu.QAsite.async.EventType;
import com.zhihu.QAsite.model.Entity;
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
 * Created by nowcoder on 2016/7/30.
 */
@Component
public class FollowHandler implements EventHandler {
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

        if (model.getEntityType() == Entity.ENTITY_QUESTION) {
            message.setContent("用户" + user.getName()
                    + "关注了你的问题,http://127.0.0.1:8080/question/" + model.getEntityId());
        } else if (model.getEntityType() == Entity.ENTITY_USER) {
            message.setContent("用户" + user.getName()
                    + "关注了你,http://127.0.0.1:8080/user/" + model.getActorId());
        }

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
