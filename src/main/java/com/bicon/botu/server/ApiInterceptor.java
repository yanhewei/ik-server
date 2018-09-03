/**  
 * 
 * @Title:  ApiInterceptor.java   
 * @Package com.bicon.botu.server   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 123774135@qq.com     
 * @date:   2018年9月3日 下午4:03:40   
 * @version V1.0 
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 
 */  
package com.bicon.botu.server;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Multimap;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**   
 * @ClassName:  ApiInterceptor   
 * @Description:扫描API如果路由里面不带有的话，就踢出去，不在进行下一步操作
 * @author: 123774135@qq.com 
 * @date:   2018年9月3日 下午4:03:40   
 *     
 * @Copyright: 2018 
 * 
 */
public class ApiInterceptor implements Handler<RoutingContext>{

	
	
	private List<String> routers ;
	
	
	
	
   public ApiInterceptor( List<String> routers) {
		this.routers = routers;
	
	}
	
	@Override
	public void handle(RoutingContext event) {
		String path = event.request().path();;
		System.out.println(path+"-----------------");
		System.out.println(routers.toString()+"++++++++++++++++++");
		if(this.routers.contains(path)) {
			System.out.println(path+"-----------------");
		}
		
	}

}
