package com.hsy.spider.base.core.storager.impl;
import com.hsy.spider.base.core.storager.Saver;
import com.hsy.spider.base.model.Page;

import java.util.Map;
/**
 * @description <p>控制台存储器，将爬虫内容输出在控制台上，此时logback.xml日志输出级别设置成error</p>
 * @author heshiyuan
 * @date 22/09/2017 2:26 PM
 * @email shiyuan4work@sina.com
 * @github https://github.com/shiyuan2he.git
 * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
 */
public class ConsoleSaver implements Saver {
    public Page save(Page page) {
        Map<Object, Object> map = page.getItems();
        map.forEach((k, v) -> System.out.println(k + " : " + v));
        return page;
    }

}
