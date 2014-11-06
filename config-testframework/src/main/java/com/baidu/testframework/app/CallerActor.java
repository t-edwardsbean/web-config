package com.baidu.testframework.app;

import akka.actor.Props;
import akka.actor.UntypedActor;
import org.springframework.stereotype.Component;

/**
 * 负责与压测服务主节点MasterActor通信
 * Created by edwardsbean on 14-11-4.
 */
@Component(value = "callerActor")
public class CallerActor  extends UntypedActor {
    public static Props props() {
        return Props.create(CallerActor.class);
    }

    @Override
    public void onReceive(Object message) throws Exception {

    }
}
