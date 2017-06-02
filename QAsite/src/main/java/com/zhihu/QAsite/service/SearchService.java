package com.zhihu.QAsite.service;

import com.zhihu.QAsite.dao.QuestionDAO;
import com.zhihu.QAsite.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by machenike on 2017/6/1 0001.
 */
@Service
public class SearchService {
    @Autowired
    QuestionDAO questionDAO;

    public List<Question> searchQuestion(String keyword,int offset,int count){
        return questionDAO.searchQuestion(keyword,offset ,count);
    }
}
