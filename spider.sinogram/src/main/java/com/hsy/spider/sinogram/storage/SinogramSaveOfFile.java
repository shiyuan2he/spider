package com.hsy.spider.sinogram.storage;
import com.hsy.spider.base.core.saver.Saver;
import com.hsy.spider.base.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class SinogramSaveOfFile implements Saver {
    private final Logger _logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Page save(Page page) {

        //结果不为空就存储！
        if (page.getItems().size() != 0) {
            _logger.info("【文件存储器】从页面抓取到汉字，正在保存。。。");
            try {
                String fileRoot = System.getProperty("user.home") + "/test/";
                String path = fileRoot + (new Date().getTime()) + ".txt" ;
                _logger.info("【文件存储器】存储文件将存储在{}文件中",path);
                File file = new File(path);
                File fileParent = file.getParentFile();
                if (!fileParent.exists()) {
                    fileParent.mkdirs();
                }
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                page.getItems().forEach((key, value) -> {
                    try {
                        fileWriter.append(key.toString() + "\n").append(value.toString() + "\n").append("\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            _logger.info("【文件存储器】page里面item没有数据。。。");
        }
        return page;
    }
}
