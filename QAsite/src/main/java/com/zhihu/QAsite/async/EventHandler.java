package com.zhihu.QAsite.async;

import java.util.List;

/**
 * Created by machenike on 2017/5/24 0024.
 */
public interface EventHandler {
    void doHandler(EventModel eventModel);

    List<EventType> getSupportEventTypes();
}
