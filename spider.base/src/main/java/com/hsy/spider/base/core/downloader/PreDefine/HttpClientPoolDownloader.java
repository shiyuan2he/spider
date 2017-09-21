package com.hsy.spider.base.core.downloader.PreDefine;

import com.hsy.spider.base.core.downloader.Downloader;
import com.hsy.spider.base.model.Page;
import com.hsy.spider.base.model.UrlSeed;
import com.hsy.spider.base.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description <p>httpclient下载器</p>
 * @author heshiyuan
 * @date 21/09/2017 4:16 PM
 * @email shiyuan4work@sina.com
 * @github https://github.com/shiyuan2he.git
 * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
 */
public class HttpClientPoolDownloader implements Downloader {
    private final Logger _logger = LoggerFactory.getLogger(this.getClass());
    public Page download(UrlSeed urlSeed) {
        _logger.info("【下载器】正在爬虫{}网站",urlSeed.getUrl());
        String html = HttpUtils.getInstance().get(urlSeed.getUrl());
        Page page = new Page(urlSeed, html);
        return page;
    }
}
