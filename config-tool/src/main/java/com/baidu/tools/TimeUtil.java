package com.baidu.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by edwardsbean on 14-11-20.
 */
public class TimeUtil {
    public static final Logger log = LoggerFactory.getLogger(TimeUtil
            .class);

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static String format(long time) {
        return format.format(new Date(time));
    }
}
