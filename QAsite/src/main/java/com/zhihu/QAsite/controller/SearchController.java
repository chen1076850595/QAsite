package com.zhihu.QAsite.controller;

import com.zhihu.QAsite.model.Entity;
import com.zhihu.QAsite.model.Question;
import com.zhihu.QAsite.model.ViewObject;
import com.zhihu.QAsite.service.FollowService;
import com.zhihu.QAsite.service.QuestionService;
import com.zhihu.QAsite.service.SearchService;
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
 * Created by machenike on 2017/6/1 0001.
 */
@Controller
public class SearchController {
    @Autowired
    SearchService searchService;
    @Autowired
    UserService userService;
    @Autowired
    FollowService followService;

    private List<ViewObject> getQuestions(String keyword, int offset, int limit) {
        List<Question> questionList = searchService.searchQuestion(keyword, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vo.set("followCount",followService.getFollowerCount(Entity.ENTITY_QUESTION,question.getId()));
            vos.add(vo);
        }
        return vos;
    }
    @RequestMapping(path = { "/search"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam("q") String keyword,
                        @RequestParam(value = "offset", defaultValue = "0") int offset,
                        @RequestParam(value = "count", defaultValue = "10") int count) {
        String key = String.format("%%%s%%",keyword);
        System.out.println(key);
        model.addAttribute("vos", getQuestions(key, 0, 10));
        model.addAttribute("keyword",key);
        return "result";
    }
}
