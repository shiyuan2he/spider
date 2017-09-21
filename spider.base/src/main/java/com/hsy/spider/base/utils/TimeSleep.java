package com.hsy.spider.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
/**
 * @description <p></p>
 * @author heshiyuan 
 * @date 21/09/2017 1:46 PM
 * @email shiyuan4work@sina.com
 * @github https://github.com/shiyuan2he.git
 * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
 */
public class TimeSleep {
    private static Logger logger = LoggerFactory.getLogger(TimeSleep.class);
    /**
     * 睡眠等待
     * @param milliseconds 毫秒
     */
    public static void sleep(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            logger.error("该线程无法获取到种子了（意味着线程正常结束！或者出错！）", e);
        }
    }

}
