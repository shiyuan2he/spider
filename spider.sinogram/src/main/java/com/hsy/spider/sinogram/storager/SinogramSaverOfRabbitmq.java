package com.hsy.spider.sinogram.storager;

import com.hsy.spider.base.core.storager.Saver;
import com.hsy.spider.base.model.Page;
import com.hsy.spider.sinogram.utils.rabbit.RabbitmqProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
/**
 * @author heshiyuan
 * @description <p></p>
 * @path spider/com.hsy.spider.base.core.storager.impl
 * @date 22/09/2017 2:34 PM
 * @github http://github.com/shiyuan2he
 * @email shiyuan4work@sina.com
 * Copyright (c) 2017 shiyuan4work@sina.com All rights reserved.
 * @price ¥5    微信：hewei1109
 */
public class SinogramSaverOfRabbitmq implements Saver{
    private static final Logger _logger = LoggerFactory.getLogger(SinogramSaverOfRabbitmq.class);

    @Override
    public Page save(Page page) {
        _logger.info("【存储器】存储内容\n{}");
        Map<Object, Object> map = page.getItems();
        map.forEach((k, v) -> RabbitmqProducer.getProducer(v));
        return page;
    }
}
