package com.zhihu.QAsite.dao;

import com.zhihu.QAsite.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by machenike on 2017/5/9 0009.
 */
@Mapper
public interface QuestionDAO {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;


    @Select({"select ",SELECT_FIELDS,"from",TABLE_NAME,"where id = #{questionId}"})
    Question selectById(int questionId);


    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);
    @Select({"select comment_count FROM",TABLE_NAME,"WHERE id = #{questionId}"})
    int selectCommentCount(int questionId);

    @Update({"update ",TABLE_NAME,"set comment_count = #{commentCount}" +
            " where id = #{questionId}"})
    int updateCommentCount(@Param("commentCount") int commentCount,
                           @Param("questionId") int questioennnId);

    @Select({"select * from ",TABLE_NAME,"where title like #{keyword} " +
            "order by created_date limit #{offset},#{limit}"})
    List<Question> searchQuestion(@Param("keyword") String keyword,@Param("offset") int offset,
                                  @Param("limit") int limit);

}
