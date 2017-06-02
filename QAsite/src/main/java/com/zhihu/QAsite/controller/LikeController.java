package com.zhihu.QAsite.controller;

import com.zhihu.QAsite.async.EventConsumer;
import com.zhihu.QAsite.async.EventModel;
import com.zhihu.QAsite.async.EventProducer;
import com.zhihu.QAsite.async.EventType;
import com.zhihu.QAsite.model.Comment;
import com.zhihu.QAsite.model.Entity;
import com.zhihu.QAsite.model.HostHolder;
import com.zhihu.QAsite.service.CommentService;
import com.zhihu.QAsite.service.LikeService;
import com.zhihu.QAsite.util.JedisAdapter;
import com.zhihu.QAsite.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by machenike on 2017/5/21 0021.
 */
@Controller
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventConsumer eventConsumer;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"/like"} , method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser()==null){
            return WendaUtil.getJSONString(999);
        }

       Comment comment = commentService.getCommentById(commentId);
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()).setEntityId(commentId)
                .setEntityType(Entity.ENTITY_COMMENT).setEntityOwnerId(comment.getUserId())
                .setExt("questionId",String.valueOf(comment.getEntityId())));

        long likeCount = likeService.like(hostHolder.getUser().getId(), Entity.ENTITY_COMMENT,commentId);
        return WendaUtil.getJSONString(0,String.valueOf(likeCount));

    }
    @RequestMapping(path = {"/dislike"} , method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser()==null){
            return WendaUtil.getJSONString(999);
        }

        long likeCount = likeService.disLike(hostHolder.getUser().getId(), Entity.ENTITY_COMMENT,commentId);
        return WendaUtil.getJSONString(0,String.valueOf(likeCount));

    }
}
