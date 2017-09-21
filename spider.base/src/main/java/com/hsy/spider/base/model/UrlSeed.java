package com.hsy.spider.base.model;
/**
 * @description <p>种子url</p>
 * @author heshiyuan
 * @date 21/09/2017 1:41 PM
 * @email shiyuan4work@sina.com
 * @github https://github.com/shiyuan2he.git
 * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
 */
public class UrlSeed implements Cloneable {
    /**
     * 优先级(默认是5)，优先级越大越优先，提前被收走。
     * 备注：这里需要自己实现，比如优先队列实现。如果无实现，则默认级别。
     */
    private long priority = 5;
    private String url;

    @Override
    public String toString() {
        return "UrlSeed{" +
                "priority=" + priority +
                ", url='" + url + '\'' +
                '}';
    }

    public UrlSeed(long priority, String url) {
        this.priority = priority;
        this.url = url;
    }

    public UrlSeed(String url) {
        this.priority = 5;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public UrlSeed setUrl(String url) {
        this.url = url;
        return this;
    }

    public long getPriority() {
        return priority;
    }

    public UrlSeed setPriority(long priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        UrlSeed urlSeed = (UrlSeed) o;
        return urlSeed.getUrl().equals(this.url);
    }

    public UrlSeed clone() {
        UrlSeed urlSeed = null;
        try {
            urlSeed = (UrlSeed) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } finally {
            return urlSeed;
        }
    }
}
