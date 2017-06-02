package com.zhihu.QAsite.dao;

import com.zhihu.QAsite.model.Message;
import com.zhihu.QAsite.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by machenike on 2017/5/15 0015.
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = "message";
    String INSERT_FIELDS = " from_id, to_id, content, created_date,has_read,conversation_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Select({"select ",SELECT_FIELDS,"from",TABLE_NAME,"where id = #{messageId}"})
    Message selectById(int messageId);

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by id desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset, @Param("limit") int limit);


    /*select  * , count(id) as id from ( select * from  message
    where from_id=13  or to_id=13 order by created_date DESC ) tt group by conversation_id  order by created_date DESC limit 0, 10;
    */
    @Select({"select count(id) from ", TABLE_NAME , " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConvesationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Select({"select ", INSERT_FIELDS, " ,count(id) as id from ( select * from ", TABLE_NAME,
            " where from_id=#{userId} or to_id=#{userId} order by created_date DESC ) tt group by conversation_id  order by created_date DESC limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset, @Param("limit") int limit);
    @Update({"update",TABLE_NAME,"set has_read = 1 where conversation_id = #{conversationId}"})
    int updateHasRead(String conversationId);

}
