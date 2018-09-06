/**  
 * 
 * @Title:  VertxHttpServer.java   
 * @Package com.bicon.botu.server   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 123774135@qq.com     
 * @date:   2018年9月3日 下午3:23:41   
 * @version V1.0 
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 
 */  
package com.bicon.botu.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.bicon.botu.cache.CacheManager;
import com.bicon.botu.tools.Constans;
import com.bicon.botu.tools.InvokerUtil;
import com.bicon.botu.tools.TaskManager;
import com.bicon.botu.ui.FreemarkUtil;
import com.google.common.base.Strings;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**   
 * @ClassName:  VertxHttpServer   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 123774135@qq.com 
 * @date:   2018年9月3日 下午3:23:41   
 *     
 * @Copyright: 2018 
 * 
 */
public class VertxHttpServer implements Server{
	CacheManager cacheManager = CacheManager.instance();
	@Override
	public void doStart() {
		try {
			
			TaskManager.start();
			Vertx vertx =	Vertx.vertx();
			Router router = Router.router(vertx);
			//router.route().handler(CorsHandler.create("vertx\\.io").allowedMethod(HttpMethod.GET));
			router.route("/word").handler( new WordItemHander());
			router.route("/").handler( hander->{
				//记载主页
				HttpServerResponse httpServerResponse =	hander.response();
				httpServerResponse.putHeader("content-type", "text/html");
            
				
				String str = FreemarkUtil.loadIndex("index.ftl");
				//System.out.println(str);
				httpServerResponse.end(str,"utf-8");
				httpServerResponse.close();
			});
			
			router.route("/update").blockingHandler( hander->{
				//记载主页
				 
				HttpServerResponse httpServerResponse =	hander.response();
				httpServerResponse.putHeader("content-type", "text/html;charset=utf-8");
				HttpServerRequest request = hander.request();
				String ipname = request.getParam("ipname");
				String port = request.getParam("port");
				//String timeout = request.getParam("time");
				cacheManager.put(Constans.HOST_NAME, ipname);
				cacheManager.put(Constans.PORT_NAME, port);
				String result = null;
				if(!StringUtils.isBlank(ipname) && !StringUtils.isBlank(port)) {
					String uri = "http://"+ipname+":"+port+"/question_and_answer_index/_update_by_query?conflicts=proceed";
					cacheManager.put(Constans.URL_KEY, uri);
					result = uri + "已经放入内存，只等定时任务去完成作业";
				}else {
					result = "没有获得参数";
				}
				
				//String uri = "http://"+ipname+":"+port+"/question_and_answer_index/_update_by_query?conflicts=proceed";
				//System.out.println(uri);
				//String result = doexecute(uri,timeout);
				httpServerResponse.end(result,"utf-8");
				httpServerResponse.close();
			});
			vertx.createHttpServer().requestHandler(router::accept).listen(8082);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	
	
	@Override
	public void doStop() {
		
		
	}
	
	public static void main(String[] args) {
		new VertxHttpServer().doStart();
	}

}
