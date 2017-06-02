package com.zhihu.QAsite.controller;

import com.zhihu.QAsite.async.EventModel;
import com.zhihu.QAsite.async.EventProducer;
import com.zhihu.QAsite.async.EventType;
import com.zhihu.QAsite.model.Comment;
import com.zhihu.QAsite.model.Entity;
import com.zhihu.QAsite.model.HostHolder;
import com.zhihu.QAsite.service.CommentService;
import com.zhihu.QAsite.service.QuestionService;
import com.zhihu.QAsite.util.WendaUtil;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.swing.text.html.HTML;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by machenike on 2017/5/14 0014.
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;
    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/addComment"}, method={RequestMethod.POST})
    public String addComment(@RequestParam("content") String content,
                             @RequestParam("questionId") int questionId){
        try {
            content = HtmlUtils.htmlEscape(content);
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            if(hostHolder.getUser()!=null){
                comment.setUserId(hostHolder.getUser().getId());

            }else{
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            comment.setEntityId(questionId);
            comment.setEntityType(Entity.ENTITY_QUESTION);
            comment.setStatus(0);
            commentService.addComment(comment);   //评论加入数据库
            eventProducer.fireEvent(new EventModel(EventType.COMMENT).setActorId(comment.getUserId())
                    .setEntityId(questionId));
            //同时更新该问题评论的总数
            //思考大量用户同时评论时，对评论总数的更新需要进行原子操作，否则评论总数出错
            //但给该段程序加锁又会造成大量用户的同时等待，时效性极低
            //------考虑新的方法给以解决----------！！！！！！！！！！！！！
           /* synchronized (new Object()){
                questionService.setCommentCount((questionService.getCommentCount(questionId)+1),questionId);
            }*/


        }catch (Exception e){
            logger.error("更新评论失败:"+e);
        }
        return "redirect:/question/"+String.valueOf(questionId);
    }
}
