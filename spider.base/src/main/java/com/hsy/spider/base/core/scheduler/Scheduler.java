package com.hsy.spider.base.core.scheduler;
import com.hsy.spider.base.model.UrlSeed;
/**
 * @description <p>
 * 调度器
 * 我们可以自定义调度器。
 * 需要自己实现去重功能！
 * </p>
 * @author heshiyuan
 * @date 21/09/2017 11:26 AM
 * @email shiyuan4work@sina.com
 * @github https://github.com/shiyuan2he.git
 * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
 */
public interface Scheduler {

    /**
     * @description <p>写进去url种子</p>
     * @param urlSeed url种子
     * @return No such property: code for class: Script1
     * @author heshiyuan
     * @date 21/09/2017 11:27 AM
     * @email shiyuan4work@sina.com
     * @github https://github.com/shiyuan2he.git
     * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
     */
    void push(UrlSeed urlSeed);

    /**
     * @description <p>拉出url种子</p>
     * @return UrlSeed url种子
     * @author heshiyuan
     * @date 21/09/2017 11:27 AM
     * @email shiyuan4work@sina.com
     * @github https://github.com/shiyuan2he.git
     * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
     */
    UrlSeed poll();
}
