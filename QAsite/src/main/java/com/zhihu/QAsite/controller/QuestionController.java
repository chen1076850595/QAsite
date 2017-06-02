package com.zhihu.QAsite.controller;

import com.zhihu.QAsite.model.*;
import com.zhihu.QAsite.service.*;
import com.zhihu.QAsite.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.ObjectView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by machenike on 2017/5/9 0009.
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    HostHolder hostHolder;
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    LikeService likeService;
    @Autowired
    FollowService followService;


    //提问
    @RequestMapping(value = "/question/add",method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content){
        try{
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setCommentCount(0);
            if(hostHolder.getUser() == null){
                //question.setUserId(WendaUtil.ANONYMOUS);
                return WendaUtil.getJSONString(999);
            }else{
            question.setUserId(hostHolder.getUser().getId());
            }
            if(questionService.addQuestion(question)>0){
                return WendaUtil.getJSONString(0);
            }

        }catch (Exception e){
            logger.error("添加题目失败："+e);
        }
        return WendaUtil.getJSONString(1,"失败");
    }

    @RequestMapping(value = "/question/{qid}",method = {RequestMethod.GET})
    public String questionDetail(Model model,
                                 @PathVariable("qid") int qid){
        Question question = questionService.selectQuestion(qid);
       List<Comment> commentList = commentService.getComment(qid, Entity.ENTITY_QUESTION);
        List<ViewObject> comments = new ArrayList<>();
        for(Comment comment:commentList){
            ViewObject vo = new ViewObject();
            vo.set("comment",comment);
            if(hostHolder.getUser()==null){
                vo.set("liked",0);
            }
            else{
                vo.set("liked",likeService.getLikeStatus(hostHolder.getUser().getId(),Entity.ENTITY_COMMENT,comment.getId()));
            }
            vo.set("likeCount",likeService.getLikeCount(Entity.ENTITY_COMMENT,comment.getId()));
            vo.set("user",userService.getUser(comment.getUserId()));
            comments.add(vo);

        }
        //获得粉丝数
        //User user = userService.getUser(question.getUserId());
       // ViewObject vo = new ViewObject();
        //vo.set("user", user);
       // vo.set("commentCount", commentService.getUserCommentCount(userId));
        //vo.set("followerCount", followService.getFollowerCount(Entity.ENTITY_QUESTION, qid));
        //vo.set("followeeCount", followService.getFolloweeCount(userId, Entity.ENTITY_USER));
        // vo.set("followCount",followService.getFollowerCount(Entity.ENTITY_QUESTION,));
        model.addAttribute("followCount",followService.getFollowerCount(Entity.ENTITY_QUESTION,qid));
        List<Integer> followUsersId = followService.getFollowers(Entity.ENTITY_QUESTION,qid,10);
        List<User> followUsers = new ArrayList<User>();
        for(int userId:followUsersId){
            followUsers.add(userService.getUser(userId));
        }
        model.addAttribute("followUsers",followUsers);

        if (hostHolder.getUser() != null) {
        model.addAttribute("followed", followService.isFollower(hostHolder.getUser().getId(), Entity.ENTITY_QUESTION, qid));
        }
        else {
        model.addAttribute("followed", false);
        }
        //model.addAttribute("user",userService.getUser(question.getUserId()));
        model.addAttribute("question",question);
        model.addAttribute("comments",comments);
        return "detail";
    }

}
