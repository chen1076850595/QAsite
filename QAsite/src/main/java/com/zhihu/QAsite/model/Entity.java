package com.zhihu.QAsite.model;

/**
 * Created by machenike on 2017/5/14 0014.
 */
public class Entity {
    public static int ENTITY_QUESTION = 1;    //评论、关注 的是问题
    public static int ENTITY_COMMENT = 2;     //评论、关注 的是评论
    public static int ENTITY_USER = 3;        //关注的是人


    public static int getEntityQuestion() {
        return ENTITY_QUESTION;
    }

    public static void setEntityQuestion(int entityQuestion) {
        ENTITY_QUESTION = entityQuestion;
    }

    public static int getEntityComment() {
        return ENTITY_COMMENT;
    }

    public static void setEntityComment(int entityComment) {
        ENTITY_COMMENT = entityComment;
    }
}
