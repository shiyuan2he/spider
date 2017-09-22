package com.hsy.spider.sinogram.starter;

import com.hsy.spider.base.core.Spider;
import com.hsy.spider.sinogram.resolver.SinogramPageResolver;
import com.hsy.spider.sinogram.storager.SinogramSaveOfConsole;
import com.hsy.spider.sinogram.storager.SinogramSaverOfRabbitmq;

/**
 * @description <p>汉字爬虫启动器</p>
 * @author heshiyuan
 * @date 21/09/2017 2:56 PM
 * @email shiyuan4work@sina.com
 * @github https://github.com/shiyuan2he.git
 * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
 */
public class SinogramSpider {

    public static final String url = "https://read.douban.com" ;
    public static final String regexRule = "+http://.*.jianshu.com/.*";
    public static void main(String[] args) {
        Spider.build()
                .setSaver(new SinogramSaverOfRabbitmq())
                .setProcessor(new SinogramPageResolver())
                .thread(5)
                .addUrlSeed(url)
                //.addRegexRule(regexRule)
                .run();
    }
}
