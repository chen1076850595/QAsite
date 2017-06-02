package com.zhihu.QAsite.controller;

import com.zhihu.QAsite.dao.QuestionDAO;
import com.zhihu.QAsite.model.Question;
import com.zhihu.QAsite.model.ViewObject;
import com.zhihu.QAsite.service.QuestionService;
import com.zhihu.QAsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by machenike on 2017/5/9 0009.
 */
@Controller
public class testController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
    @RequestMapping(path = {"/te", "/1"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("vos", getQuestions(0, 0, 10));
        return "test";
    }
}
