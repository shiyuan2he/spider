package com.hsy.spider.base.core.scheduler.impl;

import com.hsy.spider.base.core.scheduler.Scheduler;
import com.hsy.spider.base.model.UrlSeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @description <p>队列调度器</p>
 * @author heshiyuan
 * @date 21/09/2017 11:50 AM
 * @email shiyuan4work@sina.com
 * @github https://github.com/shiyuan2he.git
 * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
 */
public class QueueScheduler implements Scheduler {
    private Logger _logger = LoggerFactory.getLogger(getClass());

    private BlockingQueue<UrlSeed> queue = new LinkedBlockingQueue<>();
    private Set<UrlSeed> urlSet = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void push(UrlSeed urlSeed) {
        if (urlSeed.getUrl() == null
                || urlSeed.getUrl().trim().equals("")
                || urlSeed.getUrl().trim().equals("#")
                || urlSeed.getUrl().trim().toLowerCase().contains("javascript:"))
            return;
        if (urlSet.add(urlSeed)) {
            queue.add(urlSeed);
        } else {
            _logger.info("【调度器】UrlSeed重复:{}",urlSeed.getUrl());
        }
    }
    @Override
    public UrlSeed poll() {
        return queue.poll();
    }
}
