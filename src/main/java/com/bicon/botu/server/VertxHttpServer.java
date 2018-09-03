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

import java.util.List;

import com.bicon.botu.tools.InvokerUtil;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
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
public class VertxHttpServer implements Server{

	@Override
	public void doStart() {
		String packages = "com.bicon.botu.restfull";
	
		try {
			//List<String> classoflist = InvokerUtil.scanPackage(packages);
			List<String> routers =	InvokerUtil.getRouters(packages);
			Vertx vertx =	Vertx.vertx();
			Router router = Router.router(vertx);
			router.route().handler( new ApiInterceptor(routers));//检查api路由的正确性
			String str = "HTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFr,HTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFr,HTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和HTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFrHTTP/2是HTTP请求响应模型多帧协议。协议允许其他类型帧发送和接收。为了接收定制帧，应访在请求上应用customFr接收。为了接收定制帧，应访在请求上应用customFr";
			router.route().handler(routingContext ->{
				String uri = routingContext.request().path();
				//System.out.println("----------"+uri);
				HttpServerResponse httpServerResponse = 	routingContext.response();
				httpServerResponse.putHeader("Content-Type", "text/html");
				httpServerResponse.end(str);
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
