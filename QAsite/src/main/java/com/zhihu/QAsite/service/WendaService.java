package com.zhihu.QAsite.service;

import org.springframework.stereotype.Service;

/**
 * Created by machenike on 2017/5/9 0009.
 */
@Service
public class WendaService {
    public String getMessage(int userId) {
        return "Hello Message:" + String.valueOf(userId);
    }
}
