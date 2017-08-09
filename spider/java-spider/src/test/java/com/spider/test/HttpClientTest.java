package com.spider.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

/**
 * @description <p>HttpClient相关测试</p>
 * @author heshiyuan @date 2017年3月17日 上午10:54:16
 * @path: java-spider/com.spider.test/HttpClientTest.java
 * @price ￥：5元
 * @copyright 如有复制粘贴请通知本人或者捐赠，微信号：hewei1109
 * @email heshiyuan@chtwm.com
 * @callnumber 15910868535
 */
public class HttpClientTest {
	private static HttpClient httpClient = new HttpClient() ;
	private static String URL = "http://localhost:10000/htmz/" ;

	static{
		// 设置代理服务器
		//httpClient.getHostConfiguration().setProxy("192.168.1.111", 9527);
		//httpClient.getParams().setParameter(CredentialsProvider.PROVIDER,new My);
	}
	@Test
	public void testGetMethod() {
		GetMethod getMethod = new GetMethod(URL) ;
		try {
			int status = httpClient.executeMethod(getMethod);
			if(status == HttpStatus.SC_OK){ //200
				System.out.println(status);
				System.out.println(getMethod.getResponseBodyAsString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			getMethod.releaseConnection();//释放连接
		}
	}
	@Test
	public void testPostMethod() {
		PostMethod postMethod = new PostMethod(URL) ;
		//post传递参数
		NameValuePair[] param = new NameValuePair[2] ;
		param[0] = new NameValuePair("test1","test2") ;
		param[1] = new NameValuePair("test3","test4") ;
		postMethod.addParameters(param);
		try {
			int status = httpClient.executeMethod(postMethod);
			System.out.println(status);
			System.out.println(postMethod.getResponseBodyAsString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			postMethod.releaseConnection();//释放连接
		}
	}
}

