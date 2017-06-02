package com.zhihu.QAsite.service;

import com.zhihu.QAsite.dao.CommentDAO;
import com.zhihu.QAsite.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by machenike on 2017/5/14 0014.
 */
@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;
    public int addComment(Comment comment){
        return commentDAO.addComment(comment);
    }
    public List<Comment> getComment(int Entity,int EntityType){
        return commentDAO.selectCommentByEntity(Entity,EntityType);
    }
    public Comment getCommentById(int id){
        return commentDAO.getCommentById(id);
    }
    public int getUserCommentCount(int userId) {
        return commentDAO.getUserCommentCount(userId);
    }
}
