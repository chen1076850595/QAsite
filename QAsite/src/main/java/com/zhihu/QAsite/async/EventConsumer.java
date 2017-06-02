package com.zhihu.QAsite.async;

import com.alibaba.fastjson.JSON;
import com.zhihu.QAsite.controller.CommentController;
import com.zhihu.QAsite.util.JedisAdapter;
import com.zhihu.QAsite.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by machenike on 2017/5/24 0024.
 */
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware{
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    JedisAdapter jedisAdapter;

    private Map<EventType,List<EventHandler>> config = new HashMap<EventType,List<EventHandler>>();

    private ApplicationContext applicationContext;
    @Override
    public void afterPropertiesSet() throws Exception {
       // Map<EventType,List<EventHandler>>  beans
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if(beans != null){
            for(Map.Entry<String , EventHandler> entry: beans.entrySet()){
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for(EventType  type: eventTypes){
                    if(!config.containsKey(type)){
                        config.put(type,new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }
       // ExecutorService servicePool = Executors.newFixedThreadPool(4);
        Thread thread= new Thread(new Runnable() {
             @Override
             public void run() {
                  while(true){
                 String key = RedisKeyUtil.getEventQueueKey();
                 List<String> events = jedisAdapter.brpop(0, key);
                 for (String message : events) {
                     if (message.equals(key)) {
                         continue;
                     }
                     EventModel eventModel = JSON.parseObject(message, EventModel.class);//解析EvnetModel
                     if (!config.containsKey(eventModel.getType())) {
                         logger.error("不能识别的事件");
                         continue;
                     }
                     for (EventHandler handler : config.get(eventModel.getType())) {
                         handler.doHandler(eventModel);
                     }
                 }
             }
           }
        });
        //for(int i = 0;i<4;i++){
        //    servicePool.submit(thread);
        //}
       // servicePool.shutdown();

       // service.shutdown();
        thread.start();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
