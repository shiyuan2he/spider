package com.hsy.spider.sinogram.resolver;

import com.hsy.spider.base.core.resolver.PageResolver;
import com.hsy.spider.base.model.Page;
import com.hsy.spider.sinogram.starter.SinogramSpider;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @description <p>汉子解析器</p>
 * @author heshiyuan
 * @date 21/09/2017 11:12 AM
 * @email shiyuan4work@sina.com
 * @github https://github.com/shiyuan2he.git
 * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
 */
public class SinogramPageResolver implements PageResolver {
    private final Logger _logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Page process(Page page) {
        _logger.info("【解析器】开始解析页面");
        //如果匹配文章详情页
        /*if (!Pattern.matches(SinogramSpider.url, page.getUrlSeed().getUrl())) {
            return page ;
        }*/

        Document html = page.getDocument();
        try {
            String pStr = html.select("p").text().trim();
            String aStr = html.select("a").text().trim();
            String apanStr = html.select("span").text().trim();
            String liStr = html.select("li").text().trim();
            StringBuffer sb = new StringBuffer() ;
            sb.append(html.title().trim())
                    .append(pStr==null?"":pStr)
                    .append(aStr==null?"":aStr)
                    .append(apanStr==null?"":apanStr)
                    .append(liStr==null?"":liStr)
                    ;
            if(StringUtils.isBlank(sb.toString())){
                return page;
            }
            //用来存放爬取到的信息，供之后存储！map类型的即可，可以自定义各种嵌套！
            Map<String, String> items = new HashMap<>();
            items.put("content",sb.toString());
            //放入items中，之后会自动保存（如果你自己实现了下载器，请自己操作它。如下我自定义了自己的下载器，并将它保存到了文本中！）！
            page.setItems(items);
        } catch (NullPointerException e) {
            _logger.info("该页面没有解析到相关东西！跳过");
        }

        return page;
    }

    /**
     * 这里进行了优先级的用法示范。
     */
    @Override
    public Page processNewUrlSeeds(Page page) {
        /*page.getNewUrlSeed().forEach(urlSeed -> {
            if (Pattern.matches("http://news.xjtu.edu.cn/zsjy.*htm", urlSeed.getUrl()) || Pattern.matches("http://news.xjtu.edu.cn/info/1006/.*htm", urlSeed.getUrl())) {
                urlSeed.setPriority(8);
            }
        });*/
        return page;
    }
}
