package com.zhihu.QAsite.service;

import com.zhihu.QAsite.dao.QuestionDAO;
import com.zhihu.QAsite.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by machenike on 2017/5/9 0009.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }
    public int addQuestion(Question question){
        //敏感词过滤
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));

        return questionDAO.addQuestion(question)>0 ? question.getUserId():0;
    }
    public Question selectQuestion(int id){
        return questionDAO.selectById(id);
    }
    public int getCommentCount(int questionId){
        return questionDAO.selectCommentCount(questionId);
    }
    public int setCommentCount(int commentCount,int questionId){
        return questionDAO.updateCommentCount(commentCount,questionId);
    }

}
