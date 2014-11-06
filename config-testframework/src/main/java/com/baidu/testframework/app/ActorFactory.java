package com.baidu.testframework.app;

import akka.actor.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author xhx
 */
public class ActorFactory {
    private final static ActorSystem system;
    private final static Config config;
    static {
        config = ConfigFactory.load();
        system = ActorSystem.create("stressTestApp",config);
    }
    private final static ThreadLocal<Inbox> cacheRequestInbox = new ThreadLocal<>();
    public static ActorRef create(Props props, String name) {
        return system.actorOf(props, name);
    }

    public static Inbox createInbox() {
        Inbox i = cacheRequestInbox.get();
        if (i==null) {
            i = Inbox.create(system);
            cacheRequestInbox.set(i);
        }
        return i;
    }

    public static void shutdown() {
        system.shutdown();
    }

    public static Cancellable sendAfter(long milliseconds, ActorRef actor, Object msg) {
        return system.scheduler().scheduleOnce(Duration.create(milliseconds, TimeUnit.MILLISECONDS),
                actor, msg, system.dispatcher(), null);
    }

    public static Cancellable sendCycle(long delay, long milliseconds, ActorRef actor, Object msg) {
        return system.scheduler().schedule(
                Duration.create(delay, TimeUnit.MILLISECONDS),
                Duration.create(milliseconds, TimeUnit.MILLISECONDS),
                actor, msg, system.dispatcher(), null);
    }

}
