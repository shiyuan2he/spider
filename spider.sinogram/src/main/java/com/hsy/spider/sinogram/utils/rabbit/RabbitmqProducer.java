package com.hsy.spider.sinogram.utils.rabbit;

import com.hsy.java.util.rabbitmq.RabbitmqHelper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

/**
 * @author heshiyuan
 * @description <p></p>
 * @path spider/com.hsy.spider.sinogram.utils.rabbit
 * @date 22/09/2017 3:42 PM
 * @github http://github.com/shiyuan2he
 * @email shiyuan4work@sina.com
 * Copyright (c) 2017 shiyuan4work@sina.com All rights reserved.
 * @price ¥5    微信：hewei1109
 */
public class RabbitmqProducer {
    private static final Logger _logger = LoggerFactory.getLogger(RabbitmqProducer.class);
    public static String QUEUE_NAME = null ;
    public static String QUEUE_HOST = null ;
    public static Integer QUEUE_PORT = 0 ;
    public static String QUEUE_USER_NAME = null ;
    public static String QUEUE_PASSWORD = null ;
    static{

        Properties prop = new Properties();
        FileInputStream in = null;
        String path = "/properties/rabbitmq.properties" ;
        try {
            path = RabbitmqProducer.class.getResource(path).getPath() ;
            in = new FileInputStream(path);
            prop.load(in);
            QUEUE_NAME = prop.getProperty("rabbitmq.queue.sinogram.name") ;
            QUEUE_HOST = prop.getProperty("rabbitmq.queue.host") ;
            QUEUE_PORT = Integer.parseInt(prop.getProperty("rabbitmq.queue.port")) ;
            QUEUE_USER_NAME = prop.getProperty("rabbitmq.queue.username") ;
            QUEUE_PASSWORD = prop.getProperty("rabbitmq.queue.password") ;
            _logger.info("rabbitmq连接信息{},{},{},{},{}",QUEUE_NAME,QUEUE_HOST,QUEUE_PORT,QUEUE_USER_NAME,QUEUE_PASSWORD);
            in.close();
        } catch (FileNotFoundException e) {
            _logger.error("去读{}配置文件发生异常，异常信息：{}",path,e.getMessage());
        } catch (IOException e1) {
            _logger.error("去读{}配置文件发生异常，异常信息：{}",path,e1.getMessage());
        }
    }

    /**
     * @description <p>
     *  注1：queueDeclare第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
     *     第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）、
     *     第四个参数为当所有消费者客户端连接断开时是否自动删除队列、第五个参数为队列的其他参数
     * 注2：basicPublish第一个参数为交换机名称、第二个参数为队列映射的路由key、第三个参数为消息的其他属性、第四个参数为发送信息的主体
     * </p>
     * @param
     * @author heshiyuan
     * @date 22/09/2017 3:08 PM
     * @email shiyuan4work@sina.com
     * @github https://github.com/shiyuan2he.git
     * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
     */
    public static void getProducer(Object obj){
        //创建一个通道
        Channel channel = null;
        try {
            _logger.info("正在连接rabbitmq服务器。。。");
            channel = RabbitmqHelper.getInstance(QUEUE_NAME,QUEUE_HOST,QUEUE_PORT,QUEUE_USER_NAME,QUEUE_PASSWORD)
                    .getConnection(false).createChannel();
            _logger.info("成功连接rabbitmq服务器。。。");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //发送消息到队列中
        try {
            channel.basicPublish("", QUEUE_NAME, null, obj.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //关闭通道和连接
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
