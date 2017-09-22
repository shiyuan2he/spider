package com.hsy.spider.sinogram.storager;
import com.hsy.spider.base.core.storager.Saver;
import com.hsy.spider.base.model.Page;

import java.util.Map;

public class SinogramSaveOfConsole implements Saver {

    @Override
    public Page save(Page page) {
        Map<Object, Object> map = page.getItems();
        map.forEach((k, v) -> System.out.println(v));
        return page;
    }
}
