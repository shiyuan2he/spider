package com.hsy.spider.base.core;
import com.hsy.spider.base.core.downloader.Downloader;
import com.hsy.spider.base.core.downloader.impl.HttpClientPoolDownloader;
import com.hsy.spider.base.core.resolver.PageResolver;
import com.hsy.spider.base.core.resolver.impl.TextPageResolver;
import com.hsy.spider.base.core.storager.impl.ConsoleSaver;
import com.hsy.spider.base.core.storager.Saver;
import com.hsy.spider.base.core.scheduler.impl.QueueScheduler;
import com.hsy.spider.base.core.scheduler.Scheduler;
import com.hsy.spider.base.model.Page;
import com.hsy.spider.base.model.RegexRule;
import com.hsy.spider.base.model.UrlSeed;
import com.hsy.spider.base.utils.TimeSleep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description <p>爬虫启动器</p>
 * @author heshiyuan
 * @date 21/09/2017 11:28 AM
 * @email shiyuan4work@sina.com
 * @github https://github.com/shiyuan2he.git
 * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
 */
public class Spider {
    private final Logger _logger = LoggerFactory.getLogger(getClass());

    private Scheduler scheduler;//调度器
    private Downloader downloader;//下载器
    private PageResolver pageProcessor;//页面解析器
    private Saver saver;//存储器

    //新种子的过滤器，只有通过正则的，才会加入到待爬取种子队列
    private RegexRule regexRule;

    private int threadNum = 5;//线程池大小。默认5个爬虫在进行。
    private ThreadPoolExecutor pool;

    /**
     * 最多几个爬虫在进行。
     * 默认5个。
     *
     * @param threadNum
     * @return 自己
     */
    public Spider thread(int threadNum) {
        this.threadNum = threadNum;
        if (threadNum <= 0) {
            this.threadNum = 5;
        }
        pool = new ThreadPoolExecutor(
                threadNum,//corePoolSize 线程池线程总数量
                threadNum,//maximumPoolSize 线程池最大数量
                1500L,//keepAliveTime，线程存活时间，1.5秒
                TimeUnit.MILLISECONDS,//unit 时间单位，毫秒
                new LinkedBlockingQueue<>()//workQueue 线程执行队列
        );
        return this;
    }

    public static Spider build() {
        return new Spider();
    }

    public Spider() {
        setDefaultComponents();
        regexRule = new RegexRule();
    }

    public Spider setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public Spider setDownloader(Downloader d) {
        this.downloader = d;
        return this;
    }

    public Spider setProcessor(PageResolver p) {
        this.pageProcessor = p;
        return this;
    }

    public Spider setSaver(Saver s) {
        this.saver = s;
        return this;
    }

    /**
     * 添加初始化种子，可以多个
     *
     * @param url
     * @return Spider
     */
    public Spider addUrlSeed(String url) {
        scheduler.push(new UrlSeed(url));
        return this;
    }

    /**
     * 添加新种子需要满足的正则信息（正则规则有两种，正正则和反正则）
     * <p>
     * URL符合正则规则需要满足下面条件：
     * 1.至少能匹配一条正正则
     * 2.不能和任何反正则匹配
     * 举例：
     * 正正则示例：+a.*c是一条正正则，正则的内容为a.*c，起始加号表示正正则
     * 反正则示例：-a.*c时一条反正则，正则的内容为a.*c，起始减号表示反正则
     * 如果一个规则的起始字符不为加号且不为减号，则该正则为正正则，正则的内容为自身
     * 例如a.*c是一条正正则，正则的内容为a.*c
     *
     * @param regex 正则
     * @return Spider
     */
    public Spider addRegexRule(String regex) {
        regexRule.addRule(regex);
        return this;
    }

    private Spider setDefaultComponents() {
        thread(threadNum);

        if (scheduler == null) {
            scheduler = new QueueScheduler();
        }
        if (downloader == null) {
            downloader = new HttpClientPoolDownloader();
        }
        if (pageProcessor == null) {
            pageProcessor = new TextPageResolver();
        }
        if (saver == null) {
            saver = new ConsoleSaver();
        }
        return this;
    }

    public void run() {
        _logger.info("【启动器】爬虫启动!");
        UrlSeed urlSeed = null;
        while (true) {
            _logger.info("【启动器】当前线程池已完成:{}   运行中：{}  最大运行:{} 等待队列:{}",
                    pool.getCompletedTaskCount(),pool.getActiveCount(),pool.getPoolSize(),pool.getQueue().size());
            if (pool.getQueue().size() > pool.getCorePoolSize()) {
                //如果等待队列大于了100.就暂停接收新的url。不然会影响优先级队列的使用。
                TimeSleep.sleep(1000);
                continue;
            }
            urlSeed = scheduler.poll();
            if (urlSeed == null && pool.getActiveCount() == 0) {
                pool.shutdown();
                try {
                    pool.awaitTermination(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    _logger.error("【启动器】关闭线程池失败！", e);
                }
                _logger.info("【启动器】爬虫结束！");
                break;
            } else if (urlSeed == null) {
                //没有取到种子就等待!
                TimeSleep.sleep(1000);
            } else {
                _logger.info("【启动器】正在处理:" + urlSeed.getUrl() + "  优先级(默认:5):" + urlSeed.getPriority());
                pool.execute(new SpiderWork(urlSeed.clone()));
            }
        }

    }
    /**
     * @description <p>爬虫执行器</p>
     * @author heshiyuan
     * @date 21/09/2017 1:31 PM
     * @email shiyuan4work@sina.com
     * @github https://github.com/shiyuan2he.git
     * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
     */
    class SpiderWork implements Runnable {

        private UrlSeed urlSeed;
        SpiderWork(UrlSeed urlSeed) {
            this.urlSeed = urlSeed;
        }

        public void run() {
            _logger.debug("【执行器】线程:[" + Thread.currentThread().getName() + "]正在处理:" + urlSeed.getUrl());
            _logger.info("【执行器】当前线程池已完成:{}   运行中：{}  最大运行:{} 等待队列:{}",
                    pool.getCompletedTaskCount(),pool.getActiveCount(),pool.getPoolSize(),pool.getQueue().size());

            //下载器-下载指定页面
            Page nowPage = downloader.download(urlSeed);

            //解析器-解析页面
            pageProcessor.process(nowPage);

            //正则处理-过滤不满足规则的地址
            List<UrlSeed> urlSeedList = nowPage.links();
            for (Iterator<UrlSeed> it = urlSeedList.iterator(); it.hasNext(); ) {
                UrlSeed seed = it.next();
                if (!regexRule.regex(seed.getUrl())) {
                    _logger.debug("【执行器】{}不满足正则规则,移除此网址。",seed.getUrl());
                    it.remove();
                }
            }
            // 设置新的url种子
            nowPage.setNewUrlSeed(urlSeedList);
            // 递归解析新地址里的页面
            pageProcessor.processNewUrlSeeds(nowPage);

            // 重新调度
            nowPage.getNewUrlSeed().forEach(seed -> scheduler.push(seed));
            // 存储器保存结果
            saver.save(nowPage);
        }
    }
}

