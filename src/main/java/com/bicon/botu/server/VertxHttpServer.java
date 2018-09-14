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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bicon.botu.cache.CacheManager;
import com.bicon.botu.tools.Constans;
import com.bicon.botu.tools.PropertiesUtil;
import com.bicon.botu.tools.TaskManager;
import com.bicon.botu.ui.FreemarkUtil;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**   
 * @ClassName:  VertxHttpServer   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 123774135@qq.com 
 * @date:   2018年9月3日 下午3:23:41   
 *     
 * @Copyright: 2018 
 * 
 */
public class VertxHttpServer extends AbstractVerticle implements Server{
	private  Logger logger = LoggerFactory.getLogger(this.getClass()); 
	CacheManager cacheManager = CacheManager.instance();
	@Override
	public void doStart() {
		try {
			PropertiesUtil propertiesUtil =	new  PropertiesUtil("resource.properties",false);
			String http_port = propertiesUtil.getvalue("http_port","8082");
			
			DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", http_port));
			
			//logger.info("....................http服务开始启动..................");
			
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
					//也许有多个ip
					
					//String uri = "http://"+ipname+":"+port+"/question_and_answer_index/_update_by_query?conflicts=proceed";
					//cacheManager.put(Constans.URL_KEY, uri);
					result = "已经放入内存，只等定时任务去完成作业<a href='/'>返回</a>";
				}else {
					result = "没有获得参数";
				}
				httpServerResponse.end(result,"utf-8");
				httpServerResponse.close();
			});
			vertx.createHttpServer().requestHandler(router::accept).listen(8082);
			vertx.deployVerticle(VertxHttpServer.class.getName(), options, hander->{
				String result =  hander.result();
				boolean issuceede = hander.succeeded();
				boolean isfailed = hander.failed();
				if(issuceede) {
					logger.info("....................http服务部署完成,部署结果:"+result);
					TaskManager.start();
				}
				
				
			});
			//logger.info("....................http服务启动完成..................");
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
