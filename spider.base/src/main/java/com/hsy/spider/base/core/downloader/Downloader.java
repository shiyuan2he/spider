package com.hsy.spider.base.core.downloader;

import com.hsy.spider.base.model.Page;
import com.hsy.spider.base.model.UrlSeed;

/**
 * Created by shilei on 2017/4/10.
 * 下载器
 */
public interface Downloader {

    /**
     * @param urlSeed 下载url页面
     */
    Page download(UrlSeed urlSeed);
}
