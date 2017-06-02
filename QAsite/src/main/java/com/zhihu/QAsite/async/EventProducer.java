package com.zhihu.QAsite.async;

import com.alibaba.fastjson.JSONObject;
import com.zhihu.QAsite.util.JedisAdapter;
import com.zhihu.QAsite.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

/**
 * Created by machenike on 2017/5/24 0024.
 */
@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){
        try{
            //可以使用BlockingQueue ,此处使用redis
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key,json);       //放入队列
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
