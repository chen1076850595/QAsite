package com.zhihu.QAsite.util;

/**
 * Created by machenike on 2017/5/23 0023.
 * 获得特有的Key
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE ="DISLIKE";
    private static String BIZ_EVENTQUEUE ="EVENT_QUEUE";

    private static String BIZ_FOLLOWER = "FOLLOWER";
    private static String BIZ_FOLLOWEE = "FOLLOWEE";
    private static String BIZ_TIMELINE = "TIMELINE";


    public static String getLikeKey(int entityType,int entityId) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }
    public static String getDisLikeKey(int entityType,int entityId) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }

    //粉丝
    public static String getFollowerKey(int EntityType,int EntityId){
        return BIZ_FOLLOWER+SPLIT+String.valueOf(EntityType)+SPLIT+String.valueOf(EntityId);
    }
    //关注
    public static String getFolloweeKey(int userId,int EntityType){
        return BIZ_FOLLOWER+SPLIT+String.valueOf(userId)+SPLIT+String.valueOf(EntityType);
    }
    public static String getTimelineKey(int userId) {
        return BIZ_TIMELINE + SPLIT + String.valueOf(userId);
    }

}